package com.shevchuk.machineapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Machine {

    @Id
    private Long sourceId;

    private String owner;
    private boolean available;
    private String country;
    private String currency;
    private String machineInfo;
    private String machineType;
    private String photos;
    private Double price;
    private String source;
    private String url;

}