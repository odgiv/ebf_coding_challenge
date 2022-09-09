package backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT avg(e.salary) FROM Employee e JOIN e.company c WHERE c.id = ?1")
    public Double salaryAvg(Long companyId);
}
