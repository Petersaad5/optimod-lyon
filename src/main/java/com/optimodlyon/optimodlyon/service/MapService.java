package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.model.RoadModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.List;

@Service
public class MapService {
    private Data data;

    @PostConstruct
    public void init() {
        // Parse the XML files and load the data  TODO: change to grandPlan and demandeGrand7 on final version
        Parser.parsePlan("src/public/xml/petitPlan.xml");
        Parser.parseDemande("src/public/xml/demandePetit1.xml");
        this.data = Parser.data;
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