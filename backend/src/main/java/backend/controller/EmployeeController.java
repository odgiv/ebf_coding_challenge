package backend.controller;

import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import backend.dto.EmployeeDto;
import backend.service.EmployeeService;

@RestController
public class EmployeeController {

    private static final String EMPLOYEE_PATH = "employee";

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(EMPLOYEE_PATH)
    public ResponseEntity<EmployeeDto> getEmployee(@RequestParam Long employeeId) {

        EmployeeDto employeeDto = employeeService.getEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDto);
    }

    @PostMapping(EMPLOYEE_PATH)
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto createEmployeeRequestDto) {

        EmployeeDto newEmployeeDto = employeeService.addEmployee(createEmployeeRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployeeDto);
    }

    @PutMapping(EMPLOYEE_PATH)
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto updateEmployeeRequestDto)
            throws EntityNotFoundException {

        EmployeeDto updatedEmployeeDto = employeeService.updateEmployee(updateEmployeeRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedEmployeeDto);
    }

    @DeleteMapping(EMPLOYEE_PATH)
    public ResponseEntity<String> deleteEmployee(@RequestBody Map<String, Long> body)
            throws EntityNotFoundException, HttpMessageNotReadableException {
        Long employeeId = body.get("employeeId");
        employeeService.deleteEmployee(employeeId);

        return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted employee with id: " + employeeId);
    }
}
