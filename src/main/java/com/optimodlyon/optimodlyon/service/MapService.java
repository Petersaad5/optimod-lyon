package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class MapService {
    private final DataService dataService;

    @Autowired
    public MapService(DataService dataService) {
        this.dataService = dataService;
    }

    public MapModel parseAndGetMapData(MultipartFile file) throws IOException {
        // Convert MultipartFile to File
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);

        // Parse the uploaded XML file
        Parser.parsePlan(convFile);
        Data data = Parser.data;
        dataService.setData(data);
        return data.getMap();
    }

    public MapModel getMapData() {
        return dataService.getData().getMap();
    }
}