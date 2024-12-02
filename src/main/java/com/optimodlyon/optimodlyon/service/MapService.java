package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class MapService {
    private Data data;

    public MapModel parseAndGetMapData(MultipartFile file) throws IOException {
        // Convert MultipartFile to File
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);

        // Parse the uploaded XML file
        Parser.parsePlan(convFile);
        this.data = Parser.data;
        return data.getMap();
    }

    public MapModel getMapData() {
        return data.getMap();
    }
}