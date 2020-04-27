import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FireFoxTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.gecko.driver", "resources/geckodriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        options.addPreference("intl.accept_languages", "en-us");
        driver = new FirefoxDriver(options);
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        driver.get("http://automationpractice.com/index.php");
    }

    @Test
    public void checkLogo() {
        assertTrue(driver.findElement(By.xpath("//*[@id=\"header_logo\"]/a/img")).isDisplayed());
    }

    @Test
    public void clickByTextTest() {
        driver.findElement(By.linkText("Women"))
                .click();
        assertEquals("Women - My Store",driver.getTitle());
    }

    @Test
    public void searchTest() {
        WebElement webElement = driver.findElement(By.xpath("//*[@id=\"search_query_top\"]"));
        webElement.sendKeys("black");
        webElement = driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button"));
        webElement.sendKeys(Keys.ENTER);

        webElement = driver.findElement(By.xpath("//*[@id=\"center_column\"]/ul/li[1]/div/div[1]/div/a[1]/img"));

        assertNotNull(webElement.getText());
    }

    @Test
    public void countLinksTest() {
        List<WebElement> list = driver.findElements(By.xpath(".//a"));
        assertEquals(149, list.size());
    }

    @Test
    public void countImages() {
        List<WebElement> list = driver.findElements(By.xpath(".//img"));
        assertEquals(30, list.size());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
