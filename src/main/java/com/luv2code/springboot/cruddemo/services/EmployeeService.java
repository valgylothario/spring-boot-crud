package com.luv2code.springboot.cruddemo.services;

import com.luv2code.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(int id);
    Employee save(Employee employee);
    void deleteById(int id);
}
