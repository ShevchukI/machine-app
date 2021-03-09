package com.shevchuk.machineapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.shevchuk.machineapp.model.Machine;
import com.shevchuk.machineapp.model.MachineModelStatistic;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MachineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Machine tempMachine;
    private static MockMultipartFile multipartFile;

    private static Machine Kshlerin;
    private static Machine Raynor;
    private static Machine Purdy;
    private static Machine Tillman;

    private static boolean INITIALIZED_DATABASE = false;

    @BeforeAll
    static void setup() throws JsonProcessingException {
        String filePath = "src/test/resources/testData.csv";
        Path path = Paths.get(filePath);
        String name = "file";
        String originalName = "testData.csv";
        String contentType = "text/csv";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        multipartFile = new MockMultipartFile(name, originalName, contentType, content);

        Kshlerin = convertJsonStingToMachineObject("{\n" +
                "        \"sourceId\": 3689853,\n" +
                "        \"owner\": \"Eulalia Kshlerin\",\n" +
                "        \"available\": false,\n" +
                "        \"country\": \"germany\",\n" +
                "        \"currency\": \"usd\",\n" +
                "        \"machineInfo\": \"Year of manufacture: 2017, Type: Attachments, Actuation: Hydraulic drive\",\n" +
                "        \"machineType\": \"straw blowers/bale splitters\",\n" +
                "        \"photos\": \"[\\\"https://landwirshaft-top.de/media/9853/3689853/18765111/3689853-18765111.jpg?width=225&height=175&quality=70&crop\\\"]\",\n" +
                "        \"price\": 4950.0,\n" +
                "        \"source\": \"landwirshaft-top.de\",\n" +
                "        \"url\": \"https://www.landwirshaft-top.de/en/3689853\"\n" +
                "    }");

        Raynor = convertJsonStingToMachineObject("{\n" +
                "        \"sourceId\": 5566163,\n" +
                "        \"owner\": \"Darby Raynor\",\n" +
                "        \"available\": true,\n" +
                "        \"country\": \"poland\",\n" +
                "        \"currency\": \"aud\",\n" +
                "        \"machineInfo\": \"Year of manufacture: 2020, Round bales, Silage bales, Throwing range: 8.00 m, Type: Front-end loader extension, Actuation: Hydraulic drive\",\n" +
                "        \"machineType\": \"straw blowers/bale splitters\",\n" +
                "        \"photos\": \"[\\\"https://landwirshaft-top.de/media/6163/5566163/32637429/5566163-32637429.jpg?width=225&height=175&quality=70&crop\\\"]\",\n" +
                "        \"price\": 12900.0,\n" +
                "        \"source\": \"landwirshaft-top.de\",\n" +
                "        \"url\": \"https://www.landwirshaft-top.de/en/5566163\"\n" +
                "    }");

        Purdy = convertJsonStingToMachineObject("{\n" +
                "        \"sourceId\": 5549759,\n" +
                "        \"owner\": \"Marcella Purdy\",\n" +
                "        \"available\": false,\n" +
                "        \"country\": \"poland\",\n" +
                "        \"currency\": \"gbp\",\n" +
                "        \"machineInfo\": \"Year of manufacture: 2020, Lights, hydraulic Tailgate, Rotating discharge chute, Square bales, Silage bales, Throwing range: 18.00 m, Actuation: PTO drive system\",\n" +
                "        \"machineType\": \"straw blowers/bale splitters\",\n" +
                "        \"photos\": \"[\\\"https://powercombines.co.uk/media/9759/5549759/32511390/5549759-32511390.jpg?width=225&height=175&quality=70&crop\\\"]\",\n" +
                "        \"price\": 59000.0,\n" +
                "        \"source\": \"powercombines.co.uk\",\n" +
                "        \"url\": \"https://www.powercombines.co.uk/en/5549759\"\n" +
                "    }");

        Tillman = convertJsonStingToMachineObject("{\n" +
                "        \"sourceId\": 5523486,\n" +
                "        \"owner\": \"Frank Tillman\",\n" +
                "        \"available\": false,\n" +
                "        \"country\": \"uk\",\n" +
                "        \"currency\": \"gbp\",\n" +
                "        \"machineInfo\": \"Year of manufacture: 2018\",\n" +
                "        \"machineType\": \"straw blowers/bale splitters\",\n" +
                "        \"photos\": \"[\\\"https://der-bauer-freund.de/media/3486/5523486/32344877/5523486-32344877.jpg?width=225&height=175&quality=70&crop\\\"]\",\n" +
                "        \"price\": 11250.0,\n" +
                "        \"source\": \"der-bauer-freund.de\",\n" +
                "        \"url\": \"https://www.der-bauer-freund.de/en/5523486\"\n" +
                "    }");
    }

    @BeforeEach
    void setUp() throws Exception {
        tempMachine = new Machine(55231232L, "Frank Tillman", true, "uk", "usd",
                "Year of manufacture: 2018", "straw blowers/bale splitters",
                "[\"https://der-bauer-freund.de/media/3486/5523486/32344877/5523486-32344877.jpg?width=225&height=175&quality=70&crop\"]",
                14253.50, "der-bauer-freund.de", "https://www.der-bauer-freund.de/en/5523486");

        if (!INITIALIZED_DATABASE) {
            mockMvc.perform(multipart("/machines/upload")
                    .file(multipartFile))
                    .andExpect(status().isOk());

            INITIALIZED_DATABASE = true;
        }
    }

    @Test
    void uploadCsvFileTest() throws Exception {
        mockMvc.perform(multipart("/machines/upload")
                .file(multipartFile))
                .andExpect(status().isOk());
    }

    @Test
    void uploadCsvFileNegativeTest() throws Exception {
        String filePath = "src/test/resources/testData.txt";
        Path path = Paths.get(filePath);
        String name = "file";
        String originalName = "testData.txt";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MockMultipartFile tempFile = new MockMultipartFile(name, originalName, contentType, content);

        mockMvc.perform(multipart("/machines/upload")
                .file(tempFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("File must be in CSV format"));
    }

    @Test
    void findBySourceIdTest() throws Exception {
        mockMvc.perform(get("/machines/3689853"))
                .andExpect(status().isOk())
                .andExpect(content().json(convertObjectToJsonString(Kshlerin)));
    }

    @Test
    void findBySourceIdNegativeTest() throws Exception {
        mockMvc.perform(get("/machines/12345678"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Entity with id = 12345678 not found"));
    }

    @Test
    void saveTest() throws Exception {
        String json = convertObjectToJsonString(tempMachine);

        mockMvc.perform(post("/machines")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        mockMvc.perform(get("/machines/" + tempMachine.getSourceId()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void saveNegativeTest() throws Exception {
        tempMachine.setSourceId(null);
        tempMachine.setCurrency(null);

        String json = convertObjectToJsonString(tempMachine);

        List<String> errors = new ArrayList<>();
        errors.add("sourceId cannot be null");
        errors.add("currency cannot be null");

        String errorsJson = convertObjectToJsonString(errors);

        mockMvc.perform(post("/machines")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(errorsJson));
    }

    @Test
    void updateTest() throws Exception{
        String json = convertObjectToJsonString(tempMachine);

        mockMvc.perform(post("/machines")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        tempMachine.setPrice(100.00);
        tempMachine.setAvailable(false);
        tempMachine.setCountry("german");

        json = convertObjectToJsonString(tempMachine);

        mockMvc.perform(put("/machines/" + tempMachine.getSourceId())
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        mockMvc.perform(get("/machines/" + tempMachine.getSourceId()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void getMachineModelStatisticTest() throws Exception {
        String json = convertObjectToJsonString(tempMachine);

        mockMvc.perform(post("/machines")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        List<MachineModelStatistic> machineModelStatistics = new ArrayList<>();
        machineModelStatistics.add(new MachineModelStatistic("straw blowers/bale splitters", 8L));

        json = convertObjectToJsonString(machineModelStatistics);

        mockMvc.perform(get("/machines/model-statistic"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void findAllByOwnerTest() throws Exception {
        List<Machine> machines = new ArrayList<>();
        machines.add(tempMachine);
        machines.add(Tillman);

        String json = convertObjectToJsonString(tempMachine);

        mockMvc.perform(post("/machines")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        json = convertObjectToJsonString(machines);

        mockMvc.perform(get("/machines/owner")
                .param("owner", "Frank Tillman"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void findAllByAttributeTest() throws Exception {
        List<Machine> machines = new ArrayList<>();
        machines.add(Raynor);

        String json = convertObjectToJsonString(machines);

        mockMvc.perform(get("/machines/attribute")
                .param("attribute", "Round bales"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

        machines.add(Purdy);

        json = convertObjectToJsonString(machines);

        mockMvc.perform(get("/machines/attribute")
                .param("attribute", "Silage bales"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    private String convertObjectToJsonString(Object object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }

    private static Machine convertJsonStingToMachineObject(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Machine.class);
    }
}