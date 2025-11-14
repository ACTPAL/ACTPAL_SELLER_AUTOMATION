package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.FollowersListPage;
import Pages.LoginPage;
import base.BaseTest;
import base.Logger;

public class FollowersListTest   extends BaseTest {
	
	@Test(priority =1 )
	public  void OpenFollowerListpage () throws InterruptedException {
		  LoginPage loginPage = new LoginPage(driver);
	        loginPage.enterEmail("jump2brand@gmail.com");
	        loginPage.enterPassword("Test@123");
	        loginPage.clickLogin();
	        closePopups(1);

	        Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");
	        FollowersListPage FollowerPage = new FollowersListPage(driver);
	        FollowerPage.OpenFollowersListPage("India","Haryana","Bhiwani","Priya");
	        FollowerPage.DeleteValidation();
	        
	        
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
