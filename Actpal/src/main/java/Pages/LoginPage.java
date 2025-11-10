package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage {

    private WebDriver driver;

    
    private By emailField = By.xpath("//input[@placeholder='Email']");
    private By passwordField = By.xpath("//input[@placeholder='Enter Password']");
    private By loginButton = By.xpath("//button[@type='submit']");
    private By errorMessageEmail = By.xpath("//span[@class='errMsg']");    
    private By errorMessagePassword = By.xpath("//span[@class='errMsg']"); 
    private By successMessage = By.xpath("//h4[@class='nomargin']");  
    private By rememberMeCheckbox = By.xpath("//input[@xpath='remember']");
    private By forgotPasswordLink = By.xpath("//a[normalize-space()='Forgot Password']");
    private By createAccountButton = By.xpath("//a[@class='btn new-account w-100 btn-md']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterEmail(String email) {
    	 Assert.assertTrue(driver.findElement(emailField).isDisplayed()," Email field is  not displayed");
   	  Assert.assertTrue(driver.findElement(emailField).isEnabled()," Email field is  not clickable");
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        System.out.println("Email field is presemnt");
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getEmailErrorMessage() {
        try {
            return driver.findElement(errorMessageEmail).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordErrorMessage() {
        try {
            return driver.findElement(errorMessagePassword).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getSuccessMessage() {
        try {
            return driver.findElement(successMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickRememberMe() {
    	  Assert.assertTrue(driver.findElement(rememberMeCheckbox).isDisplayed()," Remeber me button is visible  button not displayed");
    	  Assert.assertTrue(driver.findElement(rememberMeCheckbox).isEnabled()," Remember me  button is  not clickable");
    	  
        driver.findElement(rememberMeCheckbox).click();
        System.out.println(" Remebr me  button button is clickable");
    }

   

    public void clickForgotPassword() {
        driver.findElement(forgotPasswordLink).click();
    }

    public void clickCreateAccount() {
        driver.findElement(createAccountButton).click();
    }
}

