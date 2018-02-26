package com.bk.hotel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

import com.bk.hotel.HotelApplication;
import com.bk.hotel.model.Customer;
import com.bk.hotel.repo.CustomerRepo;
import com.bk.hotel.service.CustomerService;

@ContextConfiguration(classes = { HotelApplication.class }, initializers = ITCustomerJUnit5Repo.Initializer.class)
@DirtiesContext(classMode=ClassMode.AFTER_EACH_TEST_METHOD)
public class ITCustomerJUnit5Repo {

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues.of("spring.datasource.url=" + postgres.getJdbcUrl(), //
					"spring.datasource.username=" + postgres.getUsername(), //
					"spring.datasource.password=" + postgres.getPassword()) //
					.applyTo(applicationContext);
		}
	}

	private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres-hoteldb:latest");

	@RegisterExtension
	static SpringTestContainersExtension extension = new SpringTestContainersExtension(postgres, true);

	@Autowired
	private CustomerRepo repo;
	@MockBean
	private CustomerService customerService;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

	@Test
	public void testRetrieveCustomerFromDatabase() {

		Customer customer = repo.findById(1L).get();

		assertEquals("John", customer.getFirstName());
		assertEquals("Doe", customer.getLastName());
		assertEquals("Middle", customer.getMiddleName());
		assertEquals("", customer.getSuffix());
		assertEquals("2017-10-30", dateFormat.format(customer.getDateOfLastStay()));
	}

	@Test
	public void testAddCustomerToDB() throws ParseException {

		Customer customer = new Customer(10L, "Princess", "Carolyn", "Cat", "", dateFormat.parse("2018-01-30"));
		
		repo.save(customer);

		assertEquals(3, repo.count());
	}

	@Test
	public void testCountNumberOfCustomersInDB() {

		assertEquals(2, repo.count());
	}
}