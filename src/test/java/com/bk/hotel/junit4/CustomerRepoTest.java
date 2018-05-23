package com.bk.hotel.junit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bk.hotel.HotelApplication;
import com.bk.hotel.model.Customer;
import com.bk.hotel.repo.CustomerRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = HotelApplication.class)
public class CustomerRepoTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CustomerRepo repo;
	private Customer bojack;
	private Customer todd;
	private Customer princess;

	public CustomerRepoTest() {
		bojack = new Customer.CustomerBuilder().firstName("BoJack").middleName("Horse").lastName("Horseman")
				.suffix("Sr.").build();
		todd = new Customer.CustomerBuilder().firstName("Todd").middleName("Toddifer").lastName("Chavez").suffix("Jr.")
				.build();
		princess = new Customer.CustomerBuilder().firstName("Princess").middleName("Cat").lastName("Caroline").build();
	}

	@After
	public void cleanup() {
		this.entityManager.clear();
	}

	@Test
	public void testFindAllCustomers() {
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