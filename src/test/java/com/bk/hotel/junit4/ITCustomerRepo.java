package com.bk.hotel.junit4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.bk.hotel.HotelApplication;
import com.bk.hotel.model.Customer;
import com.bk.hotel.repo.CustomerRepo;
import com.bk.hotel.service.CustomerService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotelApplication.class)
@ContextConfiguration(initializers = ITCustomerRepo.Initializer.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ITCustomerRepo {

	public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			TestPropertyValues
					.of("spring.datasource.url=jdbc:tc:postgresql://localhost/test?TC_INITSCRIPT=import.sql",
							"spring.datasource.username=admin", //
							"spring.datasource.password=admin", //
							"spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver")
					.applyTo(applicationContext);
		}
	}

	@Autowired
	private CustomerRepo repo;

	@MockBean
	private CustomerService customerService;

	@Test
	public void testRetrieveCustomerFromDatabase() {

		Customer customer = repo.findById(1L).get();

		assertEquals("John", customer.getFirstName());
		assertEquals("Doe", customer.getLastName());
		assertEquals("Middle", customer.getMiddleName());
		assertEquals("", customer.getSuffix());
	}

	@Test
	@Ignore
	public void testAddCustomerToDB() throws ParseException {

		Customer customer = new Customer(10L, "Princess", "Carolyn", "Cat", "");

		repo.save(customer);

		assertEquals(3, repo.count());
	}

	@Test
	public void testCountNumberOfCustomersInDB() {

		assertEquals(2, repo.count());
	}
}
