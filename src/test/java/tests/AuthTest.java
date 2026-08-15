package tests;

import base.BaseTest;
import client.ApiClient;
import endpoints.Endpoints;
import io.restassured.response.Response;
import reporting.ExtentManager;
import reporting.ExtentTestManager;
import utilities.ConfigReader;
import utilities.PayloadReader;
import utilities.ResponseValidator;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

public class AuthTest extends BaseTest {
	Response response;
	public static String Token;
    @BeforeClass 
    public void createTokenTest() {
    	ExtentTest test = ExtentManager
    .getInstance()
    .createTest("CRUD Operation");

ExtentTestManager.setTest(test);

        String payload = PayloadReader.read("auth.json");
        response = ApiClient.post(Endpoints.AUTH, payload);
        ExtentTestManager.getTest().info("information about testscript status of booking API");
        
        }
     @Test
     public void verifyStatusCode() {
    	 ResponseValidator.validateStatusCode(response, 200); 
    	 //ExtentTestManager.getTest().pass("Status code verified as 200");
     }
     
     @Test
     public void verifyToken() {
    	 Assert.assertNotNull(response.jsonPath().get("token"));
    	  Token = response.jsonPath().get("token").toString();
    	 ConfigReader.set("token", Token);
    	 //ExtentTestManager.getTest().pass("Token generated successfully: " + Token);
    	 
    }
     @Test
     public void verifyHeader() {
 		ResponseValidator.validateHeader(response,"Content-Type", "application/json; charset=utf-8");
 		//ExtentTestManager.getTest().pass("Response header verified successfully ");
 	}
}


