package com.example.reader.util;

import com.example.reader.model.BaseReserve;
import lombok.RequiredArgsConstructor;
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

import static java.text.MessageFormat.format;
@RequiredArgsConstructor
public class MineClass {
    public static void main(String[] args) throws IOException, ParseException {
        List<BaseReserve> baseReserveList = getBaseReserveList();
        System.out.println(baseReserveList);
    }

    public static List<BaseReserve> getBaseReserveList()throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        List<String> urlList = getURLList();
        List<BaseReserve> baseReserveList = new ArrayList<>();

        for (int i = 0; i < urlList.size()-1; i++) {
            URL url2 = new URL(urlList.get(i));
            String stringJason = parseUrl(url2);

            JSONArray json = (JSONArray) parser.parse(stringJason);
            List<JSONObject> objectList = new ArrayList<>();

            for (int j = 0; j < json.size(); j++) {
                objectList.add((JSONObject) json.get(j));

            }
//            System.out.println(json.size());
//            System.out.println(objectList.size());
//            System.out.println(objectList.get(0).get("dt"));
//            System.out.println(objectList.get(0).get("id_api"));
//            System.out.println(objectList.get(0).get("value"));
//            List<String>result=new ArrayList<>();
//            result.add(String.valueOf(objectList.get(0).get("dt"))+ objectList.get(0).get("id_api")+ objectList.get(0).get("value"));
//            System.out.println(String.valueOf(objectList.get(0).get("dt"))+"," +objectList.get(0).get("id_api")+","+ Double.valueOf(String.valueOf(objectList.get(0).get("value"))));
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
        for (int i = 0; i < dateURL.size(); i++) {
            URLList.add("https://bank.gov.ua/NBUStatService/v1/statdirectory/res?date="+dateURL.get(i)+"&json");
//            URLList.add(format("https://bank.gov.ua/NBUStatService/v1/statdirectory/res?date={i}&json", dateURL.get(i)));
            System.out.println(URLList.get(i));
        }
        return URLList;
    }

    //DATA
    private static  LocalDate dateStart = LocalDate.of(2004, 1, 1);
    private static int numberOfMonths = (LocalDate.now().getYear() - 2004) * 12 + LocalDate.now().getMonthValue() - 1;

    public  static List<String> getDateURL() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        List<LocalDate> localDateArrayList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();
        localDateArrayList.add(dateStart);
        for (int i = 0; i < numberOfMonths; i++) {
            localDateArrayList.add(localDateArrayList.get(i).plusMonths(1));
            stringList.add(localDateArrayList.get(i).format(formatter));
        }
        stringList.add(localDateArrayList.get(localDateArrayList.size() - 1).format(formatter));
        System.out.println(stringList);
        return stringList;
    }
}
