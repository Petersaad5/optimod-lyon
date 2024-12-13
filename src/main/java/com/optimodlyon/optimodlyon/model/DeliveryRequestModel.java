package com.optimodlyon.optimodlyon.model;

import java.util.List;

public class DeliveryRequestModel {
    private Long id;
    private WarehouseModel warehouse;
    private List<DeliveryModel> deliveries;
    private CourierModel courier;

    public DeliveryRequestModel() {
    }

    public DeliveryRequestModel(Long id, WarehouseModel warehouse, List<DeliveryModel> deliveries, CourierModel courier) {
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

    public WarehouseModel getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseModel warehouse) {
        this.warehouse = warehouse;
    }

    public List<DeliveryModel> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<DeliveryModel> deliveries) {
        this.deliveries = deliveries;
    }

    public void setCourier(CourierModel courier) {
        this.courier = courier;
    }

    public CourierModel getCourier() {
        return courier;
    }
}