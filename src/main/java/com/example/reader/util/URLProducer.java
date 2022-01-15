package com.example.reader.util;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;

public class URLProducer {

    private DateProducer dateProducer;

    public URLProducer(DateProducer dateProducer) {
        this.dateProducer = dateProducer;
    }

    private List<String> getURLList(){
        List<String>URLList=new ArrayList<>();
        List<String> dateURL = dateProducer.getDateURL();
        for (int i = 0; i <dateURL.size() ; i++) {
            URLList.add(format("GET https://bank.gov.ua/NBUStatService/v1/statdirectory/res?date={i}&json",dateURL.get(i)));
        }
        return URLList;
    }

}
