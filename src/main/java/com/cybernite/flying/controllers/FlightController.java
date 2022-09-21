package com.cybernite.flying.controllers;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.FlightDto;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.services.FlightService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(Constants.Flight.FLIGHT_MAPPING)
@Validated
@Log4j2
public class FlightController {

    FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightDto> addFlight(@Valid @RequestBody FlightDto flightDto) throws  FlyingNotFoundExeption {
        return new ResponseEntity<>(flightService.addFlight(flightDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public FlightDto getFlight(@PathVariable @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id) throws FlyingNotFoundExeption {
        return flightService.getFlight(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDto> putFlight(@PathVariable @Min(Constants.MIN_ID) @Max(Constants.MAX_ID)long id, @Valid @RequestBody FlightDto flightDto) throws FlyingNotFoundExeption {
        flightDto.setId(id);
        return flightService.isExist(id) ? new ResponseEntity<>(flightService.updateFlight(id, flightDto), HttpStatus.OK)
                : new ResponseEntity<>(flightService.addFlight(flightDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public FlightDto deleteFlight(@PathVariable @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id) throws FlyingNotFoundExeption {
        return flightService.deleteFlight(id);
    }

    @GetMapping
    public List<FlightDto> getAll(){
        return flightService.getAll();
    }


}
