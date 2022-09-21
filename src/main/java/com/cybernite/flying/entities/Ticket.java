package com.cybernite.flying.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "tickets")
@Data
public class Ticket {
    @Id
    private Long id;
    private long cost;
    private String place;
    @Indexed
    private Long passengerId;
    @Indexed
    private Long flightId;
}
