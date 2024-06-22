package com.cats.attendanceservice.service;

import com.cats.attendanceservice.Util.DateUtils;
import com.cats.attendanceservice.dto.AttendanceReqDto;
import com.cats.attendanceservice.events.ListEmpByEmpIdEvent;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.repository.AttendanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class AttendanceServiceImp implements AttendanceService  {
    private final AttendanceRepo attendanceRepo;
    private final ApiService apiService;
    @Value("${file.attendance.path}")
    private String attendanceText;
    @Value("${file.attendanceFinished.path}")
    private String attendanceFinished;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy h:mm a", Locale.ENGLISH);
    @Override
    public List<Attendance> getListAttendance() {
        return attendanceRepo.findAll();
    }

    @Override
    public List<Attendance> getListAttendanceOrderByDate() {
        return attendanceRepo.findAllOrderByDateIn();
    }

    @Override
    public List<Attendance> getListAttendanceByEmId(Long emId) {
        return  attendanceRepo.findByEmId(emId);
    }

    @Override
    public List<Attendance> findByDateInBetweenAndEmId(LocalDate dateIn, LocalDate dateIn2, Long emId) {
        return attendanceRepo.findByDateInBetweenAndEmId(dateIn,dateIn2,emId);
    }

    @Override
    public List<Attendance> getListAttendanceForManger(Long emId) {
        Collection<Long> emIDs = apiService.getEmployeeByUnderMangerOnlyEmId(emId);
        List<Attendance> attendanceList = attendanceRepo.findByEmIdIn(emIDs);
        applicationEventPublisher.publishEvent(new ListEmpByEmpIdEvent(this, emIDs));
        return attendanceList;
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
        Attendance attendance = new Attendance();
        attendance.setEmId(attendanceReqDto.getEmId());
        attendance.setTimeIn(attendanceReqDto.getTimeIn());
        attendance.setTimeOut(attendanceReqDto.getTimeOut());
        attendance.setDateIn(attendanceReqDto.getDateIn());
        attendance.setRemark(attendanceReqDto.getRemark());
        return attendanceRepo.save(attendance);
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
            System.out.println("Processing File: " + file.getName());
            Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
            Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");

            // Use a map to store the last timeIn for each emId
            Map<Long, LocalTime> latestTimeInMap = new HashMap<>();

            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                Matcher acNoMatcher = acNoPattern.matcher(line);
                System.out.println("line1");
                if (matcher.find() && acNoMatcher.find()) {
                    String time = matcher.group();
                    String acNo = acNoMatcher.group();
                    System.out.println("line2");
                    System.out.println(time);
                    Date date = dateFormat.parse(time);
                    System.out.println(date);
                    LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                   // LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                    Long emId = Long.valueOf(acNo);

                    // Keep track of the latest timeIn for each emId
                    latestTimeInMap.put(emId, localTime);
                }
            }

            // Process and save the latest timeIn for each emId
            for (Map.Entry<Long, LocalTime> entry : latestTimeInMap.entrySet()) {
                Long emId = entry.getKey();
                LocalTime latestTimeIn = entry.getValue();
                System.out.println("line3");
                // Retrieve the last timeIn for the given emId
                Optional<Attendance> lastAttendanceOpt = attendanceRepo.findLastTimeInByEmId(emId);
                if (lastAttendanceOpt.isPresent()) {
                    LocalTime lastTimeIn = lastAttendanceOpt.get().getTimeIn();
                    if (!latestTimeIn.isAfter(lastTimeIn)) {
                        System.out.println("Duplicate or out-of-order entry found for Ac-No: " + emId + " and Time In: " + latestTimeIn);
                        continue;
                    }
                }
                System.out.println("Save");
                Attendance  attendance = new Attendance();
                attendance.setDateIn(LocalDate.now());
                attendance.setEmId(emId);
                attendance.setTimeIn(latestTimeIn);
                attendanceRepo.save(attendance);

            }

            moveProcessedFile(file);
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName());
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Error parsing date in file: " + file.getName());
            e.printStackTrace();
        }
    }
    private void processTimeOutFile(File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            System.out.println("Processing File: " + file.getName());
            Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
            Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");

            // Use a map to store the last timeOut for each emId
            Map<Long, LocalTime> latestTimeOutMap = new HashMap<>();

            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                Matcher acNoMatcher = acNoPattern.matcher(line);
                if (matcher.find() && acNoMatcher.find()) {
                    String time = matcher.group();
                    String acNo = acNoMatcher.group();
                    Date date = dateFormat.parse(time);
                    LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();

                    Long emId = Long.valueOf(acNo);

                    // Keep track of the latest timeOut for each emId
                    latestTimeOutMap.put(emId, localTime);
                }
            }

            // Process and save the latest timeOut for each emId
            for (Map.Entry<Long, LocalTime> entry : latestTimeOutMap.entrySet()) {
                Long emId = entry.getKey();
                LocalTime latestTimeOut = entry.getValue();
                System.out.println(latestTimeOut);
                // Retrieve the last timeOut for the given emId
                Optional<Attendance> lastAttendanceOpt = attendanceRepo.findLastTimeOutByEmId(emId);
                if (lastAttendanceOpt.isPresent()) {
                    if (lastAttendanceOpt.get().getTimeOut() != null){
                        LocalTime lastTimeOut = lastAttendanceOpt.get().getTimeOut();
                        if (!latestTimeOut.isAfter(lastTimeOut)) {
                            System.out.println("Duplicate or out-of-order entry found for Ac-No: " + emId + " and Time Out: " + latestTimeOut);
                            continue;
                        }
                    }

                    // Retrieve the attendance record for the given emId and dateIn
                    LocalDate localDate = lastAttendanceOpt.get().getDateIn(); // Assuming the current date is used
                    Attendance attendance = getAttendanceByEmIdAndDateIn(localDate,emId);
                    if (attendance != null) {
                        attendance.setTimeOut(latestTimeOut);
                        attendanceRepo.save(attendance);
                        System.out.println("save");
                        //moveProcessedFile(file);
                    } else {
                        System.err.println("No matching attendance record found for Ac-No: " + emId + " on Date: " + localDate);
                    }
                }else {
                    throw  new IllegalArgumentException("Is empty");
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
