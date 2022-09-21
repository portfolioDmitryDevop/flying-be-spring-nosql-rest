package com.cybernite.flying;

import com.cybernite.flying.common.City;
import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.CompanyDto;
import com.cybernite.flying.dto.FlightDto;
import com.cybernite.flying.dto.PassengerDto;
import com.cybernite.flying.dto.TicketDto;
import com.cybernite.flying.repo.CompanyRepository;
import com.cybernite.flying.repo.FlightRepository;
import com.cybernite.flying.repo.PassengerRepository;
import com.cybernite.flying.repo.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
@WithUserDetails("dmitry@mail.ru")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlyingBeSpringSqlRestApplicationTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    TicketRepository ticketRepository;

    CompanyDto companyDto;
    FlightDto flightDto;
    PassengerDto passengerDto;
    TicketDto ticketDto;

    @PostConstruct
    public void initDb() throws Exception {
        companyRepository.deleteAll();
        passengerRepository.deleteAll();
        flightRepository.deleteAll();
        ticketRepository.deleteAll();
    }

    @Test
    @Order(1)
    void contextLoads() {
        assertNotNull(mockMvc);
    }

    @Test
    @Order(2)
    public void createCompany() throws Exception {
        companyDto = CompanyDto.builder().name("Pegas").crDate(new Date()).build();
        this.mockMvc.perform(post(Constants.Company.COMPANY_MAPPING)
                .content(mapper.writeValueAsString(companyDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pegas"));

    }

    @Test
    @Order(3)
    public void createPassenger() throws Exception {
        passengerDto = PassengerDto.builder().id(854234986L).firstName("Jon").lastName("Miller").build();
        this.mockMvc.perform(post(Constants.Passenger.PASSENGER_MAPPING)
                        .content(mapper.writeValueAsString(passengerDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(854234986L));

    }

    @Test
    @Order(4)
    public void createFlight() throws Exception {
        createCompany();
        flightDto = FlightDto.builder().id(345234567L).company("Pegas").from(City.Moscow)
                .to(City.TelAviv).dateTime(LocalDateTime.of(2022,10,15, 15, 20)).ship("Boeng727").build();

        this.mockMvc.perform(post(Constants.Flight.FLIGHT_MAPPING)
                        .content(mapper.writeValueAsString(flightDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(345234567L));
    }

    @Test
    @Order(5)
    public void createTicket() throws Exception {
        createPassenger();
        createFlight();
        ticketDto = TicketDto.builder().place("10A").id(243265789L).cost(1500L).flightId(345234567L).passengerId(854234986L).build();
        this.mockMvc.perform(post(Constants.Ticket.TICKET_MAPPING)
                        .content(mapper.writeValueAsString(ticketDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(243265789L));
    }

}
