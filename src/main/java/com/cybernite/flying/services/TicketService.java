package com.cybernite.flying.services;

import com.cybernite.flying.dto.TicketDto;
import com.cybernite.flying.entities.Ticket;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.repo.TicketRepository;
import com.cybernite.flying.utils.FlyingUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class TicketService {

    TicketRepository ticketRepository;
    PassengerService passengerService;
    FlightService flightService;
    ModelMapper mapper;

    public TicketDto addTicket(TicketDto request) throws FlyingNotFoundExeption {
        if(request.getId() == null || isExist(request.getId())) {
            while (true) {
                long id = FlyingUtils.generateId();
                if (!isExist(id)) {
                    request.setId(id);
                    break;
                }
            }
        }
        if (!flightService.isExist(request.getFlightId())) {
            throw new FlyingNotFoundExeption(String.format("Error create ticket, flight %s not found", request.getFlightId()));
        }

        Ticket ticket = mapper.map(request, Ticket.class);
        TicketDto response = mapper.map(ticketRepository.save(ticket), TicketDto.class);
        return response;
    }

    public TicketDto getTicket(long id) throws FlyingNotFoundExeption {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            throw new FlyingNotFoundExeption(String.format("ticket %s not found", ticket));
        }
        return mapper.map(ticket, TicketDto.class);
    }

    public TicketDto updateTicket(long id, TicketDto request) throws FlyingNotFoundExeption {
        if (ticketRepository.findById(id).isEmpty()) {
            throw new FlyingNotFoundExeption(String.format("Not found ticket by id ", id));
        }
        ticketRepository.save(mapper.map(request, Ticket.class));
        return request;
    }

    public TicketDto removeTicket(long id) throws FlyingNotFoundExeption {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if (ticket == null) {
            throw new FlyingNotFoundExeption("Not found ticket by id " + id);
        }
        ticketRepository.deleteById(id);
        return mapper.map(ticket, TicketDto.class);
    }

    public boolean isExist(long id) {
        return !ticketRepository.findById(id).isEmpty();
    }

    public List<TicketDto> getAll() {
        return ticketRepository.findAll().stream().map(ticket -> mapper.map(ticket, TicketDto.class)).toList();
    }

    public TicketDto registrationPassenger(long ticketId, long passengerId) throws FlyingBudRequestExeption {
        if(!isExist(ticketId)){
            throw new FlyingBudRequestExeption("Not found ticket by id " + ticketId);
        }
        if(!passengerService.isExist(passengerId)){
            throw new FlyingBudRequestExeption("Not found passenger by id " + passengerId);
        }
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        ticket.setPassengerId(passengerId);
        Ticket response = ticketRepository.save(ticket);
        return mapper.map(response, TicketDto.class);
    }

}
