package Pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import base.Logger;

public class Discountmanagement {

    private WebDriver driver;
    private WebDriverWait wait;

    // ===================== Constructor =====================
    public Discountmanagement(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===================== Locators =====================
    private By Discountmanagmeentbtn = By.xpath("//span[normalize-space()='Discount Management']");
    private By dropdown = By.xpath("//select[@name='expiresoon']");
    private By heading = By.xpath("//h5[normalize-space()='Discount Management']");
    private By caetogry = By.xpath("//select[@id='CategoryDropDown']");
    private By searchField = By.xpath("//input[@id='product_Search']");
    private By Searchicon = By.xpath("//i[@class='fa-solid fa-magnifying-glass']");
    private By resetBTN = By.xpath("//span[@class='mob-m-hide']");
    private By addNewDisBTN = By.xpath("//span[normalize-space()='Add New Discount']");
    private By CategoryDrpD = By.xpath("//span[@class='requiredField']//select[@id='CategoryDropDown']");
    private By SubCatogryDrpd = By.xpath("//span[@id='subCategoryDropDown']//select[@id='Sub_CategoryId']");
    private By ProductType = By.xpath("//select[@id='ProductTypeId']");
    private By DiscountType = By.xpath("//select[@name='DiscountType']");
    private By DicountValue = By.xpath("//input[@placeholder='Discount value']");
    private By addDiscountBTN = By.xpath("//button[normalize-space()='Add Discount']");
    private By discounthistoryBtn = By.xpath("//a[@class='btn defaultBrownBtn me-2']");
    private By discountHistorypage = By.xpath("//h5[normalize-space()='Discount History']");
    private By BackBtn = By.xpath("//span[@class='tillTab']");
    private By toastMessage = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'alert-success')]");
    private By errorMessage = By.xpath("//small[contains(@class,'text-danger')]");
    private By deleteIcon = By.xpath("//i[contains(@class,'icon-delete')]");
    private By confirmDeleteBtn = By.xpath("//button[@type='submit' and contains(@class,'btn-danger') and text()='Delete']");
    private By successToast = By.xpath("//div[contains(@class,'toast-message') or contains(@class,'alert-success')]");
    private By noDiscountMsg = By.xpath("//h4[contains(text(),'No any discount added yet.')]");
    

    // ===================== Page Methods =====================
    public void DicountManagemntPage() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            // ✅ Wait for the "Discount Management" menu/button to be clickable
            WebElement discountBtn = shortWait.until(ExpectedConditions.elementToBeClickable(Discountmanagmeentbtn));

            // Scroll down to make sure it's visible before clicking
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", discountBtn);
            Thread.sleep(300); // tiny delay for smooth scroll

            // Click normally, fallback to JS click if intercepted
            try {
                discountBtn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", discountBtn);
            }

            // ✅ Wait briefly for heading to confirm navigation
            WebDriverWait quickWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement headingElement = quickWait.until(ExpectedConditions.visibilityOfElementLocated(heading));

            Assert.assertTrue(headingElement.isDisplayed(), "Discount Management page not visible!");
            System.out.println("✅ Discount Management page opened successfully.");

            // ✅ Close popups *after* the page opens
            closePopups(1);
            System.out.println("✅ Closed popups after Discount Management page loaded.");

            // ✅ Force scroll back to the absolute top — stable and fast
            forceScrollToTop();

        } catch (TimeoutException e) {
            System.out.println("⚠️ Discount Management button or heading not found quickly — retrying once...");
            driver.navigate().refresh();

            WebElement discountBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(Discountmanagmeentbtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", discountBtn);

            WebElement headingElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(heading));
            Assert.assertTrue(headingElement.isDisplayed(), "Discount Management page still not visible!");

            closePopups(1);
            System.out.println("✅ Discount Management page loaded after retry and popups closed.");

            // ✅ Ensure top position again
            forceScrollToTop();
        } catch (Exception e) {
            System.out.println("❌ Error opening Discount Management page: " + e.getMessage());
        }
    }

    /**
     * ✅ Utility method: Quickly scrolls the page to the very top and keeps it stable.
     */
    private void forceScrollToTop() {
        try {
            // Perform multiple rapid scroll-to-top attempts for stability
            for (int i = 0; i < 12; i++) {
                // 1) blur active element (prevents autofocus scroll)
                ((JavascriptExecutor) driver).executeScript("if(document.activeElement) document.activeElement.blur();");

                // 2) temporarily disable scrolling (to prevent page script scroll)
                ((JavascriptExecutor) driver).executeScript(
                        "var _oldOverflow = document.body.style.overflow;" +
                                "document.body.style.overflow='hidden';"
                );

                // 3) force top (cover all elements)
                ((JavascriptExecutor) driver).executeScript(
                        "document.documentElement.scrollTop = 0;" +
                                "document.body.scrollTop = 0;" +
                                "window.scrollTo(0,0);"
                );

                // 4) restore overflow
                ((JavascriptExecutor) driver).executeScript(
                        "document.body.style.overflow = _oldOverflow || '';"
                );

                // 5) verify
                Long scrollY = (Long) ((JavascriptExecutor) driver).executeScript(
                        "return Math.max(window.scrollY || 0, document.documentElement.scrollTop || 0, document.body.scrollTop || 0);"
                );

                if (scrollY != null && scrollY == 0L) break;
                Thread.sleep(50); // small wait
            }

            // Final nudge
            ((JavascriptExecutor) driver).executeScript(
                    "document.documentElement.scrollTop = 0; document.body.scrollTop = 0; window.scrollTo(0,0);"
            );

            Long finalY = (Long) ((JavascriptExecutor) driver).executeScript(
                    "return Math.max(window.scrollY || 0, document.documentElement.scrollTop || 0, document.body.scrollTop || 0);"
            );

            if (finalY != null && finalY == 0L)
                System.out.println("⬆️ Page scrolled to top successfully (stable).");
            else
                System.out.println("⚠️ Page not perfectly at top, scrollY=" + finalY);

        } catch (Exception e) {
            System.out.println("⚠️ Exception while forcing top: " + e.getMessage());
        }
    }
    
  

    public void CheckstatusDropdawon(String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // longer wait for slow loads
        WebElement dropdownEl = wait.until(ExpectedConditions.elementToBeClickable(dropdown));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdownEl);
        hideOverlays();
        hideModals();

        // Click dropdown safely
        try {
            dropdownEl.click();
        } catch (Exception e) {
            new Actions(driver).moveToElement(dropdownEl).click().perform();
        }

        // Select option
        new Select(dropdownEl).selectByVisibleText(optionText);
        System.out.println("✅ Dropdown option selected: " + optionText);

        // Locator for termination-date <span> elements (adjust XPath if needed)
        By dateLocator = By.xpath("//span[normalize-space(text()) and contains(text(), '2025')]");

        // Wait until at least one date appears (safe first checkpoint)
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(dateLocator, 0));
        System.out.println("ℹ️ At least one termination date found, waiting for list to stabilize...");

        // --- Poll until the number of date elements stabilizes (no change across N polls) ---
        int prevCount = -1;
        int stableRounds = 0;
        final int requiredStableRounds = 3;    // number of consecutive polls with same count
        final long pollIntervalMs = 200;       // short poll interval (fast)
        final long timeoutMs = 5000;           // overall timeout to avoid long waits
        long endTime = System.currentTimeMillis() + timeoutMs;

        while (System.currentTimeMillis() < endTime) {
            int currentCount = driver.findElements(dateLocator).size();
            if (currentCount == prevCount) {
                stableRounds++;
                if (stableRounds >= requiredStableRounds) {
                    break; // stabilized
                }
            } else {
                prevCount = currentCount;
                stableRounds = 0;
            }

            try {
                Thread.sleep(pollIntervalMs);
            } catch (InterruptedException ignored) {}
        }

        List<WebElement> rawElements = driver.findElements(dateLocator);
        int foundCount = rawElements.size();
        System.out.println("ℹ️ Stabilized element count: " + foundCount);

        // Parse the top N dates (you used up to 5 before)
        List<LocalDate> terminationDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);

        for (int i = 0; i < rawElements.size() && i < 5; i++) {
            String text = rawElements.get(i).getText().trim();
            if (text.isEmpty()) continue;
            try {
                LocalDate date = LocalDate.parse(text, formatter);
                terminationDates.add(date);
                System.out.println("Termination Date " + i + ": " + text);
            } catch (Exception ex) {
                System.out.println("⚠️ Skipping unparsable date text: '" + text + "'");
            }
        }

        // If one or zero dates found — don't assert sort; just log and return
        if (terminationDates.size() <= 1) {
            System.out.println("ℹ️ Only " + terminationDates.size() + " termination date(s) found — skipping sorting validation.");
            return;
        }

        // Validate ascending order
        boolean sorted = true;
        for (int i = 0; i < terminationDates.size() - 1; i++) {
            if (terminationDates.get(i).isAfter(terminationDates.get(i + 1))) {
                sorted = false;
                break;
            }
        }

        Assert.assertTrue(sorted, "❌ Termination dates are not sorted correctly after applying filter!");
        System.out.println("✅ Termination dates sorted correctly after applying filter.");
    }
    




    // ---------- Add New Discount ----------

   

 
    // ---------- Common UI Actions ----------
    public void checkCategory(String categoryName) {
        WebElement dropdownEl = wait.until(ExpectedConditions.elementToBeClickable(caetogry));
        new Select(dropdownEl).selectByVisibleText(categoryName);
        System.out.println("✅ Selected category: " + categoryName);
    }

    public void SearchValidation(String searchText) {
        driver.findElement(resetBTN).click();
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchField));
        searchInput.clear();
        searchInput.sendKeys(searchText);
        driver.findElement(Searchicon).click();
        System.out.println("✅ Search performed for text: " + searchText);
    }

    public void openDiscountHistoryAndVerify() {
        wait.until(ExpectedConditions.elementToBeClickable(discounthistoryBtn)).click();
        WebElement historyPageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(discountHistorypage));
        Assert.assertTrue(historyPageHeader.isDisplayed(), "Discount History page not visible!");
        System.out.println("✅ Discount History page opened.");
    }

    public void backButtoncheckflow() {
        driver.findElement(BackBtn).click();
        WebElement headingElement = wait.until(ExpectedConditions.visibilityOfElementLocated(heading));
        Assert.assertTrue(headingElement.isDisplayed(), "Discount Management page is not displayed!");
        System.out.println("✅ Back button working fine.");
    }

    // ---------- UI Cleanup Helpers ----------
    private void hideModals() {
        try {
            List<WebElement> modals = driver.findElements(By.cssSelector(".modal, .popup, .overlay, .toast, .sticky-top"));
            for (WebElement modal : modals) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", modal);
            }
        } catch (Exception e) {
            System.out.println("No modals found.");
        }
    }

    private void hideOverlays() {
        try {
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement iframe : iframes) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", iframe);
            }
        } catch (Exception e) {
            System.out.println("No overlays found.");
        }
    }


 // ---------- Check if Toast or Field Error is Displayed ----------


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
 private void selectDropdown(By locator, String visibleText) {
     WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
     Select select = new Select(dropdown);
     select.selectByVisibleText(visibleText);
 }


 public void selectProductType(String type) {
     selectDropdown(ProductType, type);
 }

 public void selectDiscountType(String discountType) {
     selectDropdown(DiscountType, discountType);
 }

 // ✅ Set Discount Value
 public void setDiscountValue(String value) {
     WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(DicountValue));
     input.clear();
     input.sendKeys(value);
 }

 // ✅ Submit Discount
 public void clickAddDiscount() {
     WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addDiscountBTN));
     button.click();
 }

 // ✅ Read Toast Message
 public String getToastMessage() {
     try {
         return wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage)).getText().trim();
     } catch (TimeoutException e) {
         return "";
     }
 }

 // ✅ Read Error Message


 // ✅ Dependency validation
 public boolean isDropdownDefault(By dropdownLocator, String expectedDefault) {
     WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
     Select select = new Select(dropdown);
     String selected = select.getFirstSelectedOption().getText().trim();
     return selected.equals(expectedDefault);
 }

 public boolean isSubCategoryDefault() {
     return isDropdownDefault(SubCatogryDrpd, "--Sub-Category--");
 }

 public boolean isProductTypeDefault() {
     return isDropdownDefault(ProductType, "--Type--");
 }
 public void openAddDiscountForm() {
     WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addNewDisBTN));
     ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBtn);
     addBtn.click();
 }
