package com.shevchuk.machineapp.services;

import com.shevchuk.machineapp.model.Machine;
import com.shevchuk.machineapp.model.MachineModelStatistic;
import com.shevchuk.machineapp.repositories.MachineRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;

    public MachineServiceImpl(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public Machine findBySourceId(Long sourceId) {
        return machineRepository.findById(sourceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id = %s not found", sourceId)));
    }

    @Override
    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public Machine update(Machine machine, Long sourceId) {
        Long id = findBySourceId(sourceId).getSourceId();

        machine.setSourceId(id);

        return machineRepository.save(machine);
    }

    @Override
    public List<MachineModelStatistic> getMachineModelStatistic() {
        return machineRepository.getMachineModelStatistic();
    }

    @Override
    public List<Machine> findAllByOwner(String owner) {
        return machineRepository.findAllByOwner(owner);
    }

    @Override
    public List<Machine> findAllByAttribute(String attribute) {
        return machineRepository.findAllByAttribute(attribute);
    }
}
