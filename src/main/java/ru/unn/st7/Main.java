package ru.unn.st7;

import org.openqa.selenium.WebDriver;
import ru.unn.st7.config.WebDriverFactory;
import ru.unn.st7.task.IpAddressTask;
import ru.unn.st7.task.PasswordGeneratorTask;
import ru.unn.st7.task.WeatherForecastTask;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = WebDriverFactory.create();
        try {
            System.out.println("=== Задание 1: Генератор паролей ===");
            new PasswordGeneratorTask(driver).execute();

            System.out.println("\n=== Задание 2: IP-адрес ===");
            new IpAddressTask(driver).execute();

            System.out.println("\n=== Задание 3: Прогноз погоды ===");
            new WeatherForecastTask(driver).execute();
        } catch (Exception e) {
            System.err.println("Ошибка при выполнении задания: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