//🔹 Locator for table rows (update XPath if needed)
private By searchResultRows = By.xpath("//table//tr");

public boolean isSearchResultPresent(String expectedText) {
    By tableLocator = By.xpath("//table");

    WebElement table = wait.until(
        ExpectedConditions.visibilityOfElementLocated(tableLocator)
    );

    String tableText = table.getText().toLowerCase();

    return tableText.contains(expectedText.toLowerCase());
}

public List<String> getAllErrorMessages() {
    try {
        List<WebElement> errors = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//span[@class='errMsg']"))
        );
        List<String> texts = new ArrayList<>();
        for (WebElement e : errors) {
            texts.add(e.getText().trim());
        }

        // ✅ Log each error in console
        System.out.println("🔴 Found " + texts.size() + " error messages:");
        for (String t : texts) {
            System.out.println("   → " + t);
        }

        return texts;
    } catch (TimeoutException e) {
        System.out.println("⚠️ No error messages appeared!");
        return Collections.emptyList();
    }
}
public void deleteDiscount(String productName) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    boolean found = false;

    By rowsLocator = By.xpath("//table//tr");

    List<WebElement> rows = wait.until(
        ExpectedConditions.presenceOfAllElementsLocatedBy(rowsLocator)
    );

    for (int i = 0; i < rows.size(); i++) {

        // ✅ fresh row each time
        WebElement freshRow = driver.findElements(rowsLocator).get(i);

        if (freshRow.getText().toLowerCase().contains(productName.toLowerCase())) {

            WebElement deleteBtn = freshRow.findElement(deleteIcon);

            // ✅ IMPORTANT FIX: scroll page to top
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

            // ✅ Ensure element is visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", deleteBtn);

            deleteBtn.click();
            found = true;
            System.out.println("🗑️ Clicked delete icon for: " + productName);
            break;
        }
    }

    if (!found) {
        throw new RuntimeException("❌ Discount not found for: " + productName);
    }

    try {
        WebElement confirmBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn));
        confirmBtn.click();
        System.out.println("✅ Clicked on delete confirmation button.");
    } catch (Exception e) {
        System.out.println("⚠️ Delete confirmation button not found or already handled.");
    }

    try {
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
        System.out.println("✅ Delete success toast appeared: " + toast.getText());
    } catch (Exception e) {
        try {
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(noDiscountMsg));
            System.out.println("✅ Delete successful — message displayed: " + msg.getText());
        } catch (Exception ex) {
            System.out.println("⚠️ No success toast or 'No discount' message found after delete.");
        }
    }
}




