package com.bk.hotel.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bk.hotel.RoomServiceException;
import com.bk.hotel.model.Customer;
import com.bk.hotel.model.ErrorResponse;
import com.bk.hotel.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	private CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<Customer>> findAllCustomers() {
		return ResponseEntity.ok(service.findAllCustomers());
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/{id}")
	public ResponseEntity findCustomerById(@PathVariable("id") long id) {
		Customer customer = service.findCustomerById(id);
		if (customer != null) {
			return ResponseEntity.ok(customer);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search/byFNameLName")
	public ResponseEntity<?> findCustomersByFNameLName(@RequestParam("fName") String fName,
			@RequestParam("lName") String lName) {
		List<Customer> customers = service.findByCustomerFirstNameLastName(fName, lName);
		if (customers != null) {
			return ResponseEntity.ok(customers);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer) throws URISyntaxException {
		try {
			Customer newCustomer = service.saveCustomer(customer);
			return ResponseEntity.created(new URI("/customers/" + newCustomer.getId())).build();
		} catch (RoomServiceException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrorMessages()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
		try {
			service.saveCustomer(customer);
			return ResponseEntity.noContent().build();
		} catch (RoomServiceException e) {
			return ResponseEntity.badRequest().body(new ErrorResponse(e.getErrorMessages()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") long id) {
		boolean customerDelete = service.deleteResource(id);
		if (customerDelete) {
			return ResponseEntity.noContent().build();
		} else {
			List<String> errorMessages = new ArrayList<>();
			errorMessages.add("Resource does not exist or has already been deleted.");
			return ResponseEntity.badRequest().body(new ErrorResponse(errorMessages));
		}
	}

}
