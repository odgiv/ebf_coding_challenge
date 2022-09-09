package backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import backend.dto.EmployeeDto;
import backend.entity.Employee;
import backend.service.EmployeeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void getEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setName("name");
        employee.setSurname("surName");
        employee.setAddress("address");
        employee.setSalary(123.0);

        EmployeeDto employeeDto = new EmployeeDto(employee, false);

        doReturn(employeeDto).when(employeeService).getEmployee(Long.valueOf(1));

        mockMvc.perform(get("/employee?employeeId=1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.surname").value(employee.getSurname()))
                .andExpect(jsonPath("$.address").value(employee.getAddress()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    public void getEmployeeReturnBadRequestWithoutEmployeeId() throws Exception {

        mockMvc.perform(get("/employee")).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteEmployeeReturnBadRequestWithoutEmployeeId() throws Exception {

        mockMvc.perform(
                delete("/employee"))
                .andExpect(status().isBadRequest());
    }
}
