package com.jwt.employee.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.employee.app.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
