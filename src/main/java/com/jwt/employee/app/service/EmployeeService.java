package com.jwt.employee.app.service;

import java.util.List;

import com.jwt.employee.app.model.Employee;

public interface EmployeeService {
	
	Employee createEmployee(Employee employee);

	void updateEmployee(Employee employee);
	
	Employee getEmployee(int id);
	
	List<Employee> getEmployees();
	
	void deleteEmployee(int id);
	
	boolean isEmployeeExist(int id);

}
