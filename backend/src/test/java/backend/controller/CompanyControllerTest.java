package backend.controller;

import backend.dto.CompanyDto;
import backend.dto.EmployeeDto;
import backend.dto.EmployeesPaginatedListResponseDto;
import backend.entity.Company;
import backend.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    public void getCompaniesReturnsOk() throws Exception {
        this.mockMvc.perform(get("/company")).andExpect(status().isOk());
    }

    @Test
    public void getCompanyInfo() throws Exception {
        Company company = new Company();
        company.setId(Long.valueOf(1));
        company.setName("apple");
        CompanyDto companyDto = new CompanyDto(company);
        doReturn(companyDto).when(companyService).getCompany(Long.valueOf(1));

        mockMvc.perform(
                get("/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(company.getName()))
                .andExpect(jsonPath("$.id").value(company.getId()));
    }

    @Test
    public void getCompanyInfoReturnsBadRequestForUnknownCompanyId() throws Exception {

        doThrow(EntityNotFoundException.class).when(companyService).getCompany(Long.valueOf(1));

        mockMvc.perform(
                get("/company/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getEmployeesReturnsBadRequestWithoutPageParam() throws Exception {
        mockMvc.perform(
                get("/company/1/employees"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getEmployees() throws Exception {
        EmployeeDto employee1 = new EmployeeDto();
        EmployeeDto employee2 = new EmployeeDto();
        List<EmployeeDto> empList = new ArrayList<>();
        empList.add(employee1);
        empList.add(employee2);

        EmployeesPaginatedListResponseDto employeesListDto = new EmployeesPaginatedListResponseDto(empList, 2);
        doReturn(employeesListDto).when(companyService).getEmployees(Long.valueOf(1), 1, 10, "id", "asc");

        mockMvc.perform(
                get("/company/1/employees?page=1&pageSize=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEmployees").value(2));

    }

}
