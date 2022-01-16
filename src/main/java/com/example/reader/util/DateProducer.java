package com.example.reader.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateProducer {
    private  LocalDate dateStart = LocalDate.of(2004, 1, 1);
    private int numberOfMonths = (LocalDate.now().getYear() - 2004) * 12 + LocalDate.now().getMonthValue() - 1;

   public List<String> getDateURL() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        List<LocalDate> localDateArrayList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        localDateArrayList.add(dateStart);
        for (int i = 0; i < numberOfMonths; i++) {
            localDateArrayList.add(localDateArrayList.get(i).plusMonths(1));
            stringList.add(localDateArrayList.get(i).format(formatter));
        }
        stringList.add(localDateArrayList.get(localDateArrayList.size() - 1).format(formatter));
        return stringList;
    }

//    public static void main(String[] args) {
//        LocalDate dateStart = LocalDate.of(2004, 1, 1);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
//        List<LocalDate> localDateArrayList = new ArrayList<>();
//        List<String> stringList = new ArrayList<>();
//        int mounts = (LocalDate.now().getYear() - 2004) * 12+ LocalDate.now().getMonthValue() - 1;
//
//        localDateArrayList.add(dateStart);
//        for (int i = 0; i < mounts; i++) {
//            localDateArrayList.add(localDateArrayList.get(i).plusMonths(1));
//            stringList.add(localDateArrayList.get(i).format(formatter));
//            System.out.println(i + ":" + localDateArrayList.get(i));
//            System.out.println(i + ":" + stringList.get(i));
//        }
//
//        stringList.add(localDateArrayList.get(localDateArrayList.size() - 1).format(formatter));
//        System.out.println(localDateArrayList.get(localDateArrayList.size() - 1));
//        System.out.println(stringList.get(stringList.size() - 1));
//        System.out.println(localDateArrayList.size());
//        System.out.println(stringList.size());
//    }
}
