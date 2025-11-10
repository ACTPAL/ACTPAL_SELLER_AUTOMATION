package base;

import com.aventstack.extentreports.Status;

public class Logger {

    // Log info in console + ExtentReports
    public static void log(String message) {
        System.out.println(message); // Console
        ExtentListener.getTest().log(Status.INFO, message); // ExtentReports
    }

    // Log pass
    public static void pass(String message) {
        System.out.println("✅ " + message);
        ExtentListener.getTest().log(Status.PASS, message);
    }

    // Log fail
    public static void fail(String message) {
        System.out.println("❌ " + message);
        ExtentListener.getTest().log(Status.FAIL, message);
    }

    // Log warning
    public static void warn(String message) {
        System.out.println("⚠️ " + message);
        ExtentListener.getTest().log(Status.WARNING, message);
    }
}
