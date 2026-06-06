package ru.unn.st7.task;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IpAddressTask {
    private static final String IPIFY_URL = "https://api.ipify.org/?format=json";

    private final WebDriver driver;

    public IpAddressTask(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() throws Exception {
        driver.get(IPIFY_URL);
        String jsonStr = driver.findElement(By.tagName("pre")).getText();
        String ip = extractIp(jsonStr);
        System.out.println("Ваш IPv4-адрес: " + ip);
    }

    public static String extractIp(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);
        Object ip = obj.get("ip");
        if (ip == null) {
            throw new IllegalStateException("Поле 'ip' не найдено в ответе API");
        }
        return ip.toString();
    }
}
