package com.optimodlyon.optimodlyon.model;
import com.optimodlyon.optimodlyon.model.DeliveryRequestModel;
import java.util.List;

public class CourierModel {
    private Long id;
    private String name;
    private List<DeliveryRequestModel> deliveries;

    public CourierModel() {
    }

    public CourierModel(Long id, String name, List<DeliveryRequestModel> deliveries) {
        this.id = id;
        this.name = name;
        this.deliveries = deliveries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

}
