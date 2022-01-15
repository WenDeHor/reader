package com.example.reader.model;

import lombok.Value;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Value
@Entity
public class ReserveAssets implements Serializable {
    Integer dt;
    String txt;
    String txten;
    String id_api;
    Integer leveli;
    String parent;
    String freq;
    Double value;
    String tzep;
//    "dt": "20210501",
//            "txt": "Офіційні резервні активи",
//            "txten": "Official reserve assets",
//            "id_api": "RES_OffReserveAssets",
//            "leveli": 1,
//            "parent": "Off_Reserve_Assets",
//            "freq": "M",
//            "value": 28000.06,
//            "tzep": "T071USD_7"

//    String Month;
//    Double Value, Difference;
}
