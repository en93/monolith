package com.ian.monolith.repositories;

import com.ian.monolith.models.City;
import com.ian.monolith.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

//todo test save

public interface EventRepository extends MongoRepository<Event, String> {


}
