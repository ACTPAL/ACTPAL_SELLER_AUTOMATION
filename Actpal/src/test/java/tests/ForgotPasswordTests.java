package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.ForgotPasswordPage;
import base.BaseTest;
import base.Logger;

public class ForgotPasswordTests extends BaseTest {

    @Test(priority = 1)
    public void TestResetPasswordWithValidemail() throws InterruptedException {
        Logger.log("=== Starting testResetPasswordWithValidemail ===");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        Logger.log("Clicking on 'Forgot Password'");
        forgotPasswordPage.clickForgotPassword();

        Thread.sleep(1000);
        Logger.log("Entering valid email: rajeevsingh1862003@gmail.com");
        forgotPasswordPage.enterEmail("rajeevsingh1862003@gmail.com");

        Thread.sleep(1000);
        Logger.log("Clicking Reset Password button");
        forgotPasswordPage.clickResetPasswordBtn();
        Logger.pass("Reset password request sent successfully");

        Logger.log("=== Finished testResetPasswordWithValidemail ===");
    }

    @Test(priority = 2)
    public void TestResetPasswordWithinvlaidemail() throws InterruptedException {
        Logger.log("=== Starting testResetPasswordWithinvlaidemail ===");

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        Logger.log("Clicking on 'Forgot Password'");
        forgotPasswordPage.clickForgotPassword();

        Thread.sleep(1000);
        Logger.log("Entering invalid email: rajeevsi@gmail.com");
        forgotPasswordPage.enterEmail("rajeevsi@gmail.com");

        Thread.sleep(1000);
        Logger.log("Clicking Reset Password button");
        forgotPasswordPage.clickResetPasswordBtn();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement errorMsgElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='responseMessage']"))
        );
        String errorText = errorMsgElement.getText();
        Logger.log("Error message displayed: " + errorText);

        try {
            Assert.assertEquals(errorText, "This email-address not exists. Please try again!", "Duplicate email validation failed!");
            Logger.pass("Duplicate email validation message displayed correctly");
        } catch (AssertionError e) {
            Logger.fail("Duplicate email validation failed: " + e.getMessage());
            throw e;
        }

        Logger.log("=== Finished testResetPasswordWithinvlaidemail ===");
    }
}
