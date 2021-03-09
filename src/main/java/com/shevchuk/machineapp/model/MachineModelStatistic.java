package com.shevchuk.machineapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineModelStatistic {

    private String machineType;
    private Long number;
}
