package com.ian.monolith.repositories;

import com.ian.monolith.models.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CityRepository extends MongoRepository<City, String> {

    City findCityByName(String name);

    List<City> findAll();

}
