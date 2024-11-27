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

    public Long getAddress() {
        return address.getId();
    }

    public void setAddress(IntersectionModel address) {
        this.address = address;
    }


}
