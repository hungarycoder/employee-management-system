package com.hungarycoder.ems.repository;

import com.hungarycoder.ems.domain.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("""
        SELECT e FROM Employee e
        WHERE e.name LIKE CONCAT(:name, '%')
        AND LOWER(e.email) LIKE LOWER(CONCAT('%', :email))
        ORDER BY e.name DESC""")
    List<Employee> search(String name, String email);

    List<Employee> findByNameStartingWithAndEmailEndingWithIgnoreCaseOrderByNameDesc(String name,
        String email);

    Optional<Employee> findByUuid(String uuid);

    void deleteByUuid(String uuid);

    boolean existsByUuid(String uuid);

    boolean existsByEmail(String email);
}
