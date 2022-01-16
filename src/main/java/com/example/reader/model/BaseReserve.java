package com.example.reader.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BaseReserve implements Serializable {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Integer id;
    String dt;
    String id_api;
    Double value;
//    List<String> value;

//    public BaseReserve(String dt, String id_api,  List<String> value) {
//        this.dt = dt;
//        this.id_api = id_api;
//        this.value = value;
//    }
//        "dt": "20210501",
//                "txt": "Офіційні резервні активи",
//                "txten": "Official reserve assets",
//                "id_api": "RES_OffReserveAssets",
//                "leveli": 1,
//                "parent": "Off_Reserve_Assets",
//                "freq": "M",
//                "value": 28000.06,
//                "tzep": "T071USD_7"
}
