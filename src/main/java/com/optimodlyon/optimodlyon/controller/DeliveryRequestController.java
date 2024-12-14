package com.optimodlyon.optimodlyon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.optimodlyon.optimodlyon.model.*;
import com.optimodlyon.optimodlyon.service.DeliveryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public List<Tour> parseDeliveryRequestFile(
                @RequestParam("file") MultipartFile file,
                @RequestParam("couriers") String couriersJson,
                @RequestParam("deliveriesAdded") String deliveriesAddedJson
                ) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Courier> couriers = objectMapper.readValue(couriersJson, new TypeReference<List<Courier>>() {});
            List<Delivery> deliveriesAdded = objectMapper.readValue(deliveriesAddedJson, new TypeReference<List<Delivery>>() {});

            // Convert MultipartFile to File
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            return deliveryRequestService.parseAndGetBestRoutePerCourier(convFile, couriers, deliveriesAdded);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file", e);
        }
    }

    @PostMapping("/saveTour")
    public void saveTour(@RequestParam("tour") String tourJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Tour tour = objectMapper.readValue(tourJson, new TypeReference<Tour>() {});
            deliveryRequestService.saveTour(tour);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save tour", e);
        }
    }

    @GetMapping("/restoreTours")
    public List<Tour> restoreTours() {
        return deliveryRequestService.restoreTours();
    }

}