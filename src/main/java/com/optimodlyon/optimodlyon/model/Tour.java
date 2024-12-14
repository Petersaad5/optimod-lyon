package com.optimodlyon.optimodlyon.model;

public class Tour {
    private Long id;
    private Map route;
    private DeliveryRequest deliveryRequest;

    public Tour() {
    }

    public Tour(Long id, Map route, DeliveryRequest deliveryRequest) {
        this.id = id;
        this.route = route;
        this.deliveryRequest = deliveryRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map getRoute() {
        return route;
    }

    public void setRoute(Map route) {
        this.route = route;
    }

    public DeliveryRequest getDeliveryRequest() {
        return deliveryRequest;
    }

    public void setDeliveryRequest(DeliveryRequest deliveryRequest) {
        this.deliveryRequest = deliveryRequest;
    }

    @Override
    public String toString() {
        return "TourModel{" +
                "id=" + id +
                ", courier=" + deliveryRequest.getCourier() +
                ", route=" + route +
                ", deliveryRequest=" + deliveryRequest +
                '}';
    }
}
