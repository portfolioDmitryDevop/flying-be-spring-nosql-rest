package com.cybernite.flying.dto;

import com.cybernite.flying.common.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDto {
    private Long id;
    @NotEmpty
    private String company;
    @NotEmpty
    private String ship;
    @NotNull
    private City from;
    @NotNull
    private City to;
    @NotNull
    private LocalDateTime dateTime;

}
