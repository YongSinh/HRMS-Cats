package com.cats.attendanceservice.service;

import com.cats.attendanceservice.dto.AttendanceReqDto;
import com.cats.attendanceservice.model.Attendance;
import com.cats.attendanceservice.repository.AttendanceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
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

    @Value("${file.attendance.path}")
    private String attendanceText;
    @Value("${file.attendanceFinished.path}")
    private String attendanceFinished;
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
        return null;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
        Pattern pattern = Pattern.compile("\\b(\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M)\\b.*\\b(\\d{4})\\b");

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            System.out.println("Processing File: " + file.getName());

            // Use a map to store the last timeIn for each emId
            Map<Long, LocalTime> latestTimeInMap = new HashMap<>();

            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String time = matcher.group(1);
                    String acNo = matcher.group(2);

                    Date date = dateFormat.parse(time);
                    LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                    LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                    Long emId = Long.valueOf(acNo);

                    // Keep track of the latest timeIn for each emId
                    latestTimeInMap.put(emId, localTime);
                }
            }

            // Process and save the latest timeIn for each emId
            for (Map.Entry<Long, LocalTime> entry : latestTimeInMap.entrySet()) {
                Long emId = entry.getKey();
                LocalTime latestTimeIn = entry.getValue();

                // Retrieve the last timeIn for the given emId
                Optional<Attendance> lastAttendanceOpt = attendanceRepo.findLastTimeInByEmId(emId);
                if (lastAttendanceOpt.isPresent()) {
                    LocalTime lastTimeIn = lastAttendanceOpt.get().getTimeIn();
                    if (!latestTimeIn.isAfter(lastTimeIn)) {
                        System.out.println("Duplicate or out-of-order entry found for Ac-No: " + emId + " and Time In: " + latestTimeIn);
                        continue;
                    }
                }

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
        Pattern pattern = Pattern.compile("\\b(\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M)\\b.*\\b(\\d{4})\\b");

        try {
            List<String> lines = Files.readAllLines(file.toPath());
            System.out.println("Processing File: " + file.getName());

            // Use a map to store the last timeOut for each emId
            Map<Long, LocalTime> latestTimeOutMap = new HashMap<>();

            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String time = matcher.group(1);
                    String acNo = matcher.group(2);

                    Date date = dateFormat.parse(time);
                    LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                    LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

                    Long emId = Long.valueOf(acNo);

                    // Keep track of the latest timeOut for each emId
                    latestTimeOutMap.put(emId, localTime);
                }
            }

            // Process and save the latest timeOut for each emId
            for (Map.Entry<Long, LocalTime> entry : latestTimeOutMap.entrySet()) {
                String emId = String.valueOf(entry.getKey());
                LocalTime latestTimeOut = entry.getValue();

                // Retrieve the last timeOut for the given emId
                Optional<Attendance> lastAttendanceOpt = attendanceRepo.findLastTimeOutByEmId(Long.valueOf(emId));
                if (lastAttendanceOpt.isPresent()) {
                    LocalTime lastTimeOut = lastAttendanceOpt.get().getTimeOut();
                    if (!latestTimeOut.isAfter(lastTimeOut)) {
                        System.out.println("Duplicate or out-of-order entry found for Ac-No: " + emId + " and Time Out: " + latestTimeOut);
                        continue;
                    }
                }

                // Retrieve the attendance record for the given emId and dateIn
                LocalDate localDate = LocalDate.now(); // Assuming the current date is used
                Attendance attendance = attendanceRepo.findByEmIdAndDateIn(emId, String.valueOf(localDate));
                if (attendance != null) {
                    attendance.setTimeOut(latestTimeOut);
                    attendanceRepo.save(attendance);
                } else {
                    System.err.println("No matching attendance record found for Ac-No: " + emId + " on Date: " + localDate);
                }
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
                    processTimeInFile(file);
                }
            }
            return "Attendance time out have been save to database.";
        } else {
            return "Attendance file is empty or does not exist.";
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
    public Attendance getAttendanceByEmIdAndDateIn(String date, String emId) {
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
