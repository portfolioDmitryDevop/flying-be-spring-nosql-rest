package com.cybernite.flying.services;

import com.cybernite.flying.dto.CompanyDto;
import com.cybernite.flying.entities.Company;
import com.cybernite.flying.exeptions.FlyingBudRequestExeption;
import com.cybernite.flying.exeptions.FlyingNotFoundExeption;
import com.cybernite.flying.repo.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class CompanyService {

    CompanyRepository companyRepository;
    ModelMapper mapper;

    public CompanyDto addCompany(CompanyDto request) throws FlyingBudRequestExeption {
        if(!companyRepository.findById(request.getName()).isEmpty()){
            throw new FlyingBudRequestExeption(String.format("company %s exist in db", request.getName()));
        }
        Company company = mapper.map(request, Company.class);
        company.generateCrDate();
        CompanyDto response = mapper.map(companyRepository.save(company), CompanyDto.class);
        return response;
    }

    public CompanyDto getCompany(String name) throws FlyingNotFoundExeption {
        Company company = companyRepository.findById(name).orElse(null);
        if(company==null){
            throw new FlyingNotFoundExeption(String.format("company %s not found", name));
        }
        return mapper.map(company, CompanyDto.class);
    }

    public CompanyDto updateCompany(String name, CompanyDto request) throws FlyingNotFoundExeption {
        Company company = companyRepository.findById(name).orElse(null);
        if(company==null){
            throw new FlyingNotFoundExeption(String.format("company %s not found", name));
        }
        CompanyDto response = mapper.map(company, CompanyDto.class);
        Company newCompany = mapper.map(request, Company.class);
        newCompany.generateCrDate();
        companyRepository.save(newCompany);
        return response;
    }

    public CompanyDto removeCompany(String name) throws  FlyingNotFoundExeption {
        Company company = companyRepository.findById(name).orElse(null);
        if(company==null){
            throw new FlyingNotFoundExeption(String.format("company %s not found", name));
        }
        CompanyDto response = mapper.map(company, CompanyDto.class);
        companyRepository.deleteById(name);
        return response;
    }

    public boolean isExist(String name){
        return !companyRepository.findById(name).isEmpty();
    }

    public List<CompanyDto> getAll(){
        return companyRepository.findAll().stream().map(company -> mapper.map(company, CompanyDto.class)).toList();
    }

}
