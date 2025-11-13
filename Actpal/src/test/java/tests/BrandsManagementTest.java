package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.BrandsManagementPage;
import Pages.LoginPage;
import base.BaseTest;
import base.Logger;

public class BrandsManagementTest extends BaseTest {

    


	@Test(priority = 1)
    public void checkFullFlowOfBrandManagement() throws InterruptedException {
        Logger.log("=== Starting Brand Management Search Test ===");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();
        closePopups(1);

        Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");

        BrandsManagementPage brand = new BrandsManagementPage(driver);
        brand.OpenBarandManagmentPage();
        closePopups(1);
        brand.searchFunctionalityCheck("Z");
    }



        @Test(priority = 2)
        public void resetButtonFlow() throws InterruptedException {
            Logger.log("=== Starting Brand Management Reset Test ===");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterEmail("jump2brand@gmail.com");
            loginPage.enterPassword("Test@123");
            loginPage.clickLogin();
            closePopups(1);

            Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");

            BrandsManagementPage brand = new BrandsManagementPage(driver);
            brand.OpenBarandManagmentPage();
            closePopups(1);
            brand.searchFunctionalityCheck("Z");
            brand.resetButtonCheck();
        }
        
        @Test(priority = 3)
        public void AddnewBrandFullflow() throws InterruptedException {

            Logger.log("=== Starting Brand Management Full Flow Test ===");

            // ✅ Login Flow
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterEmail("jump2brand@gmail.com");
            loginPage.enterPassword("Test@123");
            loginPage.clickLogin();

            closePopups(1);

            Assert.assertTrue(
                    loginPage.getSuccessMessage().contains("Dashboard"),
                    "❌ Login failed"
            );

            // ✅ Navigate to Brand Management
            BrandsManagementPage brand = new BrandsManagementPage(driver);
            brand.OpenBarandManagmentPage();
            closePopups(1);

            // ✅ Add New Brand
            brand.addnewDiscountBTN(
                    "PrimeFashion",
                    "BlueWave Industries",
                    "9871123344",
                    "416",
                    "Maple Street, Block 7",
                    "service@bluewave.com",
                    "Algeria",
                    "Algiers",
                    "Hydra",
                    "16035"
            );

            // ✅ One method handles:
            //    - Duplicate brand
            //    - OR success popup + delete
            brand.handleBrandPopup("PrimeFashion");

            // ❌ REMOVE this — not needed anymore
            // brand.delteBrand("PrimeFashion");

            Logger.log("✅ Brand Management Full Flow Completed Successfully");
        }

      
   
        
        @Test(priority=4)
 
        public void verifyErrorCountTest() throws InterruptedException {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.enterEmail("jump2brand@gmail.com");
            loginPage.enterPassword("Test@123");
            loginPage.clickLogin();
            closePopups(1);

            Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");

            BrandsManagementPage brandsPage = new BrandsManagementPage(driver);
            brandsPage.OpenBarandManagmentPage();
            closePopups(1);
            

      
            boolean result = brandsPage.verifyErrorMessagesCount();

            Assert.assertTrue(result, "❌ Expected 7 error messages, but count is different.");
            Logger.log("✅ Test Passed: 7 error messages displayed correctly.");
        }
        
        @Test(priority=5)
        public void  EdirActionButtonFlow () throws InterruptedException {
        	 LoginPage loginPage = new LoginPage(driver);
             loginPage.enterEmail("jump2brand@gmail.com");
             loginPage.enterPassword("Test@123");
             loginPage.clickLogin();
             closePopups(1);

             Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");

             BrandsManagementPage brandsPage = new BrandsManagementPage(driver);
             brandsPage.OpenBarandManagmentPage();
             closePopups(1);
             
             String randomName = "Company_" + System.currentTimeMillis();
             brandsPage.editCompanyNameAndVerify(1, randomName);


             
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

