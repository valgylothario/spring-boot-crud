package com.luv2code.springboot.cruddemo.services;

import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    EmployeeDAO employeeDAO;

    EmployeeServiceImpl(EmployeeDAO employeeDAO){
        this.employeeDAO=employeeDAO;
    }

    @Override
    public List<Employee> findAll() {
        return this.employeeDAO.findAll();
    }

    @Override
    public Employee findById(int id) {
       return this.employeeDAO.findById(id);
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return this.employeeDAO.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        this.employeeDAO.deleteById(id);
    }
}
