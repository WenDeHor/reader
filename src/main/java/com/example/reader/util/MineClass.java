package com.example.reader.util;

import com.example.reader.model.BaseReserve;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MineClass {
    private static LocalDate DATE_START = LocalDate.of(2004, 1, 1);
    private static int NUMBER_OF_MONTH = (LocalDate.now().getYear() - 2004) * 12 + LocalDate.now().getMonthValue();

    public static void main(String[] args) throws IOException, ParseException {
        getBaseReserveList();
        writeToCsv();
    }

    private static String getNameCSV() {
        String generatedString = RandomStringUtils.randomAlphanumeric(10);
        return generatedString + ".csv";
    }

    private static void writeToCsv() {
        String csv = getNameCSV();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(csv));
            List<String> toCSV = getToCSV();
            writer.writeNext(new String[]{"Month,Value,Difference"});
            toCSV.forEach((p) -> writer.writeNext(new String[]{p}));
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getToCSV() throws IOException, ParseException {
        List<String> toCSV = new ArrayList<>();
        List<BaseReserve> baseReserveList = getBaseReserveList();
        toCSV.add(getLocalDateArrayList().get(0) + "," + baseReserveList.get(0).getValue() + "," + 00.0);
        for (int j = 1; j < baseReserveList.size(); j++) {
            double difference = baseReserveList.get(j).getValue() - baseReserveList.get(j - 1).getValue();
            toCSV.add(getLocalDateArrayList().get(j) + "," + baseReserveList.get(j).getValue() + "," + difference);
        }
        return toCSV;
    }

    private static List<BaseReserve> getBaseReserveList() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        List<String> urlList = getURLList();
        List<JSONObject> objectList = new ArrayList<>();
        for (int i = 0; i < urlList.size() - 1; i++) {
            URL url2 = new URL(urlList.get(i));
            String stringJason = parseUrl(url2);
            JSONArray json = (JSONArray) parser.parse(stringJason);
            json.forEach((m) -> objectList.add((JSONObject) m));
        }

        return objectList.stream()
                .filter(m -> m.get("id_api").equals("RES_OffReserveAssets"))
                .map(m -> new BaseReserve(
                        (String) m.get("dt"),
                        (String) m.get("id_api"),
                        Double.valueOf(String.valueOf(m.get("value")))))
                .collect(Collectors.toList());
    }

    private static String parseUrl(URL url) {
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

    private static List<String> getURLList() {
        List<String> dateURL = getDateURL();
        return dateURL.stream()
                .map(m -> ("https://bank.gov.ua/NBUStatService/v1/statdirectory/res?date=" + m + "&json"))
                .collect(Collectors.toList());
    }

    private static List<LocalDate> getLocalDateArrayList() {
        List<LocalDate> localDateArrayList = new ArrayList<>();
        localDateArrayList.add(DATE_START);
        for (int i = 0; i < NUMBER_OF_MONTH; i++) {
            localDateArrayList.add(localDateArrayList.get(i).plusMonths(1));
        }
        return localDateArrayList;
    }

    private static List<String> getDateURL() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        List<LocalDate> localDateArrayList = getLocalDateArrayList();
        return localDateArrayList.stream()
                .map(s -> s.format(formatter))
                .collect(Collectors.toList());
    }
}
