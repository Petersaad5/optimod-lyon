package com.optimodlyon.optimodlyon.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.optimodlyon.optimodlyon.model.DeliveryRequestModel;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({})
public class CourierModel {
    private Long id;
    private String name;



    public CourierModel(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public String toString() {
        return "CourierModel{id=" + id + ", name=" + name + " }";
    }


}
