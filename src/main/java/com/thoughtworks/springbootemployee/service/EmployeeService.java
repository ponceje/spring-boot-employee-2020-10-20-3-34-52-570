package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee create(Employee employee) {
        return repository.save(employee);
    }

    public void delete(int employeeId) {
         repository.delete(employeeId);
    }

    public Employee update(Employee employee) {
        return repository.update(employee);
    }

    public Employee search(String male) {
        return repository.search(male);
    }

    public List<Employee> getByPage(int page, int pageSize) {
        return repository.getByPage(page,pageSize);
    }
}
