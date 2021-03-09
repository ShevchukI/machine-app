package com.shevchuk.machineapp.services;

import org.springframework.web.multipart.MultipartFile;

public interface MachineFileService {

    void saveMachineFromMultipartFile(MultipartFile file);
}