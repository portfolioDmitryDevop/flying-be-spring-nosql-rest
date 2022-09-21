package com.cybernite.flying.controllers;

import com.cybernite.flying.common.Constants;
import com.cybernite.flying.dto.CompanyDto;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.services.CompanyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(Constants.Company.COMPANY_MAPPING)
@Validated
@Log4j2
@AllArgsConstructor
public class CompanyController {

    CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDto> postCompany(@Valid @RequestBody CompanyDto company) throws FlyingBudRequestExeption {
        CompanyDto response = companyService.addCompany(company);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public CompanyDto getCompany(@PathVariable("id") String name) throws FlyingNotFoundExeption {
        return companyService.getCompany(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDto> putCompany(@PathVariable("id") String name, @Valid @RequestBody CompanyDto company) throws FlyingNotFoundExeption, FlyingBudRequestExeption {
        company.setName(name);
        return companyService.isExist(name) ? new ResponseEntity<>(companyService.updateCompany(name, company), HttpStatus.OK)
                : new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public CompanyDto deleteCompany(@PathVariable("id") String name) throws FlyingNotFoundExeption {
        return companyService.removeCompany(name);
    }

    @GetMapping
    public List<CompanyDto> getAll(){
        return companyService.getAll();
    }



}
