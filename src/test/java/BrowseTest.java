import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BrowseTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
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
    public void setUp() throws Exception {
        driver.get("https://duckduckgo.com/");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void titlePageTest() {
        assertEquals("DuckDuckGo — Privacy, simplified.", driver.getTitle());
    }

    @Test
    public void getLogoTest() {
        WebElement webElement = driver.findElement(By.id("logo_homepage_link"));
        assertTrue(webElement.isDisplayed());
    }

    @Test
    public void enterInsteadOfClickTest() {
        WebElement webElement = driver.findElement(By.cssSelector("#search_form_input_homepage"));
        webElement.sendKeys("Test");
        webElement = driver.findElement(By.id("search_button_homepage"));
        webElement.sendKeys(Keys.ENTER);

        webElement = driver.findElement(By.partialLinkText("Speed "));

        assertNotNull(webElement.getText());
    }

    @Test
    public void getFirstTest() {
        WebElement webElement = driver.findElement(By.cssSelector("#search_form_input_homepage"));
        webElement.sendKeys("Test");
        webElement = driver.findElement(By.id("search_button_homepage"));
        webElement.sendKeys(Keys.ENTER);

        webElement = driver.findElement(By.xpath("//*[@id=\"r1-0\"]/div/h2/a[1]"));

        assertNotNull(webElement.getText());
    }

    @Test
    public void getThirdTest() {
        WebElement webElement = driver.findElement(By.cssSelector("#search_form_input_homepage"));
        webElement.sendKeys("Test");
        webElement = driver.findElement(By.id("search_button_homepage"));
        webElement.sendKeys(Keys.ENTER);

        webElement = driver.findElement(By.xpath("//*[@id=\"r1-2\"]/div/h2/a[1]"));

        assertNotNull(webElement.getText());
    }

    @Test
    public void searchItemDoesNotExistTest() {
        assertThrows(NoSuchElementException.class, () -> {
            driver.findElement(By.linkText("Brak danego elementu"));
        });
    }

}
