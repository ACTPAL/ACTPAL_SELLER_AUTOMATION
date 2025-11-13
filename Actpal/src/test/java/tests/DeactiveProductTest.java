package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import Pages.ActiveProductPage;
import Pages.AllProductsPage;
import Pages.DeactiveProductPage;
import Pages.LoginPage;
import base.BaseTest;
import base.Logger;

public class DeactiveProductTest  extends BaseTest {
	@Test(priority=1)
	 public void TestActiveProductPageLoads() {
    	 Logger.log("=== Starting testAllProductPageLoads ===");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();
        closePopups(1);
         
        String successMsg = loginPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Dashboard"), "Login failed");

        DeactiveProductPage page = new DeactiveProductPage (driver);
        page.clickDeactiveProductBtn();
        Assert.assertTrue(page.isDeactiveProductPageOpened(), "Active Product page not loaded successfully");
        Logger.log("✅ Active Product page opened successfully");
    }
	 private void closePopups(int attempts) {
	        for (int i = 0; i < attempts; i++) {
	            try {
	                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
	                By popupCloseBtn = By.xpath("//button[@class='btn-close' and @data-bs-dismiss='modal']");
	                wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
	                Logger.log("Popup closed successfully. Attempt " + (i + 1));
	            } catch (Exception e) {
	                Logger.log("No popup appeared in attempt " + (i + 1));
	            }
	        }
	    }
	 @Test(priority=2)
	 public void TestSearchKeywordFilter() {
	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();

	     closePopups(1);
	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();
	     page.searchProduct("Cricket Bat");

	     Assert.assertTrue(page.isProductPresent("Cricket Bat"), "Search keyword filter failed");
	     Logger.log("✅ Product 'Cricket Bat' is visible after search");
	 }

	 @Test(priority=3)
	 public void TestResetButton() {
	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();

	     closePopups(1);
	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();
	     page.searchProduct("Cricket Bat");

	     Assert.assertTrue(page.isProductPresent("Cricket Bat"), "Search keyword filter failed");
	     Logger.log("✅ Product 'Cricket Bat' is visible after search");

	     Assert.assertTrue(page.clickResetAndVerifyCleared(), "❌ Reset button did not clear the search field");
	     Logger.log("✅ Reset button Work Correctly and cleared the search field successfully");
	 }

	 @Test(priority = 4)
	 public void TestExportCSVFunctionality() {
	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();

	     closePopups(1);
	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();
	     page.clickExport();

	     String downloadPath = System.getProperty("user.home") + "/Downloads";
	     String fileNamePrefix = "ACTPAL_Product";

	     Assert.assertTrue(page.isFileDownloaded(downloadPath, fileNamePrefix, 10), 
	         "❌ CSV file was not downloaded successfully");
	     Logger.log("✅ CSV file downloaded successfully");
	 }

	 @Test(priority = 5)
	 public void TestSortByFilter() throws InterruptedException {
	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();

	     closePopups(1);
	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     page.applySortBy("Low to High Price");
	     Thread.sleep(2000);
	     List<Double> prices = page.getProductPrices();
	     Logger.log("Prices after Low to High Price sort: 1st=" + prices.get(0) + ", 2nd=" + prices.get(1));
	     Assert.assertTrue(prices.get(0) <= prices.get(1), "Low to High Price sorting failed");
	     Logger.log("✅ Low to High Price sorting verified successfully");

	     page.applySortBy("High to Low Price");
	     Thread.sleep(4000);
	     prices = page.getProductPrices();
	     Logger.log("Prices after High to Low Price sort: 1st=" + prices.get(0) + ", 2nd=" + prices.get(1));
	     Assert.assertTrue(prices.get(0) >= prices.get(1), "High to Low Price sorting failed");
	     Logger.log("✅ High to Low Price sorting verified successfully");

	     page.applySortBy("Low to High Discount");
	     Thread.sleep(5000);
	     List<Double> discounts = page.getProductDiscounts();
	     Logger.log("Discounts after Low to High Discount sort: 1st=" + discounts.get(0) + ", 2nd=" + discounts.get(1));
	     Assert.assertTrue(discounts.get(0) <= discounts.get(1), "Low to High Discount sorting failed");
	     Logger.log("✅ Low to High Discount sorting verified successfully");

	     page.applySortBy("High to Low Discount");
	     Thread.sleep(5000);
	     discounts = page.getProductDiscounts();
	     Logger.log("Discounts after High to Low Discount sort: 1st=" + discounts.get(0) + ", 2nd=" + discounts.get(1));
	     Assert.assertTrue(discounts.get(0) >= discounts.get(1), "High to Low Discount sorting failed");
	     Logger.log("✅ High to Low Discount sorting verified successfully");
	 }

