package com.optimodlyon.optimodlyon.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"deliveryDuration", "pickupDuration"})
public class Delivery {
    private int deliveryDuration;
    private int pickupDuration;
    private Intersection destination;
    private Intersection origin;

    public Delivery() {
    }

    public Delivery(int deliveryDuration, int pickupDuration, Intersection destination, Intersection origin) {
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

    public Intersection getDestination() {
        return destination;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public Intersection getOrigin() {
        return origin;
    }

    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    public String toString() {
        return "DeliveryModel{deliveryDuration=" + deliveryDuration + ", pickupDuration=" + pickupDuration + ", destination=" + destination + ", origin=" + origin + "}";
    }
}