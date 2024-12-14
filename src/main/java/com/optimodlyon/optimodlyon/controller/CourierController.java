package com.optimodlyon.optimodlyon.controller;

import com.optimodlyon.optimodlyon.model.Courier;
import com.optimodlyon.optimodlyon.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/couriers")
@CrossOrigin(origins = "http://localhost:4200")
public class CourierController {

    private final CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping
    public List<Courier> getCouriers() {
        return courierService.generateRandomCouriers(10);
    }
}