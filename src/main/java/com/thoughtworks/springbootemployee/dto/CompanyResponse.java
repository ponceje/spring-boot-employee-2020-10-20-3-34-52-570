package com.thoughtworks.springbootemployee.dto;

import com.thoughtworks.springbootemployee.entity.Employee;

import java.util.List;

public class CompanyResponse {

    private Integer companyId;
    private String companyName;
    private List<Employee> employees;
    private Integer employeeNum;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer companyId, String companyName, Integer employeeNum, List<Employee> employees) {
        this.companyName = companyName;
        this.employeeNum = employeeNum;
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

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }
}
