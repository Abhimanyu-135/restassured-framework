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

public class CreateBookingTest extends BaseTest {
Response response;
String payload;
@BeforeClass
public void createBookingTest() {
	 payload = PayloadReader.read("booking.json");
	response=ApiClient.post(Endpoints.BOOKING, payload);
}
@Test
public void verifyStatusCode() {
	Assert.assertEquals(response.getStatusCode(), 200);
	
}
@Test
public void verifyBookingId() {
	Assert.assertNotNull(response.jsonPath().get("bookingid"));
	String bookingid = response.jsonPath().get("bookingid").toString();
	ConfigReader.set("bookingid2", bookingid);
	System.out.println("Booking id :"+bookingid);
}
@Test
public void verifyResponseBody() {
	ResponseValidator.validateRequestVsResponse(payload, response,"booking");
}
@Test
public void verifyKeyExists() {
	ResponseValidator.validateKeyExists(response, "bookingid");
}

 
}
