package com.cybernite.flying.controllers;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.PassengerDto;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.services.PassengerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = (Constants.Passenger.PASSENGER_MAPPING))
@Log4j2
@Validated
public class PassengerController {

    private PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerDto addPassenger(@RequestBody @Valid PassengerDto passenger) throws FlyingBudRequestExeption {
        PassengerDto response = passengerService.addPassenger(passenger);
        return response;
    }

    @GetMapping("/{id}")
    public PassengerDto getPassenger(@PathVariable("id") @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id) throws FlyingNotFoundExeption {
        PassengerDto response = passengerService.getPassenger(id);
        return response;
    }

    @PutMapping("/{id}")
    public PassengerDto updatePassenger(@PathVariable("id") @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id, @RequestBody @Valid PassengerDto passenger) throws FlyingNotFoundExeption {
        PassengerDto response = passengerService.updatePassenger(id, passenger);
        return response;
    }

    @DeleteMapping("/{id}")
    public PassengerDto removePassenger(@PathVariable("id") @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id) throws FlyingNotFoundExeption {
        PassengerDto response = passengerService.removePassenger(id);
        return response;
    }

    @GetMapping
    public List<PassengerDto> getAllPassengers() {
            List<PassengerDto> response = passengerService.getAllPassenger();
            return response;
    }
}
