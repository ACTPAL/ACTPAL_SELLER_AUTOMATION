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

public class OrderManagementPage {
	 private WebDriver driver;
	 
	 private WebDriverWait wait;
	 public OrderManagementPage(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
	    }
	 
	 
	 
	 private By   orderManagmentBTN = By.xpath("//a[span[text()='Order Management']]");
	 private By     AllorderBtn = By.xpath("//span[text()='All Orders']");
	 
	 private By orderstatusDropdwn = By.xpath("//select[@id='OrderStatus-DropdownID']");
	 
	 
	 
	 public void OpenOrderManagamntPage() throws InterruptedException {

		    // ✅ Wait until Order Management is present
		    WebElement orderMgmt = wait.until(ExpectedConditions.elementToBeClickable(orderManagmentBTN));

		    // ✅ Scroll into view
		    ((JavascriptExecutor) driver).executeScript(
		            "arguments[0].scrollIntoView({block: 'center'});", orderMgmt
		    );
		    Thread.sleep(400);

		    // ✅ Click Order Management (open dropdown)
		    try {
		        orderMgmt.click();
		        System.out.println("✅ Order Management clicked.");
		    } catch (Exception e) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderMgmt);
		        System.out.println("⚠️ JS clicked Order Management.");
		    }

		    // ✅ Now click All Orders (dropdown menu)
		    WebElement allOrders = wait.until(ExpectedConditions.elementToBeClickable(AllorderBtn));
		    allOrders.click();
		    System.out.println("✅ All Orders clicked.");

		    // ✅ Validate page title
		    WebElement pageTitle = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()=\"Order Management\"]"))
		    );

		    Assert.assertTrue(pageTitle.isDisplayed(), "❌ Orders List page not opened!");
		    System.out.println("✅ Orders List Page opened successfully!");
		}
	 
	 
	 public void checkOrderStatusFilter(String expectedStatus) {

		    // ✅ 1. Select dropdown status
		    Select sc = new Select(driver.findElement(orderstatusDropdwn));
		    sc.selectByVisibleText(expectedStatus);

		    // ✅ 2. Wait for table to reload
		    wait.until(ExpectedConditions.invisibilityOfElementLocated(
		            By.xpath("//div[contains(@class,'loader')]")
		    ));

		    // ✅ 3. Get all rows that contain status column
		    List<WebElement> statusList = driver.findElements(
		            By.xpath("//td[@class='mobHide']/a[contains(@class,'activeBtn')]")
		    );

		    Assert.assertTrue(statusList.size() > 0, "❌ No order rows found after filter!");

		    // ✅ 4. Validate each row
		    for (WebElement statusElement : statusList) {

		        String classValue = statusElement.getAttribute("class");  // example: activeBtn Delivered

		        System.out.println("Row Class Found: " + classValue);

		        // ✅ For safety convert both to lowercase
		        boolean match = classValue.toLowerCase().contains(expectedStatus.toLowerCase());

		        Assert.assertTrue(match,
		                "❌ Wrong status shown!" +
		                "\nExpected: " + expectedStatus +
		                "\nFound Class: " + classValue
		        );
		    }

		    System.out.println("✅ Status filter validated successfully for: " + expectedStatus);
		}




	 
	 
	 
}
