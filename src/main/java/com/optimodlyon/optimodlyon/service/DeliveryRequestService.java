package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.utils.Parser;
import com.optimodlyon.optimodlyon.utils.TSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


import java.io.File;
import java.io.IOException;

@Service
public class DeliveryRequestService {
    private final DataService dataService;

    @Autowired
    public DeliveryRequestService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Tour> parseAndGetBestRoutePerCourier(File file, List<Courier> couriers, List<Delivery> deliveryAdded) throws IOException {

        // Parse the uploaded XML file
        List<Tour> toursWithoutTSP = Parser.parseDemande(file, couriers, deliveryAdded);
        for (int i = 0; i < couriers.size(); i++) {
            toursWithoutTSP.get(i).setRoute(TSP.tsp(toursWithoutTSP.get(i).getDeliveryRequest(), dataService.getData().getMap()));
        }
        // TSP Done in toursWithoutTSP
        return toursWithoutTSP;
    }

    public void saveTour(Tour tour) {
        Data data = dataService.getData();
        // set the tour id
        tour.setId((long) data.getTours().size());
        data.getTours().add(tour);
        dataService.setData(data);
    }

    public List<Tour> restoreTours() {
        return dataService.getData().getTours();
    }
}