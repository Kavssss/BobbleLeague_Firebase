package util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    
    public static String getDateTimeFormated() {
        return removeHorarioVeraoIndevido(
                LocalDateTime.now()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"));
    }
    
    public static String getDifferenceTime(LocalDateTime start) {
        LocalDateTime end = removeHorarioVeraoIndevido(LocalDateTime.now());
        return getDifferenceTime(start, end);
    }
    
    public static String getDifferenceTime(LocalDateTime start, LocalDateTime end) {
        return LocalTime.MIDNIGHT.plus(Duration.between(start, end))
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public static String getDifferenceTime(String start, String end) {
        LocalTime time1 = LocalTime.parse(start);
        LocalTime time2 = LocalTime.parse(end);
        return LocalTime.MIDNIGHT.plus(Duration.between(time1, time2))
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    public static LocalDateTime removeHorarioVeraoIndevido(LocalDateTime dateTime) {
        return ZoneId.systemDefault().getRules().isDaylightSavings(ZonedDateTime.now(ZoneId.systemDefault()).toInstant()) ?
                dateTime.minusHours(1) : dateTime;
    }
    
    public static String getTimeFormatedPlacar(String time) {
        String[] aux = time.split(":");
        Integer hour = Integer.valueOf(aux[0]);
        Integer min = Integer.valueOf(aux[1]);
        Integer sec = Integer.valueOf(aux[2]);
        if (hour > 0)
            min += 60 * hour;
        return String.valueOf(min).concat("\'").concat(String.valueOf(sec).concat("\""));
    }
    
}
