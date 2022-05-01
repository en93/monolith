package com.ian.monolith;

import com.ian.monolith.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener {

    @Autowired
    private CityRepository cityRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        cityRepository.saveAll(DataSetupHelper.getAllCities());
    }
}
