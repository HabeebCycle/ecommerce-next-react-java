package com.habeebcycle.marketplace.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    }
}
