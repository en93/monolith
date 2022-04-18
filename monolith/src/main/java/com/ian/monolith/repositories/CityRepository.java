package com.ian.monolith.repositories;

import com.ian.monolith.models.City;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//todo test save

public interface CityRepository extends MongoRepository<City, String> {

    //todo does this need annotations?
    City findCityById(String id);

    List<City> findAll();

}

//public interface ItemRepository extends MongoRepository<GroceryItem, String> {
//
//    @Query("{name:'?0'}")
//    GroceryItem findItemByName(String name);
//
//    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
//    List<GroceryItem> findAll(String category);
//
//    public long count();
//
//}