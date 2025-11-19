package tests;
 
import java.time.Duration;
 
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
 
import Pages.LoginPage;
import Pages.VisitorsPage;
import base.BaseTest;
import base.Logger;
 
public class VisitorsTest extends BaseTest {
 
	@Test(priority = 1)
	public void visitorsPageValidation() throws InterruptedException {
 
	    LoginPage loginPage = new LoginPage(driver);
	    loginPage.enterEmail("jump2brand@gmail.com");
	    loginPage.enterPassword("Test@123");
	    loginPage.clickLogin();
 
	    closePopups(1);
 
	    Assert.assertTrue(loginPage.getSuccessMessage().contains("Dashboard"), "❌ Login failed");
 
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
	    VisitorsPage visitPage = new VisitorsPage(wait, driver);
 
	    visitPage.OpenVisitorPage();
	        // STEP 1 → Count products before searching
	        int defaultCount = visitPage.getProductCount();
	        System.out.println("📌 Default product count: " + defaultCount);
 
	        // STEP 2 → Perform search
	        visitPage.searchFullFlow("Infinix Hot 8");
 
	        // After search, count should reduce
	        int searchCount = visitPage.getProductCount();
	        System.out.println("🔍 After search product count: " + searchCount);
 
	        Assert.assertTrue(searchCount < defaultCount,
	                "❌ Search did NOT filter results!");
 
	        // STEP 3 → Click reset
	        visitPage.resetBTNFlowCheck();
 
	        // STEP 4 → Count after reset
	        int afterResetCount = visitPage.getProductCount();
	        System.out.println("🔄 After reset product count: " + afterResetCount);
 
	        // STEP 5 → Validate matched with initial product count
	        Assert.assertEquals(afterResetCount, defaultCount,
	                "❌ Reset failed — product list did not return to default!");
 
	        System.out.println("✔ Reset successful — product list restored to default.");
	        visitPage.TotalVisitor();
	        visitPage.verifyVisitorPopupDetails();
	        visitPage.CheckcutBTN();
	        visitPage.clickExportAndVerifyDownload();
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