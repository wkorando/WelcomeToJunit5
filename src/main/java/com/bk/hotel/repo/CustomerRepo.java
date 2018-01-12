package com.bk.hotel.repo;

import org.springframework.data.repository.CrudRepository;

import com.bk.hotel.model.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Long> {
	Iterable<Customer> findCustomersByFirstNameAndLastName(String firstName, String lastName);
}
