package com.cats.attendanceservice.Util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static List<LocalDate> getUpcomingWeekends() {
        List<LocalDate> weekends = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekends.add(date);
            }
        }
        return weekends;
    }

}
