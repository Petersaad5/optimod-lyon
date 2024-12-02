package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.model.RoadModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MapService {
    private Data data;

    public MapModel parseAndGetMapData(MultipartFile file) throws IOException {
        // Parse the uploaded XML file
        Parser.parsePlan(file.getInputStream().toString());
        this.data = Parser.data;
        return data.getMap();
    }



    public MapModel getMapData() {
        return data.getMap();
    }

    public List<IntersectionModel> getIntersections() {
        return data.getIntersections();
    }

    public List<RoadModel> getRoads() {
        return data.getRoads();
    }


}