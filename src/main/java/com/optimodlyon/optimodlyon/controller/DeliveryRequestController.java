package com.optimodlyon.optimodlyon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.service.DeliveryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deliveryRequest")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryRequestController {

    private final DeliveryRequestService deliveryRequestService;

    @Autowired
    public DeliveryRequestController(DeliveryRequestService deliveryRequestService) {
        this.deliveryRequestService = deliveryRequestService;
    }

    @PostMapping("/parseAndGetBestRoutePerCourier")
    public List<TourModel> parseDeliveryRequestFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("couriers") String couriersJson,
                                             @RequestParam("deliveriesAdded") String deliveriesAddedJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<CourierModel> couriers = objectMapper.readValue(couriersJson, new TypeReference<List<CourierModel>>() {});
            List<DeliveryModel> deliveriesAdded = objectMapper.readValue(deliveriesAddedJson, new TypeReference<List<DeliveryModel>>() {});

            // return {MapModel, DeliveryRequestModel}
            System.out.println("Couriers: " + couriers.get(0));
            System.out.println("Deliveries added: " + deliveriesAdded.get(0));
            return deliveryRequestService.parseAndGetBestRoutePerCourier(file, couriers, deliveriesAdded);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file", e);
        }
    }

    @PostMapping("/saveTour")
    public void saveTour(@RequestParam("tour") String tourJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            TourModel tour = objectMapper.readValue(tourJson, new TypeReference<TourModel>() {});
            deliveryRequestService.saveTour(tour);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save tour", e);
        }
    }

    @GetMapping("/restoreTours")
    public List<TourModel> restoreTours() {
        return deliveryRequestService.restoreTours();
    }

}