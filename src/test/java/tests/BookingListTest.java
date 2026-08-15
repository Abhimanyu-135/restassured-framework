package tests;

import base.BaseTest;
import client.ApiClient;
import endpoints.Endpoints;
import io.restassured.response.Response;
import utilities.ConfigReader;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

public class BookingListTest extends BaseTest {
	Response response;

    @BeforeClass
    public void getAllBookings() {

         response = ApiClient.get(Endpoints.BOOKING);
         }
    @Test
    public void verifyStatusCode() {

        Assert.assertEquals(response.statusCode(), 200);
        
    }
    @Test
    public void listBookingId() {

    	List<Integer> bookingIds=response.jsonPath().getList("bookingid");
    	String bookingid1 = bookingIds.get(0).toString();
    	ConfigReader.set("bookingid", bookingid1);
        for(Integer id:bookingIds) {
        	System.out.println("Booking ID: " + id);
        }
    }
}
