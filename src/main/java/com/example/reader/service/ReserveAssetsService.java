package com.example.reader.service;

import com.example.reader.model.BaseReserve;
import com.example.reader.repository.ReserveAssetsRepository;
import com.example.reader.util.DateProducer;
import com.example.reader.util.URLProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReserveAssetsService {

    URLProducer urlProducer;

    public String parseUrl(URL url) {
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

    public List<BaseReserve> getBaseReserveList()throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        List<String> urlList = urlProducer.getURLList();
        List<BaseReserve> baseReserveList = new ArrayList<>();

        for (int i = 0; i < urlList.size()-1; i++) {
            URL url2 = new URL(urlList.get(i));
            String stringJason = parseUrl(url2);
            JSONArray json = (JSONArray) parser.parse(stringJason);
            List<JSONObject> objectList = new ArrayList<>();
            for (int j = 0; j < json.size(); j++) {
                objectList.add((JSONObject) json.get(j));
            }
            System.out.println();

//            BaseReserve model = new BaseReserve(
//                    (String) objectList.get(0).get("dt"),
//                    (String) objectList.get(0).get("id_api"),
//                    (Double) objectList.get(0).get("value"));
//            baseReserveList.add(model);
//            {
//                "dt": "20210501",
//                    "txt": "Офіційні резервні активи",
//                    "txten": "Official reserve assets",
//                    "id_api": "RES_OffReserveAssets",
//                    "leveli": 1,
//                    "parent": "Off_Reserve_Assets",
//                    "freq": "M",
//                    "value": 28000.06,
//                    "tzep": "T071USD_7"
//            },
        }
        System.out.println(baseReserveList.size());
        return baseReserveList;
    }
}
