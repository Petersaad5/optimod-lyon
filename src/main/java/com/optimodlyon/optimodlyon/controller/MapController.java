package com.optimodlyon.optimodlyon.controller;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.model.RoadModel;
import com.optimodlyon.optimodlyon.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/map")
public class MapController {
    private final MapService mapService;

    @Autowired
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }


    @GetMapping
    public MapModel getMapData() {
        return mapService.getMapData();
    }

    @GetMapping("/intersections")
    public List<IntersectionModel> getIntersections() {
        return mapService.getIntersections();
    }

    @GetMapping("/roads")
    public List<RoadModel> getRoads() {
        return mapService.getRoads();
    }
}