package Pages;
 
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.File;
 
import java.util.Arrays;
import java.util.Comparator;
 
import base.Logger;
 
public class VisitorsPage {
 
    private WebDriverWait wait;
    private WebDriver driver;
 
    public VisitorsPage(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        this.driver = driver;
    }
 
    private By VisitorPageBTN = By.xpath("//span[contains(text(),'Visitors')]");
    private By VisitorPageTitle = By.xpath("//h4[contains(text(),'Visitors')]");
    private By SearchBar = By.xpath("//input[@placeholder='Search Product']");
    private By Serachicon = By.xpath("//i[@class='fa fa-search']");
    private By TitleList = By.xpath("//span[@class='text-dark p-0']");
    private By ResetBTn = By.xpath("//i[@class='icon icon-reset']");
    private By  TotalvisitorCount = By.xpath("//span[@class=\"edit_view inDetail clamp lc-1\"]");
    private By   ExportBtn = By.xpath("//button[@class=\"btn defaultGrayBtn\"]");
    private By cutBtn = By.xpath("//i[@class=\"fa-regular fa-circle-xmark\"]");
 
private By IPAdress = By.xpath("//th[contains(text(),'IP Address')]");
private By Location  = By.xpath("//th[contains(text(),'Location')]");
private By Date      = By.xpath("//th[contains(text(),'Date')]");
    
    
    
    
 
    public void OpenVisitorPage() throws InterruptedException {
 
        WebElement VisitorPageBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(VisitorPageBTN));
 
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", VisitorPageBtn);
        Thread.sleep(400);
 
        try {
            VisitorPageBtn.click();
            Logger.log("✅ Visitor Button clicked successfully.");
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", VisitorPageBtn);
            Logger.log("⚠️ JS clicked Visitor Page Button");
        }
 
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(VisitorPageTitle));
        Assert.assertTrue(pageTitle.isDisplayed(), "❌ Visitor page not opened!");
        Logger.log("✅ Visitor Page opened successfully!");
    }
    public void searchFullFlow(String searchKeyword) throws InterruptedException {
 
        Logger.log("🔍 Starting search for keyword: " + searchKeyword);
 
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(SearchBar));
        searchBar.clear();
        searchBar.sendKeys(searchKeyword);
        wait.until(ExpectedConditions.elementToBeClickable(Serachicon)).click();
 
        String keyword = searchKeyword.toLowerCase();
 
        // ⭐ NEW: Check for INVALID keyword (NO product found)
        try {
            WebDriverWait smallWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement noResultMsg = smallWait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4"))
            );
 
            String msgText = noResultMsg.getText().trim();
 
            if (msgText.contains("Access the location")) {
                Logger.log("❗ No product found for keyword: " + searchKeyword);
                Logger.log("ℹ️ User entered invalid search keyword. No related product available.");
 
                // Stop further checks → this is a valid case
                return;
            }
 
        } catch (Exception e) {
            // ignore → means results might exist, continue normal flow
        }
        // ⭐ END OF INVALID KEYWORD CHECK
 
 
        // 🔥 Wait until results MATCH the search keyword
        boolean stableResultsLoaded = new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(driver1 -> {
 
                    List<WebElement> items = driver1.findElements(TitleList);
 
                    if (items.size() == 0)
                        return false;
 
                    for (WebElement item : items) {
                        String title = item.getText().trim().toLowerCase();
 
                        // If any product doesn't match → real results not loaded yet → keep waiting
                        if (!title.contains(keyword)) {
                            return false;
                        }
                    }
                    return true;
                });
 
        if (!stableResultsLoaded) {
            Assert.fail("❌ Correct filtered results did NOT load within time. Application search is inconsistent.");
        }
 
        // ✔ Now validate
        List<WebElement> results = driver.findElements(TitleList);
        boolean atLeastOneMatch = false;
 
        for (WebElement titleElement : results) {
            String title = titleElement.getText().trim().toLowerCase();
            Logger.log("📌 Title Found: " + title);
 
            Assert.assertTrue(title.contains(keyword),
                    "❌ Search returned an invalid product!\n" +
                    "➡ Visible Title: " + title + "\n" +
                    "➡ Expected to contain keyword: " + keyword);
 
            atLeastOneMatch = true;
            Logger.log("✅ Matching product: " + title);
        }
 
        Assert.assertTrue(atLeastOneMatch, "❌ No matching product found.");
        Logger.log("✅ Search validation passed.");
    }
 
    
 
