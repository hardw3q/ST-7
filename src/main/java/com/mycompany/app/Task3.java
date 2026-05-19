package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {
    public static void run(WebDriver webDriver) throws Exception {
        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=56&longitude=44"
                + "&hourly=temperature_2m,rain"
                + "&current=cloud_cover"
                + "&timezone=Europe%2FMoscow"
                + "&forecast_days=1"
                + "&wind_speed_unit=ms";

        webDriver.get(url);
        WebElement elem = webDriver.findElement(By.tagName("pre"));
        String jsonStr = elem.getText();

        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonStr);
        JSONObject hourly = (JSONObject) obj.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temps  = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains  = (JSONArray) hourly.get("rain");

        String header    = String.format("%-4s  %-20s  %-18s  %s", "№", "Дата/время", "Температура (°C)", "Осадки (мм)");
        String separator = "-".repeat(62);

        StringBuilder sb = new StringBuilder();
        sb.append(header).append(System.lineSeparator());
        sb.append(separator).append(System.lineSeparator());

        for (int i = 0; i < times.size(); i++) {
            String row = String.format("%-4d  %-20s  %-18s  %s",
                    i + 1,
                    times.get(i),
                    temps.get(i),
                    rains.get(i));
            sb.append(row).append(System.lineSeparator());
        }

        System.out.println(sb.toString());

        try (PrintWriter writer = new PrintWriter(new FileWriter("result/forecast.txt"))) {
            writer.print(sb.toString());
        }
        System.out.println("Прогноз сохранён в result/forecast.txt");
    }
}
