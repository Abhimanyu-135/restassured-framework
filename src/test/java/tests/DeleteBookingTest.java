package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import client.ApiClient;
import endpoints.Endpoints;
import io.restassured.response.Response;
import utilities.ConfigReader;
import utilities.ResponseValidator;

public class DeleteBookingTest extends BaseTest {
	 Response response;
	
	@BeforeClass
	public  void deleteBooking() {
		String bookingId =ConfigReader.get("bookingid");
		String endpoint = Endpoints.BOOKING + "/" + bookingId;
	    String token = ConfigReader.get("token");
	    response = ApiClient.delete(endpoint, token);
	
	
}
	@Test
	public void verifyStatusCode() {
		Assert.assertNotNull(response, "Response is null");
		ResponseValidator.validateStatusCode(response, 201);
	}
	@Test
	public void verifyStatusMessage() {
		Assert.assertEquals(response.asString(), "Created");
	}
	@Test
	public void verifyCookie() {
		if(response.getCookies().isEmpty()) {
			System.out.println("No cookies present.");
		}else {
		
		ResponseValidator.validateCookie(response, "token");
	}}
	@Test
	public void verifyHeader() {
		ResponseValidator.validateHeader(response,"Content-Type", "text/plain; charset=utf-8");
	}
	}

