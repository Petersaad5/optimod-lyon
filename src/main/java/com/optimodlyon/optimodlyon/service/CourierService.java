package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Courier;
import com.optimodlyon.optimodlyon.model.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CourierService {
    private static final String[] NAMES = {
            "John Doe", "Jane Smith", "Alex Johnson", "Chris Williams", "Pat Brown", "Taylor Jones",
            "Jordan Garcia", "Morgan Miller", "Casey Davis", "Riley Rodriguez", "Sam Martinez",
            "Jamie Hernandez", "Drew Lopez", "Cameron Gonzalez", "Avery Wilson", "Quinn Anderson",
            "Parker Thomas", "Reese Taylor", "Skyler Moore", "Dakota Jackson"
    };

    private final DataService dataService;

    @Autowired
    public CourierService(DataService dataService) {
        this.dataService = dataService;
    }

    public List<Courier> generateRandomCouriers(int count) {
        List<String> nameList = new ArrayList<>(List.of(NAMES));
        Collections.shuffle(nameList);

        List<Courier> couriers = new ArrayList<>();
        for (int i = 0; i < count && i < nameList.size(); i++) {
            couriers.add(new Courier((long) i, nameList.get(i)));
        }
        Data data = dataService.getData();
        data.setCouriers(couriers);
        dataService.setData(data);
        return couriers;
    }
}