package com.optimodlyon.optimodlyon.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.optimodlyon.optimodlyon.model.DeliveryRequestModel;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"deliveries"})
public class CourierModel {
    private Long id;
    private String name;
    private List<DeliveryRequestModel> deliveries;

    public CourierModel() {
        this.deliveries = new ArrayList<>();
    }

    public CourierModel(Long id, String name) {
        this.id = id;
        this.name = name;
        this.deliveries = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "CourierModel{id=" + id + ", name=" + name + ", deliveries=" + deliveries + "}";
    }

    public void addDelivery(DeliveryRequestModel delivery) {
        this.deliveries.add(delivery);
    }

}
