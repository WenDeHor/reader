package com.example.reader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ReserveAssetsService {
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

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        URL url2 = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/res?date=202105&json");
        String s = parseUrl(url2);
        JSONArray json = (JSONArray) parser.parse(s);
        List<JSONObject> objectList = new ArrayList<>();
        for (int i = 0; i < json.size(); i++) {
            objectList.add((JSONObject) json.get(i));
        }
        System.out.println(objectList.get(0).get("dt"));
        System.out.println(objectList.get(0).get("value"));
        System.out.println(objectList.get(0).get("id_api"));
    }
}
