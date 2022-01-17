package testbase;

import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeyWords;
import reports.ExtentManager;

public class BaseTest {
	
	public ApplicationKeyWords app;
	public ExtentReports rep;
	public ExtentTest test;
	
	
	
	@BeforeTest(alwaysRun=true)
	public void beforeTest(ITestContext context) {
		System.out.println("****Before Test****");
		
		//Initialising of same app object for all tests
		app = new ApplicationKeyWords();
		context.setAttribute("app", app);
		
		//Initialising reporting for all tests
		rep = ExtentManager.getReports();
		test = rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting "+context.getCurrentXmlTest().getName());
		app.setReport(test);
		context.setAttribute("reports", rep);
		context.setAttribute("testcase", test);
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(ITestContext context) {
		System.out.println("------Before Method-----");
		test = (ExtentTest)context.getAttribute("testcase");
		
		String criticalFailure = (String)context.getAttribute("CriticalFailure");
		if(criticalFailure != null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Critical failure in previous test");
			throw new SkipException("Critical failure in previous test");
		}
		app = (ApplicationKeyWords)context.getAttribute("app");
		rep = (ExtentReports)context.getAttribute("reports");
		
		
	}
	
	@AfterTest
	public void quit() {
		if(rep!=null)
			rep.flush();
	}
}
