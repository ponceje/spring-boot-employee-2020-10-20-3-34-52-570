package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CompanyIntegrationTest {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown(){
        companyRepository.deleteAll();
    }

    @Test
    void should_return_companies_when_getAll() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated());
        //WHEN and THEN
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyName").isString())
                .andExpect(jsonPath("$[0].employees").isArray())
                .andExpect(jsonPath("$[0].employeeNum").isNumber());
    }

    @Test
    void should_create_company_when_create_given_company_request() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        //WHEN and THEN

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyName").isString())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employeeNum").isNumber());
    }

    @Test
    void should_return_companies_when_getById() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated());

        //WHEN and THEN
        mockMvc.perform(get("/companies/{companyId}",companyRepository.findAll().get(0).getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyName").isString())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employeeNum").isNumber());
    }

    @Test
    void should_return_company_when_update() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated());

        String companyJson1 = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "    \"employeeNumber\": 100,\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"employee1\",\n" +
                "            \"age\": 22,\n" +
                "            \"gender\": \"female\",\n" +
                "            \"salary\": 200\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"employee2\",\n" +
                "            \"age\": 22,\n" +
                "            \"gender\": \"female\",\n" +
                "            \"salary\": 200\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        //WHEN and THEN
        mockMvc.perform(put("/companies/{companyId}",companyRepository.findAll().get(0).getCompanyId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyId").isNumber())
                .andExpect(jsonPath("$.companyName").isString())
                .andExpect(jsonPath("$.employees").isArray())
                .andExpect(jsonPath("$.employeeNum").isNumber());
    }

    @Test
    void should_return_company_when_delete() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated());

        //WHEN and THEN
        mockMvc.perform(get("/companies/{companyId}",companyRepository.findAll().get(0).getCompanyId()))
                .andExpect(status().isOk());
    }

    @Test
    void should_return_company_when_getByPage() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated());

        //WHEN and THEN
        mockMvc.perform(get("/companies?page=0&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].companyId").isNumber())
                .andExpect(jsonPath("$[0].companyName").isString())
                .andExpect(jsonPath("$[0].employees").isArray())
                .andExpect(jsonPath("$[0].employeeNum").isNumber());
    }

    @Test
    void should_return_companies_when_getEmployees() throws Exception {
        //GIVEN
        String companyJson = "{\n" +
                "    \"companyName\": \"OOCL\",\n" +
                "     \"employees\": [\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "            \n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Bob Smith\",\n" +
                "            \"age\": 10,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 1000\n" +
                "        }\n" +
                "        ]\n" +
                "}";
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(companyJson))
                .andExpect(status().isCreated());

        //WHEN and THEN
        mockMvc.perform(get("/companies/{companyId}/employees",companyRepository.findAll().get(0).getCompanyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].age").isNumber())
                .andExpect(jsonPath("$[0].gender").isString())
                .andExpect(jsonPath("$[0].salary").isNumber());
    }

}
