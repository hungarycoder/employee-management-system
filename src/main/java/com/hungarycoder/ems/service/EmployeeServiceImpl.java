package com.hungarycoder.ems.service;

import com.hungarycoder.ems.domain.Employee;
import com.hungarycoder.ems.model.employee.CreateEmployeeRequestDTO;
import com.hungarycoder.ems.model.employee.CreateEmployeeResponseDTO;
import com.hungarycoder.ems.model.employee.ModifyEmployeeRequestDTO;
import com.hungarycoder.ems.model.exception.EmailAlreadyExistsException;
import com.hungarycoder.ems.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> search(String name, String email) {
        return employeeRepository.findByNameStartingWithAndEmailEndingWithIgnoreCaseOrderByNameDesc(
            name, email);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    @Transactional
    @Override
    public CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                "Ezzel az email címmel már rögzítettek munkavállalót: " + request.getEmail());
        }
        Employee employee = new Employee();
        employee.setUuid(UUID.randomUUID().toString());
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employeeRepository.save(employee);
        return new CreateEmployeeResponseDTO(employee.getUuid());
    }

    @Transactional
    @Override
    public void modifyEmployee(ModifyEmployeeRequestDTO request) {
        Employee employee = employeeRepository.findByUuid(request.getUuid()).orElseThrow(
            () -> new EntityNotFoundException(
                "Employee is not found with uuid: " + request.getUuid()));
        if (!request.getEmail().equals(employee.getEmail())
            && employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                "Ezzel az email címmel már rögzítettek munkavállalót: " + request.getEmail());
        }
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public void deleteEmployee(String uuid) {
        if (!employeeRepository.existsByUuid(uuid)) {
            throw new EntityNotFoundException("Employee is not found with uuid: " + uuid);
        }
        employeeRepository.deleteByUuid(uuid);
    }
}
