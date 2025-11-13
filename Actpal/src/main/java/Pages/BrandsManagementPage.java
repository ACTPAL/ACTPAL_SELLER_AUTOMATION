package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.Logger;

public class BrandsManagementPage {
	
	
	 private WebDriver driver;
	 
	    private WebDriverWait wait;
	 public BrandsManagementPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 🔹 Declare globally once
	    }
	    
	    
	      private By  BarndManagment = By.xpath("//a[@class='icon icon-brand-management icon-set']");
	      private By BrandManagemntpageTitle =  By.xpath("/html/body/div[1]/div/div/div[2]/div[1]/div[1]/h5");
	      private By Searchbar = By.xpath("//input[@id='brandSearch']");
	      private By BrandNames = By.xpath("//span[@class='text-dark p-0']"); 
	      private By NoBrandMessage = By.xpath("//h4[normalize-space()='Not any brand added yet.']");
	      private  By  ResetBtn  =  By.xpath("//button[@type=\"reset\"]");
	     private By addnewBTn = By.xpath("//span[@class=\"tillTab\"]");
	     private By Areacode = By.xpath("//input[@placeholder='Area Code']");
	     private  By CompanyName = By.xpath("//input[@name='CompanyName']");
	     private By  BrandName = By.xpath("//input[@name='BrandName']");
	     private By  Phonenumber = By.xpath("//input[@placeholder='Phone number']");
	     private By email = By.xpath("//input[@name='Email']");
	     private By address = By.xpath("//input[@name='Address']");
	     private By CuntryDrp = By.xpath("//select[@id='Country-DropdownID']");
	     private By stateDrp = By.xpath("//select[@id='StateProvince-DropDownID']");
	     private By  CityDrp = By.xpath("//select[@id='City-DropdownID']");
	     private By ZipCode = By.xpath("//input[@name='PinCode']");
	     private By AddBTN = By.xpath("//button[@class=\"btn defaultBrownBtn\"]");
	     private  By logoUpload = By.id("company_logo");
	     private By errorMessages = By.xpath("//span[@class='errMsg']");
	     private  By cartificateUpload = By.id("addcertificate");
	    private By deleteIcon  = By.xpath("//span[@data-name='PrimeFashion']//i[@class='icon icon-delete icon-equal']");
	    private By CloseBtnPOP  = By.xpath("//button[@class=\"defaultGrayBtn w-100 btn btn-primary\"]");
	    private By  valtidationText  = By.xpath("//strong[contains(text(),'Thank you for submitting')]");
	    private By DuplicateBrandtext  = By.xpath("//div[text()='Brand name alredy exists']");
	    private By deletepop = By.xpath("//button[@class=\"btn w-100 btn-danger\"]");
	 
	     
	     
	

	      
	      
	      
	      
	        
	       public  void  OpenBarandManagmentPage () throws InterruptedException {
	    	   
	    	    wait.until(ExpectedConditions.presenceOfElementLocated(BarndManagment));
	    	   JavascriptExecutor  js = (JavascriptExecutor)driver;
	    	   WebElement BrandManagemntBTN = driver.findElement(BarndManagment);
	    	   js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", BrandManagemntBTN);
	    	   Thread.sleep(500);
	    	   wait.until(ExpectedConditions.elementToBeClickable(BrandManagemntBTN));
	    	   BrandManagemntBTN.click();
	    	   WebElement pageTitle = wait.until(
	    		        ExpectedConditions.visibilityOfElementLocated(BrandManagemntpageTitle)
	    		    );
	    		    Assert.assertTrue(pageTitle.isDisplayed(), "❌ Brand Management Page Title not visible — page may not have loaded.");
	    		    Logger.log("✅ Brand Management Page opened successfully!");
	    		
	    	   
	}
	       
	       public void searchFunctionalityCheck(String brandName) {
	           WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(Searchbar));
	           search.clear();
	           search.sendKeys(brandName, Keys.ENTER);

	           // Short wait for results
	           try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

	           List<WebElement> noResult = driver.findElements(NoBrandMessage);

	           if (!noResult.isEmpty()) {
	               Assert.assertTrue(noResult.get(0).isDisplayed(), "❌ 'No brand added yet' message missing.");
	               Logger.log("⚠️ No results found for: " + brandName);
	           } else {
	               List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(BrandNames));
	               Assert.assertTrue(results.size() > 0, "❌ No brand results displayed.");

	               boolean allMatch = true;
	               for (WebElement brand : results) {
	                   String text = brand.getText().trim().toLowerCase();
	                   if (!text.contains(brandName.toLowerCase())) {
	                       allMatch = false;
	                       Logger.log("❌ Mismatch: " + text);
	                   } else {
	                       Logger.log("✅ Found: " + text);
	                   }
	               }
	               Assert.assertTrue(allMatch, "❌ Some results didn’t match: " + brandName);
	           }
	       }

	       // 🔹 Step 3: Reset Button Check
	       public void resetButtonCheck() {
	           try {
	               WebElement resetButton = wait.until(ExpectedConditions.visibilityOfElementLocated(ResetBtn));

	               // Scroll into view first
	               ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", resetButton);
	               Thread.sleep(500);

	               // Try normal click
	               try {
	                   wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
	                   Logger.log("✅ Reset button clicked normally.");
	               } catch (ElementClickInterceptedException e) {
	                   Logger.log("⚠️ Normal click failed, using JavaScript click.");
	                   ((JavascriptExecutor) driver).executeScript("arguments[0].click();", resetButton);
	               }

	               // Wait for brand list to appear after reset
	               wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(BrandNames));
	               List<WebElement> spans = driver.findElements(BrandNames);
	               Logger.log("✅ Total brands listed after reset: " + spans.size());

	               // Print all brand names with index
	               for (int i = 0; i < spans.size(); i++) {
	                   Logger.log("Index " + i + ": " + spans.get(i).getText());
	               }

	               // Example click: index 1
	               if (spans.size() > 1) {
	                   spans.get(1).click();
	                   Logger.log("✅ Clicked brand at index 1 successfully.");
	               } else {
	                   Logger.log("⚠️ Not enough elements to click index 1.");
	               }

	           } catch (Exception e) {
	               Logger.log("❌ Error while checking Reset button: " + e.getMessage());
	               e.printStackTrace();
	           }
	  
	       
	      }
	       public void addnewDiscountBTN(String Brandmname,String Compnayname , String phonenumber, String areaCode,String Address,String Email,String Country, String State,String City,String zipCode  ) throws InterruptedException {
	    	    try {
	    	        // ✅ Scroll to top before interacting
	    	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
	    	        Thread.sleep(500);

	    	        // ✅ Wait until the button is clickable
	    	        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addnewBTn));

	    	        // ✅ Try normal click first
	    	        try {
	    	            addButton.click();
	    	            Logger.log("✅ Add New Discount button clicked normally.");
	    	        } catch (ElementClickInterceptedException e) {
	    	            Logger.log("⚠️ Normal click failed, using JavaScript click.");
	    	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
	    	        }

	    	    } catch (Exception e) {
	    	        Logger.log("❌ Error while clicking Add New Discount button: " + e.getMessage());
	    	        e.printStackTrace();
	    	    }
	    	    
	    	    
	    	   driver.findElement(BrandName).sendKeys(Brandmname);
	    	   driver.findElement(CompanyName).sendKeys(Compnayname);
	    	   driver.findElement(Phonenumber).sendKeys(phonenumber);
	    	   driver.findElement(Areacode).sendKeys(areaCode);
	    	   driver.findElement(address).sendKeys(Address);
	    	   driver.findElement(email).sendKeys(Email);

	    	// 🔹 Step 1: Select Country
	    	WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(CuntryDrp));
	    	Select sc = new Select(countryDropdown);
	    	sc.selectByVisibleText(Country);
	    	Logger.log("✅ Country selected: " + Country);

	    	// 🔹 Step 2: Select State (wait till enabled)
	    	WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(stateDrp));
	    	Select sc1 = new Select(stateDropdown);
	    	sc1.selectByVisibleText(State);
	    	Logger.log("✅ State selected: " + State);

	    	// 🔹 Step 3: Wait for City dropdown to become enabled and populated
	    	WebElement cityDropdown = driver.findElement(CityDrp);

	    	// ✅ Wait until city dropdown is enabled (in case it’s disabled initially)
	    	wait.until(driver -> cityDropdown.isEnabled());

	    	// ✅ Wait until city dropdown has at least one option (other than default)
	    	wait.until(driver -> {
	    	    Select tempSelect = new Select(cityDropdown);
	    	    return tempSelect.getOptions().size() > 1;
	    	});

	    	Select sc2 = new Select(cityDropdown);
	    	sc2.selectByVisibleText(City);
	    	Logger.log("✅ City selected: " + City);
	    	
	    	driver.findElement(ZipCode).sendKeys(zipCode);
	    	
	    	
	    	String imagePath = "C:\\Users\\insof_f8ae8du\\Downloads\\images.jpeg"; 
	    	WebElement uploadInput = driver.findElement(logoUpload);
	    	uploadInput.sendKeys(imagePath);

	    	String certificatePath = "C:\\Users\\insof_f8ae8du\\Downloads\\Jump2brand379_Shipping_09-25-2024_to_09-25-2025 (1).pdf";
	    	WebElement certificateInput = driver.findElement(cartificateUpload);
	    	certificateInput.sendKeys(certificatePath);
