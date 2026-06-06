package ru.unn.st7.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
    // Укажите путь к chromedriver.exe, например: "C:/Users/имя/Downloads/chromedriver.exe"
    // Если оставить пустой строкой — Selenium Manager (4.6+) скачает драйвер автоматически
    private static final String DRIVER_PATH = "";

    public static WebDriver create() {
        if (!DRIVER_PATH.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
        }
        return new ChromeDriver();
    }
}
