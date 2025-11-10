package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {
    private WebDriver driver;

    // Step 1 – Business Details
    private By becomeSellerBtn = By.xpath("//a[normalize-space()='Become a Seller']");
    private By businessIdentityName = By.xpath("//input[@name='businessName']");
    private By industryDropdown = By.id("Industry-DropdownID");
    private By businessTypeDropdown = By.xpath("//select[@name='businessType']");
    private By businessEmailField = By.xpath("//input[@name='email']");
    private By businessUsernameField = By.xpath("//input[@name='userName']");
    private By captchaLabel = By.xpath("//label[@id='captchaLabel']");
    private By captchaInput = By.xpath("//input[@placeholder='Enter the code shown above']");
    private By nextBtnStep1 = By.xpath("//button[@type='submit']");

    // Step 2 – Contact Person
    private By customerNameField = By.xpath("//input[@name='contactPersonName']");
    private By einField = By.xpath("//input[@name='einNumber']");
    private By countryDropdown = By.xpath("//select[@name='country']");
    private By stateDropdown = By.xpath("//select[@name='state']");
    private By cityDropdown = By.xpath("//select[@name='city']");
    private By zipCodeField = By.xpath("//input[@name='zipCode']");
    private By addressField = By.xpath("//textarea[@name='address']");
    private By countryCodeField = By.xpath("//input[@placeholder='Country code']");
    private By phoneNumberField = By.xpath("//input[@placeholder='Phone number']");

    // Step 3 – Password
    private By passwordField = By.xpath("//input[@name='password']");
    private By confirmPasswordField = By.xpath("//input[@name='confirmPassword']");
    private By termsCheckbox = By.xpath("//input[@id='termcondition']");
    private By createAccountBtn = By.xpath("//button[@type='submit']");

    // Confirmation popup
    private By successPopup = By.xpath("//div[@class='border-0 justify-content-center mt-4 modal-header']");
    private By closePopupBtn = By.xpath("//button[normalize-space()='Close']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Step 1 actions ---
    public void clickBecomeSeller() {
        driver.findElement(becomeSellerBtn).click();
    }

    public void enterBusinessIdentityName(String name) {
        driver.findElement(businessIdentityName).sendKeys(name);
    }

    public void selectIndustry(String industry) {
        Select dropdown = new Select(driver.findElement(industryDropdown));
        dropdown.selectByVisibleText(industry);
    }

    public void selectBusinessType(String type) {
    	Select dropdown = new Select(driver.findElement(businessTypeDropdown));
        dropdown.selectByVisibleText(type);
    }

    public void enterBusinessEmail(String email) {
        driver.findElement(businessEmailField).sendKeys(email);
    }

    public void enterBusinessUsername(String username) {
        driver.findElement(businessUsernameField).sendKeys(username);
    }

    public void enterCaptcha() {
    	String captchaText = driver.findElement(captchaLabel).getText();
        captchaText = captchaText.replaceAll("\\s+", ""); 
        driver.findElement(captchaInput).clear();
        driver.findElement(captchaInput).sendKeys(captchaText);
    }

    public void clickNextStep1() {
        driver.findElement(nextBtnStep1).click();
    }

    // --- Step 2 actions ---
    public void enterCustomerName(String name) {
        driver.findElement(customerNameField).sendKeys(name);
    }

    public void enterEIN(String ein) {
        driver.findElement(einField).sendKeys(ein);
    }

    public void selectCountry(String country) {
        new Select(driver.findElement(countryDropdown)).selectByVisibleText(country);
    }

    public void selectState(String state) {
        new Select(driver.findElement(stateDropdown)).selectByVisibleText(state);
    }

    public void selectCity(String city) {
        new Select(driver.findElement(cityDropdown)).selectByVisibleText(city);
    }

    public void enterZipCode(String zip) {
        driver.findElement(zipCodeField).sendKeys(zip);
    }

    public void enterAddress(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void enterPhone(String code, String phone) {
        driver.findElement(countryCodeField).sendKeys(code);
        driver.findElement(phoneNumberField).sendKeys(phone);
    }

    public void clickNextStep2() {
    	WebElement nextBtnStep2 = driver.findElement(By.xpath("//button[@type='submit']"));

        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0,200);", nextBtnStep2);

        // Small wait to stabilize
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Click
        nextBtnStep2.click();
    }

    // --- Step 3 actions ---
    public void enterPassword(String pwd) {
        driver.findElement(passwordField).sendKeys(pwd);
    }

    public void enterConfirmPassword(String pwd) {
        driver.findElement(confirmPasswordField).sendKeys(pwd);
    }

    public void clickTermsCheckbox() {
        if (!driver.findElement(termsCheckbox).isSelected()) {
            driver.findElement(termsCheckbox).click();
        }
    }

    public void clickCreateAccount() {
        driver.findElement(createAccountBtn).click();
    }

    // --- Confirmation ---
    public boolean isPopupDisplayed() {
        return driver.findElement(successPopup).isDisplayed();
    }

    public void closePopup() {
        driver.findElement(closePopupBtn).click();
    }
}
