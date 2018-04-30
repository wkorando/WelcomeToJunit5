package com.bk.hotel.controller;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import com.bk.hotel.HotelApplication;
import com.bk.hotel.RoomServiceException;
import com.bk.hotel.model.Customer;
import com.bk.hotel.service.CustomerService;

@SpringJUnitWebConfig(HotelApplication.class)
@WebMvcTest(controllers = CustomerController.class, secure = false)
public class TestCustomersController implements GetResourceEndpointTest<Customer, Long>, PutPostEndpointTesting,
		DeleteResourceEndpointTest<Long>, SearchEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService service;

	@Override
	public String baseEndpoint() {
		return "/customers";
	}

	@Override
	public Long getExistingResource() {
		return 1L;
	}

	@Override
	public Long getNonExistingResoruce() {
		return 0L;
	}

	@Override
	public Customer foundResource() {
		return new Customer(0L, "Joe", "Blow", "Kokomo", "Jr.");
	}

	@Override
	public String getFoundResourceJsonContent() {
		return "{\"firstName\": \"Joe\", \"lastName\" : \"Blow\", \"middleName\" : \"Kokomo\", \"suffix\" : \"Jr.\" }";
	}

	@Override
	public OngoingStubbing<Customer> mockExistingBehavior() {
		return when(service.findCustomerById(getExistingResource()));
	}

	@Override
	public OngoingStubbing<Customer> mockNonExistingBehavior() {
		return when(service.findCustomerById(getNonExistingResoruce()));
	}

	@Override
	public OngoingStubbing<List<Customer>> mockFindAllResourcesBehavior() {
		return when(service.findAllCustomers());
	}

	@Override
	public MockMvc getMockMvc() {
		return mockMvc;
	}

	@Override
	public void mockCreateResource() {
		when(service.saveCustomer(foundResource())).thenReturn(foundResource());
	}

	@Override
	public void mockUpdateResource() {
		when(service.saveCustomer(foundResource())).thenReturn(foundResource());
	}

	@Override
	public String newResourceWithErrorsAsJson() {
		return "{\"firstName\": \"\", \"lastName\" : \"\", \"middleName\" : \"Kokomo\", \"suffix\" : \"Jr.\" }";
	}

	@Override
	public String createNewResourceErrorMessages() {
		return "\"Must provide a first name.\",\"Must provide a last name.\"";
	}

	@Override
	public void mockErrorCreatingResource() {
		RoomServiceException e = new RoomServiceException();
		e.addErrorMessage("Must provide a first name.");
		e.addErrorMessage("Must provide a last name.");

		when(service.saveCustomer(new Customer(0L, "", "", "Kokomo", "Jr."))).thenThrow(e);
	}

	@Override
	public String newResourceId() {
		return "0";
	}

	@Override
	public String updateResourceId() {
		return "2";
	}

	@Override
	public String newResourceAsJson() {
		return getFoundResourceJsonContent();
	}

	@Override
	public String updateResourceAsJson() {
		return getFoundResourceJsonContent();
	}

	@Override
	public String failedDeleteResourceErrorMessages() {
		return "\"Resource does not exist or has already been deleted.\"";
	}

	@Override
	public void mockDeleteExistingResourceBehavior() {
		when(service.deleteResource(getExistingResource())).thenReturn(true);
	}

	@Override
	public void mockDeleteNonExistingResourceBehavior() {
		when(service.deleteResource(getNonExistingResoruce())).thenReturn(false);
	}

	@Override
	public Stream<SuccessfulSearchScenario> successfulSearchScenarios() {
		return Stream.of(new SuccessfulSearchScenario("/byFNameLName?fName=John&lName=Doe", "[{\"firstName\" : \"John\", \"lastName\" : \"Doe\", \"middleName\" : \"M\", \"suffix\" : \"S\"}]") {

			@Override
			public void mockedBehavior() {
				when(service.findByCustomerFirstNameLastName("John", "Doe"))
						.thenReturn(Arrays.asList(new Customer(1L, "John", "Doe", "M", "S")));
			}
		});
	}

}