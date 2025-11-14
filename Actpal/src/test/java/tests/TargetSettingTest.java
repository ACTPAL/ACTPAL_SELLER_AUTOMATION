package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.LoginPage;
import Pages.TargetSettingPage;
import base.BaseTest;
import base.Logger;

public class TargetSettingTest extends BaseTest {

@ Test(priority=1)
	public void OpenTargetOpenPage () {
		LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();
        closePopups(1);

        Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");
        TargetSettingPage  targetpage = new TargetSettingPage(driver);
        
        targetpage.OpenTargetSettingPage();
        targetpage.searchFunctionality("rajeev1");
        targetpage.ResetButtonFunctionality();
        
        
		
	}

@Test(priority=2)
public void addnewTargetingFullFlow() throws InterruptedException {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.enterEmail("jump2brand@gmail.com");
    loginPage.enterPassword("Test@123");
    loginPage.clickLogin();
    closePopups(1);

    Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");
    TargetSettingPage targetpage = new TargetSettingPage(driver);

    targetpage.OpenTargetSettingPage();

    // ✅ Added random number here
    String randomName = "RajeevSingh" + (int)(Math.random() * 10000);

    // ✅ Create targeting and verify it
    targetpage.CreateaNewTargetting(randomName, 
                                   "This is new Targeting", 
                                   "Products",
                                   "F",
                                   "ind",
                                   "nep",
                                   "Male",
                                   "Married",
                                   "Rajeev");
}


	private void closePopups(int attempts) {
        for (int i = 0; i < attempts; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                By popupCloseBtn = By.xpath("//button[@class='btn-close' and @data-bs-dismiss='modal']");
                wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
                Logger.log("Popup closed successfully (attempt " + (i + 1) + ")");
            } catch (Exception e) {
                Logger.log("No popup appeared in attempt " + (i + 1) + ")");
            }
        }
    }

	
	
	

}
