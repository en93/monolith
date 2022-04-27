package com.ian.monolith.repositories;


import com.ian.monolith.models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


//Contains methods from MongoRepository such as findAll
public interface EventRepository extends MongoRepository<Event, String> {

}
