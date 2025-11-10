package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        // ✅ Set the path to your EdgeDriver (matching version)
        System.setProperty("webdriver.edge.driver", "C:\\Drivers\\EdgeDriver\\msedgedriver.exe");

        // ✅ Create Edge options
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-notifications");

        // ✅ Initialize EdgeDriver
        driver = new EdgeDriver(options);

        // ✅ Wait setup and open your site
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://seller-development.dpn111r2gb89q.amplifyapp.com/login");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
