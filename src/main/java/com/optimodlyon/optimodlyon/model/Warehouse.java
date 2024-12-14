package com.optimodlyon.optimodlyon.model;

import java.util.Date;

public class Warehouse {
    Date departureTime;
    Intersection address;

    public Warehouse() {
    }

    public Warehouse(Date departureTime, Intersection address) {
        this.departureTime = departureTime;
        this.address = address;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Intersection getAddress() {
        return this.address;
    }

    public void setAddress(Intersection address) {
        this.address = address;
    }


}
