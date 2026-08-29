import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumFrontendTest {
    private WebDriver driver;
    @BeforeEach
    public void init() {
        driver = new FirefoxDriver();
        driver.get("https://localhost");
    }

    @AfterEach
    public void quit() {
        driver.quit();
    }

    @Test
    public void testThatTitleIsCorrect() {
        assertEquals("web lab 3", driver.getTitle());
    }

    @Nested
    @DisplayName("With Login")
    class WithLogin {
        @BeforeEach
        public void init() {
            WebElement loginTextBox = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/form/label[1]/input"));
            WebElement passwordTextBox = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/form/label[2]/input"));
            WebElement loginButton = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/form/div/button"));

            loginTextBox.sendKeys("1");
            passwordTextBox.sendKeys("1");

            loginButton.click();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        }

        @Test
        public void testLogin() {
            assertEquals("https://localhost/app", driver.getCurrentUrl());
        }
        @Test
        public void testInputtingCorrectHit() {
            var xInputField = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/section[1]/div[1]/div[1]/div/input"));
            var yInputField = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/section[1]/div[1]/div[2]/div/input"));
            var rInputField = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/section[1]/div[1]/div[3]/div/input"));
            var submitButton = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/section[1]/div[2]/button[1]"));

            xInputField.sendKeys("0\n");
            yInputField.sendKeys("0\n");
            rInputField.sendKeys("1\n");
            driver.findElement(By.xpath("/html/body/div[1]/div/main/div/div/section[2]/div/div/div[3]")).click();
            submitButton.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            var actual = driver.findElement(By.xpath("/html/body/div[1]/div/main/div/section/table/tbody/tr[1]/td[4]")).getText();
            assertEquals("Есть пробитие", actual);
        }
    }



}
