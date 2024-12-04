package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.utils.Parser;
import com.optimodlyon.optimodlyon.utils.TourOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DeliveryRequestService {
    private final DataService dataService;

    @Autowired
    public DeliveryRequestService(DataService dataService) {
        this.dataService = dataService;
    }

    public TourOptimizer.Tour parseAndGetDeliveryRequestData(MultipartFile file) throws IOException {
        // Convert MultipartFile to File
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);

        // Parse the uploaded XML file
        Parser.parseDemande(convFile);
        Data data = Parser.data;
        dataService.setData(data);

        // Calculate the optimal tour
        return TourOptimizer.calculateOptimalTour(data.getDeliveryRequest(), data.getMap());
    }
}