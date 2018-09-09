package com.netcracker.shaw.report;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentReports;

public class TestListener extends SeleniumTestUp
		implements ITestListener, IInvokedMethodListener, IAnnotationTransformer {
 
	public String category;
	Logger log = Logger.getLogger(TestListener.class);
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	public static ExtentReports extent = ExtentReportManager.getInstance();

	@Override
	public void onFinish(ITestContext Result) {
		log.debug("I am in onFinish method " + Result.getName());
		// Do tier down operations for extentreports reporting!
		// ExtentTestManager.endTest(extent);
		// extent.flush();
	}

	@Override
	public void onStart(ITestContext Result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {

	}

	// When Test case get failed, this method is called.
	@Override
	public void onTestFailure(ITestResult Result) {
		log.debug("The name of the testcase failed is :" + Result.getName());
		//ExtentTestManager.endTest(extent);
	}

	// When Test case get Skipped, this method is called.
	@Override
	public void onTestSkipped(ITestResult Result) {
		log.debug("The name of the testcase Skipped is :" + Result.getName());
		ExtentTestManager.endTest(extent);
	}

	// When Test case get Started, this method is called.
	@Override
	public void onTestStart(ITestResult Result) {
		log.debug(Result.getName() + " test case started");
		log.debug("Test Name is " + ExtentTestManager.getTest());
		ExtentTestManager.startTest(ExtentTestManager.getTest(), Result.getMethod().getMethodName(), Result.getMethod().getDescription());

	}

	// When Test case get passed, this method is called.
	@Override
	public void onTestSuccess(ITestResult Result) {
		log.debug("The name of the testcase passed is :" + Result.getName());
		ExtentTestManager.endTest(extent);
	}

	private String returnMethodName(ITestNGMethod method) {

		return method.getRealClass().getSimpleName() + "." + method.getMethodName();

	}

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {

		String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());

	}

	// This belongs to IInvokedMethodListener and will execute after every
	// method including @Before @After @Test
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult Result) {

		String textMsg = "Completed executing following method : " + returnMethodName(method.getTestMethod());
		ExtentTestManager.endTest(extent);

	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		log.debug("Inside Transform");
		if (xls.getDataForTestName(xls, testMethod.getName(), "Run").equals("No")) {
			annotation.setEnabled(false);
			log.debug("Skipped Test Case: " + testMethod.getName());
		}

	}

}