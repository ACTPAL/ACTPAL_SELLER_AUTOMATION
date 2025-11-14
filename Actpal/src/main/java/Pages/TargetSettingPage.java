package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
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

	    public TargetSettingPage(WebDriver driver, WebDriverWait wait) {
	        this.wait = wait;
	        this.driver = driver;
	    }

	    // ✅ ADD THIS ↓↓↓
	    public TargetSettingPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    }
    
    private By TargetSettingBTN = By.xpath("//span[contains(text(),'Target Setting')]");
    private By Pagevalidation = By.xpath("//span[contains(text(),'Target Setting')]");
    private By Searchbar = By.id("searchKey");
    private By searchicon  = By.xpath("//i[@class=\"fa fa-search\"]");
    private By targetlistname = By.xpath("//td[@class=\"mob-flex-wrap mobw-85\"]");
    private By resetButton = By.xpath("//i[@class=\"icon icon-reset\"]");
    private By deleteIcon = By.xpath("//i[@class=\"icon icon-delete\"]");
    private By Viewicon = By.xpath("//i[@class=\"icon icon-delete\"]");
    private By addnewtargetingBtn = By.xpath("//span[@class=\"tillTab mob-icon\"]");
    private By tagetingname  = By.xpath("//input[@class=\"form-control requiredField\"]");
    private By description    = By.xpath("//textarea[@name=\"description\"]");
    private By SaveBtn =  By.xpath("//button[@type=\"submit\"]");
    private By ProductDrp = By.xpath("//Select[@class=\"form-control\"]");
    private By IncludeItems =  By.xpath("//input[@class=\"form-control\"]");
    private By ProductserviceBackBTn = By.xpath("//button[@class=\"btn defaultGrayBtn mx-2 next backToBasicDetails\"]");
    private By saveBtnProductservice = By.xpath("//button[@class=\"btn defaultBrownBtn next saveItemDetails\"]");
    private By ExcludeLocation = By.xpath("//input[@class=\"form-control\" and @placeholder=\"Exclude Location\" ]");
    private By inccludeLocation = By.xpath("//input[@placeholder=\"Include Location\"]");
    private By LocationsaveBTN   = By.xpath("//button[@class=\"saveLocation saveTargetLocation btn defaultBrownBtn\"]");
    
   private  By UserRestrictionssaveBTN =  By.xpath("//button[@class=\"btn defaultBrownBtn saveUserDetails updateBasicInfo\"]");
    private By SearchGroupsForIncluding   = By.xpath("//input[@class=\"form-control\"]");     
    private By UpdatedTragetingBtn = By.xpath("//i[@class='icon icon-add-white']");

    	
    
    
    
    
    
    
    
    
  
    int defaultTargetCount = 0; // declare globally or store before search
		  
     
     
    
    
    public void   OpenTargetSettingPage  () {
    	  WebElement TargetSettingbtn =  wait.until(ExpectedConditions.visibilityOfElementLocated(TargetSettingBTN)) ;
    	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", TargetSettingbtn);
        try {
        	TargetSettingbtn.click();
        	Logger.log("✅ Clicked Targe Setting List.");
        }catch(Exception e) {
        	((JavascriptExecutor) driver).executeScript("arguments[0].click();",TargetSettingbtn );
	        Logger.log("⚠️ JS clicked Target Setting List.");
        	
        }
        WebElement ThepageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(Pagevalidation));
        Assert.assertTrue(ThepageTitle.isDisplayed(), "❌ Target Setting List page not opened!");
	    Logger.log("✅ Target Setting List pageopened successfully!");
	    closePopups(1);
    	  
    }
    
    public void searchFunctionality(String SearchKeyword) {
    	
    	List<WebElement> defaultTargets = driver.findElements(targetlistname);
    	defaultTargetCount = defaultTargets.size();
    	Logger.log("📋 [Target] Default total targets before search: " + defaultTargetCount);

        Logger.log("🔍 [Target] Performing search for keyword: '" + SearchKeyword + "'");

     
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(Searchbar));
        searchBox.click();
        searchBox.clear();
        searchBox.sendKeys(SearchKeyword);
        driver.findElement(searchicon).click();

   
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(targetlistname),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Set your audience targeting type')]"))
            ));
        } catch (Exception e) {
            Logger.log("⚠️ [Target] Timeout waiting for search results or fallback message.");
        }

        // Collect all results
        List<WebElement> targetNames = driver.findElements(targetlistname);
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
    }

    public void ResetButtonFunctionality() {
        Logger.log("🔁 [Target] Clicking Reset button to restore default view.");

        // Step 1: Capture default count BEFORE search (if needed)
        // Optional: could store it in a class variable instead of passing it

        WebElement ResetBTN = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        ResetBTN.click();

        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(targetlistname));
            List<WebElement> allTargets = driver.findElements(targetlistname);

            if (allTargets.size() >= 5) { // Example threshold for normal view
                Logger.log("✅ [Target] Reset successful — Normal view restored with total targets: " + allTargets.size());
            } else {
                Logger.log("❌ [Target] Reset failed — Only " + allTargets.size() + " targets visible after reset.");
                Assert.fail("❌ [Target] Normal view not restored properly after Reset.");
            }
        } catch (Exception e) {
            Logger.log("❌ [Target] Exception while restoring normal view: " + e.getMessage());
            Assert.fail("❌ [Target] Reset failed — Normal view not restored.");
        }
    }
    
    
    public void CreateaNewTargetting(String  targetingName, String Description, String ProductDRPText, String ProductName, String ExcludeLoction, String includeLoction,String genderValue ,String maritalValue,String GroupText) throws InterruptedException {
    	wait.until(ExpectedConditions.visibilityOfElementLocated(addnewtargetingBtn)).click();
    	wait.until(ExpectedConditions.visibilityOfElementLocated(tagetingname)).sendKeys(targetingName);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(description)).sendKeys(Description);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(SaveBtn)).click();
    	WebElement productDropdown  =   wait.until(ExpectedConditions.visibilityOfElementLocated(ProductDrp));
    	 Select sc = new  Select (productDropdown);
    	 sc.selectByVisibleText(ProductDRPText);
    	// Type in the IncludeItems input
    	 WebElement includeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(IncludeItems));
    	 includeInput.sendKeys(ProductName);
    	// ✅ Wait for first suggestion under autoCompleteBox and click it
    	
    	 By firstSuggestion = By.xpath("(//ul[@class='autoCompleteBox active']/li)[1]");
    	 List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstSuggestion));

    	 // ✅ Assertion: verify suggestions are present
    	 Assert.assertTrue(suggestions.size() > 0, "❌ No product suggestions appeared after typing!");

    	 // Click the first suggestion
    	 suggestions.get(0).click();

    	 // ✅ Optional: verify that the suggestion list disappears (means click worked)
    	 boolean isDropdownGone = wait.until(ExpectedConditions.invisibilityOfElementLocated(firstSuggestion));
    	 Assert.assertTrue(isDropdownGone, "❌ First product was not selected properly.");

    	 System.out.println("✅ Product selection passed — first suggestion clicked successfully.");
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(saveBtnProductservice)).click();
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(ProductserviceBackBTn)).click();
     	 wait.until(ExpectedConditions.visibilityOfElementLocated(saveBtnProductservice)).click();
         WebElement includeLocationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(inccludeLocation));
         includeLocationInput.sendKeys(includeLoction);
         List<WebElement> includeLocSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstSuggestion));
         Assert.assertTrue(includeLocSuggestions.size() > 0, "❌ No include location suggestions appeared!");
         includeLocSuggestions.get(0).click();
         wait.until(ExpectedConditions.invisibilityOfElementLocated(firstSuggestion));
         System.out.println("✅ Include Location selected successfully.");

         // ✅ Exclude Location
         WebElement excludeLocationInput = wait.until(ExpectedConditions.visibilityOfElementLocated(ExcludeLocation));
         excludeLocationInput.sendKeys(ExcludeLoction);
         List<WebElement> excludeLocSuggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(firstSuggestion));
         Assert.assertTrue(excludeLocSuggestions.size() > 0, "❌ No exclude location suggestions appeared!");
         excludeLocSuggestions.get(0).click();
         wait.until(ExpectedConditions.invisibilityOfElementLocated(firstSuggestion));
         System.out.println("✅ Exclude Location selected successfully.");
         WebElement locationSaveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(LocationsaveBTN));

      // ✅ Scroll the button into view (center)
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", locationSaveButton);

      // ✅ Wait until clickable, then click (fallback to JS click)
      wait.until(ExpectedConditions.elementToBeClickable(locationSaveButton));
      try {
          locationSaveButton.click();
      } catch (ElementClickInterceptedException e) {
          ((JavascriptExecutor) driver).executeScript("arguments[0].click();", locationSaveButton);
          System.out.println("⚠️ Normal click intercepted — used JS click instead.");
      }

      // ✅ Scroll back to top (normal view)
      ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
      System.out.println("✅ Location Save button clicked and view scrolled back to top.");
   // ✅ Select Gender dynamically
   // ===== GENDER SELECTION =====
      Map<String, String> genderMap = new HashMap<>();
      genderMap.put("Both", "Both");
      genderMap.put("Male", "Men");
      genderMap.put("Female", "Women");

      if (genderMap.containsKey(genderValue)) {
          String genderId = genderMap.get(genderValue);
          By genderOption = By.xpath("//input[@id='" + genderId + "' and @name='gender']");
          WebElement genderElement = wait.until(ExpectedConditions.elementToBeClickable(genderOption));
          genderElement.click();
          System.out.println("✅ Gender selected: " + genderValue);
      } else {
          Assert.fail("❌ Gender option not found for: " + genderValue);
      }

      // ===== MARITAL STATUS SELECTION =====
      Map<String, String> maritalMap = new HashMap<>();
      maritalMap.put("Both", "BothMS");
      maritalMap.put("Married", "Married");
      maritalMap.put("Single", "Single");

      if (maritalMap.containsKey(maritalValue)) {
          String maritalId = maritalMap.get(maritalValue);
          By maritalOption = By.xpath("//input[@id='" + maritalId + "' and @name='MarriedStatus']");
          WebElement maritalElement = wait.until(ExpectedConditions.elementToBeClickable(maritalOption));
          maritalElement.click();
          System.out.println("✅ Marital Status selected: " + maritalValue);
      } else {
          Assert.fail("❌ Marital Status option not found for: " + maritalValue);
      }

      wait.until(ExpectedConditions.visibilityOfElementLocated(UserRestrictionssaveBTN)).click();

      
       wait.until(ExpectedConditions.visibilityOfElementLocated(SearchGroupsForIncluding)).sendKeys(GroupText);
       wait.until(ExpectedConditions.visibilityOfElementLocated(UpdatedTragetingBtn)).click();
       closePopups(1);
    // ===== VERIFY NEW TARGETING EXISTS IN THE LIST =====
    // ===== VERIFY NEW TARGETING EXISTS IN LIST =====
       By allTargetings = By.xpath("//td[@class='mob-flex-wrap mobw-85']/a");
       boolean targetingFound = false;
       int attempts = 0;

       while(attempts < 10) { // retry up to 10 times
           try {
               // Wait for the list to be visible
               List<WebElement> targetingElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(allTargetings));
               
               for (WebElement el : targetingElements) {
                   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el); // scroll each element
                   if (el.getText().trim().equals(targetingName)) {
                       targetingFound = true;
                       break;
                   }
               }
               if(targetingFound) break; // exit loop if found
           } catch (Exception e) {
               // ignore and retry
           }
           Thread.sleep(1000); // wait 1 second before retry
           attempts++;
       }

       Assert.assertTrue(targetingFound, "❌ Verification failed — New targeting NOT found in the list: " + targetingName);
       System.out.println("✅ Verification passed — New targeting is present in the list: " + targetingName);
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
