package com.cybernite.flying.repo;

import com.cybernite.flying.entities.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, Long> {
}
