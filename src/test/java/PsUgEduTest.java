import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PsUgEduTest {

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
        Thread.sleep(5000);
        driver.get("https://ps.ug.edu.pl/");
    }

    @Test
    public void countLinksTest() {
        List<WebElement> list = driver.findElements(By.xpath(".//a"));
        assertEquals(16, list.size());
    }

    @Test
    public void countImages() {
        List<WebElement> list = driver.findElements(By.xpath(".//img"));
        assertEquals(8, list.size());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void LinksTest() throws InterruptedException {
        String startTitle = driver.getTitle();
        List<String> hrefs = driver.findElements(By.xpath("//a[@href and string-length(@href)!=0 ]"))
                .stream()
                .map(x -> x.getAttribute("href"))
                .filter(x -> !x.endsWith("xml"))
                .filter(x -> !x.startsWith("mailto"))
                .collect(Collectors.toList());

        for ( String href : hrefs ) {
            driver.get(href);
            driver.navigate()
                    .back();
        }
        assertEquals(startTitle, driver.getTitle());
    }

    @Test
    public void FormTest() {
        driver.get("https://inf.ug.edu.pl/sq/src/login.php");
        List<WebElement> list = driver.findElement(By.xpath("//form")).findElements(By.xpath("./*"));
        assertEquals(1,list.size());
    }

}
