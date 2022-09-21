package com.cybernite.flying.repo;

import com.cybernite.flying.entities.Passenger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "passengers", path = "passengers")
public interface PassengerRepository extends MongoRepository<Passenger, Long> {
    List<Passenger> findByLastName(@Param("name") String name);
}
