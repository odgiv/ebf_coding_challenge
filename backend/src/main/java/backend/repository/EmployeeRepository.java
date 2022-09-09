package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import backend.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByCompany_Id(Long id, Pageable pageable);

    @Query(value = "SELECT COUNT(e) FROM Employee e WHERE e.company.id = ?1")
    Long countEmployeesByCompanyId(Long companyId);
}
