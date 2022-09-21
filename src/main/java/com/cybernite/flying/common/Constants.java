package com.cybernite.flying.common;

public interface Constants {

    long MIN_ID = 100_000_000;
    long MAX_ID = 999_000_000;

    interface Passenger {
        String PASSENGER_MAPPING = "/passengers";
    }

    interface Company {
        String COMPANY_MAPPING = "/companies";
    }

    interface Flight {
        String FLIGHT_MAPPING = "/flights";
    }

    interface  Ticket {
        String TICKET_MAPPING = "/tickets";
        String REGISTRAION_PASSENGER = "/registration";
    }

    interface AUTH {
        String LOGIN_MAPPING = "/auth";
        String LOGIN_SIGNIN = "/signin";
        String LOGIN_SIGNUP = "/signup";
    }

    interface ROLE {
        String ADMIN = "ADMIN";
        String USER = "USER";
    }

    interface WebSocket {
        String ENDPOINT = "/websocket-flying";
        String TOPIC = "/topic/flying";
    }



}
