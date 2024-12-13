package com.optimodlyon.optimodlyon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"deliveryDuration", "pickupDuration"})
public class DeliveryModel {
    private int deliveryDuration;
    private int pickupDuration;
    private IntersectionModel destination;
    private IntersectionModel origin;

    public DeliveryModel() {
    }

    public DeliveryModel(int deliveryDuration, int pickupDuration, IntersectionModel destination, IntersectionModel origin) {
        this.deliveryDuration = deliveryDuration;
        this.pickupDuration = pickupDuration;
        this.destination = destination;
        this.origin = origin;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(int deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public int getPickupDuration() {
        return pickupDuration;
    }

    public void setPickupDuration(int pickupDuration) {
        this.pickupDuration = pickupDuration;
    }

    public IntersectionModel getDestination() {
        return destination;
    }

    public void setDestination(IntersectionModel destination) {
        this.destination = destination;
    }

    public IntersectionModel getOrigin() {
        return origin;
    }

    public void setOrigin(IntersectionModel origin) {
        this.origin = origin;
    }

    public String toString() {
        return "DeliveryModel{deliveryDuration=" + deliveryDuration + ", pickupDuration=" + pickupDuration + ", destination=" + destination + ", origin=" + origin + "}";
    }
}