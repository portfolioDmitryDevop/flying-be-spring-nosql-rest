package com.cybernite.flying.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {
    @NotEmpty
    private String name;
    private Date crDate;
}
