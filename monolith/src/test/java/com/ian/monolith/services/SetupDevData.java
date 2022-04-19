package com.ian.monolith.services;

import com.ian.monolith.models.City;
import com.ian.monolith.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//Todo find a better way to load setup data
//@SpringBootTest
public class SetupDevData {

    @Autowired
    CityRepository cityRepository;

    @Test
    public void loadCityData(){
        cityRepository.save(new City("Test", "1"));
    }
}