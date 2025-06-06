package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    private EntityManager entityManager;

    public EmployeeDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> theQuery= entityManager.createQuery("from Employee",Employee.class);
        List<Employee> employees= theQuery.getResultList();
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Employee employee= entityManager.find(Employee.class,id);
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        Employee dbemployee= entityManager.merge(employee);
        return dbemployee;
    }

    @Override
    public void deleteById(int id) {
        Employee employee= entityManager.find(Employee.class,id);
        entityManager.remove(employee);
    }
}
