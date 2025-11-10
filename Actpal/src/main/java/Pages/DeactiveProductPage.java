package Pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Logger;

public class DeactiveProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DeactiveProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Increased wait
    }

    private By manageProductBtn = By.xpath("//a[@class='icon icon-manage-products icon-set']");
    private By deactiveProductBTN = By.xpath("//span[normalize-space()='De-active Product']");
    private By pageHeading = By.xpath("//h4[normalize-space()='Manage Products']");

    public boolean isDeactiveProductPageOpened() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickDeactiveProductBtn() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(manageProductBtn)).click();
            closePopups(1);

            WebElement deactiveBtnElement = wait.until(ExpectedConditions.elementToBeClickable(deactiveProductBTN));
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)"); // scroll down
            Thread.sleep(500);
            deactiveBtnElement.click();

            wait.until(ExpectedConditions.urlContains("deactive-products"));
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)"); // scroll back to top

            return driver.getCurrentUrl().contains("deactive-products");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    private void closePopups(int attempts) {
        for (int i = 0; i < attempts; i++) {
            try {
                By popupCloseBtn = By.xpath("//button[@class='btn-close' and @data-bs-dismiss='modal']");
                wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
                Logger.log("Popup closed successfully. Attempt " + (i + 1));
            } catch (Exception e) {
                Logger.log("No popup appeared in attempt " + (i + 1));
            }
        }
    }
}

