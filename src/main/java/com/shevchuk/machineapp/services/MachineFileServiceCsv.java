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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MachineFileServiceCsv implements MachineFileService {

    private final MachineService machineService;

    public MachineFileServiceCsv(MachineService machineService) {
        this.machineService = machineService;
    }

    @Override
    public void saveMachine(MultipartFile file) {
        List<Machine> machines = getMachine(file);

        machines.stream()
                .filter(machine->Objects.nonNull(machine.getSourceId()))
                .map(machineService::save)
                .collect(Collectors.toList());

    }

    @Override
    public List<Machine> getMachine(MultipartFile file) {

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