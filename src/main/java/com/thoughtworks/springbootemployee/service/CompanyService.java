package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    public static final String COMPANY_NOT_FOUND = "Company Not Found";
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company create(Company company){
        return companyRepository.save(company);
    }

    public Company findByCompanyId(Integer companyId){
        Company company = companyRepository.findById(companyId).get();
        if(company!=null){
            return company;
        }
        throw new CompanyNotFoundException(COMPANY_NOT_FOUND);
    }

    public Company update(Integer companyId, Company companyUpdate){
        Company company = findByCompanyId(companyId);
        if (company!=null){
            company.setCompanyName(companyUpdate.getCompanyName());
            return companyRepository.save(company);
        }
        throw new CompanyNotFoundException(COMPANY_NOT_FOUND);
    }

    public void delete(Integer companyId){
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresent(companyRepository::delete);
    }

    public List<Company> getByPage(int page, int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return companyRepository.findAll(pageable).toList();
    }

    public List<Employee> getEmployees(Integer companyId){
        return Optional.ofNullable(companyRepository.findById(companyId).get().getEmployees())
                .orElseThrow(()->new CompanyNotFoundException(COMPANY_NOT_FOUND));
    }
}
