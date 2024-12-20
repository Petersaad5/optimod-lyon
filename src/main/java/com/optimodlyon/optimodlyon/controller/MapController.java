package com.optimodlyon.optimodlyon.controller;

import com.optimodlyon.optimodlyon.model.Map;
import com.optimodlyon.optimodlyon.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/map")
@CrossOrigin(origins = "http://localhost:4200")
public class MapController {

    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public Map getMapData() {
        return mapService.getMapData();
    }

    @PostMapping("/parse")
    public Map parseMapFile(@RequestParam("file") MultipartFile file) {
        try {
            return mapService.parseAndGetMapData(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file", e);
        }
    }
}