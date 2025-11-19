package Pages;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.Logger;

public class TargetSettingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // constructors (keeps your original constructors)
    public TargetSettingPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        this.driver = driver;
    }

    // convenience constructor (kept)
    public TargetSettingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ======== locators (kept the same variable names you asked to keep) ========
    private By TargetSettingBTN = By.xpath("//span[contains(text(),'Target Setting')]");
    private By Pagevalidation = By.xpath("//span[contains(text(),'Target Setting')]");
    private By Searchbar = By.id("searchKey");
    private By searchicon = By.xpath("//i[@class=\"fa fa-search\"]");
    private By targetlistname = By.xpath("//td[@class=\"mob-flex-wrap mobw-85\"]");
    private By resetButton = By.xpath("//i[@class=\"icon icon-reset\"]");
    private By deleteIcon = By.xpath("//i[@class=\"icon icon-delete\"]");
    private By Viewicon = By.xpath("//i[@class=\"icon icon-delete\"]"); // kept name as requested
    private By DeletePopBTN = By.xpath("//button[@class=\"btn w-100 btn-danger\"]");
    private By addnewtargetingBtn = By.xpath("//span[@class=\"tillTab mob-icon\"]");
    private By tagetingname = By.xpath("//input[@class=\"form-control requiredField\"]");
    private By description = By.xpath("//textarea[@name=\"description\"]");
    private By SaveBtn = By.xpath("//button[@type=\"submit\"]");
    private By ProductDrp = By.xpath("//Select[@class=\"form-control\"]");
    private By IncludeItems = By.xpath("//input[@class=\"form-control\"]");
    private By ProductserviceBackBTn = By.xpath("//button[@class=\"btn defaultGrayBtn mx-2 next backToBasicDetails\"]");
    private By saveBtnProductservice = By.xpath("//button[@class=\"btn defaultBrownBtn next saveItemDetails\"]");
    private By ExcludeLocation = By.xpath("//input[@class=\"form-control\" and @placeholder=\"Exclude Location\" ]");
    private By inccludeLocation = By.xpath("//input[@placeholder=\"Include Location\"]");
    private By LocationsaveBTN = By.xpath("//button[@class=\"saveLocation saveTargetLocation btn defaultBrownBtn\"]");
    private By UserRestrictionssaveBTN = By.xpath("//button[@class=\"btn defaultBrownBtn saveUserDetails updateBasicInfo\"]");
    private By SearchGroupsForIncluding = By.xpath("//input[@class=\"form-control\"]");
    private By UpdatedTragetingBtn = By.xpath("//i[@class='icon icon-add-white']");

    int defaultTargetCount = 0;

    // ----------------- Helper utilities -----------------
    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    private void safeClick(WebElement el) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el)).click();
        } catch (ElementClickInterceptedException ex) {
            // fallback to JS click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            Logger.log("⚠️ JS fallback click used.");
        }
    }

    /**
     * Waits for presence of locator, refreshing the underlying DOM expectation to avoid stale elements.
     */
    private WebElement waitForRefreshedPresence(By locator) {
        return wait.until(ExpectedConditions.refreshed(ExpectedConditions.presenceOfElementLocated(locator)));
    }

    // ----------------- Page actions -----------------

    public void OpenTargetSettingPage() {
        WebElement TargetSettingbtn = wait.until(ExpectedConditions.visibilityOfElementLocated(TargetSettingBTN));
        scrollIntoView(TargetSettingbtn);

        try {
            safeClick(TargetSettingbtn);
            Logger.log("✅ Clicked Targe Setting List.");
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", TargetSettingbtn);
            Logger.log("⚠️ JS clicked Target Setting List (fallback).");
        }

        WebElement ThepageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(Pagevalidation));
        Assert.assertTrue(ThepageTitle.isDisplayed(), "❌ Target Setting List page not opened!");
        Logger.log("✅ Target Setting List pageopened successfully!");
        closePopups(1);
    }

    public void searchFunctionality(String SearchKeyword) {
        Logger.log("🔍 [Target] Performing search for keyword: '" + SearchKeyword + "'");

        try {
            // Wait for search box, clear and type
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(Searchbar));
            scrollIntoView(searchBox);
            searchBox.clear();
            searchBox.sendKeys(SearchKeyword);

            // Click search
            WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchicon));
            safeClick(searchBtn);

            // Wait for results to refresh and then collect fresh elements
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(targetlistname),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Set your audience targeting type')]"))
            ));

            // Re-locate fresh elements (avoid stale elements)
            List<WebElement> targetNames = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(targetlistname));
            Logger.log("📋 [Target] Total results found: " + targetNames.size());

            boolean isMatchFound = false;
            if (!targetNames.isEmpty()) {
                for (WebElement nameElement : targetNames) {
                    String text = nameElement.getText().trim();
                    if (text.toLowerCase().contains(SearchKeyword.toLowerCase())) {
                        isMatchFound = true;
                        Logger.log("✅ [Target] Match found: " + text);
                        break;
                    }
                }
            } else {
                // fallback message
                try {
                    WebElement noDataMessage = driver.findElement(By.xpath("//h4[contains(text(),'Set your audience targeting type')]"));
                    if (noDataMessage.isDisplayed()) {
                        Logger.log("ℹ️ [Target] No results found — Default message displayed: " + noDataMessage.getText());
                    }
                } catch (Exception e) {
                    Logger.log("❌ [Target] Neither results nor default message found!");
                }
            }

            if (isMatchFound) {
                Assert.assertTrue(true, "✅ [Target] Search results contain the keyword: " + SearchKeyword);
            } else {
                Assert.fail("❌ [Target] No search result matched the keyword: " + SearchKeyword);
            }

        } catch (StaleElementReferenceException se) {
            Logger.log("⚠️ [Target] StaleElementReferenceException during search. Retrying once...");
            // retry once
            try {
                Thread.sleep(250);
                searchFunctionality(SearchKeyword);
            } catch (InterruptedException ignored) {}
        } catch (Exception e) {
            Logger.log("❌ [Target] Exception during searchFunctionality: " + e.getMessage());
            Assert.fail("❌ [Target] Search failed due to exception.");
        }
    }
    public void ResetButtonFunctionality() {
        Logger.log("🔁 [Target] Clicking Reset button to restore default view.");

        WebElement ResetBTN = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        scrollIntoView(ResetBTN);
        safeClick(ResetBTN);

        try {
            // 1️⃣ Ensure search box is cleared
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(Searchbar));
            Assert.assertTrue(searchBox.getAttribute("value").isEmpty(),
                    "❌ [Target] Search bar is NOT cleared after Reset.");

            // 2️⃣ Wait for list to refresh
            wait.until(ExpectedConditions.presenceOfElementLocated(targetlistname));

            // 3️⃣ Verify AT LEAST ONE TARGET ROW is visible
            List<WebElement> allTargets = driver.findElements(targetlistname);
            if (allTargets.size() == 0) {
                // Check if "No Data" appears
                List<WebElement> noData = driver.findElements(
                        By.xpath("//h4[contains(text(),'Set your audience targeting type')]"));

                if (!noData.isEmpty()) {
                    Assert.fail("❌ [Target] Reset returned NO DATA list!");
                } else {
                    Assert.fail("❌ [Target] Neither data nor default message after Reset!");
                }
            }

            Logger.log("✅ [Target] Reset successful — List refreshed with " + allTargets.size() + " items.");

        } catch (Exception e) {
            Logger.log("❌ [Target] Reset failed due to exception: " + e.getMessage());
            Assert.fail("❌ [Target] Reset failed — Normal view not restored.");
        }
    }

    public void CreateaNewTargetting(
            String targetingName,
            String Description,
            String ProductDRPText,
            String ProductName,
            String ExcludeLoction,
            String includeLoction,
            String genderValue,
            String maritalValue,
            String GroupText) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // =============================
        //  STEP 1 : BASIC DETAILS
        // =============================
        wait.until(ExpectedConditions.elementToBeClickable(addnewtargetingBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(tagetingname)).sendKeys(targetingName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(description)).sendKeys(Description);
        wait.until(ExpectedConditions.elementToBeClickable(SaveBtn)).click();


        // =============================
        //  STEP 2 : PRODUCT / SERVICE
        // =============================
        WebElement productDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(ProductDrp));
        Select sc = new Select(productDropdown);
        sc.selectByVisibleText(ProductDRPText);

        WebElement includeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(IncludeItems));
        includeInput.sendKeys(ProductName);

        By firstSuggestion = By.xpath("(//ul[@class='autoCompleteBox active']/li)[1]");

        List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstSuggestion));
        Assert.assertFalse(suggestions.isEmpty(), "❌ No product suggestions appeared!");
        suggestions.get(0).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(firstSuggestion));

        wait.until(ExpectedConditions.elementToBeClickable(saveBtnProductservice)).click();
        wait.until(ExpectedConditions.elementToBeClickable(ProductserviceBackBTn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveBtnProductservice)).click();


        // =============================
        //  STEP 3 : LOCATION (INCLUDE)
        // =============================
        WebElement includeLocationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inccludeLocation));
        includeLocationInput.sendKeys(includeLoction);

        List<WebElement> includeLocSuggestions =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstSuggestion));

        Assert.assertFalse(includeLocSuggestions.isEmpty(), "❌ No include location suggestions!");
        includeLocSuggestions.get(0).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(firstSuggestion));


        // =============================
        //  STEP 4 : LOCATION (EXCLUDE)
        // =============================
        WebElement excludeLocationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(ExcludeLocation));
        excludeLocationInput.sendKeys(ExcludeLoction);

        List<WebElement> excludeLocSuggestions =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstSuggestion));

        Assert.assertFalse(excludeLocSuggestions.isEmpty(), "❌ No exclude location suggestions!");
        excludeLocSuggestions.get(0).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(firstSuggestion));


        // =============================
        //  STEP 5 : LOCATION SAVE
        // =============================
        WebElement locationSaveButton = wait.until(ExpectedConditions.elementToBeClickable(LocationsaveBTN));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", locationSaveButton);

        try {
            locationSaveButton.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", locationSaveButton);
        }

        js.executeScript("window.scrollTo(0, 0);");


        // =============================
        //  STEP 6 : GENDER
        // =============================
        Map<String, String> genderMap = new HashMap<>();
        genderMap.put("Both", "Both");
        genderMap.put("Male", "Men");
        genderMap.put("Female", "Women");

        if (genderMap.containsKey(genderValue)) {
            String genderId = genderMap.get(genderValue);
            By genderOption = By.xpath("//input[@id='" + genderId + "' and @name='gender']");
            wait.until(ExpectedConditions.elementToBeClickable(genderOption)).click();
        } else {
            Assert.fail("❌ Invalid Gender value: " + genderValue);
        }


        // =============================
        //  STEP 7 : MARITAL STATUS
        // =============================
        Map<String, String> maritalMap = new HashMap<>();
        maritalMap.put("Both", "BothMS");
        maritalMap.put("Married", "Married");
        maritalMap.put("Single", "Single");

        if (maritalMap.containsKey(maritalValue)) {
            String maritalId = maritalMap.get(maritalValue);
            By maritalOption = By.xpath("//input[@id='" + maritalId + "' and @name='MarriedStatus']");
            wait.until(ExpectedConditions.elementToBeClickable(maritalOption)).click();
        } else {
            Assert.fail("❌ Invalid Marital Status: " + maritalValue);
        }

        wait.until(ExpectedConditions.elementToBeClickable(UserRestrictionssaveBTN)).click();


        // =============================
        //  STEP 8 : GROUP SEARCH
        // =============================
        wait.until(ExpectedConditions.visibilityOfElementLocated(SearchGroupsForIncluding)).sendKeys(GroupText);
        wait.until(ExpectedConditions.elementToBeClickable(UpdatedTragetingBtn)).click();

        closePopups(1);


        // =============================
        //  STEP 9 : FINAL VERIFICATION
        //     (Scroll until new targeting appears)
        // =============================

        By newTarget = By.xpath("//a[normalize-space()='" + targetingName + "']");

        js.executeScript("window.scrollTo(0, 0);");

        boolean found = scrollUntilVisible(newTarget, 20); // max 20 scroll attempts

        Assert.assertTrue(found,
                "❌ New Targeting NOT found after scrolling entire list: " + targetingName);

        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(newTarget));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);

        System.out.println("✅ New Targeting successfully created & verified: " + targetingName);
    }
    public boolean scrollUntilVisible(By locator, int maxScrolls) throws InterruptedException {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < maxScrolls; i++) {

            try {
                WebElement element = driver.findElement(locator);

                if (element.isDisplayed()) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                    return true;
                }

            } catch (Exception ignored) {}

            js.executeScript("window.scrollBy(0, 400);");
            Thread.sleep(600);
        }

        return false;
    }
    
    public void deleteFirstRowTarget() {
        Logger.log("🗑️ [Delete] Attempting to delete FIRST row target");

        try {

            // 1️⃣ Get all delete icons
            List<WebElement> deleteIcons = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(deleteIcon));

            if (deleteIcons.isEmpty()) {
                Assert.fail("❌ No delete icons found in list!");
            }

            // 2️⃣ Click FIRST delete icon
            WebElement firstDeleteIcon = deleteIcons.get(0);
            scrollIntoView(firstDeleteIcon);
            safeClick(firstDeleteIcon);

            Logger.log("🟠 First row delete icon clicked");

            // 3️⃣ Click popup delete button
            By popupDeleteButton = By.xpath("//button[@class=\"btn w-100 btn-danger\"]");
            WebElement popupBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(popupDeleteButton));

            scrollIntoView(popupBtn);
            safeClick(popupBtn);

            Logger.log("🟢 Popup delete button clicked");

            // 4️⃣ Wait for popup to close
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popupDeleteButton));
            Logger.log("🟢 Popup closed successfully");

            Logger.log("✅ First row target deleted successfully");

        } catch (Exception e) {
            Logger.log("❌ Failed to delete FIRST row | Reason: " + e.getMessage());
            Assert.fail("❌ Delete operation failed for FIRST row");
        }
    }
public void CheckMethod() {
	System.out.println("Hello Rajeev Singh ");
}


    // close popups helper (kept, but slightly optimized)
    private void closePopups(int attempts) {
        for (int i = 0; i < attempts; i++) {
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
                By popupCloseBtn = By.xpath("//button[@class='btn-close' and @data-bs-dismiss='modal']");
                shortWait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
                Logger.log("Popup closed successfully (attempt " + (i + 1) + ")");
            } catch (Exception e) {
                Logger.log("No popup appeared in attempt " + (i + 1) + ")");
            }
        }
    }
}
