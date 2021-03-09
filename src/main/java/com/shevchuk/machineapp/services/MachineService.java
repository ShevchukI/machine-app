package com.shevchuk.machineapp.services;

import com.shevchuk.machineapp.model.Machine;
import com.shevchuk.machineapp.model.MachineModelStatistic;

import java.util.List;

public interface MachineService {

    Machine findBySourceId(Long sourceId);

    Machine save(Machine machine);

    Machine update(Machine machine, Long sourceId);

    List<MachineModelStatistic> getMachineModelStatistic();

    List<Machine> findAllByOwner(String owner);

    List<Machine> findAllByAttribute(String attribute);
}
