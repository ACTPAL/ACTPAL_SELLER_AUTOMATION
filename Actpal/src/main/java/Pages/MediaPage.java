
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MediaPage {
    WebDriver driver;
    WebDriverWait wait;

    public MediaPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Locators ---
    By searchBar = By.xpath("//input[@placeholder='Search folder by keywords']");
    By enterSearch = By.xpath("//i[@class='fa fa-search']");

    By selectButton = By.xpath("//input[@id='Radio1']");
    By selectAllButton = By.xpath("//input[@id='Radio2']");
    By resetButton = By.xpath("//button[@type='reset']");
    By uploadButton = By.id("");   // update this when ID is available
    By createFolderButton = By.id("create-folder-btn");

    // Parent container
    By mediaItems = By.cssSelector(".media-item");

    // Child locators inside media-item
    By mediaName = By.cssSelector("h6.fs-small");
    By mediaDate = By.cssSelector(".media-date");
    By viewIcon = By.cssSelector(".icon-view");
    By deleteIcon = By.cssSelector(".icon-delete");
    By copyLinkIcon = By.cssSelector(".icon-copy");

    // --- Methods ---

    public void searchMedia(String keyword) {
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBar));
        search.clear();
        search.sendKeys(keyword);
        driver.findElement(enterSearch).click();
    }

    public boolean isMediaDisplayed(String keyword) {
        List<WebElement> items = driver.findElements(mediaItems);
        for (WebElement item : items) {
            String name = item.findElement(mediaName).getText();
            if (name.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    public void clickSelect(int index) {
        driver.findElements(selectButton).get(index).click();
    }

    public boolean isSelected(int index) {
        return driver.findElements(selectButton).get(index).isSelected();
    }

    public void clickSelectAll() {
        driver.findElement(selectAllButton).click();
    }

    public boolean areAllSelected() {
        List<WebElement> selects = driver.findElements(selectButton);
        for (WebElement select : selects) {
            if (!select.isSelected()) return false;
        }
        return true;
    }

    public void clickReset() {
        driver.findElement(resetButton).click();
    }

    public String getSearchText() {
        return driver.findElement(searchBar).getAttribute("value");
    }

    public void clickUploadMedia() {
        driver.findElement(uploadButton).click();
    }

    public boolean isUploadButtonDisplayed() {
        return driver.findElement(uploadButton).isDisplayed();
    }

    public void clickCreateFolder() {
        driver.findElement(createFolderButton).click();
    }

    public boolean isCreateFolderButtonDisplayed() {
        return driver.findElement(createFolderButton).isDisplayed();
    }

    // Hover on specific media item
    public void hoverOnMedia(int index) {
        Actions actions = new Actions(driver);
        WebElement media = driver.findElements(mediaItems).get(index);
        actions.moveToElement(media).perform();
    }

    // Check if hover icons are visible for a specific media item
    public boolean isHoverIconsVisible(int index) {
        WebElement item = driver.findElements(mediaItems).get(index);
        hoverOnMedia(index);
        return item.findElement(viewIcon).isDisplayed() &&
               item.findElement(deleteIcon).isDisplayed() &&
               item.findElement(copyLinkIcon).isDisplayed();
    }

    // Get media name for item at index
    public String getMediaName(int index) {
        WebElement item = driver.findElements(mediaItems).get(index);
        return item.findElement(mediaName).getText();
    }

    // Get media date for item at index
    public String getMediaDate(int index) {
        WebElement item = driver.findElements(mediaItems).get(index);
        return item.findElement(mediaDate).getText();
    }
}
