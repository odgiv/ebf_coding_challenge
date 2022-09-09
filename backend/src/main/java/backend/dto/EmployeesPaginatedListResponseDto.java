package backend.dto;

import java.util.List;

public class EmployeesPaginatedListResponseDto {
    List<EmployeeDto> employees;
    long totalEmployees;

    public EmployeesPaginatedListResponseDto(List<EmployeeDto> employees, long total) {
        this.employees = employees;
        this.totalEmployees = total;

    }

    public List<EmployeeDto> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public long getTotalEmployees() {
        return this.totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

}
