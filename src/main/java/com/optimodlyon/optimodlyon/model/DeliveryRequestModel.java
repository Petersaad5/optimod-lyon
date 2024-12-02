package com.optimodlyon.optimodlyon.model;

public class DeliveryRequestModel {

    private Long destinationId;
    private Long originId;
    private Integer deliveryDuration;
    private Integer deliveryTime;

    public DeliveryRequestModel() {
    }

    public DeliveryRequestModel(Long originId, Long destinationId, Integer deliveryDuration, Integer deliveryTime) {
        this.originId = originId;
        this.destinationId = destinationId;
        this.deliveryDuration = deliveryDuration;
        this.deliveryTime = deliveryTime;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Integer getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(Integer deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return "DeliveryRequestModel{" +
                "destinationId=" + destinationId +
                ", originId=" + originId +
                ", deliveryDuration=" + deliveryDuration +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
