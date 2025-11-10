package Pages;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;

import java.util.ArrayList;
import java.util.List;


public class AddProductPage {
    private WebDriver driver;

    // --- Locators ---
    private By manageProductBtn = By.xpath("//a[@class='icon icon-manage-products icon-set']");
    private By addNewProductBtn = By.xpath("//span[normalize-space()='Add New Product']");
    private By productTypeDropdown = By.xpath("//select[@id='TypeId']");
    private By productNameField = By.xpath("//input[@placeholder='Product Name']");
    private By brandDropdown = By.id("Brand-DropdownID");
    private By userDropdown = By.xpath("//select[@id='ProductUsedId']");
    private By cautionaryDropdown = By.xpath("//select[@id='CautionId']");
    private By skuField = By.xpath("//input[@placeholder='Product Id']");
    private By categoryDropdown = By.xpath("//select[@id='CategoryDropDown']");
    private By subCategoryDropdown = By.xpath("//select[@id='Sub_CategoryId']");
    private By productCategoryDropdown = By.xpath("//select[@id='ProductTypeId']");
    private By shortSummaryField = By.xpath("//textarea[@placeholder='Short Summary']");
    private By descriptionField = By.cssSelector("div.ql-editor");
    private By addProductBtn = By.xpath("//button[@type='submit']");

    // Discount
    private By discountYesRadio = By.xpath("//input[@id='flexRadioDefault1']");
    private By discountNoRadio  = By.xpath("//input[@id='flexRadioDefault2']");
    private By discountTypeDropdown = By.name("DiscountType");
    private By discountValueInput = By.xpath("//input[@placeholder='Discount Value']");
    private By freeShippingYes = By.xpath("//input[@id='flexRadioDefault1']");
    private By freeShippingNo  = By.xpath("//input[@id='flexRadioDefault2']");
    private By discountNextBtn = By.xpath("//button[@id='productAttributes']");

    // Attribute
    private By attributeDropdown = By.name("AttributeId");
    private By enterAttri = By.xpath("//input[@placeholder='Enter Custom Attribute']");
    private By valueField = By.xpath("//input[@placeholder='Value']");
    private By saveNextAttrBtn = By.xpath("//button[@id='submitAllAttributeForm']");

    // Sale Policy
   
    private By Skip = By.xpath("//button[normalize-space()='Skip']");
    // Search Keyword 
    private By searchkey = By.xpath("//textarea[@class=\"form-control\"]");
    private By  saveBTN  = By.xpath("//button[@id='productInventory']");
    
    
    // Inventory
    private By addInventoryBtn = By.xpath("//button[contains(@class,'editInventory')]");
    private By productCodeField = By.xpath("//input[@placeholder='SKU']");
    private By colorDrp = By.xpath("//div[@id='add_ProductInventoryForm']//div[contains(@class,'requiredField')]/div[1]");
    private By sizeField = By.xpath("//input[@placeholder='Size']");
    private By priceField = By.xpath("//input[@placeholder='Price']");
    private By quantity =  By.xpath("//input[@placeholder='Quantity']");
    private By quantityField = By.xpath("//input[@placeholder='Piece per quantity']");
    private By weightField = By.xpath("//input[@placeholder='Weight']");
    private By widthField = By.xpath("//input[@placeholder='Width']");
    private By lengthField = By.xpath("//input[@placeholder='Length']");
    private By heightField = By.xpath("//input[@placeholder='Height']");
    private By saveInventoryBtn = By.xpath("//button[@type='submit']");
    
    //valiadtion & Media 
    private By  mediaIconBtn = By.xpath("//i[@class='icon icon-add icon-equal']");
    private By picSelect = By.xpath("//li[1]//div[1]//button[2]//i[1]");
    private By addMediabtn = By.xpath("//button[normalize-space()='Add Media']");
    private By allProduct = By.xpath("//span[normalize-space()='All Products']");
    private By searchBar = By.xpath("//input[@id='product_Search']");
    private By SerachIcon = By.xpath("//i[@class='fa-solid fa-magnifying-glass']");
    private By  actionBtn =  By.xpath("//i[@class='icon icon-add icon-equal']");

