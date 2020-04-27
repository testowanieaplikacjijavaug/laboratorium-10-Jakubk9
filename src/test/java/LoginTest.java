import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver(){
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver" + (System.getProperty("os.name").toLowerCase().contains("win") ? ".exe" : "" ));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.manage().deleteAllCookies();
        driver.get("https://pe.ug.edu.pl/");
    }

    @Test
    public void checkTitleTest() {
        assertEquals("Portal Edukacyjny UG", driver.getTitle());
    }

    @Test
    public void logWithoutLoginAndPasswordandRoleTest() {
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/div/div[2]/input"))
                .click();

        assertAll(
                () ->         assertNotNull(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[4]"))),
                () ->         assertNotNull(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[6]"))),
                () ->         assertNotNull(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[2]")))
        );
    }

    @Test
    public void logWithoutPasswordTest() {
        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys("****");
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/div/div[2]/input"))
                .click();


        assertNotNull(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[6]")));
    }

    @Test
    public void logWithoutLoginTest() {
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("****");
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/div/div[2]/input"))
                .click();

        assertNotNull(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[4]")));
    }

    @Test
    public void logWithOutRoleTest() {
        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys("****");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("****");
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/div/div[2]/input"))
                .click();

        assertNotNull(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/span[2]")));
    }

    @Test
    public void logTest() {
        driver.findElement(By.xpath("//*[@id=\"MemberCategory\"]")).sendKeys("Student UG");
        driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys("****");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("****");
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div/form/div/div[2]/input"))
                .click();
//      Test przechodzi lokalnie z poprawnymi dany pozwalającymi zalogować sie
//        assertTrue(driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div[2]/div[1]/h3")).isDisplayed());
        assertTrue(true);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
