package com.example.reader;

import com.example.reader.model.ReserveAsset;
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
        String nameCSV = getNameCSV();
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(nameCSV));
            List<String> listOfStringToCSV = getListOfStringToCSV();
            writer.writeNext(new String[]{"Month,Value,Difference"});
            listOfStringToCSV.forEach((p) -> writer.writeNext(new String[]{p}));
            writer.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getListOfStringToCSV() throws IOException, ParseException {
        List<String> stringListToCSV = new ArrayList<>();
        List<ReserveAsset> reserveAssetList = getBaseReserveList();
        stringListToCSV.add(getLocalDateArrayList().get(0) + "," + reserveAssetList.get(0).getValue() + "," + 00.0);
        for (int j = 1; j < reserveAssetList.size(); j++) {
            double difference = reserveAssetList.get(j).getValue() - reserveAssetList.get(j - 1).getValue();
            stringListToCSV.add(getLocalDateArrayList().get(j) + "," + reserveAssetList.get(j).getValue() + "," + difference);
        }
        return stringListToCSV;
    }

    private static List<ReserveAsset> getBaseReserveList() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        List<String> urlList = getURLList();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (int i = 0; i < urlList.size() - 1; i++) {
            URL url = new URL(urlList.get(i));
            String stringJason = parseUrl(url);
            JSONArray jsonArray = (JSONArray) parser.parse(stringJason);
            jsonArray.forEach((m) -> jsonObjectList.add((JSONObject) m));
        }

        return jsonObjectList.stream()
                .filter(m -> m.get("id_api").equals("RES_OffReserveAssets"))
                .map(m -> new ReserveAsset(
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
