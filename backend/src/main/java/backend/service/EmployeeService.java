package backend.service;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import backend.dto.EmployeeDto;
import backend.entity.Company;
import backend.entity.Employee;
import backend.repository.CompanyRepository;
import backend.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    public EmployeeDto getEmployee(Long employeeId) throws EntityNotFoundException {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        EmployeeDto employeeDto = new EmployeeDto(employee, true);
        return employeeDto;
    }

    public EmployeeDto storeEmployeeDtoToDb(EmployeeDto employeeDto, Employee employee, Company company) {
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setEmail(employeeDto.getEmail());
        employee.setSalary(employeeDto.getSalary());
        employee.setAddress(employeeDto.getAddress());
        employee.setCompany(company);
        employee = employeeRepository.save(employee);
        return new EmployeeDto(employee, false);
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Company company = companyRepository.getReferenceById(employeeDto.getCompanyId());
        Employee newEmployee = new Employee();
        EmployeeDto newEmployeeDto = storeEmployeeDtoToDb(employeeDto, newEmployee, company);
        return newEmployeeDto;

    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) throws EntityNotFoundException {
        Company company = companyRepository.getReferenceById(employeeDto.getCompanyId());
        Employee employee = employeeRepository.getReferenceById(employeeDto.getId());
        EmployeeDto newEmployeeDto = storeEmployeeDtoToDb(employeeDto, employee, company);
        return newEmployeeDto;
    }

    public void deleteEmployee(Long employeeId) throws EntityNotFoundException {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        employeeRepository.delete(employee);

    }
}
