package com.cybernite.flying.services;

import com.cybernite.flying.dto.FlightDto;
import com.cybernite.flying.entities.Flight;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.repo.FlightRepository;
import com.cybernite.flying.utils.FlyingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class FlightService {

    FlightRepository flightRepository;
    CompanyService companyService;
    ModelMapper mapper;

    public FlightDto addFlight(FlightDto request) throws  FlyingNotFoundExeption {
        if(request.getId() == null || isExist(request.getId())) {
            log.debug("generate id");
            while (true) {
                long id = FlyingUtils.generateId();
                if (!isExist(id)) {
                    request.setId(id);
                    break;
                }
            }
        }
        if(!companyService.isExist(request.getCompany())){
            throw new FlyingNotFoundExeption(String.format("Error create flight, company %s not found", request.getCompany()));
        }

        Flight flight = mapper.map(request, Flight.class);
        FlightDto response = mapper.map(flightRepository.save(flight), FlightDto.class);
        return response;
    }

    public FlightDto getFlight(long id) throws FlyingNotFoundExeption {
        Flight flight = flightRepository.findById(id).orElse(null);
        if(flight == null){
            throw new FlyingNotFoundExeption(String.format("Not found flight %d", id));
        }
        FlightDto response = mapper.map(flight, FlightDto.class);
        return response;
    }
    public FlightDto updateFlight(long id, FlightDto request) throws FlyingNotFoundExeption {
        if(flightRepository.findById(id).isEmpty()){
            throw new FlyingNotFoundExeption(String.format("Not found flight id ", id));
        }
        flightRepository.save(mapper.map(request, Flight.class));
        return request;
    }

    public FlightDto deleteFlight(long id) throws FlyingNotFoundExeption {
        Flight flight = flightRepository.findById(id).orElse(null);
        if(flight==null){
            throw new FlyingNotFoundExeption("Not found flight id " + id);
        }
        flightRepository.deleteById(id);
        return mapper.map(flight, FlightDto.class);
    }

    public boolean isExist(long id){
        return !flightRepository.findById(id).isEmpty();
    }

    public List<FlightDto> getAll() {
        return flightRepository.findAll().stream().map(flight -> mapper.map(flight, FlightDto.class)).toList();

    }
}
