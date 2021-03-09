package com.shevchuk.machineapp.controllers;

import com.shevchuk.machineapp.model.Machine;
import com.shevchuk.machineapp.model.MachineModelStatistic;
import com.shevchuk.machineapp.services.MachineFileService;
import com.shevchuk.machineapp.services.MachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;
    private final MachineFileService machineFileService;

    public MachineController(MachineService machineService, MachineFileService machineFileService) {
        this.machineService = machineService;
        this.machineFileService = machineFileService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        machineFileService.saveMachineFromMultipartFile(file);

        return new ResponseEntity<>("File uploaded successfully",HttpStatus.OK);
    }

    @RequestMapping(value = "/{sourceId}", method = RequestMethod.GET)
    public ResponseEntity<Machine> findBySourceId(@PathVariable("sourceId") Long sourceId) {
        return new ResponseEntity<>(machineService.findBySourceId(sourceId), HttpStatus.OK);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<Machine> save(@Valid @RequestBody Machine machine) {
        return new ResponseEntity<>(machineService.save(machine), HttpStatus.OK);
    }

    @RequestMapping(value = "/{sourceId}", method = RequestMethod.PUT)
    public ResponseEntity<Machine> update(@PathVariable("sourceId") Long sourceId, @Valid @RequestBody Machine machine) {
        return new ResponseEntity<>(machineService.update(machine, sourceId), HttpStatus.OK);
    }

    @RequestMapping(value = "/model-statistic", method = RequestMethod.GET)
    public ResponseEntity<List<MachineModelStatistic>> getMachineModelStatistic() {
        return new ResponseEntity<>(machineService.getMachineModelStatistic(), HttpStatus.OK);
    }

    @RequestMapping(value = "/owner", method = RequestMethod.GET)
    public ResponseEntity<List<Machine>> findAllByOwner(@RequestParam String owner) {
        return new ResponseEntity<>(machineService.findAllByOwner(owner), HttpStatus.OK);
    }

    @RequestMapping(value = "/attribute", method = RequestMethod.GET)
    public ResponseEntity<List<Machine>> findAllByAttribute(@RequestParam String attribute) {
        return new ResponseEntity<>(machineService.findAllByAttribute(attribute), HttpStatus.OK);
    }
}