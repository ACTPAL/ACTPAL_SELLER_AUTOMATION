package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.Logger;

public class FollowersListPage {
	
	 
	 private WebDriver driver;
	 private WebDriverWait wait;
	 
	   public FollowersListPage  (WebDriver driver) {
		   this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); 
	    }
	    
	 private  By FollowersListPageBTN  = By.xpath("//span[contains(text(),\"Followers List\")]");
	 
	 private By VlationPage =  By.xpath("//h5[@class=\"blueHeading\"]");
	 private  By CountryDRP = By.id("Country-DropdownID");
	 private By  StateDRP = By.id("StateProvince-DropDownID");
	 private By   CityDrp   = By.id("City-DropdownID");
	 private By   sreachbar = By.id("searchUser");
	 
	 private By Searchicon = By.xpath("//i[@class=\"fa fa-search\"]");
	 private By    resetBTN = By.xpath("//span[@class=\"mob-m-hide\"]");
	 private  By     DeleteIcon = By.xpath("//span[@class=\"removeFollowerBySeller btn float-end\"]");

	 
	 
	 public void OpenFollowersListPage(String Country, String state, String City, String keyword) throws InterruptedException {

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		    // Step 1️⃣ Scroll and click Followers List Page
		    WebElement followersListPageBTN = wait.until(ExpectedConditions.visibilityOfElementLocated(FollowersListPageBTN));
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", followersListPageBTN);
		    Thread.sleep(2000);

		    try {
		        followersListPageBTN.click();
		        Logger.log("✅ Clicked Followers List.");
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", followersListPageBTN);
		        Logger.log("⚠️ JS clicked Followers List.");
		    }

		    // Step 2️⃣ Verify Followers List Page Opened
		    WebElement ThepageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(VlationPage));
		    Assert.assertTrue(ThepageTitle.isDisplayed(), "❌ Followers List page not opened!");
		    Logger.log("✅ Followers List Page opened successfully!");
		    closePopups(1);

		    // Step 3️⃣ Select filters
		    WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(CountryDRP));
		    Select sc1 = new Select(countryDropdown);
		    sc1.selectByVisibleText(Country);

		    wait.until(driver1 -> driver1.findElement(StateDRP).isEnabled());
		    Select sc2 = new Select(driver.findElement(StateDRP));
		    sc2.selectByVisibleText(state);

		    wait.until(driver1 -> driver1.findElement(CityDrp).isEnabled());
		    Select sc3 = new Select(driver.findElement(CityDrp));
		    sc3.selectByVisibleText(City);

		    Logger.log("✅ Filters applied: " + Country + " / " + state + " / " + City);

		    // Step 4️⃣ Check for "No Followers Found!" message
		    Thread.sleep(2000);
		    List<WebElement> noFollowersMsg = driver.findElements(By.xpath("//h4[contains(text(),'No Followers Found!')]"));
		    if (!noFollowersMsg.isEmpty()) {
		        Logger.log("⚠️ No data found for applied filters → " + Country + " / " + state + " / " + City);
		        Logger.log("ℹ️ Validation skipped as no follower records are available.");
		        return;
		    }

		    // Step 5️⃣ Validate displayed text matches selected filters
		    WebElement locationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='col']")));
		    String actualText = locationElement.getText().replaceAll("\\s+", "").trim();

		    String selectedCountry = sc1.getFirstSelectedOption().getText().trim();
		    String selectedState = sc2.getFirstSelectedOption().getText().trim();
		    String selectedCity = sc3.getFirstSelectedOption().getText().trim();

		    String expectedText = selectedCountry + "/" + selectedState + "/" + selectedCity;

		    Logger.log("🟢 Expected (from filters): " + expectedText);
		    Logger.log("🟢 Actual (on page): " + actualText);

		    if (actualText.equalsIgnoreCase(expectedText)) {
		        Logger.log("✅ Filter validation passed — displayed text matches selected filters!");
		    } else {
		        Logger.log("❌ Filter validation failed!");
		        Logger.log("Expected: " + expectedText + " | Actual: " + actualText);
		        Assert.fail("Displayed Country/State/City does not match selected filters!");
		    }

		    // Step 6️⃣ Click Reset Button to return to default state
		    try {
		        WebElement resetButton = wait.until(ExpectedConditions.elementToBeClickable(resetBTN));
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", resetButton);
		        resetButton.click();
		        Logger.log("🔄 Clicked Reset button successfully — returned to default view.");
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(resetBTN));
		        Logger.log("⚠️ Reset button clicked via JS — returned to default view.");
		    }

		  
		 // Step 7️⃣ Search Feature: Enter keyword and validate results
		 
		    try {
		        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(sreachbar));
		        searchBar.clear();
		        searchBar.sendKeys(keyword);
		        Logger.log("🔍 Entered search keyword: " + keyword);

		        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(Searchicon));
		        searchIcon.click();
		        Logger.log("✅ Clicked search icon.");

		        Thread.sleep(2000); // wait for search results to load

		        // 🔹 Step A: Check for "No Followers Found!" message
		        List<WebElement> noResults = driver.findElements(By.xpath("//h4[normalize-space(text())='No Followers Found!']"));
		        if (!noResults.isEmpty()) {
		            Logger.log("⚠️ No followers found for search keyword: " + keyword);
		            Assert.fail("❌ No results found for keyword: " + keyword);
		            return;
		        }

		        // 🔹 Step B: Capture all displayed follower names
		        List<WebElement> results = driver.findElements(By.xpath("//span[contains(@class,'memberDetail')]"));
		        if (results.isEmpty()) {
		            Logger.log("❌ No follower elements found in DOM after search.");
		            Assert.fail("Search results not located for keyword: " + keyword);
		            return;
		        }

		        Logger.log("📋 Found " + results.size() + " follower(s) after searching for: " + keyword);

		        boolean allMatch = true;
		        for (WebElement result : results) {
		            String followerName = result.getText().trim().toLowerCase();
		            Logger.log("🔸 Follower found: " + followerName);
		            if (!followerName.contains(keyword.toLowerCase())) {
		                allMatch = false;
		                Logger.log("❌ Mismatch — does not contain keyword: " + followerName);
		            }
		        }

		        if (allMatch) {
		            Logger.log("✅ All follower names contain the search keyword: " + keyword);
		        } else {
		            Assert.fail("Some followers do not contain the keyword: " + keyword);
		        }

		    } catch (Exception e) {
		        Logger.log("❌ Search validation failed due to exception: " + e.getMessage());
		        Assert.fail("Search step encountered an unexpected error!");
		    }

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
