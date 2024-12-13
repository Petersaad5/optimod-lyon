package com.optimodlyon.optimodlyon.service;

import com.optimodlyon.optimodlyon.model.Data;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    private Data data;

    public DataService() {
        this.data = new Data();
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