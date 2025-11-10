package tests;

import org.testng.annotations.Test;
import Pages.LoginPage;
import base.BaseTest;
import base.Logger;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginTests extends BaseTest {

    @Test(priority = 1)
    public void TestValxpathLogin() {
        Logger.log("=== Starting testValxpathLogin ===");

        LoginPage loginPage = new LoginPage(driver);
        Logger.log("Entering valid email");
        loginPage.enterEmail("yavov92487@gamegta.com");

        Logger.log("Entering valid password");
        loginPage.enterPassword("@Devil1234");

        Logger.log("Clicking Login button");
        loginPage.clickLogin();

        String successMsg = loginPage.getSuccessMessage();
        Logger.log("Success message received: " + successMsg);

        Assert.assertTrue(successMsg.contains("Dashboard"), "Login failed - Success message not shown");
        Logger.pass("Login successful and Dashboard displayed");
    }

    @Test(priority = 2)
    public void TestInvalxpathEmail() {
        Logger.log("=== Starting testInvalxpathEmail ===");

        LoginPage loginPage = new LoginPage(driver);
        Logger.log("Leaving email empty");
        loginPage.enterEmail("");

        Logger.log("Entering password");
        loginPage.enterPassword("ValxpathPassword123");

        Logger.log("Clicking Login button");
        loginPage.clickLogin();

        String errorMsg = loginPage.getEmailErrorMessage();
        Logger.log("Email validation message: " + errorMsg);

        Assert.assertTrue(errorMsg.contains("Please enter email"), "Email error message not displayed");
        Logger.pass("Email validation message displayed correctly");
    }

    @Test(priority = 3)
    public void TestInvalxpathPassword() {
        Logger.log("=== Starting testInvalxpathPassword ===");

        LoginPage loginPage = new LoginPage(driver);
        Logger.log("Entering email");
        loginPage.enterEmail("valxpathuser@example.com");

        Logger.log("Leaving password empty");
        loginPage.enterPassword("");

        Logger.log("Clicking Login button");
        loginPage.clickLogin();

        String errorMsg = loginPage.getPasswordErrorMessage();
        Logger.log("Password validation message: " + errorMsg);

        Assert.assertTrue(errorMsg.contains("Please enter password"), "Password error message not displayed");
        Logger.pass("Password validation message displayed correctly");
    }

    @Test(priority = 4)
    public void Checkforgotpassbtn() {
        Logger.log("=== Starting checkforgotpassbtn ===");

        LoginPage loginPage = new LoginPage(driver);

        Logger.log("Verifying Forgot Password button is displayed and clickable");
        Assert.assertTrue(driver.findElement(By.xpath("//a[normalize-space()='Forgot Password']")).isDisplayed(),
                "Forgot Password link not displayed");
        Assert.assertTrue(driver.findElement(By.xpath("//a[normalize-space()='Forgot Password']")).isEnabled(),
                "Forgot Password link not clickable");
        Logger.pass("Forgot Password button is clickable");

        Logger.log("Clicking Forgot Password button");
        loginPage.clickForgotPassword();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        String heading = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h4[@class='mb-2 text-center']")))
                .getText();

        Logger.log("Page heading after click: " + heading);
        Assert.assertEquals(heading, "Forgot Password?", "Redirection failed! Heading not found");
        Logger.pass("Redirected to Forgot Password page successfully");
    }

    @Test(priority = 5)
    public void Createaccount() {
        Logger.log("=== Starting createacc ===");

        LoginPage loginPage = new LoginPage(driver);

        Logger.log("Verifying Create Account button is displayed and clickable");
        Assert.assertTrue(driver.findElement(By.xpath("//a[normalize-space()='Create New Account']")).isDisplayed(),
                "Create Account button not displayed");
        Assert.assertTrue(driver.findElement(By.xpath("//a[normalize-space()='Create New Account']")).isEnabled(),
                "Create Account button not clickable");
        Logger.pass("Create Account button is clickable");

        Logger.log("Clicking Create Account button");
        loginPage.clickCreateAccount();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        String heading = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h4[@class='marginB30Only signUp-form text-center mb-0']")))
                .getText();

        Logger.log("Page heading after click: " + heading);
        Assert.assertTrue(heading.contains("Seller Registration"),
                "Redirection failed! 'Seller Registration' not found in heading. Actual: " + heading);
        Logger.pass("Redirected to Create Account page successfully");
    }
}



