package com.cybernite.flying.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketDto {

    private Long id;
    @NotNull
    private Long cost;
    @NotNull
    private String place;
    private Long passengerId;
    @NotNull
    private Long flightId;

}
