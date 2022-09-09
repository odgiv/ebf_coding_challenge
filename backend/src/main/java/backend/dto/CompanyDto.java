package backend.dto;

import backend.entity.Company;

public class CompanyDto {
    private Long id;
    private String name;
    private Double averageSalary;

    public CompanyDto(Company company) {
        this.id = company.getId();
        this.name = company.getName();
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

    public Double getAverageSalary() {
        return this.averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

}
