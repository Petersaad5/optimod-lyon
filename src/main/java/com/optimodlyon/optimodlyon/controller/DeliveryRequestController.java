package com.optimodlyon.optimodlyon.controller;

import com.optimodlyon.optimodlyon.model.IntersectionModel;
import com.optimodlyon.optimodlyon.service.DeliveryRequestService;
import com.optimodlyon.optimodlyon.utils.TourOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/deliveryRequest")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryRequestController {

    private final DeliveryRequestService deliveryRequestService;

    @Autowired
    public DeliveryRequestController(DeliveryRequestService deliveryRequestService) {
        this.deliveryRequestService = deliveryRequestService;
    }

    @PostMapping("/parse")
    public TourOptimizer.Tour parseDeliveryRequestFile(@RequestParam("file") MultipartFile file) {
        try {
            return deliveryRequestService.parseAndGetDeliveryRequestData(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse XML file", e);
        }
    }
}