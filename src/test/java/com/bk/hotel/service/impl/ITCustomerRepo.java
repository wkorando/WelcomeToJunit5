package com.bk.hotel.service.impl;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;

import com.bk.hotel.HotelApplication;
import com.bk.hotel.model.Customer;
import com.bk.hotel.repo.CustomerRepo;
import com.bk.hotel.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@ContextConfiguration(initializers = ITCustomerRepo.Initializer.class)
public class ITCustomerRepo {

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=jdbc:tc:postgresql://localhost:5432/test?TC_INITSCRIPT=init_customerdb.sql",
					"spring.datasource.username=admin", //
					"spring.datasource.password=admin", //
					"spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver")
					.applyTo(applicationContext);
		}
	}

	@ClassRule
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>().withUsername("admin")
			.withPassword("admin");
	@Autowired
	private CustomerRepo repo;

	@MockBean
	private CustomerService customerService;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

	@Test
	public void testDatabaseCall() {
		Customer customer = repo.findById(1L).get();

		assertEquals("John", customer.getFirstName());
		assertEquals("Doe", customer.getLastName());
		assertEquals("Middle", customer.getMiddleName());
		assertEquals("", customer.getSuffix());
		assertEquals("2017-10-30", dateFormat.format(customer.getDateOfLastStay()));
	}
}
