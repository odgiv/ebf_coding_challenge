package backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import backend.dto.CompanyDto;
import backend.dto.EmployeesPaginatedListResponseDto;
import backend.exception.BackendException;
import backend.service.CompanyService;

@RestController
public class CompanyController {

    private static final String COMPANY_PATH = "company";

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(COMPANY_PATH)
    public List<CompanyDto> getCompanies() {
        return this.companyService.getCompanies();
    }

    @GetMapping(COMPANY_PATH + "/{companyId}")
    public ResponseEntity<CompanyDto> getCompanyInfo(@PathVariable("companyId") final Long companyId)
            throws EntityNotFoundException, BackendException {
        CompanyDto companyDto = this.companyService.getCompany(companyId);
        return ResponseEntity.status(HttpStatus.OK).body(companyDto);
    }

    @GetMapping(COMPANY_PATH + "/{companyId}/employees")
    public EmployeesPaginatedListResponseDto getEmployees(@PathVariable("companyId") final Long companyId,
            @RequestParam final int page,
            @RequestParam(defaultValue = "20") final int pageSize,
            @RequestParam(defaultValue = "id") final String sortField,
            @RequestParam(defaultValue = "asc") final String sortDirection) throws BackendException {
        return this.companyService.getEmployees(companyId, page, pageSize, sortField, sortDirection);
    }

    @PostMapping(COMPANY_PATH)
    public ResponseEntity<CompanyDto> addCompany(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        CompanyDto companyDto = this.companyService.addCompany(name);
        return ResponseEntity.status(HttpStatus.OK).body(companyDto);
    }
}
