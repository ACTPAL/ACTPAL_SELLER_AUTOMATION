package tests;

import base.BaseTest;
import base.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import Pages.LoginPage;
import Pages.MediaPage;

import java.time.Duration;

public class MediaTest extends BaseTest {

    @Test(priority = 1)
    public void testMediaPageFeatures() {
        Logger.log("=== Starting testMediaPageFeatures ===");

        // --- Login ---
        LoginPage loginPage = new LoginPage(driver);
        Logger.log("Entering email");
        loginPage.enterEmail("jump2brand@gmail.com");

        Logger.log("Entering password");
        loginPage.enterPassword("Test@123");

        Logger.log("Clicking Login button");
        loginPage.clickLogin();

        String successMsg = loginPage.getSuccessMessage();
        Logger.log("Login message received: " + successMsg);
        Assert.assertTrue(successMsg.contains("Dashboard"), "❌ Login failed - Dashboard not displayed");
        Logger.pass("Login successful. Dashboard displayed");

        // --- Navigate to Media Page ---
        Logger.log("Navigating to Media page");
        driver.get("https://seller-development.dpn111r2gb89q.amplifyapp.com/media");

        // Close any popups (try up to 3 times)
        for (int i = 0; i < 3; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                By popupCloseBtn = By.xpath("//button[@class='btn-close' and @data-bs-dismiss='modal']");
                wait.until(ExpectedConditions.elementToBeClickable(popupCloseBtn)).click();
                Logger.log("Popup closed successfully. Attempt " + (i + 1));
            } catch (Exception e) {
                Logger.log("No popup appeared in attempt " + (i + 1));
            }
        }

        MediaPage mediaPage = new MediaPage(driver);

        // --- Search Media ---
        Logger.log("Searching for media: test");
        mediaPage.searchMedia("test");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean searchResult = wait.until(d -> mediaPage.isMediaDisplayed("test"));
        Assert.assertTrue(searchResult, "❌ Search failed: Media not found");
        Logger.pass("Search successful: Media found");

        // --- Select one media ---
        Logger.log("Selecting first media item");
        mediaPage.clickSelect(0);
        Assert.assertTrue(mediaPage.isSelected(0), "❌ Media selection failed");
        Logger.pass("First media item selected successfully");

        // --- Select All media ---
        Logger.log("Selecting all media items");
        mediaPage.clickSelectAll();
        Assert.assertTrue(mediaPage.areAllSelected(), "❌ Select All failed");
        Logger.pass("All media items selected successfully");

        // --- Reset search ---
        Logger.log("Resetting search");
        mediaPage.clickReset();
        Assert.assertEquals(mediaPage.getSearchText(), "", "❌ Reset search failed - search bar not cleared");
        Logger.pass("Search reset successfully");

        // --- Upload media button visible ---
        Logger.log("Verifying Upload button visibility");
        Assert.assertTrue(mediaPage.isUploadButtonDisplayed(), "❌ Upload button not visible");
        Logger.pass("Upload button is visible");

        // --- Create folder button visible ---
        Logger.log("Verifying Create Folder button visibility");
        Assert.assertTrue(mediaPage.isCreateFolderButtonDisplayed(), "❌ Create folder button not visible");
        Logger.pass("Create Folder button is visible");

        // --- Mouse hover effects ---
        Logger.log("Verifying hover icons on first media item");
        Assert.assertTrue(mediaPage.isHoverIconsVisible(0), "❌ Hover icons not visible on first media item");
        Logger.pass("Hover icons displayed correctly");

        // --- Media name and date validations ---
        String name = mediaPage.getMediaName(0);
        String date = mediaPage.getMediaDate(0);

        Logger.log("Validating media name and upload date");
        Assert.assertNotNull(name, "❌ Media name is missing");
        Assert.assertFalse(name.isEmpty(), "❌ Media name is empty");
        Assert.assertNotNull(date, "❌ Media upload date is missing");
        Assert.assertFalse(date.isEmpty(), "❌ Media upload date is empty");

        Logger.pass("Media Name: " + name + ", Upload Date: " + date);
        Logger.log("✅ Media Name: " + name);
        Logger.log("✅ Upload Date: " + date);
    }
}


