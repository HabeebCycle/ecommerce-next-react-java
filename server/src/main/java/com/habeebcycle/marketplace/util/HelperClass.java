package com.habeebcycle.marketplace.util;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HelperClass {

    public static List<Long> getLongListFromString(@NotNull String s){
        try {
            return Arrays
                    .stream(s.split(",").clone())
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
        }catch (Exception ex){
            return null;
        }
    }

    public static String getStringFromLongList(List<Long> list){
        StringBuilder listStr = new StringBuilder();
        for (Long aLong : list) {
            if(aLong != null)
                listStr.append(aLong).append(",");
        }
        return listStr.toString();
    }
}
