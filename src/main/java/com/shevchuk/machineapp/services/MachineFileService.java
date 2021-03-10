package com.shevchuk.machineapp.services;

import com.shevchuk.machineapp.model.Machine;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MachineFileService {

    void saveMachine(MultipartFile file);

    List<Machine> getMachine(MultipartFile file);
}