package com.optimodlyon.optimodlyon.controller;

import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.DeliveryRequestModel;
import com.optimodlyon.optimodlyon.service.DeliveryRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.optimodlyon.optimodlyon.model.MapModel;

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

    @PostMapping("/parseAndGetBestRoute")
    public MapModel parseDeliveryRequestFile(@RequestParam("file") MultipartFile file) {
        try {
            // return {MapModel, DeliveryRequestModel}
            return deliveryRequestService.parseAndGetBestRoute(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file", e);
        }
    }

    @PostMapping("/getDeliveryRequest")
    public DeliveryRequestModel getDeliveryRequest() {
        try {
            // return {MapModel, DeliveryRequestModel}
            return deliveryRequestService.parseAndGetDeliveryRequest();
        } catch (IOException e) {
            throw new NullPointerException("Delivery request data is null");
        }
    }
}