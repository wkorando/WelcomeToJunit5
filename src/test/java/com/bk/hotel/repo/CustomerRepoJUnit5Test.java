package com.bk.hotel.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.bk.hotel.model.Customer;
import com.bk.hotel.test.utils.SpringTestContainersExtension;

@DataJpaTest
@DirtiesContext
public class CustomerRepoJUnit5Test {
	private TestEntityManager entityManager;

	private CustomerRepo repo;
	private Customer bojack;
	private Customer todd;
	private Customer princess;

	public CustomerRepoJUnit5Test(@Autowired TestEntityManager entityManager, @Autowired CustomerRepo repo) {
		bojack = new Customer.CustomerBuilder().firstName("BoJack").middleName("Horse").lastName("Horseman")
				.suffix("Sr.").build();
		todd = new Customer.CustomerBuilder().firstName("Todd").middleName("Toddifer").lastName("Chavez").suffix("Jr.")
				.build();
		princess = new Customer.CustomerBuilder().firstName("Princess").middleName("Cat").lastName("Caroline").build();
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
		this.entityManager.persist(todd);
		this.entityManager.persist(princess);
		Iterable<Customer> customers = repo.findAll();

		assertThat(customers).extracting("firstName", "lastName").contains(tuple("BoJack", "Horseman"),
				tuple("Princess", "Caroline"), tuple("Todd", "Chavez"));
	}

	@Test
	public void testFindCustomersByFNameAndLName(TestInfo testInfo) {
		this.entityManager.persist(bojack);
		this.entityManager.persist(todd);
		this.entityManager.persist(princess);
		Iterable<Customer> customers = repo.findCustomersByFirstNameAndLastName("Princess", "Caroline");
		System.out.println(testInfo.getDisplayName());
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
