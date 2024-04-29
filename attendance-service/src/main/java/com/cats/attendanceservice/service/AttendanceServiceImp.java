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
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
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
            // Iterate over each file in the directory
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    // Read the text file
                    try {
                        // Using java.nio.file.Files to read file content
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                        String content = new String(Files.readAllBytes(file.toPath()));
                        List<String> storeList = Files.readAllLines(file.toPath());
                        System.out.println("File Name: " + file.getName());
                        Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
                        Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");

                        for (String data : storeList) {
                            // Create a Matcher object to find the pattern in the string
                            Attendance attendance = new Attendance();
                            Matcher matcher = pattern.matcher(data);
                            Matcher acNoMatcher = acNoPattern.matcher(data);
                            // Find and print the time
                            if (matcher.find() && acNoMatcher.find()) {
                                String time = matcher.group();
                                String emId = acNoMatcher.group();
                                Date date = dateFormat.parse(time);
                                LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                                LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                                attendance.setDateIn(localDate);
                                attendance.setTimeIn(localTime);
                                attendance.setEmId(Long.valueOf(emId));
                                attendanceRepo.save(attendance);
                            }

                        }
                        Path sourcePath = file.toPath();
                        Path destinationPath = Paths.get(attendanceFinished, file.getName());
                        Files.move(sourcePath, destinationPath);

                    } catch (IOException e) {
                        System.err.println("Error reading file: " + file.getName());
                        e.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return "Attendance time in have been save to database.";
        } else {
            return "Attendance is empty or does not exist.";
        }
    }

    @Override
    public String manualAsyncTimeOut() {
        // Create a File object representing the directory
        File directory = new File(attendanceText);
        // List all files in the directory
        File[] files = directory.listFiles();
        if (files != null) {
            // Iterate over each file in the directory
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    // Read the text file
                    try {
                        // Using java.nio.file.Files to read file content
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                        String content = new String(Files.readAllBytes(file.toPath()));
                        List<String> storeList = Files.readAllLines(file.toPath());
                        Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
                        Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");

                        for (String data : storeList) {
                            // Create a Matcher object to find the pattern in the string
                            Matcher matcher = pattern.matcher(data);
                            Matcher acNoMatcher = acNoPattern.matcher(data);
                            // Find and print the time
                            if (matcher.find() && acNoMatcher.find()) {
                                String time = matcher.group();
                                String emId = acNoMatcher.group();
                                Date date = dateFormat.parse(time);
                                LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                                LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                                Attendance attendance = getAttendanceByEmIdAndDateIn(localDate.toString(),emId);
                                attendance.setDateIn(localDate);
                                attendance.setTimeOut(localTime);
                                attendance.setEmId(Long.valueOf(emId));
                                attendanceRepo.save(attendance);
                            }

                        }
                        Path sourcePath = file.toPath();
                        Path destinationPath = Paths.get(attendanceFinished, file.getName());
                        Files.move(sourcePath, destinationPath);

                    } catch (IOException e) {
                        System.err.println("Error reading file: " + file.getName());
                        e.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return "Attendance time out have been save to database.";
        } else {
            return "Attendance file is empty or does not exist.";
        }
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
            // Iterate over each file in the directory
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    // Read the text file
                    try {
                        // Using java.nio.file.Files to read file content
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                        String content = new String(Files.readAllBytes(file.toPath()));
                        List<String> storeList = Files.readAllLines(file.toPath());
                        System.out.println("File Name: " + file.getName());
                        Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
                        Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");

                        for (String data : storeList) {
                            // Create a Matcher object to find the pattern in the string
                            Attendance attendance = new Attendance();
                            Matcher matcher = pattern.matcher(data);
                            Matcher acNoMatcher = acNoPattern.matcher(data);
                            // Find and print the time
                            if (matcher.find() && acNoMatcher.find()) {
                                String time = matcher.group();
                                System.out.println("Time In: " + time);
                                String acNo = acNoMatcher.group();
                                System.out.println("Ac-No: " + acNo);
                                Date date = dateFormat.parse(time);
                                LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                                LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                                attendance.setDateIn(localDate);
                                attendance.setTimeIn(localTime);
                                attendance.setTimeOut(localTime);
                                attendance.setEmId(Long.valueOf(acNo));
                                attendanceRepo.save(attendance);

                            }

                        }
                        Path sourcePath = file.toPath();
                        Path destinationPath = Paths.get(attendanceFinished, file.getName());
                        Files.move(sourcePath, destinationPath);
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + file.getName());
                        e.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
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
            // Iterate over each file in the directory
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                    // Read the text file
                    try {
                        // Using java.nio.file.Files to read file content
                        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy h:mm a");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                        String content = new String(Files.readAllBytes(file.toPath()));
                        List<String> storeList = Files.readAllLines(file.toPath());
                        Pattern pattern = Pattern.compile("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{2} [AP]M");
                        Pattern acNoPattern = Pattern.compile("\\b\\d{4}\\b");

                        for (String data : storeList) {
                            // Create a Matcher object to find the pattern in the string
                            Matcher matcher = pattern.matcher(data);
                            Matcher acNoMatcher = acNoPattern.matcher(data);
                            // Find and print the time
                            if (matcher.find() && acNoMatcher.find()) {
                                String time = matcher.group();
                                String emId = acNoMatcher.group();
                                Date date = dateFormat.parse(time);
                                LocalTime localTime = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalTime();
                                LocalDate localDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                                Attendance attendance = getAttendanceByEmIdAndDateIn(localDate.toString(),emId);
                                attendance.setDateIn(localDate);
                                attendance.setTimeOut(localTime);
                                attendance.setEmId(Long.valueOf(emId));
                                attendanceRepo.save(attendance);
                            }

                        }
                        Path sourcePath = file.toPath();
                        Path destinationPath = Paths.get(attendanceFinished, file.getName());
                        Files.move(sourcePath, destinationPath);
                    } catch (IOException e) {
                        System.err.println("Error reading file: " + file.getName());
                        e.printStackTrace();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } else {
            System.err.println("Directory is empty or does not exist.");
        }
    }
}
