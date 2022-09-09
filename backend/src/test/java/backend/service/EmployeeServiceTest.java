package backend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import org.mockito.Mockito;
import org.junit.jupiter.api.Test;

import backend.dto.EmployeeDto;
import backend.entity.Company;
import backend.entity.Employee;
import backend.repository.CompanyRepository;
import backend.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void getEmployee() throws EntityNotFoundException {
        Long employeeId = Long.valueOf(1);
        Employee employeeMock = mock(Employee.class);
        // when
        doReturn(employeeMock).when(employeeRepository).getReferenceById(employeeId);
        doReturn(mock(Company.class)).when(employeeMock).getCompany();
        employeeService.getEmployee(employeeId);
        // then
        Mockito.verify(employeeRepository,
                Mockito.times(1)).getReferenceById(employeeId);
    }

    @Test
    void storeEmployeeDtoToDb() {
        doReturn(mock(Employee.class)).when(employeeRepository).save(Mockito.any());
        employeeService.storeEmployeeDtoToDb(mock(EmployeeDto.class), mock(Employee.class), mock(Company.class));

        Mockito.verify(employeeRepository,
                Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void addEmployee() {
        doReturn(mock(Employee.class)).when(employeeRepository).save(Mockito.any());
        employeeService.addEmployee(mock(EmployeeDto.class));
        Mockito.verify(employeeRepository,
                Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void updateEmployee() {
        Long companyId = Long.valueOf(1);
        Long employeeId = Long.valueOf(1);

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setCompanyId(companyId);

        doReturn(mock(Employee.class)).when(employeeRepository).save(Mockito.any());
        doReturn(mock(Company.class)).when(companyRepository).getReferenceById(companyId);
        doReturn(mock(Employee.class)).when(employeeRepository).getReferenceById(employeeId);

        employeeService.updateEmployee(employeeDto);

        Mockito.verify(employeeRepository,
                Mockito.times(1)).getReferenceById(employeeId);

        Mockito.verify(companyRepository,
                Mockito.times(1)).getReferenceById(companyId);

        Mockito.verify(employeeRepository,
                Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deleteEmployee() {
        Long employeeId = Long.valueOf(1);
        doNothing().when(employeeRepository).delete(Mockito.any());
        employeeService.deleteEmployee(employeeId);
        Mockito.verify(employeeRepository, times(1)).delete(Mockito.any());
    }
}