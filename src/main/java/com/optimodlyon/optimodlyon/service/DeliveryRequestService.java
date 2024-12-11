package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.DeliveryRequestModel;
import com.optimodlyon.optimodlyon.model.MapModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import com.optimodlyon.optimodlyon.utils.TSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.ArrayList;


import java.io.File;
import java.io.IOException;

@Service
public class DeliveryRequestService {
    private final DataService dataService;

    @Autowired
    public DeliveryRequestService(DataService dataService) {
        this.dataService = dataService;
    }

    public MapModel parseAndGetBestRoute(MultipartFile file) throws IOException {
        // Convert MultipartFile to File
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);

        // Parse the uploaded XML file
        Parser.parseDemande(convFile);
        Data data = Parser.data;
        dataService.setData(data);
        List<Object> result = new ArrayList<>();
        MapModel bestMap = TSP.tsp(data.getDeliveryRequest(), data.getMap());
        return bestMap;
    }

    public DeliveryRequestModel parseAndGetDeliveryRequest() throws IOException {
        // check if Data.getDeliveryRequest() is null
        if (dataService.getData().getDeliveryRequest() == null) {
            throw new RuntimeException("Delivery request data is null");
        }
        return dataService.getData().getDeliveryRequest();
    }

}