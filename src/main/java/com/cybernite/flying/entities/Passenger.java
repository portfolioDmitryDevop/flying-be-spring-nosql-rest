package com.cybernite.flying.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "passengers")
@Data
public class Passenger {
    @Id
    private long id;
    private String firstName;
    private String lastName;
}
