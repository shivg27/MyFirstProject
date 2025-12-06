package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import common.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class BaseAPITest {
    public RequestSpecification httpRequest;
    @BeforeMethod()
    public void init(){
//        RestAssured.baseURI = "https://demoqa.com";
        RestAssured.baseURI = ConfigReader.read().getProperty("baseURI");
        httpRequest=RestAssured.given();

    }

    public static ExtentReports extentReport;

    public static ExtentReports getReporterObject() {
        String path = System.getProperty("user.dir") + "/reports/index.html";

        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("API Automation Practice Project Results");
        reporter.config().setDocumentTitle("RestAssured Test Report");

        extentReport = new ExtentReports();
        extentReport.attachReporter(reporter);
        extentReport.setSystemInfo("Tester", "Bhawana");
        extentReport.setSystemInfo("Environment", "QA");

        return extentReport;
    }

    @AfterTest
    public void tearDown() {
        extentReport.flush();  // writes the report to index.html
    }
}
