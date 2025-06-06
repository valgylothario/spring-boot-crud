package com.luv2code.springboot.cruddemo.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private ObjectMapper objectMapper;
    private EmployeeService employeeService;

    public EmployeeRestController(EmployeeService employeeService, ObjectMapper objectMapper) {
        this.employeeService = employeeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return this.employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable(name = "employeeId") int employeeId) {
        Employee employee = this.employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee id not found" + employeeId);
        }

        return employee;
    }

    @PostMapping("/employees")
    public Employee save(@RequestBody Employee employee) {
        employee.setId(0);
        Employee dbEmployee = this.employeeService.save(employee);
        return dbEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        Employee dbEmployee = this.employeeService.save(employee);
        return dbEmployee;
    }

    @PatchMapping("/employees/{employeeId}")
    public Employee updateEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayload) {
        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee could not be found");
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body- " + employeeId);
        }

        Employee patchEmployee = apply(patchPayload, employee);
        Employee dbEmployee = employeeService.save(patchEmployee);

        return dbEmployee;

    }

    @DeleteMapping("/employee/{employeeId}")
    public String deleteById(@PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);
        if (employee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        this.employeeService.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }

    private Employee apply(Map<String, Object> patchPayload, Employee employee) {
        ObjectNode employeeNode = this.objectMapper.convertValue(employee, ObjectNode.class);

        ObjectNode patchPayloadNode = this.objectMapper.convertValue(patchPayload, ObjectNode.class);

        employeeNode.setAll(patchPayloadNode);

        return objectMapper.convertValue(employeeNode, Employee.class);

    }

}
