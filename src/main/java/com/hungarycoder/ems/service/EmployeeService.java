package com.hungarycoder.ems.service;

import com.hungarycoder.ems.domain.Employee;
import com.hungarycoder.ems.model.employee.CreateEmployeeRequestDTO;
import com.hungarycoder.ems.model.employee.CreateEmployeeResponseDTO;
import com.hungarycoder.ems.model.employee.ModifyEmployeeRequestDTO;
import java.util.List;

public interface EmployeeService {

    List<Employee> search(String name, String email);

    List<Employee> list();

    CreateEmployeeResponseDTO createEmployee(CreateEmployeeRequestDTO request);

    void modifyEmployee(ModifyEmployeeRequestDTO request);

    void deleteEmployee(String uuid);
}
