package com.cybernite.flying.controllers;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.TicketDto;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.services.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(Constants.Ticket.TICKET_MAPPING)
@Validated
@AllArgsConstructor
public class TicketController {

    TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDto> addTicket(@Valid @RequestBody TicketDto request) throws FlyingNotFoundExeption {
        return new ResponseEntity<>(ticketService.addTicket(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public TicketDto getTicket(@PathVariable @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id) throws FlyingNotFoundExeption {
        return ticketService.getTicket(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketDto> putTicket(@PathVariable @Min(Constants.MIN_ID) @Max(Constants.MAX_ID)long id, @Valid @RequestBody TicketDto request) throws FlyingNotFoundExeption {
        request.setId(id);
        return ticketService.isExist(id) ? new ResponseEntity<>(ticketService.updateTicket(id, request), HttpStatus.OK)
                : new ResponseEntity<>(ticketService.addTicket(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public TicketDto deleteTicket(@PathVariable @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long id) throws FlyingNotFoundExeption {
        return ticketService.removeTicket(id);
    }

    @GetMapping
    public List<TicketDto> getAll(){
        return ticketService.getAll();
    }

    @PutMapping(Constants.Ticket.REGISTRAION_PASSENGER)
    public TicketDto registrationPassenger(@RequestParam @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long ticketId, @RequestParam @Min(Constants.MIN_ID) @Max(Constants.MAX_ID) long passengerId) throws FlyingBudRequestExeption {
        return ticketService.registrationPassenger(ticketId, passengerId);
    }


}
