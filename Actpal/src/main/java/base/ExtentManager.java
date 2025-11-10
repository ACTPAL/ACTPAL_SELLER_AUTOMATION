package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    // Returns the singleton ExtentReports instance
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance(getReportPath());
        }
        return extent;
    }

    // Create ExtentReports instance with file name
    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(fileName);
        reporter.config().setDocumentTitle("Automation Test Report");
        reporter.config().setReportName("Functional Testing");
        reporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        // Optional: System info
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", "Actpal Seller");
        extent.setSystemInfo("Tester", "Rajeev Singh");

        return extent;
    }

    // Generate dynamic report path with timestamp
    private static String getReportPath() {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        return System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";
    }
}
