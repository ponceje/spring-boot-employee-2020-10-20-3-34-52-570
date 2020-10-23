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

    public EmployeeResponse update(Integer employeeId, EmployeeRequest employeeRequest) {
        if(getById(employeeId) != null){
            Employee employee = employeeMapper.toEntity(employeeRequest);
            employee.setId(employeeId);
            return employeeMapper.toResponse(repository.save(employee));
        }
        throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
    }

    public List<EmployeeResponse> getByGender(String gender) {
        List<Employee> employees = repository.findByGender(gender);
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    public List<EmployeeResponse> getByPage(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        List<Employee> employees = repository.findAll(pageable).toList();
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    public Optional<Employee> getById(Integer id) {
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()){
            return employee;
        }
        throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
    }
}
