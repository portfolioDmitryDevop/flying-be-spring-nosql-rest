package com.cybernite.flying.entities;

import com.cybernite.flying.common.City;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Flight {
    @Id
    private Long id;
    private String company;
    private String ship;
    private City from;
    private City to;
    private LocalDateTime dateTime;

}
