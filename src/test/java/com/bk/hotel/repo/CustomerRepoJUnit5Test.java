package com.bk.hotel.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.bk.hotel.model.Customer;

@SpringJUnitConfig
@DataJpaTest
@DirtiesContext
@Disabled
public class CustomerRepoJUnit5Test {
	private TestEntityManager entityManager;

	private CustomerRepo repo;
	private Customer bojack;
	private Customer todd;
	private Customer princess;

	public CustomerRepoJUnit5Test(@Autowired TestEntityManager entityManager, @Autowired CustomerRepo repo) {
		bojack = new Customer(1L, "Bojack", "Horseman", "Horse", "Sr.");
		todd = new Customer(2L, "Todd", "Chavez", "Toddifer", "Jr.");
		princess = new Customer(3L, "Princess", "Caroline", "Cat", null);
		this.repo = repo;
		this.entityManager = entityManager;
	}

	@AfterEach
	public void cleanup() {
		this.entityManager.clear();
	}

	@Test
	public void testFindAllCustomers(@Autowired CustomerRepo repo) {
		this.entityManager.persist(bojack);
		Iterable<Customer> customers = repo.findAll();

		int count = 0;
		for (Customer repoCustomer : customers) {
			assertEquals("BoJack", repoCustomer.getFirstName());
			assertEquals("Horseman", repoCustomer.getLastName());
			assertEquals("Horse", repoCustomer.getMiddleName());
			assertEquals("Sr.", repoCustomer.getSuffix());
			assertTrue(repoCustomer.getId() > 0L);
			count++;
		}
		assertEquals(1, count);
	}

	@Test
	public void testFindCustomersByFNameAndLName() {
		this.entityManager.persist(bojack);
		this.entityManager.persist(todd);
		this.entityManager.persist(princess);
		Iterable<Customer> customers = repo.findCustomersByFirstNameAndLastName("Princess", "Caroline");

		int count = 0;
		for (Customer repoCustomer : customers) {
			assertEquals("Princess", repoCustomer.getFirstName());
			assertEquals("Caroline", repoCustomer.getLastName());
			assertEquals("Cat", repoCustomer.getMiddleName());
			assertTrue(repoCustomer.getId() > 0L);
			assertNull(repoCustomer.getSuffix());
			count++;
		}
		assertEquals(1, count);
	}

}
