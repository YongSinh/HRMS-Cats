package com.cats.attendanceservice.service;

import com.cats.attendanceservice.Util.DateUtils;
import com.cats.attendanceservice.dto.*;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.repository.AttendanceRepo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class AttendanceServiceImp implements AttendanceService {
    private final AttendanceRepo attendanceRepo;
    private final ApiService apiService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.ENGLISH);
    @Value("${file.attendance.path}")
    private String attendanceText;
    @Value("${file.attendanceFinished.path}")
    private String attendanceFinished;

    @Override
    public List<AttendanceRepDto> getListAttendance() {
        return mapper.AttendanceRepToAttendanceRepDtos(attendanceRepo.findAllByOrderByIdDesc(), apiService);
    }

    @Override
    public List<AttendanceRepDto> getListAttendanceOrderByDate() {
        return mapper.AttendanceRepToAttendanceRepDtos(attendanceRepo.findAllOrderByDateIn(), apiService);
    }

    @Override
    public List<AttendanceRepDto> getListAttendanceByEmId(Long emId) {
        return mapper.AttendanceRepToAttendanceRepDtos(attendanceRepo.findByEmId(emId), apiService);
    }

    @Override
    public List<AttendanceRepDto> findByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId) {
        return mapper.AttendanceRepToAttendanceRepDtos(attendanceRepo.findByDateInBetweenAndEmId(dateIn, dateIn2, emId), apiService);
    }

    @Override
    public List<AttendanceRepDto> getListAttendanceForManger(Long emId) {
        Collection<Long> emIDs = apiService.getEmployeeByUnderMangerOnlyEmId(emId);
        return mapper.AttendanceRepToAttendanceRepDtos(attendanceRepo.findByEmIdIn(emIDs), apiService);
    }

    @Override
    public Attendance update(Long Id, AttendanceReqDto attendanceReqDto) {
        Attendance update = getAttendanceById(Id);
        update.setEmId(attendanceReqDto.getEmId());
        update.setTimeIn(attendanceReqDto.getTimeIn());
        update.setTimeOut(attendanceReqDto.getTimeOut());
        update.setRemark(attendanceReqDto.getRemark());
        update.setDateIn(attendanceReqDto.getDateIn());
        attendanceRepo.save(update);
        return update;
    }

    @Override
    public Attendance create(AttendanceReqDto attendanceReqDto) {
        Optional<Attendance> existingAttendance = attendanceRepo.findByEmIdAndDateIn(
                attendanceReqDto.getEmId(), attendanceReqDto.getDateIn());

        if (existingAttendance.isPresent()) {
            // Handle the case where attendance already exists for this date
            throw new IllegalStateException("Employee has already timed in or out for this day.");
        }

        Attendance attendance = new Attendance();
        attendance.setEmId(attendanceReqDto.getEmId());
        attendance.setTimeIn(attendanceReqDto.getTimeIn());
        attendance.setTimeOut(attendanceReqDto.getTimeOut());
        attendance.setDateIn(attendanceReqDto.getDateIn());
        attendance.setRemark(attendanceReqDto.getRemark());
        return attendanceRepo.save(attendance);
    }

    @Override
    public Attendance timeOut(Long Id, TimeOutReqDto attendanceReqDto) {
        Attendance update = getAttendanceById(Id);

        // If timeOut is being updated, ensure it's only updated once
        if (update.getTimeOut() != null && attendanceReqDto.getTimeOut() != null) {
            throw new IllegalStateException("Time out already recorded for this day.");
        }

        update.setTimeOut(attendanceReqDto.getTimeOut());
        update.setDateOut(attendanceReqDto.getDateOut());
        update.setRemark(attendanceReqDto.getRemark());
        attendanceRepo.save(update);
        return update;
    }

    @Override
    public Attendance timeIn(TimeInReqDto attendanceReqDto) {
        List<Attendance> existingAttendance = attendanceRepo.preventDuplicates(
                attendanceReqDto.getEmId(), attendanceReqDto.getDateIn());
//        if (!existingAttendance.isEmpty()) {
//            // Handle the case where attendance already exists for this date
//            throw new IllegalStateException("Attendance record already exists for employee ID "
//                    + attendanceReqDto.getEmId() + " on " + attendanceReqDto.getDateIn());
//        }
//        Attendance attendance = new Attendance();
//        attendance.setEmId(attendanceReqDto.getEmId());
//        attendance.setTimeIn(attendanceReqDto.getTimeIn());
//        attendance.setDateIn(attendanceReqDto.getDateIn());
//        attendance.setRemark(attendanceReqDto.getRemark());
//        attendance.setOnLeave(false);
//        attendanceRepo.deleteDuplicates(attendanceReqDto.getEmId(), attendanceReqDto.getDateIn());
//        return attendanceRepo.save(attendance);
        if (attendanceReqDto.getTimeIn() == null) {
            throw new IllegalStateException("TimeIn cannot be null or empty for employee ID "
                    + attendanceReqDto.getEmId() + " on " + attendanceReqDto.getDateIn());
        }

        if (!existingAttendance.isEmpty()) {
            // Update existing attendance if found
            Attendance attendance = existingAttendance.get(0);  // Assuming only one record for each employee per date
            attendance.setTimeIn(attendanceReqDto.getTimeIn());  // Set the TimeIn value
            attendance.setRemark(attendanceReqDto.getRemark());  // Update Remark
            attendance.setOnLeave(false);  // Optionally update onLeave if necessary
            return attendanceRepo.save(attendance);  // Save the updated attendance record
        } else {
            // Handle the case where no attendance record exists for this date
            Attendance attendance = new Attendance();
            attendance.setEmId(attendanceReqDto.getEmId());
            attendance.setTimeIn(attendanceReqDto.getTimeIn());
            attendance.setDateIn(attendanceReqDto.getDateIn());
            attendance.setRemark(attendanceReqDto.getRemark());
            attendance.setOnLeave(false);
            return attendanceRepo.save(attendance);  // Create new attendance record
        }
    }

    @Override
    public List<ReportAttendanceDto> getReportByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId) throws IOException {
        List<Attendance> attendances = attendanceRepo.findByDateInBetweenAndEmId(dateIn, dateIn2, emId);
        return reportAttendanceDtoList(attendances, emId);
    }

    public List<ReportAttendanceDto> reportAttendanceDtoList(List<Attendance> attendanceList, Long emId) throws IOException {
        JsonNode employeeInfo = apiService.getEmployeeInFoByEmId(emId);
        if (employeeInfo == null) {
            throw new IOException("Employee information not found for ID: " + emId);
        }

        String employeeName = employeeInfo.get("fullName").asText();
        return attendanceList.stream()
                .map(attendance -> {
                    ReportAttendanceDto dto = new ReportAttendanceDto();
                    dto.setName(employeeName);
                    dto.setDateIn(attendance.getDateIn().toString());

                    // Check if timeIn is null
                    if (attendance.getTimeIn() != null) {
                        dto.setTimeIn(attendance.getTimeIn().toString());
                    } else {
                        dto.setTimeIn("N/A"); // or any default value you prefer
                    }

                    dto.setDateOut(attendance.getDateOut() != null ? attendance.getDateOut().toString() : "N/A");

                    // Check if timeOut is null
                    if (attendance.getTimeOut() != null) {
                        dto.setTimeOut(attendance.getTimeOut().toString());
                    } else {
                        dto.setTimeOut("N/A"); // or any default value you prefer
                    }

                    dto.setEmId(emId);
                    dto.setRemark(attendance.getRemark());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public String manualAsyncTimeIn() {
        // Create a File object representing the directory
        File directory = new File(attendanceText);
        // List all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    processTimeInFile(file);
                }
            }
            return "Attendance time in have been save to database.";
        } else {
            return "Attendance is empty or does not exist.";
        }
    }

    private void processTimeInFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

            // Regex patterns
            Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
            Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");
            Pattern dateInPattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");

            // Map to store the latest Time and Date for each emId
            Map<Long, TimeAndDate> latestTimeInMap = new HashMap<>();

            for (String line : lines) {
                Matcher timeMatcher = pattern.matcher(line);
                Matcher acNoMatcher = acNoPattern.matcher(line);
                Matcher dateMatcher = dateInPattern.matcher(line);

                if (timeMatcher.find() && acNoMatcher.find() && dateMatcher.find()) {
                    String time = timeMatcher.group();
                    String acNo = acNoMatcher.group();
                    Date date = dateFormat.parse(time);
                    String dateString = dateMatcher.group();

                    LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                    LocalDate localDate = LocalDate.parse(dateString, dateOnlyFormatter);

                    Long emId = Long.valueOf(acNo);

                    // Update the map with the latest time and date for each emId
                    latestTimeInMap.put(emId, new TimeAndDate(localTime, localDate));
                }
            }

            // Process and save the latest timeIn for each emId
            for (Map.Entry<Long, TimeAndDate> entry : latestTimeInMap.entrySet()) {
                Long emId = entry.getKey();
                LocalTime latestTimeIn = entry.getValue().getLocalTime();
                LocalDate latestDateIn = entry.getValue().getLocalDate();

                // Fetch the last recorded attendance for this employee ID and date
                Optional<Attendance> lastAttendanceOpt = attendanceRepo.findLastTimeInByEmId(emId, latestDateIn);
                if (lastAttendanceOpt.isPresent()) {
                    LocalTime lastTimeIn = lastAttendanceOpt.get().getTimeIn();
                    LocalDate lastDateIn = lastAttendanceOpt.get().getDateIn();
                    // Check date and time conditions
                    if (latestDateIn.isAfter(lastDateIn)) {
                        System.out.println("Newer date entry found for Ac-No: " + emId);
                    } else if (latestDateIn.isEqual(lastDateIn) && latestTimeIn.isAfter(lastTimeIn)) {
                        System.out.println("Newer time entry found for Ac-No: " + emId);
                    } else {
                        System.out.println("Duplicate or out-of-order entry for Ac-No: " + emId);
                        continue;
                    }
                }

                // Save the latest attendance entry
                Attendance attendance = new Attendance();
                attendance.setDateIn(latestDateIn);
                attendance.setEmId(emId);
                attendance.setTimeIn(latestTimeIn);
                attendanceRepo.save(attendance);

            }
            moveProcessedFile(file);
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName());
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date/time in file: " + file.getName());
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void processTimeOutFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            DateTimeFormatter dateOnlyFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            // Regex patterns
            Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
            Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");
            Pattern dateInPattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4}");

            // Map to store the latest Time and Date for each emId
            Map<Long, TimeAndDate> latestTimeInMap = new HashMap<>();

            for (String line : lines) {
                Matcher timeMatcher = pattern.matcher(line);
                Matcher acNoMatcher = acNoPattern.matcher(line);
                Matcher dateMatcher = dateInPattern.matcher(line);

                if (timeMatcher.find() && acNoMatcher.find() && dateMatcher.find()) {
                    String time = timeMatcher.group();
                    String acNo = acNoMatcher.group();
                    Date date = dateFormat.parse(time);
                    String dateString = dateMatcher.group();

                    LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                    LocalDate localDate = LocalDate.parse(dateString, dateOnlyFormatter);

                    Long emId = Long.valueOf(acNo);

                    // Update the map with the latest time and date for each emId
                    latestTimeInMap.put(emId, new TimeAndDate(localTime, localDate));
                }
            }

            // Process and save the latest timeOut for each emId
            for (Map.Entry<Long, TimeAndDate> entry : latestTimeInMap.entrySet()) {
                Long emId = entry.getKey();
                LocalTime latestTimeOut = entry.getValue().getLocalTime();
                LocalDate latestDateIn = entry.getValue().getLocalDate();
                // Retrieve the last timeOut for the given emId
                Optional<Attendance> lastAttendanceOpt = attendanceRepo.findLastTimeOutByEmId(emId, latestDateIn);
                if (lastAttendanceOpt.isPresent()) {
                    if (lastAttendanceOpt.get().getTimeOut() != null) {
                        LocalTime lastTimeOut = lastAttendanceOpt.get().getTimeOut();
                        if (!latestTimeOut.isAfter(lastTimeOut)) {
                            System.out.println("Duplicate or out-of-order entry found for Ac-No: " + emId + " and Time Out: " + latestTimeOut);
                            continue;
                        }
                    }

                    // Retrieve the attendance record for the given emId and dateIn
                    LocalDate localDate = lastAttendanceOpt.get().getDateIn(); // Assuming the current date is used
                    Attendance attendance = getAttendanceByEmIdAndDateIn(localDate, emId);
                    if (attendance != null) {
                        attendance.setDateOut(localDate);
                        attendance.setTimeOut(latestTimeOut);
                        attendanceRepo.save(attendance);
                        //moveProcessedFile(file);
                    } else {
                        System.err.println("No matching attendance record found for Ac-No: " + emId + " on Date: " + localDate);
                    }
                } else {
                    throw new IllegalArgumentException("Is empty");
                }

            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName());
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Error parsing date in file: " + file.getName());
            e.printStackTrace();
        }
    }

    private void moveProcessedFile(File file) throws IOException {
        Path sourcePath = file.toPath();
        Path destinationPath = Paths.get(attendanceFinished, file.getName());
        Files.move(sourcePath, destinationPath);
    }

    @Override
    public String manualAsyncTimeOut() {
        // Create a File object representing the directory
        File directory = new File(attendanceText);
        // List all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    processTimeOutFile(file);
                }
            }
            return "Attendance time out have been save to database.";
        } else {
            return "Attendance file is empty or does not exist.";
        }
    }

    @Scheduled(cron = "0 0 22 * * FRI")
    @Override
    public void createWeekendAttendance() throws IOException {
        List<LocalDate> weekends = DateUtils.getUpcomingWeekends();
        Collection<Long> emIds = apiService.getListAllEmployeeOnlyEmId();
        for (Long emId : emIds) {
            for (LocalDate date : weekends) {
                Attendance attendance = new Attendance();
                attendance.setEmId(emId);
                attendance.setDateIn(date);
                attendance.setTimeIn(null); // example time, set according to your requirements
                attendance.setTimeOut(null); // example time, set according to your requirements

                if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    attendance.setRemark("Saturday");
                } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    attendance.setRemark("Sunday");
                }

                attendanceRepo.save(attendance);
            }
        }
    }

    @Override
    public List<Attendance> getAttendanceByDepartmentOrPosition(Collection<Long> emId) {
        return attendanceRepo.findByEmIdIn(emId);
    }


    @Override
    public Attendance getAttendanceById(Long id) {
        return attendanceRepo.findById(id).orElseThrow(() ->
                new IllegalArgumentException(
                        "attendance  with id: " + id + " could not be found"));
    }

    @Override
    public Attendance getAttendanceByEmIdAndDateIn(LocalDate date, Long emId) {
        return attendanceRepo.findByEmIdAndDateIn(date, emId);
    }

    @Scheduled(cron = "0 9 * * * *")
    public void autoAsyncTimeIn() {
        // Create a File object representing the directory
        File directory = new File(attendanceText);
        // List all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    processTimeInFile(file);
                }
            }
        } else {
            System.err.println("Directory is empty or does not exist.");
        }
    }


    @Scheduled(cron = "0 18 * * * *")
    public void autoAsyncTimeOut() {
        // Create a File object representing the directory
        File directory = new File(attendanceText);
        // List all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    processTimeOutFile(file);
                }
            }
        } else {
            System.err.println("Directory is empty or does not exist.");
        }
    }
}
