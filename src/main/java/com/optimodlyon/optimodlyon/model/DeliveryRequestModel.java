package com.optimodlyon.optimodlyon.model;

import java.util.List;

public class DeliveryRequestModel {
    private Long id;
    private WarehouseModel warehouse;
    private List<DeliveryModel> deliveries;

    public DeliveryRequestModel() {
    }

    public DeliveryRequestModel(Long id, WarehouseModel warehouse, List<DeliveryModel> deliveries) {
        this.id = id;
        this.warehouse = warehouse;
        this.deliveries = deliveries;
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
}