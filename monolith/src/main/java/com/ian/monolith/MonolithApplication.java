package com.ian.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

}

/*
TODO

pagination of results
Batch to clear cache
add gps awareness
split into microservices
create proxy rest server to handle eventfinda api
mobile responsive
More pages
Better event html
Docker
Refactor code
update unit tests
better externalize connections and env details
bootstrap css
put in aws

 */
