package com.shevchuk.machineapp.repositories;

import com.shevchuk.machineapp.model.Machine;
import com.shevchuk.machineapp.model.MachineModelStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    @Query("SELECT new com.shevchuk.machineapp.model.MachineModelStatistic(m.machineType, COUNT(m)) FROM Machine m GROUP BY m.machineType")
    List<MachineModelStatistic> getMachineModelStatistic();

    List<Machine> findAllByOwner(String owner);

    @Query("SELECT m FROM Machine m WHERE m.machineInfo LIKE %?1%")
    List<Machine> findAllByAttribute(String attribute);
}
