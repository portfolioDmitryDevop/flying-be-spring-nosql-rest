package com.cybernite.flying.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class FlyingConfigurator {

    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
