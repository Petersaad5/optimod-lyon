package com.optimodlyon.optimodlyon.model;

public class DeliveryModel {
    int deliveryDuration;
    int pickupDuration;
    IntersectionModel destination;
    IntersectionModel origin;

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

}
