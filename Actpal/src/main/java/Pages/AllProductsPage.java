package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;



import base.Logger;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AllProductsPage {

    private WebDriver driver;
    public AllProductsPage(WebDriver driver) {
        this.driver = driver;
     
    }

    // --- Locators ---
    private By manageProductBtn = By.xpath("//a[@class='icon icon-manage-products icon-set']");
    private By allProductBTN = By.xpath("//span[normalize-space()='All Products']");
    private By pageHeading = By.xpath("//h4[normalize-space()='Manage Products']");
    private By importBtn = By.xpath("//button[normalize-space()='Import']");
    private By exportCSVBtn = By.xpath("//span[normalize-space()='Export']");
    private By searchBox = By.xpath("//input[@id='product_Search']");
    private By searchBtn = By.xpath("//i[@class='fa-solid fa-magnifying-glass']");
    private By resetBtn = By.xpath("//button[normalize-space()='Reset']");
    private By statusFilter = By.xpath("//select[@name='Status']");
    private By productStatusList = By.cssSelector("span.activateCurrentProduct");
    private By productCells = By.cssSelector("td.w-12");
    private By hardFilterPanel = By.xpath("//button[@id='advanceFilterPopup']");
    private By advancefilterSeachBTn = By.xpath("//*[@id=\"submitProductSearch\"]");
    private By advancedFilterBtn = By.xpath("//span[normalize-space()='Advance Filter']");
    private By paginationNext = By.xpath("//a[@aria-label='Next']");
    private By emptyMsg = By.xpath("//div[text()='No products available']");
    private By addInventoryBtn = By.xpath("//button[@title='Add Inventory']");
    private By AddProductInventory  = By.xpath("//*[@id=\"myModalLabel\"]");
    private By addInventoryActionBtn1 = By.xpath("//button[contains(@class,'editInventory') and text()='Add Inventory']");
    private By editIcon = By.xpath("//tr[@id='15767']//i[@class='icon icon-edit']");
    private By addnewProductBTN =  By.xpath("//span[@class='tillTab ']");
    private By toggleButtons = By.xpath("//span[contains(@class,'activateCurrentProduct')]");
    private By categoryDropdown = By.id("CategoryDropDown");
    private By productCategoryTexts = By.xpath("//a[contains(@class,'fs-xsmall') and contains(@class,'text-secondary')]");
    // --- Page Actions ---
    public void clickAllProductBtn() {
        try {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

            // Step 1: Click "Manage Product"
            WebElement manageBtn = wait.until(ExpectedConditions.elementToBeClickable(manageProductBtn));
            manageBtn.click();

            // Step 2: Wait until "All Products" button becomes clickable
            WebElement allProduct = wait.until(ExpectedConditions.elementToBeClickable(allProductBTN));

            // Step 3: Scroll All Products into center view
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", allProduct);
            Thread.sleep(500); // let scroll finish

            // Step 4: Click normally, with JS fallback
            try {
                allProduct.click();
            } catch (ElementClickInterceptedException e) {
                Logger.log("⚠️ Click intercepted — trying JavaScript click...");
                js.executeScript("arguments[0].click();", allProduct);
            }

            // Step 5: Optional – close any popups
            closePopups(1);

            // Step 6: Smoothly scroll the page to the top
            js.executeScript("window.scrollTo({top: 0, behavior: 'smooth'});");
            Thread.sleep(800); // short delay for smooth scroll to finish

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("❌ Failed to click on 'All Products' button: " + e.getMessage());
        }
    }


 // In AllProductsPage.java


    public boolean isAllProductPageOpened() {
        try {
            return driver.findElement(pageHeading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void clickImport() {
        driver.findElement(importBtn).click();
    }

    public void clickExport() {
        driver.findElement(exportCSVBtn).click();
    }



    public boolean isFileDownloaded(String downloadPath, String fileNamePrefix, int timeoutInSeconds) {
        File folder = new File(downloadPath);
        int waited = 0;
        while (waited < timeoutInSeconds) {
            File[] listOfFiles = folder.listFiles((dir, name) -> name.startsWith(fileNamePrefix) && name.endsWith(".csv"));
            if (listOfFiles != null && listOfFiles.length > 0) {
                return true; // File found
            }
            try {
                Thread.sleep(1000); // wait 1 second
                waited++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false; // Timeout, file not found
    }


    public void searchProduct(String keyword) {
        driver.findElement(searchBox).clear();
        driver.findElement(searchBox).sendKeys(keyword);
        driver.findElement(searchBtn).click();
    }
    
    public boolean clickResetAndVerifyCleared() {
        driver.findElement(resetBtn).click();

       
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(driver -> {
            String value = driver.findElement(searchBox).getAttribute("value");
            return value == null || value.trim().isEmpty();
        });
    }

    public boolean isProductPresent(String productName) {
        try {
            By productLink = By.xpath("//a[normalize-space(text())='" + productName + "']");
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(productLink));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void applyStatusFilter(String status) {
        Select select = new Select(driver.findElement(statusFilter));
        select.selectByVisibleText(status);
        driver.findElement(searchBtn).click();

       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productStatusList));
            Logger.log("✅ Filter applied successfully for: " + status);
        } catch (TimeoutException e) {
            Logger.log("⚠️ No products found for filter: " + status);
        }
    }

    // 🧠 Get all visible product statuses from data attribute or inner text
    public List<String> getAllProductStatuses() {
        List<WebElement> statusElements = driver.findElements(productStatusList);
        List<String> statuses = new ArrayList<>();

        if (statusElements.isEmpty()) {
            Logger.log("⚠️ No product statuses found!");
            return statuses;
        }

        for (WebElement el : statusElements) {
            String dataStatus = el.getAttribute("data-status"); // "1" or "0"
            String textStatus = el.getText().trim(); // "Active" or "Deactive"

            // Prefer text, fallback to data-status
            String finalStatus = !textStatus.isEmpty()
                    ? textStatus
                    : ("1".equals(dataStatus) ? "Active" : "Deactive");

            statuses.add(finalStatus);
            Logger.log("Product Status: " + finalStatus + " (data-status=" + dataStatus + ")");
        }

        return statuses;
    }
    
    public void  isHardFilterPanelDisplayed(String criteria) {
    	    driver.findElement(hardFilterPanel).click();
    	    WebElement dropdown = driver.findElement(By.xpath("//li[@class='col-xs']//select[@name='SortByPrice']")); // matches <select name="SortByPrice">
            Select select = new Select(dropdown);
            select.selectByVisibleText(criteria); // must match option text exactly
            driver.findElement(advancefilterSeachBTn).click();
    	    
       
    }

    public void clickAdvancedFilter() {
        driver.findElement(advancedFilterBtn).click();
    }

    public void navigatePaginationNext() {
        driver.findElement(paginationNext).click();
    }

    public boolean isEmptyListMessageDisplayed() {
        return driver.findElement(emptyMsg).isDisplayed();
    }

    public void clickAddInventoryAndValidate() {
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    	wait.until(ExpectedConditions.elementToBeClickable(addInventoryBtn)).click();
        
        // Wait until modal is visible
      
        WebElement modalTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(AddProductInventory));
        
        // Assert the modal title
        Assert.assertTrue(modalTitle.isDisplayed(), "Add Inventory modal title is not displayed");
        
        // Check if Add Inventory action button is visible
        WebElement addInventoryActionBtn = driver.findElement(addInventoryActionBtn1);
        Assert.assertTrue(addInventoryActionBtn.isDisplayed(), "Add Inventory action button is not visible");
        
        Logger.log("Add Inventory modal opened successfully and action button is visible.");
        driver.findElement(By.xpath("//i[@class='fa-regular fa-circle-xmark fa-lg']")).click();
    }
 // In AllProductsPage.java
    public void clickViewAndValidate(int rowIndex) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        By viewButtonsLocator = By.xpath("//i[contains(@class,'icon-view')]");

        // Wait until all view buttons are visible
        List<WebElement> viewButtons = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(viewButtonsLocator)
        );

        Logger.log("Total View buttons found: " + viewButtons.size());

        if (viewButtons.isEmpty()) {
            throw new IllegalStateException("❌ No View buttons found on the page.");
        }

        if (rowIndex >= viewButtons.size()) {
            throw new IllegalArgumentException(
                "❌ Invalid row index: " + rowIndex + ". Total buttons: " + viewButtons.size()
            );
        }

        WebElement viewButton = viewButtons.get(rowIndex);

        // Scroll into view and click
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", viewButton);
        js.executeScript("arguments[0].click();", viewButton);

        Logger.log("🖱️ Clicked on View button at index: " + rowIndex);

        // Save current tab handle
        String originalWindow = driver.getWindowHandle();

        // Wait for new tab to appear and switch
        wait.until(driver1 -> driver1.getWindowHandles().size() > 1);
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        Logger.log("🔄 Switched to new tab.");

        // Wait for product details page
        wait.until(ExpectedConditions.urlContains("product_details"));
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(
            currentUrl.contains("product_details"),
            "❌ Expected URL to contain 'product_details', but found: " + currentUrl
        );

        Logger.log("✅ Product Details page opened successfully in new tab: " + currentUrl);

        // ✅ Close the new tab and switch back
        driver.close();
        driver.switchTo().window(originalWindow);
        Logger.log("🔙 Closed member tab and returned to original tab.");
    }




    public void clickEditIcon(int rowIndex) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // ✅ Convert to 0-based index for internal use
        int actualIndex = rowIndex - 1;

        // ✅ Wait until at least one Edit icon appears
        By editIconLocator = By.xpath("//i[contains(@class,'icon-edit')]");
        List<WebElement> icons = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(editIconLocator));

        int totalIcons = icons.size();
        Logger.log("🧭 Total Edit icons found: " + totalIcons);

        if (totalIcons == 0) {
            throw new IllegalStateException("❌ No Edit icons found on the All Products page!");
        }

        if (actualIndex < 0 || actualIndex >= totalIcons) {
            throw new IllegalArgumentException("❌ Invalid index: " + rowIndex + ". Must be between 1 and " + totalIcons);
        }

        // ✅ Re-fetch the element to avoid stale reference
        WebElement editBtn = driver.findElements(editIconLocator).get(actualIndex);

        // Scroll and click
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", editBtn);
        js.executeScript("arguments[0].click();", editBtn);
        Logger.log("🖱️ Clicked Edit icon at position: " + rowIndex);

        // ✅ Validate Edit Product page loaded
        try {
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h5[@class='blueHeading fw-semibold mb-0']")));
            WebElement productNameLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[contains(normalize-space(),'Product Name')]")));

            if (heading.isDisplayed() && productNameLabel.isDisplayed()) {
                Logger.log("✅ Edit Product page opened successfully.");
            } else {
                throw new AssertionError("❌ Edit Product page did not load correctly — expected elements not visible.");
            }

        } catch (Exception e) {
            throw new AssertionError("❌ Edit Product page did not load correctly — expected elements not found.", e);
        }
    }



    public void addNewProductBtnCheck() {
        driver.findElement(addnewProductBTN).click();
        Logger.log("🖱️ Clicked 'Add New Product' button.");
        closePopups(1);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Optional popup handling
         

            // Wait for heading and label directly
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class,'blueHeading') and not(contains(@class,'fs-xs-small')) and normalize-space()='Create a New Product']")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(normalize-space(),'Product Name')]")));

            Logger.log("✅ 'Create a New Product' page loaded successfully.");

        } catch (Exception e) {
            throw new AssertionError("❌ 'Create a New Product' page did not load correctly — expected elements not found.", e);
        }
    }

    public boolean toggleStatusByIndex(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        List<WebElement> buttons = driver.findElements(toggleButtons);
        if (buttons.isEmpty()) {
            throw new RuntimeException("No toggle buttons found on the page!");
        }
        if (index <= 0 || index > buttons.size()) {
            throw new IllegalArgumentException("Invalid index: " + index + ". Total buttons: " + buttons.size());
        }

        WebElement button = buttons.get(index - 1);
        String oldText = button.getText().trim();
        String oldStatus = button.getAttribute("data-status");

        // Click toggle button (normal or JS fallback)
        try {
            button.click();
        } catch (WebDriverException e) {  // covers ElementClickInterceptedException
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }

        // Wait for popup button (Activate or Deactivate)
        By popupBtn = By.xpath("//button[contains(@class,'defaultBrownBtn') and " +
                "(normalize-space(text())='Activate' or normalize-space(text())='Deactivate')]");
        WebElement popupButton = wait.until(ExpectedConditions.elementToBeClickable(popupBtn));

        // Click popup confirm button
        try {
            popupButton.click();
        } catch (WebDriverException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popupButton);
        }

        // Wait for status to change
        boolean changed = false;
        for (int i = 0; i < 10; i++) {
            List<WebElement> updatedButtons = driver.findElements(toggleButtons);
            WebElement newButton = updatedButtons.get(index - 1);
            String newText = newButton.getText().trim();
            if (!newText.equalsIgnoreCase(oldText)) {
                changed = true;
                break;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }

        if (!changed) {
            Logger.log("Status did not change after confirming popup for index " + index);
            return false;
        }

        // Verify final status
        WebElement updatedButton = driver.findElements(toggleButtons).get(index - 1);
        String newText = updatedButton.getText().trim();
        String newStatus = updatedButton.getAttribute("data-status");

        Logger.log("Old: " + oldText + " (" + oldStatus + ") -> New: " + newText + " (" + newStatus + ")");

        if ("1".equals(oldStatus)) {
            return "0".equals(newStatus) && newText.equalsIgnoreCase("De-active");
        } else if ("0".equals(oldStatus)) {
            return "1".equals(newStatus) && newText.equalsIgnoreCase("Active");
        } else {
            return false;
        }
    }
    
    public void selectCategory(String categoryName) {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));

        Select select = new Select(dropdown);
        select.selectByVisibleText(categoryName);

        // Wait for product list to refresh — using presence of category text links
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCategoryTexts));
    }

    // Get all category text values shown for products
    public List<WebElement> getAllProductCategoryTexts() {
        return driver.findElements(productCategoryTexts);
    }

    // Verify all products contain the expected category
    public boolean verifyAllProductsBelongToCategory(String expectedCategory) {
        List<WebElement> productCategories = getAllProductCategoryTexts();
        if (productCategories.isEmpty()) {
            Logger.log("⚠️ No products found for category: " + expectedCategory);
            return false;
        }

        for (WebElement categoryText : productCategories) {
            String text = categoryText.getText().trim();
            Logger.log("Product category text: " + text);
            if (!text.contains(expectedCategory)) {
                Logger.log("❌ Found product without category '" + expectedCategory + "': " + text);
                return false;
            }
        }
        Logger.log("✅ All products belong to category: " + expectedCategory);
        return true;
    }

    // Optional: Add a method for sorting
    public void applySortBy(String criteria) {
        WebElement dropdown = driver.findElement(By.name("SortByPrice")); // matches <select name="SortByPrice">
        Select select = new Select(dropdown);
        select.selectByVisibleText(criteria);
        driver.findElement(searchBtn).click(); 
    }

    // Get list of product prices
 // ✅ FIXED — handles stale element issue safely
 // ✅ FIXED — handles stale element issue safely
    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        By cells = By.cssSelector("td.w-12");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cells));

        List<WebElement> elements = driver.findElements(cells);
        for (int i = 0; i < elements.size(); i++) {
            try {
                WebElement e = driver.findElements(cells).get(i); // re-fetch each time
                String text = e.getText(); // "$1.00 / 10.00%"
                String pricePart = text.split("/")[0].replaceAll("[^0-9.]", "");
                prices.add(Double.parseDouble(pricePart));
            } catch (org.openqa.selenium.StaleElementReferenceException se) {
                Logger.log("⚠️ Stale element detected at index " + i + ", retrying...");
                WebElement e = driver.findElements(cells).get(i);
                String text = e.getText();
                String pricePart = text.split("/")[0].replaceAll("[^0-9.]", "");
                prices.add(Double.parseDouble(pricePart));
            }
        }
        return prices;
    }

    // ✅ FIXED — handles stale element issue safely
    public List<Double> getProductDiscounts() {
        List<Double> discounts = new ArrayList<>();
        By cells = By.cssSelector("td.w-12");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cells));

        List<WebElement> elements = driver.findElements(cells);
        for (int i = 0; i < elements.size(); i++) {
            try {
                WebElement e = driver.findElements(cells).get(i); // re-fetch each time
                String text = e.getText(); // "$1.00 / 10.00%"
                if (text.contains("/")) {
                    String discountPart = text.split("/")[1].replaceAll("[^0-9.]", "");
                    discounts.add(Double.parseDouble(discountPart));
                }
            } catch (org.openqa.selenium.StaleElementReferenceException se) {
                Logger.log("⚠️ Stale element detected at index " + i + ", retrying...");
                WebElement e = driver.findElements(cells).get(i);
                String text = e.getText();
                if (text.contains("/")) {
                    String discountPart = text.split("/")[1].replaceAll("[^0-9.]", "");
                    discounts.add(Double.parseDouble(discountPart));
                }
            }
        }
        return discounts;
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

