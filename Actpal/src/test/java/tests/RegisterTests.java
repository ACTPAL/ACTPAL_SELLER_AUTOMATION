package tests;

import Pages.RegisterPage;
import base.BaseTest;
import base.Logger;

import java.time.Duration;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {

    @Test(priority = 1)
    public void TestUserRegistration() throws InterruptedException {
        Logger.log("=== Starting testUserRegistration ===");
        RegisterPage register = new RegisterPage(driver);

        Logger.log("Clicking Become Seller");
        register.clickBecomeSeller();

        Logger.log("Entering Business Identity Name");
        register.enterBusinessIdentityName("My Test Business");

        Logger.log("Selecting Industry: Home and Furniture");
        register.selectIndustry("Home and Furniture");

        Logger.log("Selecting Business Type: Furniture Outlets");
        register.selectBusinessType("Furniture Outlets");

        String email = "test" + System.currentTimeMillis() + "@mail.com";
        Logger.log("Entering Business Email: " + email);
        register.enterBusinessEmail(email);

        String randomUser = "testUser" + UUID.randomUUID().toString().substring(0, 6);
        Logger.log("Entering Username: " + randomUser);
        register.enterBusinessUsername(randomUser);

        Logger.log("Entering CAPTCHA");
        register.enterCaptcha();

        Logger.log("Clicking Next Step 1");
        register.clickNextStep1();

        Logger.log("Filling Customer Details");
        register.enterCustomerName("John Doe");
        register.enterEIN("123456789");
        register.selectCountry("India");
        register.selectState("Andhra Pradesh");
        register.selectCity("Adivivaram");
        register.enterZipCode("110001");
        register.enterAddress("123 Test Street");
        register.enterPhone("+91", "9876543210");

        Logger.log("Clicking Next Step 2");
        register.clickNextStep2();

        Logger.log("Setting Password and Confirm Password");
        register.enterPassword("Password@123");
        register.enterConfirmPassword("Password@123");

        Logger.log("Agreeing to Terms");
        register.clickTermsCheckbox();

        Logger.log("Clicking Create Account");
        register.clickCreateAccount();

        Assert.assertTrue(register.isPopupDisplayed(), "Registration success popup not displayed!");
        Logger.pass("✅ Registration successful, popup displayed");
        register.closePopup();
    }

    @Test(priority = 2)
    public void UserRegistrationDuplicateEmail() {
        Logger.log("=== Starting UserRegistrationDuplicateEmail ===");
        RegisterPage register = new RegisterPage(driver);

        Logger.log("Clicking Become Seller");
        register.clickBecomeSeller();

        Logger.log("Entering Business Identity Name");
        register.enterBusinessIdentityName("My Test Business");
        register.selectIndustry("Home and Furniture");
        register.selectBusinessType("Furniture Outlets");

        Logger.log("Entering Duplicate Email: rajeevsingh1862003@gmail.com");
        register.enterBusinessEmail("rajeevsingh1862003@gmail.com");

        String randomUser = "testUser" + UUID.randomUUID().toString().substring(0, 6);
        Logger.log("Entering Username: " + randomUser);
        register.enterBusinessUsername(randomUser);

        Logger.log("Entering CAPTCHA");
        register.enterCaptcha();

        Logger.log("Clicking Next Step 1");
        register.clickNextStep1();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement errorMsgElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='errorMsg']"))
        );
        String errorText = errorMsgElement.getText();
        Logger.log("Duplicate Email Error Message: " + errorText);
        Assert.assertEquals(errorText, "Email id already exists!", "Duplicate email validation failed!");
        Logger.pass("✅ Duplicate email validation passed");
    }

    @Test(priority = 3)
    public void UserRegistrationDuplicateuserName() {
        Logger.log("=== Starting UserRegistrationDuplicateuserName ===");
        RegisterPage register = new RegisterPage(driver);

        Logger.log("Clicking Become Seller");
        register.clickBecomeSeller();
        register.enterBusinessIdentityName("My Test Business");
        register.selectIndustry("Home and Furniture");
        register.selectBusinessType("Furniture Outlets");

        String email = "test" + System.currentTimeMillis() + "@mail.com";
        Logger.log("Entering Business Email: " + email);
        register.enterBusinessEmail(email);

        Logger.log("Entering Duplicate Username: rajjevsimhgh");
        register.enterBusinessUsername("rajjevsimhgh");

        Logger.log("Entering CAPTCHA");
        register.enterCaptcha();

        Logger.log("Clicking Next Step 1");
        register.clickNextStep1();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement errorMsgElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='errorMsg']"))
        );
        String errorText = errorMsgElement.getText();
        Logger.log("Duplicate Username Error Message: " + errorText);
        Assert.assertEquals(errorText, "Username already exists!", "Duplicate username validation failed!");
        Logger.pass("✅ Duplicate username validation passed");
    }
}

