package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import org.springframework.beans.BeanUtils;

public class EmployeeMapper {
    public EmployeeResponse toResponse (Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();

        BeanUtils.copyProperties(employee, employeeResponse);

        return employeeResponse;
    }

    public Employee toEntity (EmployeeRequest employeeRequest) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeRequest, employee);

        return employee;
    }
}
