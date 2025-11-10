package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import base.Logger;
import Pages.LoginPage;
import Pages.AddProductPage;

public class AddProductTest extends BaseTest {

    @Test(priority = 1)
    public void TestAddNewProductFullFlow() throws InterruptedException {
        Logger.log("=== Starting testAddNewProductFullFlow ===");

        // --- Step 1: Login ---
        Logger.log("Step 1: Logging in");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();

        String successMsg = loginPage.getSuccessMessage();
        try {
            Assert.assertTrue(successMsg.contains("Dashboard"), "Login failed - Dashboard not loaded!");
            Logger.pass("Login successful: " + successMsg);
        } catch (AssertionError e) {
            Logger.fail("Login failed: " + e.getMessage());
            throw e;
        }

        // --- Step 2: Close popups ---
        Logger.log("Step 2: Closing popups if any");
        closePopups(1);

        AddProductPage addProduct = new AddProductPage(driver);

        // --- Step 3: Open Add Product Page ---
        Logger.log("Step 3: Opening Add Product Page");
        addProduct.openAddNewProductPage();
        closePopups(1);

        // --- Step 4: Generate and store SKU ---
        String generatedSKU = "SKU" + System.currentTimeMillis();
        Logger.log("Step 4: Generated Product ID (SKU): " + generatedSKU);

        // --- Step 5: Enter Basic Details ---
        Logger.log("Step 5: Entering product basic details");
        addProduct.enterBasicDetails(
                "Football",
                generatedSKU,
                "This is a short summary",
                "This is a detailed description",
                "GTIN",
                "ZARA",
                "New",
                "ChokingHazardBalloon",
                "Men",
                "Watches & Wearable",
                "Mens Watches"
        );
        addProduct.clickAddProduct();
        Logger.pass("Basic details entered successfully");

        // --- Step 6: Add Attributes ---
        Logger.log("Step 6: Adding attributes");
        addProduct.addAttribute("Color", "2");
        Logger.pass("Attribute added successfully");

        // --- Step 7: Discount ---
        Logger.log("Step 7: Setting discount");
        addProduct.selectDiscountOption(true);
        addProduct.setDiscountDetails("Percent(%)", "10", true);
        addProduct.clickDiscountNext();
        Logger.pass("Discount applied successfully");

        // --- Step 8: Sales Policy ---
        Logger.log("Step 8: Filling sales policy");
        addProduct.fillSalePolicy();
        Logger.pass("Sales policy filled");

        // --- Step 9: Search Keyword ---
        Logger.log("Step 9: Searching keyword 'Product1'");
        addProduct.searchKeyword("Product1");
        Logger.pass("Keyword search completed");

        // --- Step 10: Add Inventory ---
        Logger.log("Step 10: Adding inventory for SKU: " + generatedSKU);
        addProduct.addInventory(
                generatedSKU,
                "Black",
                "M",
                "12",
                "999",
                "10",
                "4",
                "10",
                "20",
                "5"
        );
        Logger.pass("Inventory added successfully");

        // --- Step 11: Validation ---
        Logger.log("Step 11: Validating product addition");
        addProduct.validation(generatedSKU);

        WebElement skuElement = driver.findElement(By.xpath("//td[contains(@class,'text-secondary') and text()='" + generatedSKU + "']"));
        String actualSKU = skuElement.getText();

        try {
            Assert.assertEquals(actualSKU, generatedSKU, "SKU does not match! Product not added successfully.");
            Logger.pass("Product added successfully with SKU: " + actualSKU);
        } catch (AssertionError e) {
            Logger.fail("Product validation failed: " + e.getMessage());
            throw e;
        }

        Logger.log("=== Finished testAddNewProductFullFlow ===");
        Thread.sleep(5000);
    }

    @Test(priority = 2)
    public void CheckDupicatproduct() throws InterruptedException {
        Logger.log("=== Starting checkDupicatproduct ===");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();

        String successMsg = loginPage.getSuccessMessage();
        try {
            Assert.assertTrue(successMsg.contains("Dashboard"), "Login failed - Dashboard not loaded!");
            Logger.pass("Login successful: " + successMsg);
        } catch (AssertionError e) {
            Logger.fail("Login failed: " + e.getMessage());
            throw e;
        }

        closePopups(1);

        AddProductPage addProduct = new AddProductPage(driver);
        addProduct.openAddNewProductPage();
        closePopups(1);

        Logger.log("Filling basic details for duplicate check");
        addProduct.enterBasicDetails(
                "Test Product Auto",
                "567",
                "This is a short summary",
                "This is a detailed description",
                "GTIN",
                "ZARA",
                "New",
                "ChokingHazardBalloon",
                "Men",
                "Watches & Wearable",
                "Mens Watches"
        );
        addProduct.clickAddProduct();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        By duplicateMsg = By.xpath("//p[contains(@class,'text-danger') and contains(text(),'This Product Id already exists with another product')]");

        try {
            String actualMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(duplicateMsg)).getText();
            Assert.assertTrue(actualMsg.contains("This Product Id already exists with another product"));
            Logger.pass("Duplicate product detected successfully: " + actualMsg);
        } catch (Exception e) {
            Logger.fail("Duplicate product message not displayed — validation failed!");
            Assert.fail(e.getMessage());
        }

        Logger.log("=== Finished checkDupicatproduct ===");
    }

    @Test(priority =3)
    public void TestMandatoryBasicDetailsValidationSeparate() throws InterruptedException {
        Logger.log("=== Starting testMandatoryBasicDetailsValidationSeparate ===");

        LoginPage loginPage = new LoginPage(driver);
        AddProductPage addProduct = new AddProductPage(driver);

        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();

        String successMsg = loginPage.getSuccessMessage();
        try {
            Assert.assertTrue(successMsg.contains("Dashboard"), "Login failed - Dashboard not loaded!");
            Logger.pass("Login successful: " + successMsg);
        } catch (AssertionError e) {
            Logger.fail("Login failed: " + e.getMessage());
            throw e;
        }

        closePopups(1);

        addProduct.openAddNewProductPage();
        closePopups(1);

        Logger.log("Validating mandatory basic details");
        addProduct.validateMandatoryBasicDetails();
        Logger.pass("Mandatory basic details validation completed");

        Logger.log("=== Finished testMandatoryBasicDetailsValidationSeparate ===");
    }

    @Test(priority = 4)
    public void VerifyCategorySubcategoryDependency() throws InterruptedException {
        Logger.log("=== Starting verifyCategorySubcategoryDependency ===");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail("jump2brand@gmail.com");
        loginPage.enterPassword("Test@123");
        loginPage.clickLogin();

        String successMsg = loginPage.getSuccessMessage();
        try {
            Assert.assertTrue(successMsg.contains("Dashboard"), "Login failed - Dashboard not loaded!");
            Logger.pass("✅ Login successful: " + successMsg);
        } catch (AssertionError e) {
            Logger.fail("Login failed: " + e.getMessage());
            throw e;
        }

        closePopups(1);

        AddProductPage addProduct = new AddProductPage(driver);
        addProduct.openAddNewProductPage();
        closePopups(1);

        Logger.log("Verifying category-subcategory dependency");
        addProduct.verifyCategorySubcategoryDependency("Men", "Watches & Wearable");
        Logger.pass("Category-subcategory dependency verified successfully");

        Logger.log("=== Finished verifyCategorySubcategoryDependency ===");
    }

    // --- Helper method ---
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
}


