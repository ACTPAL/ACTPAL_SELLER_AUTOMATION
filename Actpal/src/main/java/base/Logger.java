package base;

import com.aventstack.extentreports.Status;

public class Logger {

    // Log info in console + ExtentReports
    public static void log(String message) {
        System.out.println(message); // ✅ fixed - no "Logger." prefix
        try {
            if (ExtentListener.getTest() != null) {
                ExtentListener.getTest().log(Status.INFO, message); // ExtentReports
            }
        } catch (Exception e) {
            System.err.println("⚠️ Logger INFO failed: " + e.getMessage());
        }
    }

    // Log pass
    public static void pass(String message) {
        System.out.println("✅ " + message); // ✅ print directly, not via Logger.log()
        try {
            if (ExtentListener.getTest() != null) {
                ExtentListener.getTest().log(Status.PASS, message);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Logger PASS failed: " + e.getMessage());
        }
    }

    // Log fail
    public static void fail(String message) {
        System.out.println("❌ " + message); // ✅ direct print
        try {
            if (ExtentListener.getTest() != null) {
                ExtentListener.getTest().log(Status.FAIL, message);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Logger FAIL failed: " + e.getMessage());
        }
    }

    // Log warning
    public static void warn(String message) {
        System.out.println("⚠️ " + message); // ✅ direct print
        try {
            if (ExtentListener.getTest() != null) {
                ExtentListener.getTest().log(Status.WARNING, message);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Logger WARN failed: " + e.getMessage());
        }
    }
}
