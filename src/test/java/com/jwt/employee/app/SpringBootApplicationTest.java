package com.jwt.employee.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.jwt.employee.app.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testCreateEmployeeSuccess() throws URISyntaxException {

		final String baseURL = "http://localhost:" + randomServerPort + "/api/v1/employee/";
		URI uri = new URI(baseURL);
		Employee employee = new Employee("mukesh", 43, 10000);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-EMP-PERSIST", "true");
		HttpEntity<Employee> httpEntity = new HttpEntity<>(employee, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, httpEntity, String.class);
		assertEquals(201, result.getStatusCodeValue());

	}

	@Test
	public void testCreateEmployee_Header_Missing() throws URISyntaxException {
		final String baseURL = "http://localhost:" + randomServerPort + "/api/v1/employee/";
		URI uri = new URI(baseURL);
		Employee employee = new Employee("Peter", 32, 90900);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, httpEntity, String.class);
		assertEquals(400, result.getStatusCodeValue());

	}

	@Test
	public void testUpdateEmployee() throws URISyntaxException {
		final String baseURL = "http://localhost:" + randomServerPort + "/api/v1/employee/3";
		URI uri = new URI(baseURL);
		Employee employee = new Employee("Ritesh", 30, 90900);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<String> result = this.restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
		assertEquals(200, result.getStatusCodeValue());

	}

	@Test
	public void test_delete_employee() throws URISyntaxException {
		final String baseURL = "http://localhost:" + randomServerPort + "/api/v1/employee/3";
		URI uri = new URI(baseURL);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Employee> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> result = this.restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, String.class);
		assertEquals(200, result.getStatusCodeValue());

	}

	@Test
	public void test_getEmployees() throws URISyntaxException {
		final String baseURL = "http://localhost:" + randomServerPort + "/api/v1/employee/";
		URI uri = new URI(baseURL);
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri, String.class);
		String body = responseEntity.getBody();
		System.out.println(body);
		assertEquals(200, responseEntity.getStatusCode().value());

	}

	@Test
	public void test_getEmployees_By_id() throws URISyntaxException {
		final String baseURL = "http://localhost:" + randomServerPort + "/api/v1/employee/4";
		URI uri = new URI(baseURL);
		ResponseEntity<Employee> responseEntity = this.restTemplate.getForEntity(uri, Employee.class);
		Employee employee = responseEntity.getBody();
		System.out.println("employee = " + employee);
		// Verify request succeed
		assertEquals(200, responseEntity.getStatusCodeValue());

	}

}
