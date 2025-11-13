package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.Discountmanagement;
import Pages.LoginPage;
import base.BaseTest;
import base.Logger;

public class Dicountmanagement extends BaseTest {

    private Discountmanagement discountPage;

    @BeforeMethod
    public void setUpTest() {
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
        discountPage = new Discountmanagement(driver);
        discountPage.DicountManagemntPage();  // navigate to page
    }
    
    @Test(priority  =1)
    public void validateAddNewDiscount_PositiveFlow() {
        // 🔹 Step 1: Add new discount
        discountPage.openAddDiscountForm();
        discountPage.selectCategory("Books, Media & Stationary");
        discountPage.selectSubCategory("PA & Stage");
        discountPage.selectProductType("DJ Equipment");
        discountPage.selectDiscountType("Percent(%)");
        discountPage.setDiscountValue("10");
        discountPage.clickAddDiscount();

        // 🔹 Step 2: Search and verify discount was added
        discountPage.SearchValidation("DJ Equipment");
        boolean resultFound = discountPage.isSearchResultPresent("DJ Equipment");
        Assert.assertTrue(resultFound, "❌ Discount not found in search results!");
        Logger.log("✅ Discount successfully added and verified through search.");

        // 🔹 Step 3: Delete the discount
        discountPage.deleteDiscount("DJ Equipment");

        // 🔹 Step 4: Wait briefly and re-search for the same product type
        discountPage.SearchValidation("DJ Equipment");
        boolean stillPresent = discountPage.isSearchResultPresent("DJ Equipment");

        // 🔹 Step 5: Validate deletion
        Assert.assertFalse(stillPresent, "❌ Discount still present after deletion!");
        Logger.log("✅ Discount successfully deleted — not found in search.");

        // 🔹 Step 6: Optional — check for "No any discount added yet." message
        boolean messageDisplayed = discountPage.isNoDiscountMessageVisible();
        if (messageDisplayed) {
            Logger.log("✅ 'No any discount added yet.' message displayed after deletion.");
        }
    }

    @Test(priority = 2, description = "Verify Dropdown Dependency Flow: Category → SubCategory → ProductType")
    public void verifyCategorySubcategoryDependency() {

        Logger.log("=== Starting Dropdown Dependency Flow Test ===");
        discountPage.openAddDiscountForm();

        // Step 1: Validate Default Placeholders
        Logger.log("Step 1: Validating default placeholders...");
        Assert.assertTrue(discountPage.isSubCategoryDefault(),
                "❌ SubCategory should default to '--Sub-Category--' when Category not selected.");
        Assert.assertTrue(discountPage.isProductTypeDefault(),
                "❌ Product Type should default to '--Type--' when SubCategory not selected.");
        Logger.pass("✅ Default placeholders verified successfully.");

        // Step 2: Select Category and Validate SubCategory Population
        Logger.log("Step 2: Selecting Category and verifying SubCategory list...");
        discountPage.selectCategory("Books, Media & Stationary"); // change as per your actual dropdown
        List<String> subOptions = discountPage.getDropdownOptions(discountPage.getSubCatogryDrpd());
        Logger.log("SubCategory options after selecting Category: " + subOptions);

        Assert.assertTrue(subOptions.size() > 1, "❌ SubCategory list did not populate after selecting Category!");
        Logger.pass("✅ SubCategory dropdown populated correctly after Category selection.");

        // Step 3: Select SubCategory and Validate Product Type Population
        Logger.log("Step 3: Selecting SubCategory and verifying Product Type list...");
        discountPage.selectSubCategory("PA & Stage"); // change as per your options
        List<String> typeOptions = discountPage.getDropdownOptions(discountPage.getProductTypeDrpd());
        Logger.log("Product Type options after selecting SubCategory: " + typeOptions);

        Assert.assertTrue(typeOptions.size() > 1, "❌ Product Type list did not populate after selecting SubCategory!");
        Logger.pass("✅ Product Type dropdown populated correctly after SubCategory selection.");

        Logger.pass("🎉 Dropdown dependency validation passed successfully!");
    }


    @Test(priority = 3)
    
    public void validateNoSelection_ShowsError() {
        discountPage.openAddDiscountForm();
        discountPage.clickAddDiscount();

        List<String> errors = discountPage.getAllErrorMessages();

        // ✅ Log count
        Logger.log("✅ Total errors displayed: " + errors.size());

        // ✅ Expect 4 validation errors
        Assert.assertEquals(errors.size(), 4, "Expected 4 validation errors but found " + errors.size());

        // ✅ Optional: verify each one is not empty
        for (String e : errors) {
            Assert.assertTrue(e.length() > 0, "Empty error text found!");
        }
    }

 
    
    @Test(priority = 4)
    public void verifyDiscountHistoryAndBackButton() {
        discountPage.openDiscountHistoryAndVerify();
        discountPage.backButtoncheckflow();
    }

    @Test(priority = 5)
    public void verifyStatusDropdownSorting() {
        discountPage.CheckstatusDropdawon("Expires Soon");
    }

    @Test(priority = 6)
    public void verifySearchAndCategoryFilter() {
        discountPage.checkCategory("Baby & Kids");
        discountPage.SearchValidation("T-Shirt");
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
    
}

