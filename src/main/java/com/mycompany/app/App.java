package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Selenium Manager (4.6+) automatically manages ChromeDriver.
        // If auto-detection fails, uncomment and set the path:
        // System.setProperty("webdriver.chrome.driver", "C:/path/to/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        try {
            // Task 1: password generator
            System.out.println("=== Задание 1: Генератор паролей ===");
            webDriver.get("https://www.calculator.net/password-generator.html");

            // Wait for page body to load, then give JS time to generate the password
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            Thread.sleep(2000);

            // Try input[type='text'], textarea, then JS fallback
            String password = null;

            // JS: find password — leaf element text, no spaces, len 8-50, with at least one digit or symbol
            Object result = ((JavascriptExecutor) webDriver).executeScript(
                "var specPat = /[0-9!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>?\\/\\\\`~]/;" +
                "var all = document.querySelectorAll('*');" +
                "for(var i=0;i<all.length;i++){" +
                "  if(all[i].children.length>0) continue;" +
                "  var v=(all[i].value||all[i].textContent||'').trim();" +
                "  if(v.length>=8&&v.length<=50&&!/\\s/.test(v)&&specPat.test(v)) return v;" +
                "} return null;"
            );
            if (result != null) password = result.toString();

            if (password != null) {
                System.out.println("Сгенерированный пароль: " + password);
            } else {
                System.out.println("Пароль не найден на странице.");
            }

            // Task 2: IP address
            System.out.println("\n=== Задание 2: IP-адрес ===");
            Task2.run(webDriver);

            // Task 3: Weather forecast
            System.out.println("\n=== Задание 3: Прогноз погоды ===");
            Task3.run(webDriver);

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e.toString());
        } finally {
            webDriver.quit();
        }
    }
}
