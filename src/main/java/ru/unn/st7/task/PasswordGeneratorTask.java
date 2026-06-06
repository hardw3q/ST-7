package ru.unn.st7.task;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordGeneratorTask {
    private static final String URL = "https://www.calculator.net/password-generator.html";
    // Пароль генерируется в <b> внутри div.verybigtext внутри div#resultid
    private static final By PASSWORD_LOCATOR = By.cssSelector("#resultid .verybigtext b");

    private final WebDriver driver;

    public PasswordGeneratorTask(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() {
        driver.get(URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement passwordElem = wait.until(
                ExpectedConditions.visibilityOfElementLocated(PASSWORD_LOCATOR));
        System.out.println("Сгенерированный пароль: " + passwordElem.getText());
    }
}
