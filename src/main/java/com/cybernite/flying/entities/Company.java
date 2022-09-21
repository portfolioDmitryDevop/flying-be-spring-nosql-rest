package com.cybernite.flying.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "companies")
@Data
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    private String name;
    private Date crDate;

    public void generateCrDate(){
        if(crDate==null){
            crDate = new Date();
        }
    }


}
