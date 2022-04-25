package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/")
public class EmpController {
	@Autowired
	private EmpRepository empRepository;
	
	@GetMapping
	public List<Employee> getAllEmp(){
		return this.empRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Employee getEmpById(@PathVariable(value = "id") long empId) {
		return this.empRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :"+empId));
	}
	
	@PostMapping
	public Employee createEmp(@RequestBody Employee employee) {
		return this.empRepository.save(employee);
	}
	
	@PutMapping("/{id}")
	public Employee updateUser(@RequestBody Employee employee, @PathVariable("id") long empId) {
		Employee existingUser = this.empRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :"+empId));
		existingUser.setFirstName(employee.getFirstName() );
		existingUser.setLastName(employee.getLastName());
		existingUser.setEmail(employee.getEmail());
		return this.empRepository.save(existingUser);
 	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee>deleteUser(@PathVariable ("id") long empId){
		Employee existingUser = this.empRepository.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id :"+empId));
		this.empRepository.delete(existingUser);
		return ResponseEntity.ok().build();		
	}
}