public boolean isNoDiscountMessageVisible() {
    try {
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(noDiscountMsg));
        return msg.isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

 public void clickResetBTN () {
	  driver.findElement(resetBTN).click();
 }
//✅ Select dropdown by visible text
public void selectCategory(String category) {
  Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(CategoryDrpD)));
  select.selectByVisibleText(category);
  System.out.println("📂 Selected Category: " + category);
}

public void selectSubCategory(String subCategory) {
  Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(SubCatogryDrpd)));
  select.selectByVisibleText(subCategory);
  System.out.println("📁 Selected SubCategory: " + subCategory);
}

//✅ Get all options of dropdown
public List<String> getDropdownOptions(By locator) {
  WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
  Select select = new Select(dropdown);
  return select.getOptions().stream()
          .map(e -> e.getText().trim())
          .collect(Collectors.toList());
}

//✅ Get currently selected text
public String getSelectedOption(By locator) {
  Select select = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
  return select.getFirstSelectedOption().getText().trim();
}

//✅ Validate both sub and type defaults
public boolean isDefaultDropdownState(String expectedSub, String expectedType) {
  String sub = getSelectedOption(SubCatogryDrpd);
  String type = getSelectedOption(ProductType);
  System.out.println("🔍 Current defaults → SubCategory: " + sub + ", ProductType: " + type);
  return sub.equals(expectedSub) && type.equals(expectedType);
}
public By getSubCatogryDrpd() {
    return SubCatogryDrpd;
}

public By getProductTypeDrpd() {
    return ProductType;
}



}