public int getProductCount() {
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(TitleList));
    return driver.findElements(TitleList).size();
}
 
public void resetBTNFlowCheck() {
 
    // Click reset button
    wait.until(ExpectedConditions.elementToBeClickable(ResetBTn)).click();
 
    // Best fix → Wait until product list is refreshed completely
    wait.until(ExpectedConditions.refreshed(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(TitleList)
    ));
 
    // Extra safety wait: ensure minimum product count appears (e.g. 5 or more)
    wait.until(driver -> driver.findElements(TitleList).size() >= 5);
 
    System.out.println("✅ Reset complete → Default product list loaded successfully.");
}
 
public void TotalVisitor() {
 
    List<WebElement> visitorElements = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(TotalvisitorCount)
    );
 
    List<Integer> actualList = new ArrayList<>();
 
    // Extract numbers
    for (WebElement element : visitorElements) {
        String text = element.getText().trim().replace(",", "");
        int count = Integer.parseInt(text);
 
        Logger.log("📌 Visitor Count Found: " + count);
        actualList.add(count);
    }
 
    // Copy actual list
    List<Integer> expectedDescending = new ArrayList<>(actualList);
 
    // Sort DESCENDING
    expectedDescending.sort(Collections.reverseOrder());
 
    // Validate
    Assert.assertEquals(actualList, expectedDescending,
            "❌ Visitor list is NOT in descending order!\n" +
            "Actual: " + actualList + "\nExpected: " + expectedDescending);
 
    Logger.log("✅ Visitor counts are in correct DESCENDING order: " + actualList);
}
public void verifyVisitorPopupDetails() {
 
    Logger.log("🔍 Clicking first visitor row...");
 
    // Click 1st row
    WebElement firstRow = wait.until(
            ExpectedConditions.elementToBeClickable(TotalvisitorCount)
    );
    firstRow.click();
 
    Logger.log("📌 Visitor detail popup opened.");
 
    // Verify IP Address
    WebElement ip = wait.until(
            ExpectedConditions.visibilityOfElementLocated(IPAdress)
    );
    Assert.assertTrue(ip.isDisplayed(), "❌ IP Address is NOT visible!");
    Logger.log("✅ IP Address is visible.");
 
    // Verify Location
    WebElement loc = wait.until(
            ExpectedConditions.visibilityOfElementLocated(Location)
    );
    Assert.assertTrue(loc.isDisplayed(), "❌ Location is NOT visible!");
    Logger.log("✅ Location is visible.");
 
    // Verify Date
    WebElement dt = wait.until(
            ExpectedConditions.visibilityOfElementLocated(Date)
    );
    Assert.assertTrue(dt.isDisplayed(), "❌ Date is NOT visible!");
    Logger.log("✅ Date is visible.");
 
    Logger.log("🎉 Popup validation passed successfully!");
}
 
public void clickExportAndVerifyDownload() {
    Logger.log("🔍 Clicking Export button...");
 
    // Click the Export button
    wait.until(ExpectedConditions.elementToBeClickable(ExportBtn)).click();
 
    // Wait a few seconds for download to complete
    try {
        Thread.sleep(5000); // Adjust this wait based on expected download time
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
 
    // Path to default download folder (adjust if needed)
    String downloadPath = System.getProperty("user.home") + "/Downloads";
 
    File dir = new File(downloadPath);
    File[] files = dir.listFiles();
 
    if (files == null || files.length == 0) {
        Assert.fail("❌ No file found in download folder!");
    }
 
    // Find the latest file in the folder
    File lastModifiedFile = Arrays.stream(files)
            .filter(File::isFile)
            .max(Comparator.comparingLong(File::lastModified))
            .orElse(null);
 
    if (lastModifiedFile == null) {
        Assert.fail("❌ No downloaded file found!");
    }
 
    Logger.log("✅ Export file downloaded successfully: " + lastModifiedFile.getName());
}
public void  CheckcutBTN () {
	wait.until(ExpectedConditions.visibilityOfElementLocated(cutBtn)).click();
}
    
}