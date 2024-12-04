package com.optimodlyon.optimodlyon.model;
import java.util.List;

public class Data {
    MapModel map;
    List<IntersectionModel> intersections;
    List<RoadModel> roads;
    List <CourierModel> couriers;
    List <DeliveryModel> deliveries;
    DeliveryRequestModel deliveryRequest;
    List <WarehouseModel> warehouses;


    public Data() {
    }

    public Data(MapModel map, List<IntersectionModel> intersections, List<RoadModel> roads) {
        this.map = map;
        this.intersections = intersections;
        this.roads = roads;
    }

    public MapModel getMap() {
        return map;
    }

    public void setMap(MapModel map) {
        this.map = map;
    }

    public List<IntersectionModel> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<IntersectionModel> intersections) {
        this.intersections = intersections;
    }

    public List<RoadModel> getRoads() {
        return roads;
    }

    public void setRoads(List<RoadModel> roads) {
        this.roads = roads;
    }

    public List<CourierModel> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<CourierModel> couriers) {
        this.couriers = couriers;
    }

    public DeliveryRequestModel getDeliveryRequest() {
        return deliveryRequest;
    }

    public void setDeliveryRequest(DeliveryRequestModel deliveryRequest) {
        this.deliveryRequest = deliveryRequest;
    }
}
