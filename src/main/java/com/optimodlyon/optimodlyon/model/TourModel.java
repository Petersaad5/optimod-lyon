package com.optimodlyon.optimodlyon.model;

public class TourModel {
    private Long id;
    private MapModel route;
    private DeliveryRequestModel deliveryRequest;

    public TourModel() {
    }

    public TourModel(Long id, MapModel route, DeliveryRequestModel deliveryRequest) {
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

    public MapModel getRoute() {
        return route;
    }

    public void setRoute(MapModel route) {
        this.route = route;
    }

    public DeliveryRequestModel getDeliveryRequest() {
        return deliveryRequest;
    }

    public void setDeliveryRequest(DeliveryRequestModel deliveryRequest) {
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
