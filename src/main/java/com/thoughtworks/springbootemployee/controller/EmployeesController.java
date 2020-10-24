package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.service.EmployeeService;
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
        return employeeService.create(employeeRequest);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse get(@PathVariable Integer employeeId) {
        return employeeService.getById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable(required = true) Integer employeeId,
                           @RequestBody(required = true) EmployeeRequest employUpdate) {
        return employeeService.update(employeeId, employUpdate);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Integer employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam("gender") String gender){
        return  employeeService.getByGender(gender);
    }
    @GetMapping(params = {"page" , "pageSize"})
    public List<EmployeeResponse> getByPage(@RequestParam() int page,@RequestParam() int pageSize){
        return employeeService.getByPage(page,pageSize);
    }

}
