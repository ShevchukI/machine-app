package com.shevchuk.machineapp.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.shevchuk.machineapp.exceptions.FileException;
import com.shevchuk.machineapp.model.Machine;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class MachineFileServiceImpl implements MachineFileService {

    private final MachineService machineService;

    public MachineFileServiceImpl(MachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public void saveMachineFromMultipartFile(MultipartFile file) {
        List<Machine> machines = getMachineFromMultipartFile(file);

        machines.forEach(machine -> {
            if (machine.getSourceId() != null) {
                machineService.save(machine);
            }
        });
    }

    private List<Machine> getMachineFromMultipartFile(MultipartFile file) {

        if (file.getOriginalFilename() == null || !FilenameUtils.getExtension(file.getOriginalFilename()).equals("csv")) {
            throw new FileException("File must be in CSV format");
        }

        List<Machine> machines = null;

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Machine> csvToBean = new CsvToBeanBuilder<Machine>(reader)
                    .withType(Machine.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withThrowExceptions(false)
                    .build();

            machines = csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return machines;
    }
}