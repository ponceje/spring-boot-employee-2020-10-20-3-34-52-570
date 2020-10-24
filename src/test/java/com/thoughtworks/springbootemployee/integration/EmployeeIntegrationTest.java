package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.apiguardian.api.API;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.Retention;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    private void tearDown(){
        employeeRepository.deleteAll();
        System.out.println("delete");
    }

    @Test
    void should_return_employees_when_getAll() throws Exception {
        // given
        Employee employee = new Employee(1,"Christian",20,"male",10000);
        employeeRepository.save(employee);

        // when then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Christian"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(10000));

    }

    @Test
    void should_create_employee_when_perform_post_given_employee_request() throws Exception {
        // given
        String employeeAsJsoin = "{\n" +
                "        \"name\":\"Christian\",\n" +
                "        \"age\":20,\n" +
                "        \"gender\":\"male\",\n" +
                "        \"salary\":10000\n" +
                "}";

        // when then
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJsoin))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Christian"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(10000));
    }

    @Test
    void should_return_employee_when_getById_given_employeeId() throws Exception {
        // given
        Employee employee = new Employee(1,"Christian",20,"male",10000);
        employeeRepository.save(employee);

        // when then
        mockMvc.perform(get("/employees/{employeeId}",employeeRepository.findAll().get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Christian"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(10000));
    }

    @Test
    void should_update_employee_when_update_given_employee_request() throws Exception {
        // given
        Employee employee = new Employee(1,"Christian",20,"male",10000);
        employeeRepository.save(employee);
        String employeeAsJsoin = "{\n" +
                "        \"name\":\"Christian Tayag\",\n" +
                "        \"age\":20,\n" +
                "        \"gender\":\"male\",\n" +
                "        \"salary\":10000\n" +
                "}";

        // when then
        mockMvc.perform(put("/employees/{employeeId}",employeeRepository.findAll().get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAsJsoin))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Christian Tayag"))
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(10000));
    }

    @Test
    void should_delete_employee_when_delete_given_companyId() throws Exception {
        // given
        Employee employee = new Employee(1,"Christian",20,"male",10000);
        employeeRepository.save(employee);

        // when then
        List<Employee> employees = employeeRepository.findAll();
        mockMvc.perform(delete("/employees/{employeeId}",employees.get(0).getId()))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_male_employees_when_getByGender_given_params_gender_male() throws Exception {
        // given
        Employee employee = new Employee(1,"Christian",20,"male",10000);
        Employee employee1 = new Employee(2,"Christian Tayag",20,"male",10000);
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when then
        mockMvc.perform(get("/employees?gender=male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Christian"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(10000));
    }

    @Test
    void should_return_employees_when_getByPage_given_page_and_pagesize() throws Exception {
        // given
        Employee employee = new Employee(1,"Christian",20,"male",10000);
        Employee employee1 = new Employee(2,"Christian Tayag",20,"male",10000);
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when then
        mockMvc.perform(get("/employees?page={page}&pagesize={pagesize}",0,5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Christian"))
                .andExpect(jsonPath("$[0].age").value(20))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(10000));
    }
}
