package com.bk.hotel.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;

import com.bk.hotel.HotelApplication;
import com.bk.hotel.model.Customer;
import com.bk.hotel.repo.CustomerRepo;
import com.bk.hotel.service.CustomerService;

@SpringJUnitConfig(classes = HotelApplication.class, initializers = ITCustomerJUnit5Repo.Initializer.class)
public class ITCustomerJUnit5Repo {

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

	public PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>().withUsername("admin").withPassword("admin");

	@RegisterExtension
	public TestContainersExtension containersExtension = new TestContainersExtension(postgres);
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