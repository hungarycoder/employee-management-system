package com.hungarycoder.ems;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hungarycoder.ems.domain.Employee;
import com.hungarycoder.ems.model.employee.CreateEmployeeRequestDTO;
import com.hungarycoder.ems.model.employee.CreateEmployeeResponseDTO;
import com.hungarycoder.ems.model.exception.EmailAlreadyExistsException;
import com.hungarycoder.ems.repository.EmployeeRepository;
import com.hungarycoder.ems.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeManagementSystemApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    void testCreateEmployeeSuccess() {
        CreateEmployeeRequestDTO request = new CreateEmployeeRequestDTO("John Doe",
            "john.doe@example.com");

        CreateEmployeeResponseDTO response = employeeService.createEmployee(request);

        assertNotNull(response.getUuid());
        Optional<Employee> createdEmployee = employeeRepository.findByUuid(response.getUuid());
        assertTrue(createdEmployee.isPresent());
        assertEquals("John Doe", createdEmployee.get().getName());
    }

    @Test
    void testCreateEmployeeEmailAlreadyExists() {
        Employee existingEmployee = new Employee();
        existingEmployee.setUuid(UUID.randomUUID().toString());
        existingEmployee.setName("Jane Doe");
        existingEmployee.setEmail("jane.doe@example.com");
        employeeRepository.save(existingEmployee);

        CreateEmployeeRequestDTO request = new CreateEmployeeRequestDTO("John Doe",
            "jane.doe@example.com");

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class,
            () -> employeeService.createEmployee(request));

        assertEquals(
            "Ezzel az email címmel már rögzítettek munkavállalót: " + existingEmployee.getEmail(),
            exception.getMessage());
    }

    @Test
    void testSearchEmployee() {
        Employee employee = new Employee();
        employee.setUuid(UUID.randomUUID().toString());
        employee.setName("John Smith");
        employee.setEmail("john.smith@example.com");
        employeeRepository.save(employee);

        List<Employee> employees = employeeService.search("John", "example.com");

        assertEquals(1, employees.size());
        assertEquals("John Smith", employees.getFirst().getName());
    }

    @Test
    void testDeleteEmployeeNotFound() {
        String uuid = UUID.randomUUID().toString();

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> employeeService.deleteEmployee(uuid));

        assertEquals("Employee is not found with uuid: " + uuid, exception.getMessage());
    }
}