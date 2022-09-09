package backend.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import javax.persistence.EntityNotFoundException;
import backend.dto.CompanyDto;
import backend.dto.EmployeeDto;
import backend.dto.EmployeesPaginatedListResponseDto;
import backend.entity.Company;
import backend.entity.Employee;
import backend.exception.BackendException;
import backend.repository.CompanyRepository;
import backend.repository.EmployeeRepository;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<CompanyDto> getCompanies() {

        List<Company> companies = companyRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return companies.stream()
                .map(c -> new CompanyDto(c))
                .collect(Collectors.toList());
    }

    public CompanyDto getCompany(Long companyId) throws EntityNotFoundException, BackendException {
        try {
            Company company = companyRepository.getReferenceById(companyId);
            Double averageSalary = companyRepository.salaryAvg(companyId);
            CompanyDto companyDto = new CompanyDto(company);
            companyDto.setAverageSalary(averageSalary);
            return companyDto;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new BackendException("Exception happened in getCompany: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public EmployeesPaginatedListResponseDto getEmployees(Long companyId, int page, int pageSize, String sortField,
            String sortDirection) throws BackendException {
        try {
            Sort sort = Sort.by(Sort.Direction.fromOptionalString(sortDirection).orElse(Sort.Direction.ASC), sortField);
            PageRequest pageable = PageRequest.of(page, pageSize, sort);
            Page<Employee> employees = employeeRepository.findAllByCompany_Id(companyId, pageable);
            List<EmployeeDto> employeesDto = employees.stream().map(e -> new EmployeeDto(e, false))
                    .collect(Collectors.toList());
            Long count = employeeRepository.countEmployeesByCompanyId(companyId);
            return new EmployeesPaginatedListResponseDto(employeesDto, count);
        } catch (Exception e) {
            throw new BackendException("Exception happened in getEmployees: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Double getAverageSalary(Long companyId) {
        return companyRepository.salaryAvg(companyId);
    }

    public CompanyDto addCompany(String name) {
        Company newCompany = new Company();
        newCompany.setName(name);
        newCompany = companyRepository.save(newCompany);
        return new CompanyDto(newCompany);
    }
}
