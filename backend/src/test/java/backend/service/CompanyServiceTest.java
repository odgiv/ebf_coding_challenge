package backend.service;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import backend.entity.Company;
import backend.exception.BackendException;
import backend.repository.CompanyRepository;
import backend.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CompanyService companyService;

    @Test
    void getCompany() throws EntityNotFoundException, BackendException {
        Long companyId = Long.valueOf(1);

        // when
        doReturn(mock(Company.class)).when(companyRepository).getReferenceById(companyId);
        doReturn(1.0).when(companyRepository).salaryAvg(companyId);

        companyService.getCompany(companyId);

        // then
        Mockito.verify(companyRepository, Mockito.times(1)).getReferenceById(companyId);
        Mockito.verify(companyRepository, Mockito.times(1)).salaryAvg(companyId);

    }

    @Test
    void getEmployees() throws BackendException {
        // given
        var page = 0;
        var pageSize = 10;
        String sortField = "name";
        var pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, sortField));

        Long companyId = Long.valueOf(1);

        // when
        doReturn(mock(Page.class)).when(employeeRepository).findAllByCompany_Id(companyId, pageable);
        companyService.getEmployees(companyId, page, pageSize, sortField, Sort.Direction.ASC.name());

        // then
        Mockito.verify(employeeRepository, Mockito.times(1)).findAllByCompany_Id(companyId, pageable);

    }

    @Test
    void getCompanies() {
        doReturn(new ArrayList<Company>()).when(companyRepository).findAll(Sort.by(Sort.Direction.ASC, "name"));
        companyService.getCompanies();

        Mockito.verify(companyRepository, Mockito.times(1)).findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Test
    void addCompany() {
        String companyName = "company name";
        Company company = new Company();
        company.setName(companyName);
        when(companyRepository.save(Mockito.any())).thenReturn(company);
        companyService.addCompany(companyName);

        // save on repository is called
        Mockito.verify(companyRepository, times(1)).save(Mockito.any());
    }

    @Test
    void getAverageSalary() {
        Long companyId = Long.valueOf(1);
        when(companyRepository.salaryAvg(companyId)).thenReturn(1.0);

        companyService.getAverageSalary(companyId);

        Mockito.verify(companyRepository, times(1)).salaryAvg(companyId);
    }
}
