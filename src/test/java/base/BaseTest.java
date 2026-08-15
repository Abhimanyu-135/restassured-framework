package base;
import io.restassured.RestAssured;
import reporting.EmailExtentReport;
import utilities.ConfigReader;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    @BeforeTest
    public void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");
    }
    @AfterSuite
    public void sendExtentReportEmail() {
       System.out.println("Suite Execution Completed — Sending Extent Report...");
       EmailExtentReport.sendReport();
    }
}