;
	  

	    	Logger.log("✅ Brand logo uploaded successfully!");
	    	driver.findElement(AddBTN).click();
	    	
	  
	    
	    	
	    	
	       }
	       
	       
	       public void handleBrandPopup(String brandName) {
	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

	    	    try {
	    	        // ✅ duplicate check
	    	        WebElement duplicate = wait.until(
	    	                ExpectedConditions.visibilityOfElementLocated(DuplicateBrandtext)
	    	        );

	    	        Logger.log("✅ Duplicate brand — no delete needed.");
	    	        return;  // ✅ STOP here
	    	    } catch (Exception e) {
	    	        Logger.log("ℹ️ No duplicate. Checking success popup...");
	    	    }

	    	    // ✅ success popup
	    	    validateBrandSuccessPopup();
	    	    closeSuccessPopup();

	    	    // ✅ delete ONLY AFTER SUCCESS
	    	    delteBrand(brandName);

	    	    Logger.log("✅ Brand added and deleted successfully.");
	    	}

	       
	       public boolean verifyErrorMessagesCount() {
	    	    // ✅ Step 1: Click "Add New" tab
	    	   try {
	    	        // ✅ Scroll to top before interacting
	    		   ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200);");

	    	        Thread.sleep(500);

	    	        // ✅ Wait until the button is clickable
	    	        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addnewBTn));

	    	        // ✅ Try normal click first
	    	        try {
	    	            addButton.click();
	    	            Logger.log("✅ Add New Discount button clicked normally.");
	    	        } catch (ElementClickInterceptedException e) {
	    	            Logger.log("⚠️ Normal click failed, using JavaScript click.");
	    	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
	    	        }

	    	    } catch (Exception e) {
	    	        Logger.log("❌ Error while clicking Add New Discount button: " + e.getMessage());
	    	        e.printStackTrace();
	    	    }

	    	    // ✅ Step 2: Click "Add" button to trigger validation errors
	    	    driver.findElement(AddBTN).click();

	    	    // ✅ Step 3: Wait until error messages are visible
	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    	    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(errorMessages));

	    	    // ✅ Step 4: Get all error elements
	    	    List<WebElement> errors = driver.findElements(errorMessages);

	    	    Logger.log("----- Error Messages Found -----");
	    	    for (WebElement e : errors) {
	    	        String msg = e.getText().trim();
	    	        if (!msg.isEmpty()) {
	    	            Logger.log(msg);
	    	        }
	    	    }

	    	    long count = errors.stream()
	    	                       .filter(e -> !e.getText().trim().isEmpty())
	    	                       .count();

	    	    Logger.log("Total visible error messages: " + count);
	    	    return count == 7;
	    	}
	       
	       public void validateBrandSuccessPopup() {
	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	    WebElement msg = wait.until(
	    	        ExpectedConditions.visibilityOfElementLocated(valtidationText)
	    	    );

	    	    String actual = msg.getText().trim();

	    	    Assert.assertTrue(
	    	        actual.contains("Thank you for submitting"),
	    	        "❌ Brand NOT added!"
	    	    );
	    	}



	       public void closeSuccessPopup() {
	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    	    WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(CloseBtnPOP));
	    	    closeBtn.click();
	    	}

	       public void DuplictaeBran() {
	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	    WebElement msg = wait.until(
	    	        ExpectedConditions.visibilityOfElementLocated(DuplicateBrandtext)
	    	    );

	    	    String actual = msg.getText().trim();

	    	    Assert.assertEquals(
	    	        actual,
	    	        "Brand name alredy exists",
	    	        "❌ Duplicate brand message not shown!"
	    	    );
	    	}

	       
	       public void delteBrand(String brandName) {
	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    	    try {

	    	        // ✅ 1. Search the brand in the table
	    	        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(Searchbar));
	    	        search.clear();
	    	        search.sendKeys(brandName, Keys.ENTER);
	    	        Thread.sleep(1000);

	    	        // ✅ 2. Wait for the delete icon of the FIRST row
	    	        WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteIcon));
	    	        deleteBtn.click();
	    	        Logger.log("✅ Delete button clicked.");

	    	        // ✅ 3. Wait for confirmation popup
	    	        WebElement popupDeleteBtn = wait.until(
	    	                ExpectedConditions.elementToBeClickable(deletepop)
	    	        );

	    	        // ✅ 4. Click delete inside popup
	    	        popupDeleteBtn.click();
	    	        Logger.log("✅ Delete confirmed from popup.");

	    	        // ✅ 5. Optional: Validate row is deleted
	    	        Thread.sleep(1000);
	    	        search.clear();
	    	        search.sendKeys(brandName, Keys.ENTER);
	    	        Thread.sleep(1000);

	    	        List<WebElement> rows = driver.findElements(By.xpath("//table//tr"));
	    	        if (rows.size() <= 1) {
	    	            Logger.log("✅ Brand deleted successfully: " + brandName);
	    	        } else {
	    	            Logger.log("⚠️ Brand still appears in list. Check manually.");
	    	        }

	    	    } catch (Exception e) {
	    	        Logger.log("❌ Error while deleting brand: " + e.getMessage());
	    	        e.printStackTrace();
	    	    }
	    	}
	       
	       
	       
	       
	       public void editCompanyNameAndVerify(int index, String newCompanyName) throws InterruptedException {

	    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    	    // ✅ Always scroll to top
	    	    js.executeScript("window.scrollTo(0, 0)");
	    	    Thread.sleep(500);

	    	    // ✅ 1. Get current company names
	    	    List<WebElement> companyNamesBefore = wait.until(
	    	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	    	                    By.xpath("(//span[@class='text-dark p-0'])")
	    	            )
	    	    );

	    	    if (index < 0 || index >= companyNamesBefore.size()) {
	    	        throw new IllegalArgumentException("Invalid index: " + index);
	    	    }

	    	    // ✅ 2. Fetch OLD name
	    	    String oldName = companyNamesBefore.get(index).getText().trim();
	    	    Logger.log("OLD Company Name at index " + index + ": " + oldName);

	    	    // ✅ 3. Click edit button for same index
	    	    WebElement editBtn = wait.until(
	    	            ExpectedConditions.elementToBeClickable(
	    	                    By.xpath("(//i[contains(@class,'icon-edit') and contains(@class,'icon-equal')])[" + (index + 1) + "]")
	    	            )
	    	    );

	    	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", editBtn);
	    	    Thread.sleep(400);
	    	    editBtn.click();

	    	    // ✅ 4. Change name
	    	    WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(CompanyName));
	    	    nameField.clear();
	    	    nameField.sendKeys(newCompanyName);

	    	    // ✅ 5. Click save (Add button)
	    	    driver.findElement(AddBTN).click();

	    	    // ✅ 6. HANDLE POPUP — CLOSE IT
	    	    try {
	    	        WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(
	    	                By.xpath("//button[@class='defaultGrayBtn w-100 btn btn-primary']")
	    	        ));

	    	        closePopup.click();
	    	        Logger.log("✅ Popup closed successfully");

	    	    } catch (Exception e) {
	    	        Logger.log("⚠ No popup appeared after Add button");
	    	    }

	    	    // ✅ Wait for UI refresh
	    	    Thread.sleep(1500);

	    	    // ✅ 7. Re-fetch updated company names
	    	    List<WebElement> companyNamesAfter = wait.until(
	    	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	    	                    By.xpath("(//span[@class='text-dark p-0'])")
	    	            )
	    	    );

	    	    // ✅ 8. Fetch UPDATED name
	    	    String updatedName = companyNamesAfter.get(index).getText().trim();
	    	    Logger.log("NEW Company Name at index " + index + ": " + updatedName);

	    	    // ✅ 9. Validate
	    	    if (updatedName.equals(oldName)) {
	    	        Logger.log("❌ FAILED: Company name NOT changed!");
	    	        Assert.fail("Company name did NOT change. Old: " + oldName + " | New: " + updatedName);
	    	    } else {
	    	        Logger.log("✅ SUCCESS: Company name updated successfully → " + updatedName);
	    	    }
	    	}


	       
}
	

