package com.bk.hotel.service;

import java.util.List;

import com.bk.hotel.model.Customer;

public interface CustomerService {
	Customer findCustomerById(long id);

	List<Customer> findAllCustomers();
	
	Customer saveCustomer(Customer customer);

	boolean deleteResource(Long id);

	List<Customer> findByCustomerFirstNameLastName(String fName, String lName);
}
