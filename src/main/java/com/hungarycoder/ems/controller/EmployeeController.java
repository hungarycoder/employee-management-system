package com.hungarycoder.ems.controller;

import com.hungarycoder.ems.domain.Employee;
import com.hungarycoder.ems.model.employee.CreateEmployeeRequestDTO;
import com.hungarycoder.ems.model.employee.CreateEmployeeResponseDTO;
import com.hungarycoder.ems.model.employee.ModifyEmployeeRequestDTO;
import com.hungarycoder.ems.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<Employee>> list() {
        logger.info("Listing employees...");
        return ResponseEntity.ok(employeeService.list());
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<Employee>> search(@RequestParam String name, @RequestParam String email) {
        logger.info("Searching for employees. Name: {}, email: {}", name, email);
        return ResponseEntity.ok(employeeService.search(name, email));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<CreateEmployeeResponseDTO> createEmployee(@Valid @RequestBody CreateEmployeeRequestDTO request) {
        logger.info("Creating employee. Name: {}, email: {}", request.getName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(request));
    }

    @PatchMapping(value = "/modify")
    public ResponseEntity<Void> modifyEmployee(@Valid @RequestBody ModifyEmployeeRequestDTO request) {
        logger.info("Modifying employee. Name: {}, email: {}", request.getName(), request.getEmail());
        employeeService.modifyEmployee(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String uuid) {
        logger.info("Deleting employee: {}", uuid);
        employeeService.deleteEmployee(uuid);
        return ResponseEntity.ok().build();
    }
}
