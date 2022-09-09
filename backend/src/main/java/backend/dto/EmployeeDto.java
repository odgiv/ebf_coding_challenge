package backend.dto;

import backend.entity.Employee;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EmployeeDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "employee name should have at least 2 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, message = "employee surname should have at least 2 characters")
    private String surname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String address;

    private Double salary;

    private Long companyId;
    private CompanyDto company;

    public EmployeeDto() {
    }

    public EmployeeDto(Employee employee, boolean includeCompany) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.email = employee.getEmail();
        this.address = employee.getAddress();
        this.salary = employee.getSalary();
        if (includeCompany) {
            this.company = new CompanyDto(employee.getCompany());
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surName) {
        this.surname = surName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getSalary() {
        return this.salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }
}
