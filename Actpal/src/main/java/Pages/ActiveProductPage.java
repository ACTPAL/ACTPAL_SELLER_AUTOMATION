package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Logger;

public class ActiveProductPage {
	
	 private WebDriver driver;
	    public ActiveProductPage (WebDriver driver) {
	        this.driver = driver;
	        new WebDriverWait(driver, Duration.ofSeconds(5));
	    }
	    
	    private By manageProductBtn = By.xpath("//a[@class='icon icon-manage-products icon-set']");
	    private By activeProductBTN  = By.xpath("//span[normalize-space()='Active Product']");
	    private By pageHeading = By.xpath("//h4[normalize-space()='Manage Products']");
	
	public boolean isActiveProductPageOpened() {
        try {
            return driver.findElement(pageHeading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
	
	public boolean clickActiveProductBtn() {
	    try {
	        
	        driver.findElement(manageProductBtn).click();

	        // 2️⃣ Wait for page to stabilize before scroll
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        wait.until(ExpectedConditions.presenceOfElementLocated(activeProductBTN));

	        // 3️⃣ Proper scroll using JS after element is in DOM
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        WebElement activeBtn = driver.findElement(activeProductBTN);
	        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", activeBtn);
	        Thread.sleep(500); // Small pause to let scroll finish (stabilizes headless runs)

	        // 4️⃣ Wait again till clickable
	        wait.until(ExpectedConditions.elementToBeClickable(activeBtn));
	        activeBtn.click();

	        // 5️⃣ Close popup (your existing logic)
	        closePopups(1);

	        // 6️⃣ Scroll slightly up for consistency
	        js.executeScript("window.scrollBy(0, -200)");

	        // 7️⃣ Validate page navigation
	        return driver.getCurrentUrl().contains("active-products");

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
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
