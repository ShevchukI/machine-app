package com.shevchuk.machineapp.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Machine {

    @Id
    @NotNull(message = "sourceId cannot be null")
    @CsvBindByName
    private Long sourceId;

    @CsvBindByName
    private String owner;

    @CsvBindByName
    private boolean available;

    @CsvBindByName
    private String country;

    @NotNull(message = "currency cannot be null")
    @CsvBindByName
    private String currency;

    @CsvBindByName
    private String machineInfo;

    @CsvBindByName
    private String machineType;

    @CsvBindByName
    private String photos;

    @NotNull(message = "price cannot be null")
    @Min(value = 0, message = "price cannot be less than 0")
    @CsvBindByName
    private Double price;

    @CsvBindByName
    private String source;

    @CsvBindByName
    private String url;

}