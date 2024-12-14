package com.optimodlyon.optimodlyon.model;

import java.util.List;

public class DeliveryRequest {
    private Long id;
    private Warehouse warehouse;
    private List<Delivery> deliveries;
    private Courier courier;

    public DeliveryRequest() {
    }

    public DeliveryRequest(Long id, Warehouse warehouse, List<Delivery> deliveries, Courier courier) {
        this.id = id;
        this.warehouse = warehouse;
        this.deliveries = deliveries;
        this.courier = courier;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Courier getCourier() {
        return courier;
    }
}