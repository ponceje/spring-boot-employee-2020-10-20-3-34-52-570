package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    public EmployeesController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        List<Employee> employees = employeeService.getAll();
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        Employee saveEmployee = employeeService.create(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(saveEmployee);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse get(@PathVariable Integer employeeId) {
        return employeeMapper.toResponse(employeeService.getById(employeeId));
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable(required = true) Integer employeeId,
                                   @RequestBody(required = true) EmployeeRequest employUpdate) {
        return employeeMapper.toResponse(employeeService.update(employeeId, employeeMapper.toEntity(employUpdate)));
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Integer employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam("gender") String gender){
        List<Employee> byGender = employeeService.getByGender(gender);
        return byGender.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"page" , "pageSize"})
    public List<EmployeeResponse> getByPage(@RequestParam() int page,@RequestParam() int pageSize){
        List<Employee> employees = employeeService.getByPage(page, pageSize);
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

}
