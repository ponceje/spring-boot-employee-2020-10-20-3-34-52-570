package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepositoryLegacy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    //Givens
    EmployeeRepositoryLegacy repository = Mockito.mock(EmployeeRepositoryLegacy.class);
    Employee employeeRequest = new Employee(1, "junjun", 10, "male", 200 );
    List<Employee> expectedEmployees = asList(new Employee(),new Employee());

    @Test
    void should_get_all_when_get_employees(){
        //GIVEN
        when(repository.findAll()).thenReturn(expectedEmployees);
        EmployeeService service = new EmployeeService(repository);
        //WHEN
        List<Employee> actual = service.getAll();
        //THEN
        Assertions.assertEquals(2, actual.size());
    }
    @Test
    void should_create_employee_when_create_given_employee_request(){
        //GIVEN
        when(repository.save(employeeRequest)).thenReturn(employeeRequest);
        EmployeeService employeeService = new EmployeeService(repository);
        //WHEN
        Employee actual = employeeService.create(employeeRequest);
        //THEN
        Assertions.assertEquals(1, actual.getId());
    }

    @Test
    void should_delete_employee_when_delete_given_employee_request(){
        //given
        EmployeeService employeeService = new EmployeeService(repository);
        // when
        employeeService.delete(1);
        //then
        verify(repository).delete(1);

    }
    @Test
    void should_get_updated_employeeId_when_update_given_employee_request(){
        //GIVEN
        when(repository.update(1,employeeRequest)).thenReturn(employeeRequest);
        //WHEN
        EmployeeService employeeService = new EmployeeService(repository);
        Employee actual = employeeService.update(1, employeeRequest);
        //THEN
        Assertions.assertEquals(1, actual.getId());
    }
    @Test
    void should_get_employee_with_correct_gender_when_search_given_employee_request(){
        //GIVEN
        when(repository.getByGender("male")).thenReturn(expectedEmployees);
        //WHEN
        EmployeeService employeeService = new EmployeeService(repository);
        List<Employee> actual = employeeService.getByGender("male");
        //THEN
        Assertions.assertEquals(2, actual.size());
    }
    @Test
    void should_get_correct_page_size_when_given_getPage_employee_request(){
        //GIVEN
        when(repository.getByPage(1, 5)).thenReturn(expectedEmployees);
        EmployeeService service = new EmployeeService(repository);
        //WHEN
        List<Employee> actual = service.getByPage(1,5);
        //THEN
        Assertions.assertEquals(2, actual.size());
    }
    @Test
    void should_get_employee_with_correct_id_when_search_given_employee_request(){
        //GIVEN
        when(repository.getById(1)).thenReturn(employeeRequest);
        //WHEN
        EmployeeService employeeService = new EmployeeService(repository);
        Employee actual = employeeService.getById(1);
        //THEN
        Assertions.assertEquals(1, actual.getId());
    }

    

}