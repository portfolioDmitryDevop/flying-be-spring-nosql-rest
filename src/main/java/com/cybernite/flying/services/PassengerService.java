package com.cybernite.flying.services;

import com.cybernite.flying.dto.PassengerDto;
import com.cybernite.flying.entities.Passenger;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.repo.PassengerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PassengerService {

    PassengerRepository passengerRepository;
    ModelMapper mapper;

    public PassengerDto addPassenger(PassengerDto request) throws FlyingBudRequestExeption {
        if(isExist(request.getId())){
            throw new FlyingBudRequestExeption(String.format("passenger with id %s exists", request.getId()));
        }
        Passenger passenger = mapper.map(request, Passenger.class);
        PassengerDto response = mapper.map(passengerRepository.save(passenger), PassengerDto.class);
        return response;
    }

    public PassengerDto getPassenger(long id) throws FlyingNotFoundExeption {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        if(passenger == null){
            throw new FlyingNotFoundExeption("Not found passenger by id {} " + id);
        }
        PassengerDto response = mapper.map(passenger, PassengerDto.class);
        return response;
    }

    public PassengerDto removePassenger(long id) throws FlyingNotFoundExeption {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        if(passenger == null){
            throw new FlyingNotFoundExeption("Not found passenger by id " + id);
        }
        PassengerDto response = mapper.map(passenger, PassengerDto.class);
        passengerRepository.deleteById(id);
        return response;
    }

    public PassengerDto updatePassenger(long id, PassengerDto request) throws FlyingNotFoundExeption {
        Passenger passenger = passengerRepository.findById(id).orElse(null);
        if(passenger==null){
            throw new FlyingNotFoundExeption("Not found flight id " + id);
        }
        passengerRepository.save(mapper.map(request, Passenger.class));
        return mapper.map(passenger, PassengerDto.class);
    }

    public List<PassengerDto> getAllPassenger() {
        return passengerRepository.findAll().stream().map(passenger -> mapper.map(passenger, PassengerDto.class)).toList();
    }

    public boolean isExist(Long id){
        return !passengerRepository.findById(id).isEmpty();
    }



}
