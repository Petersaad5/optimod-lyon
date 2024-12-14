package com.optimodlyon.optimodlyon.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({})
public class Courier {
    private Long id;
    private String name;

    public Courier() {
    }

    public Courier(Long id, String name) {
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
