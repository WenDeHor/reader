package com.example.reader.util;

import com.example.reader.model.BaseReserve;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.text.MessageFormat.format;

@RequiredArgsConstructor
public class MineClass {
    public static void main(String[] args) throws IOException, ParseException {
        getBaseReserveList();
        writeToCsv();

    }

    public static String getNameCSV() {
        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        return generatedString + ".csv";
    }

    public static void writeToCsv() {
        String csv = getNameCSV();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            List<String> toCSV = getToCSV();
            for (int i = 0; i < toCSV.size(); i++) {
                writer.writeNext(new String[]{toCSV.get(i)});
            }

            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getToCSV() throws IOException, ParseException {
        List<String> toCSV = new ArrayList<>();
        List<BaseReserve> baseReserveList = getBaseReserveList();
        toCSV.add(getLocalDateArrayList().get(0) + "," + baseReserveList.get(0).getValue() + "," + 00.0);
        for (int j = 1; j < baseReserveList.size(); j++) {
            double difference = baseReserveList.get(j).getValue() - baseReserveList.get(j - 1).getValue();
            toCSV.add(getLocalDateArrayList().get(j) + "," + baseReserveList.get(j).getValue() + "," + difference);
        }

        return toCSV;
    }

    public static List<BaseReserve> getBaseReserveList() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        List<String> urlList = getURLList();
        List<BaseReserve> baseReserveList = new ArrayList<>();
        for (int i = 0; i < urlList.size() - 1; i++) {
            URL url2 = new URL(urlList.get(i));
            String stringJason = parseUrl(url2);

            JSONArray json = (JSONArray) parser.parse(stringJason);
            List<JSONObject> objectList = new ArrayList<>();
            for (Object o : json) {
                objectList.add((JSONObject) o);
            }

            BaseReserve model = new BaseReserve(
                    (String) objectList.get(0).get("dt"),
                    (String) objectList.get(0).get("id_api"),
                    Double.valueOf(String.valueOf(objectList.get(0).get("value"))));
            baseReserveList.add(model);
        }
        return baseReserveList;
    }

    public static String parseUrl(URL url) {
        if (url == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static List<String> getURLList() {
        List<String> URLList = new ArrayList<>();
        List<String> dateURL = getDateURL();
        for (String s : dateURL) {
            URLList.add("https://bank.gov.ua/NBUStatService/v1/statdirectory/res?date=" + s + "&json");
        }
        return URLList;
    }

    private static LocalDate dateStart = LocalDate.of(2004, 1, 1);
    private static int numberOfMonths = (LocalDate.now().getYear() - 2004) * 12 + LocalDate.now().getMonthValue();

    public static List<LocalDate> getLocalDateArrayList() {
        List<LocalDate> localDateArrayList = new ArrayList<>();
        localDateArrayList.add(dateStart);
        for (int i = 0; i < numberOfMonths; i++) {
            localDateArrayList.add(localDateArrayList.get(i).plusMonths(1));
        }
        return localDateArrayList;
    }

    public static List<String> getDateURL() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        List<String> stringList = new ArrayList<>();
        List<LocalDate> localDateArrayList = getLocalDateArrayList();
        for (int i = 0; i < numberOfMonths; i++) {
            stringList.add(localDateArrayList.get(i).format(formatter));
        }
        stringList.add(localDateArrayList.get(localDateArrayList.size() - 1).format(formatter));
        return stringList;
    }
}