    // --- Constructor ---
    public AddProductPage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Page Methods ---
    public void openAddNewProductPage() {
        driver.findElement(manageProductBtn).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.elementToBeClickable(addNewProductBtn));
        driver.findElement(addNewProductBtn).click();
    }

    public void enterBasicDetails(String name, String sku, String summary, String desc, String productType,
                                  String brandValue, String userD, String cautionD, String category, String subCategory,
                                  String productCategory) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElement(productNameField).sendKeys(name);
        driver.findElement(skuField).sendKeys(sku);
        driver.findElement(shortSummaryField).sendKeys(summary);

        WebElement descElement = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionField));
        js.executeScript("arguments[0].innerHTML = arguments[1];", descElement, desc);

     // Product Type dropdown
        WebElement productTypeElement = wait.until(ExpectedConditions.elementToBeClickable(productTypeDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", productTypeElement);
        Thread.sleep(300);
        new Select(productTypeElement).selectByVisibleText(productType);

        // Brand dropdown
        WebElement brandElement = driver.findElement(brandDropdown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", brandElement);
        Thread.sleep(300);
        brandElement.click();
        List<WebElement> brandOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#Brand-DropdownID option")));
        boolean found = false;
        for (WebElement option : brandOptions) {
            if (option.getText().equals(brandValue)) {
                option.click();
                found = true;
                break;
            }
        }
        if (!found) throw new RuntimeException("Brand option not found!");

        // User dropdown
        WebElement userElement = wait.until(ExpectedConditions.elementToBeClickable(userDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", userElement);
        Thread.sleep(300);
        new Select(userElement).selectByVisibleText(userD);

        // Caution dropdown
        WebElement cautionElement = wait.until(ExpectedConditions.elementToBeClickable(cautionaryDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", cautionElement);
        Thread.sleep(300);
        new Select(cautionElement).selectByVisibleText(cautionD);

        // Category dropdown
        WebElement categoryElement = wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", categoryElement);
        Thread.sleep(300);
        new Select(categoryElement).selectByVisibleText(category);

        // Sub Category dropdown
        WebElement subCategoryElement = wait.until(ExpectedConditions.elementToBeClickable(subCategoryDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", subCategoryElement);
        Thread.sleep(300);
        new Select(subCategoryElement).selectByVisibleText(subCategory);

        // Product Category dropdown
        WebElement productCategoryElement = wait.until(ExpectedConditions.elementToBeClickable(productCategoryDropdown));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", productCategoryElement);
        Thread.sleep(300);
        new Select(productCategoryElement).selectByVisibleText(productCategory);

    }

    public void clickAddProduct() {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement addBtn = wait.until(ExpectedConditions.presenceOfElementLocated(addProductBtn));

        // Smooth scroll
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addBtn);

     
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

  
        wait.until(ExpectedConditions.elementToBeClickable(addBtn));

   
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn);
    }

    // --- Discount ---
    public void selectDiscountOption(boolean hasDiscount) {
        driver.findElement(hasDiscount ? discountYesRadio : discountNoRadio).click();
    }

    public void setDiscountDetails(String discountType, String discountValue, boolean freeShipping) {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        if (discountType != null && !discountType.isEmpty())
            new Select(wait.until(ExpectedConditions.elementToBeClickable(discountTypeDropdown))).selectByVisibleText(discountType);

        if (discountValue != null && !discountValue.isEmpty()) {
            WebElement valueInput = wait.until(ExpectedConditions.visibilityOfElementLocated(discountValueInput));
            valueInput.clear();
            valueInput.sendKeys(discountValue);
        }

        driver.findElement(freeShipping ? freeShippingYes : freeShippingNo).click();
    }

    public void clickDiscountNext() {
        driver.findElement(discountNextBtn).click();
    }

    // --- Attribute ---
    public void addAttribute(String attributeName, String attributeValue) {
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        new Select(wait.until(ExpectedConditions.elementToBeClickable(attributeDropdown))).selectByVisibleText("Custom Attribute");
        WebElement attrInput = wait.until(ExpectedConditions.visibilityOfElementLocated(enterAttri));
        attrInput.clear();
        attrInput.sendKeys(attributeName);
        WebElement valInput = wait.until(ExpectedConditions.visibilityOfElementLocated(valueField));
        valInput.clear();
        valInput.sendKeys(attributeValue);
        driver.findElement(saveNextAttrBtn).click();
    }

    // --- Sale Policy ---
    public void fillSalePolicy() {
        
  driver.findElement(Skip).click();

    // --- Start Date Selection ---
    
    // --- Inventory ---
    }
    
 public void searchKeyword( String keyword) {
	 
	 driver.findElement(searchkey).clear();
	 driver.findElement(searchkey).sendKeys(keyword);
     driver.findElement(saveBTN).click();
	 
 }
 
 public void addInventory(String sku, String color, String size, String Quantity, String price, String qty,
         String weight, String width, String length, String height) {
// Click "Add Inventory"
	 
	  WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(5));
wait.until(ExpectedConditions.elementToBeClickable(addInventoryBtn)).click();

// Enter SKU
wait.until(ExpectedConditions.visibilityOfElementLocated(productCodeField)).sendKeys(sku);

// Open color dropdown
WebElement colorDropdown = wait.until(ExpectedConditions.elementToBeClickable(colorDrp));
colorDropdown.click();

// Select desired color from dropdown (dynamic)
By colorOption = By.xpath("//span[normalize-space(text())='" + color + "']");
wait.until(ExpectedConditions.elementToBeClickable(colorOption)).click();

// Fill other fields
driver.findElement(sizeField).sendKeys(size);
driver.findElement(quantity).sendKeys(Quantity);
driver.findElement(priceField).sendKeys(price);
driver.findElement(quantityField).sendKeys(qty);
driver.findElement(weightField).sendKeys(weight);
driver.findElement(widthField).sendKeys(width);
driver.findElement(lengthField).sendKeys(length);
driver.findElement(heightField).sendKeys(height);

// Click Save
driver.findElement(saveInventoryBtn).click();
}
 public void  validation(String ProductID ) throws InterruptedException {
	 
	 WebDriverWait  wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	 
	 driver.findElement(mediaIconBtn).click();
	 
	 wait.until(ExpectedConditions.elementToBeClickable(picSelect)).click();
	 wait.until(ExpectedConditions.elementToBeClickable(addMediabtn)).click();
	 
	 
	 WebElement allProductsBtn = wait.until(ExpectedConditions.presenceOfElementLocated(allProduct));

	 // Scroll the button into view (so part of it is visible)
	 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", allProductsBtn);

	 // Optional: small pause if needed for animations

	 // Now click safely
	 allProductsBtn.click();

	 closePopups(1);
	 driver.findElement(searchBar).sendKeys(ProductID);
	 WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(SerachIcon));
	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);

	 WebElement ActionBtn = wait.until(ExpectedConditions.elementToBeClickable(actionBtn));
	 ((JavascriptExecutor) driver).executeScript("arguments[0].click();",ActionBtn);
	// Now verify the effect of click
	
 }
 private void closePopups(int attempts) {
     for (int i = 0; i < attempts; i++) {
         try {
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
             By popupCloseBtn = By.xpath("//button[@class='btn-close' and @data-bs-dismiss='modal']");
             wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
             System.out.println("Popup closed successfully. Attempt " + (i + 1));
         } catch (Exception e) {
             System.out.println("No popup appeared in attempt " + (i + 1));
         }
     }
 
}
//--- New Method for Basic Details Mandatory Validation ---
 public void validateMandatoryBasicDetails() throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    long timestamp = System.currentTimeMillis();

	    // --- 1️⃣ Product Name blank ---
	    enterBasicDetails(
	            "",                               // Product Name
	            "SKU" + timestamp,                // SKU
	            "",                               // Short Summary
	            "Detailed description",           // Description
	            "UPC",               // Product Type
	            "ZARA",                            // Brand
	            "New",                   // Used Type
	            "ChokingHazardBalloon",        // Cautionary Statement
	            "Men",                    // Category
	            "Watches & Wearable",                // Sub-category
	            "Mens Watches"             // Placeholder for remaining dropdowns
	    );
	    clickAddProduct();
	    By productNameError = By.xpath("//span[contains(text(),'Please enter product name')]");
	    WebElement productNameMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameError));
	    js.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'});", productNameMsgElement);
	    Assert.assertEquals(productNameMsgElement.getText(), "Please enter product name");
	    System.out.println("✅ Product Name mandatory validation passed");

	    // --- 2️⃣ Brand blank ---
	    enterBasicDetails(
	            "Test Product",
	            "SKU" + System.currentTimeMillis(),
	            "",
	            "Detailed description",
	            "GTIN",
	            "Select Brand",
	            "New",
	            "ChokingHazardBalloon",
	            "Men",
	            "Watches & Wearable",
	            "Mens Watches"
	    );
	    clickAddProduct();
	    By brandError = By.xpath("(//span[@class='errMsg'])[1]");
	    WebElement brandMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(brandError));
	    js.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'});", brandMsgElement);
	    String brandMsg = brandMsgElement.getText();
	    Assert.assertEquals(brandMsg, "Please select the brand");
	    System.out.println("✅ Brand mandatory validation passed: " + brandMsg);


	    // --- 3️⃣ Category blank ---
	    enterBasicDetails(
	            "",
	            "",
	            "",
	            "Detailed description",
	            "GTIN",
	            "ZARA",
	            "New",
	            "ChokingHazardBalloon",
	            "--Category--",
	            "--Sub-Category--",
	            "--Type--"
	    );
	    clickAddProduct();
	 
	    By categoryError = By.xpath("//span[normalize-space()='Please select the category']");
	    WebElement categoryMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryError));
	    js.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'});", categoryMsgElement);
	    String categoryMsg = categoryMsgElement.getText();
	    Assert.assertEquals(categoryMsg, "Please select the category");
	    System.out.println("✅ Category mandatory validation passed: " + categoryMsg);



	   

	    // --- 5️⃣ Product Type blank ---
	    enterBasicDetails(
	            "=",
	            "",
	            "Short summary",
	            "Detailed description",
	            "--Product Type--",               // Product Type blank
	            "ZARA",
	            "New",
	            "ChokingHazardBalloon",
	            "Men",
	            "Watches & Wearable",
	            "Mens Watches"
	    );
	    clickAddProduct();
	    By productTypeError = By.xpath("//span[contains(text(),'Please select the product type')]");
	    WebElement productTypeMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(productTypeError));
	    js.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'});", productTypeMsg);
	    Assert.assertEquals(productTypeMsg.getText(), "Please select the product type");
	    System.out.println("✅ Product Type mandatory validation passed");

	    // --- 6️⃣ Used Type blank ---
	  

	  

	    By shortSummaryField = By.xpath("//textarea[@placeholder='Short Summary']"); // replace with the actual locator
	 // Clear Short Summary field
	    WebElement summaryFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(shortSummaryField));
	    summaryFieldElement.clear(); // clears any existing text

	    // Now click Add Product
	    clickAddProduct();

	    // Wait for and validate the error message
	    By summaryError = By.xpath("//span[normalize-space()='Please enter short summary']");
	    WebElement summaryMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(summaryError));
	    js.executeScript("arguments[0].scrollIntoView({behavior:'auto', block:'center'});", summaryMsgElement);
	    String summaryMsg = summaryMsgElement.getText();
	    Assert.assertEquals(summaryMsg, "Please enter short summary");
	    System.out.println("✅ Short Summary mandatory validation passed: " + summaryMsg);

	    System.out.println("✅ All Basic Details mandatory validations passed successfully!");

	    // You can continue the same pattern for remaining dropdowns/fields.
	}

 public void verifyCategorySubcategoryDependency(String category, String expectedSubCategory) throws InterruptedException {
     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     JavascriptExecutor js = (JavascriptExecutor) driver;

     // --- Step 1: Select the Category ---
     WebElement categoryDropdown1 = wait.until(ExpectedConditions.elementToBeClickable(categoryDropdown));
     Select categorySelect = new Select(categoryDropdown1);
     categorySelect.selectByVisibleText(category);
     System.out.println("✅ Category selected: " + category);

     // --- Step 2: Wait for Sub-category dropdown to refresh ---
     Thread.sleep(2000);

     WebElement subCategoryDropdown1 = wait.until(ExpectedConditions.elementToBeClickable(subCategoryDropdown));
     Select subCategorySelect = new Select(subCategoryDropdown1);

     // --- Step 3: Capture all Sub-category options ---
     List<WebElement> subCategoryOptions = subCategorySelect.getOptions();
     List<String> subCategoryTexts = new ArrayList<>();
     for (WebElement option : subCategoryOptions) {
         subCategoryTexts.add(option.getText().trim());
     }

     // --- Step 4: Verify that the expected sub-category is present ---
     boolean isSubCategoryPresent = subCategoryTexts.contains(expectedSubCategory);
     Assert.assertTrue(isSubCategoryPresent,
             "❌ Expected sub-category '" + expectedSubCategory + "' not found for category '" + category + "'. Found: " + subCategoryTexts);

     js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", subCategoryDropdown1);
     System.out.println("✅ Verified: Selecting '" + category + "' auto-populates sub-category with '" + expectedSubCategory + "'");
     System.out.println("✅ Available Sub-categories: " + subCategoryTexts);
 }
  

}


