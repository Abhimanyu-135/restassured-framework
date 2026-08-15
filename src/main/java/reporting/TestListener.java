package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.ArrayList;
import java.util.List;
public class TestListener implements ITestListener {
	public static int passed = 0;
    public static int failed = 0;
    public static int skipped = 0;
    public static int total = 0;
    public static List<String> failedTests = new ArrayList<>();

    ExtentReports extent = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        // ✅ CREATE ExtentTest BEFORE test execution
        ExtentTest test = extent.createTest(
                result.getMethod().getMethodName()
        );
        ExtentTestManager.setTest(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	 passed++;
        ExtentTestManager.getTest().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
    	failed++;
    	failedTests.add(result.getMethod().getMethodName());
        ExtentTestManager.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    	skipped++;
        ExtentTestManager.getTest().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
    	total = context.getAllTestMethods().length;
        extent.flush();
    }
}
