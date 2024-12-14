package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.CourierModel;
import com.optimodlyon.optimodlyon.model.Data;
import com.optimodlyon.optimodlyon.model.TourModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {
    private Data data;

    public DataService() {
        this.data = new Data();
    }

    public void initData(List<TourModel> tours, List<CourierModel> couriers) {
        this.data = new Data();
        this.data.setTours(tours);
        this.data.setCouriers(couriers);
    }

    public void initData() {
        this.data = new Data();
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}