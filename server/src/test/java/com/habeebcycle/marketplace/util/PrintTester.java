package com.habeebcycle.marketplace.util;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class PrintTester {
    public static void main(String[] args) {
        String s = "1,3,5,7,9";
        List<String> strList = Arrays.asList(s.split(",").clone());
        List<Long> longStream = strList.stream().map(Long::parseLong).collect(Collectors.toList());
        System.out.println(longStream);

        longStream.add(null);
        StringBuilder listStr = new StringBuilder();
        for (Long aLong : longStream) {
            if(aLong != null)
                listStr.append(aLong).append(",");
        }
        System.out.println(listStr.toString());

        strList = Arrays.asList(listStr.toString().split(",").clone());
        longStream = strList.stream().map(Long::parseLong).collect(Collectors.toList());
        System.out.println(longStream);

        System.out.println(longStream.toString());

        TimeZone utc = TimeZone.getTimeZone("UTC");
        System.out.println(LocalDateTime.now());//LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalDateTime.now().toLocalTime()).
        System.out.println(LocalDateTime.now(utc.toZoneId()).plusHours(11));
        System.out.println(Instant.now().atZone(utc.toZoneId()));

        ZoneId australia = ZoneId.of("Australia/Sydney");
        LocalDateTime localDateAndTime = LocalDateTime.now();

        ZonedDateTime dateAndTimeInSydney = ZonedDateTime.of(localDateAndTime, australia );
        System.out.println("Current date and time in a particular timezone (Sydney) : " + dateAndTimeInSydney);

        ZonedDateTime utcDate = dateAndTimeInSydney.withZoneSameInstant(ZoneOffset.UTC);
        System.out.println("Current date and time in UTC : " + utcDate);

        ZonedDateTime convertFromUTC = ZonedDateTime.of(utcDate.toLocalDateTime(), australia);
        System.out.println("utcDate.toLocalDateTime(): " + utcDate.toLocalDateTime());
        System.out.println("convertFromUTC: " + utcDate.toOffsetDateTime().atZoneSameInstant(australia));

        System.out.println("My Method 1: " + convertToUtc(localDateAndTime));
        System.out.println("My Method 2: " + convertFromUtc(utcDate.toLocalDateTime()));
    }

    static LocalDateTime convertToUtc(LocalDateTime dateTime) {
        ZonedDateTime dateTimeInMyZone = ZonedDateTime.
                of(dateTime, ZoneId.systemDefault());

        return dateTimeInMyZone
                .withZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();

    }

    static LocalDateTime convertFromUtc(LocalDateTime utcDateTime){
        return ZonedDateTime.
                of(utcDateTime, ZoneId.of("UTC"))
                .toOffsetDateTime()
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
