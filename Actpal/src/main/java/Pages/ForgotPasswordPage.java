package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    private WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    private By forgotBtn = By.xpath("//a[normalize-space()='Forgot Password']");
    private By emailField = By.xpath("//input[@placeholder='Email']");
    private By resetPassBtn = By.xpath("//button[@type='submit']");
    private By passwordField = By.xpath("//input[@placeholder='Your Password']");
    private By confirmPass = By.xpath("//input[@placeholder='Confirm Password']");
    private By resetPassBtn2 = By.xpath("//button[@type='submit']");

    public void clickForgotPassword() {
        driver.findElement(forgotBtn).click();
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickResetPasswordBtn() {
        driver.findElement(resetPassBtn).click();
    }

    public void enterNewPassword(String newPassword) {
        driver.findElement(passwordField).sendKeys(newPassword);
    }

    public void enterConfirmPassword(String confirmPassword) {
        driver.findElement(confirmPass).sendKeys(confirmPassword);
    }

    public void clickFinalResetPasswordBtn() {
        driver.findElement(resetPassBtn2).click();
    }
}
