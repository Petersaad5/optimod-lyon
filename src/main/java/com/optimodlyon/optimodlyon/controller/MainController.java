package com.optimodlyon.optimodlyon.controller;

import com.optimodlyon.optimodlyon.model.CourierModel;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.service.CourierService;
import com.optimodlyon.optimodlyon.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MainController {

    private final CourierService courierService;
    private final MapService mapService;

    @Autowired
    public MainController(CourierService courierService, MapService mapService) {
        this.courierService = courierService;
        this.mapService = mapService;
    }

    @GetMapping("/couriers")
    public List<CourierModel> getCouriers() {
        return courierService.generateRandomCouriers(10);
    }

    @GetMapping("/map")
    public MapModel getMapData() {
        return mapService.getMapData();
    }

    @PostMapping("/map/parse")
    public MapModel parseMapFile(@RequestParam("file") MultipartFile file) {
        try {
            return mapService.parseAndGetMapData(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file", e);
        }
    }
}