package com.cybernite.flying;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class FlyingBeSpringSqlRestApplication {

    public static void main(String[] args) {
        log.debug("Start application");
        SpringApplication.run(FlyingBeSpringSqlRestApplication.class, args);
        log.debug("Application started");
    }

}
