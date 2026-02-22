package com.practice.selenium.e2e;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegisterE2ETest {

    @LocalServerPort
    private int port;

    static WebDriver driver;
    static WebDriverWait wait;

    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    void completeFormAndSubmit() {

        driver.get("http://localhost:" + port + "/register");

        // Text inputs
        driver.findElement(By.id("name")).sendKeys("Juan Perez");
        driver.findElement(By.id("email")).sendKeys("juan@test.com");
        driver.findElement(By.id("password")).sendKeys("123456");

        // Select dropdown
        Select departmentSelect = new Select(driver.findElement(By.id("department")));
        departmentSelect.selectByValue("IT");

        // Radio
        driver.findElement(By.id("fullTime")).click();

        // Checkboxes
        driver.findElement(By.id("javaSkill")).click();
        driver.findElement(By.id("sqlSkill")).click();

        // Textarea
        driver.findElement(By.id("comments"))
                .sendKeys("Empleado con experiencia en backend");

        // Submit
        driver.findElement(By.id("submitBtn")).click();

        // Espera explícita hasta que aparezca mensaje
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("successMessage"))
        );

        assertEquals("Empleado registrado correctamente", successMessage.getText());
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}
