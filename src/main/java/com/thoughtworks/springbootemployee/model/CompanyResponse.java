package com.thoughtworks.springbootemployee.model;

import javax.persistence.*;
import java.util.List;

public class CompanyResponse {

    private Integer companyId;
    private String companyName;
    private List<Employee> employees;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer companyId, String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
