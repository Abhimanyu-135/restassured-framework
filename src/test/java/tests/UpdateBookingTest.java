package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import client.ApiClient;
import endpoints.Endpoints;
import io.restassured.response.Response;
import utilities.ConfigReader;
import utilities.PayloadReader;
import utilities.ResponseValidator;

public class UpdateBookingTest  extends BaseTest{
	Response response;
	String payload;
	@BeforeClass
	public  void updateBooking() {
		String bookingId =ConfigReader.get("bookingid2");
		String endpoint = Endpoints.BOOKING + "/" + bookingId;
	    String token = ConfigReader.get("token");
	    payload = PayloadReader.read("UpdateBooking.json");
	    response = ApiClient.put(endpoint,payload,token);
}
	@Test
	public void verifyStatusCode() {
		Assert.assertNotNull(response, "Response is null");
		ResponseValidator.validateStatusCode(response, 200);

}
	@Test
	public void verifyCookie() {
		if(response.getCookies().isEmpty()) {
			System.out.println("No cookies present.");
		}else {
		
		ResponseValidator.validateCookie(response, "token");}
		
		}	
	@Test
	public void verifyHeader() {
		ResponseValidator.validateHeader(response,"Content-Type", "application/json; charset=utf-8");
	}

}

