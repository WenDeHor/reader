package com.example.reader.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateProducer {
    private static Date currentDate = new Date();


    public static void main(String[] args) {
//        SimpleDateFormat dateFormat = null;
//        dateFormat = new SimpleDateFormat("yyyyyMM");
//        System.out.println(dateFormat.format(currentDate));

        LocalDate dateStart = LocalDate.of(2004, 1, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
//        String format1 = LocalDate.now().format(formatter);
//        System.out.println(format1);
        List<LocalDate> integerList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        int mounts=(2022-2004)*12;

        integerList.add(dateStart);
//        stringList.add(dateStart.format(formatter));
        for (int i = 0; i < mounts; i++) {
            integerList.add(integerList.get(i).plusMonths(1));
            stringList.add(integerList.get(i).format(formatter));

            System.out.println(i+":"+integerList.get(i));
            System.out.println(i+":"+stringList.get(i));
        }
        System.out.println(integerList.size());
        System.out.println(stringList.size());


//        LocalDate date2 = LocalDate.parse(date, formatter);
//        LocalTime time = LocalTime.parse("22:12");
//        LocalDateTime dateTime = LocalDateTime.of(date2, time);
//        System.out.println(dateTime); //2016-02-12T22:12

//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd MM yyyy");
//        LocalDate date3 = LocalDate.parse("12 02 2016", formatter2);
//
//        LocalDate localDate = date3.plusMonths(1);
//        Period period = Period.ofDays(10);
//        System.out.println(localDate);


    }
}
