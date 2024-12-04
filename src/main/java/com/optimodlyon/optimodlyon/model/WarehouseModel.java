package com.optimodlyon.optimodlyon.model;

import java.util.Date;

public class WarehouseModel {
    Date departureTime;
    IntersectionModel address;

    public WarehouseModel() {
    }

    public WarehouseModel(Date departureTime, IntersectionModel address) {
        this.departureTime = departureTime;
        this.address = address;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public IntersectionModel getAddress() {
        return this.address;
    }

    public void setAddress(IntersectionModel address) {
        this.address = address;
    }


}
