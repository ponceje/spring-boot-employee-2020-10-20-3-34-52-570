package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    public static final String EMPLOYEE_NOT_FOUND = "Employee Not Found";
    private final EmployeeRepository repository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
        this.employeeMapper = new EmployeeMapper();
    }

    public List<EmployeeResponse> getAll() {
        List<Employee> employees = repository.findAll();
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Employee saveEmployee = repository.save(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(saveEmployee);
    }

    public void delete(Integer employeeId) {
        repository.deleteById(employeeId);
    }

    public Employee update(Integer employeeId, Employee employeeRequest) {
        if(getById(employeeId).isPresent()){
            employeeRequest.setId(employeeId);
            return repository.save(employeeRequest);
        }
        throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
    }

    public List<Employee> getByGender(String gender) {
        return repository.findByGender(gender);
    }

    public List<Employee> getByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return repository.findAll(pageable).toList();
    }

    public Optional<Employee> getById(Integer id) {
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()){
            return employee;
        }
        throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
    }
}
