package com.jwt.employee.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.employee.app.exception.EmployeeNotfoundException;
import com.jwt.employee.app.model.Employee;
import com.jwt.employee.app.service.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeServiceController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/employee")
	public ResponseEntity<Object> createEmployee(@RequestHeader(name = "X-EMP-PERSIST", required = true) String headerP,
			@RequestBody Employee employee) {
		Employee createdEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<>("Employee is created with id = " + createdEmployee.getId(), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmployees() {
		List<Employee> employeeList = employeeService.getEmployees();
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getEmployee(@PathVariable("id") int id) throws EmployeeNotfoundException
	{
		boolean isEmployeeExist = employeeService.isEmployeeExist(id);
		if (isEmployeeExist)
		{
			Employee employee = employeeService.getEmployee(id);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		}
		else
		{
			throw new EmployeeNotfoundException();
		}
	}

	@PutMapping("/employee/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) throws EmployeeNotfoundException {

		boolean employeeExist = employeeService.isEmployeeExist(id);
		if(employeeExist) {
			employee.setId(id);
			employeeService.updateEmployee(employee);
			return new ResponseEntity<>("Employee updated successfully", HttpStatus.OK);
		}
		else {
			throw new EmployeeNotfoundException();
		}
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) throws EmployeeNotfoundException {

		boolean employeeExist = employeeService.isEmployeeExist(id);
		if (employeeExist) {
			employeeService.deleteEmployee(id);
			return new ResponseEntity<>("Employee is deleted successfully ", HttpStatus.OK);
		} else {
			throw new EmployeeNotfoundException();

		}

	}

}