	 @Test(priority = 6)
	 public void TestStatusFilter() throws InterruptedException {
	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();

	     closePopups(1);
	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	     page.applyStatusFilter("Active");
	     wait.until(driver -> driver.findElements(By.cssSelector("span.activateCurrentProduct")).size() > 0);
	     Thread.sleep(4000);

	     List<String> activeStatuses = page.getAllProductStatuses();
	     long activeCount = activeStatuses.stream().filter(s -> s.equalsIgnoreCase("Active")).count();

	     if (!activeStatuses.isEmpty()) {
	         boolean allActive = activeStatuses.stream().allMatch(s -> s.equalsIgnoreCase("Active"));
	         Assert.assertTrue(allActive, "❌ Some products are NOT Active after applying 'Active' filter!");
	         Logger.log("✅ Verified all products are Active. Count: " + activeCount);
	         Logger.log("Total Active Products: " + activeCount);
	     } else {
	         Logger.log("⚠️ No Active products found.");
	     }

	     page.applyStatusFilter("De-active");
	     wait.until(driver -> driver.findElements(By.cssSelector("span.activateCurrentProduct")).size() > 0);
	     Thread.sleep(4000);

	     List<String> deactiveStatuses = page.getAllProductStatuses();
	     long deactiveCount = deactiveStatuses.stream()
	         .filter(s -> s.equalsIgnoreCase("De-active") || s.equalsIgnoreCase("Deactive"))
	         .count();

	     if (!deactiveStatuses.isEmpty()) {
	         boolean allDeactive = deactiveStatuses.stream()
	                 .allMatch(s -> s.equalsIgnoreCase("De-active") || s.equalsIgnoreCase("Deactive"));
	         Assert.assertTrue(allDeactive, "❌ Some products are NOT De-active after applying 'De-active' filter!");
	         Logger.log("✅ Verified all products are De-active. Count: " + deactiveCount);
	         Logger.log("Total De-active Products: " + deactiveCount);
	     } else {
	         Logger.log("⚠️ No De-active products found.");
	     }

	     Logger.log("✅ Status filter test completed successfully!");
	 }

	 @Test(priority = 7)
	 public void TestAdvancedFilter() throws InterruptedException {
	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();
	     closePopups(1);

	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     Logger.log("✅ All Products page opened successfully");

	     List<Double> beforePrices = page.getProductPrices();
	     Logger.log("Prices before applying filter: " + beforePrices);

	     page.isHardFilterPanelDisplayed("Low to High Price");
	     Thread.sleep(2000);

	     List<Double> afterPrices = page.getProductPrices();
	     Logger.log("Prices after applying filter: " + afterPrices);

	     Assert.assertNotEquals(beforePrices, afterPrices, "⚠️ Prices did not change after applying filter!");

	     for (int i = 0; i < afterPrices.size() - 1; i++) {
	         Assert.assertTrue(afterPrices.get(i) <= afterPrices.get(i + 1),
	                 "❌ Prices are not sorted correctly at index " + i + ": " + afterPrices);
	     }

	     Logger.log("✅ Advanced Filter working properly and sorting verified.");
	 }

	 @Test(priority = 9)
	 public void testActionSection() {
	     Logger.log("=== Starting test: Add Inventory Flow ===");

	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();
	     closePopups(1);

	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     Logger.log("✅ All Products page opened successfully");

	     page.clickAddInventoryAndValidate();
	     page.clickViewAndValidate(0);
	     page.clickEditIcon(0);

	     Logger.log("=== Test Completed: Add Inventory Flow ===");
	 }

	 @Test(priority = 10)
	 public void AddnewProductBTN() {
	     Logger.log("=== Starting test: Add New Product Flow ===");

	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();
	     closePopups(1);

	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     page.addNewProductBtnCheck();

	     Logger.log("=== Test Completed: Add New Product Flow ===");
	 }

	 @Test(priority = 11)
	 public void TestToggleByIndex() {
	     Logger.log("=== Starting test: Toggle Status Flow ===");

	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();
	     closePopups(1);

	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     int indexToClick = 2;
	     boolean result = page.toggleStatusByIndex(indexToClick);
	     Assert.assertTrue(result, "❌ Toggle failed for row: " + indexToClick);
	     Logger.log("✅ Toggle successful for row " + indexToClick);
	 }

	 @Test(priority = 12)
	 public void TestFilterProductsByCategory() {
	     Logger.log("=== Starting test: Filter Products By Category ===");

	     LoginPage loginPage = new LoginPage(driver);
	     loginPage.enterEmail("jump2brand@gmail.com");
	     loginPage.enterPassword("Test@123");
	     loginPage.clickLogin();
	     closePopups(1);

	     DeactiveProductPage page1 = new DeactiveProductPage(driver);
	     AllProductsPage page = new AllProductsPage(driver);
	     page1.clickDeactiveProductBtn();

	     String categoryToSelect = "Men";
	     page.selectCategory(categoryToSelect);

	     boolean allMatch = page.verifyAllProductsBelongToCategory(categoryToSelect);
	     Assert.assertTrue(allMatch, "Some products do not belong to category: " + categoryToSelect);
	 }


}
