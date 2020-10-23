package com.thoughtworks.springbootemployee.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String companyNotFound) {
        super(companyNotFound);
    }
}
