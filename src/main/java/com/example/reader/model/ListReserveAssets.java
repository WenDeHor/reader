package com.example.reader.model;

import lombok.Value;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
@Value
@Entity
public class ListReserveAssets implements Serializable {
    List<ReserveAssets> reserveAssetsList;
}
