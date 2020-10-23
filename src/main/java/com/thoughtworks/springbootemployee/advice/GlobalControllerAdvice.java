package com.thoughtworks.springbootemployee.advice;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFoundException(EmployeeNotFoundException employeeNotFound){
        return new ErrorResponse(employeeNotFound.getMessage(), HttpStatus.NOT_FOUND.name());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompanyNotFoundException(CompanyNotFoundException companyNotFoundException){
        return new ErrorResponse(companyNotFoundException.getMessage(), HttpStatus.NOT_FOUND.name());
    }
}
