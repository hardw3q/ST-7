package ru.unn.st7.task;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Files;
import java.nio.file.Path;

public class WeatherForecastTask {
    private static final double LATITUDE = 56.0;
    private static final double LONGITUDE = 44.0;
    private static final String OUTPUT_FILE = "result/forecast.txt";

    private final WebDriver driver;

    public WeatherForecastTask(WebDriver driver) {
        this.driver = driver;
    }

    public void execute() throws Exception {
        driver.get(buildUrl());
        String jsonStr = driver.findElement(By.tagName("pre")).getText();
        String table = buildTable(jsonStr);
        System.out.println(table);
        Files.writeString(Path.of(OUTPUT_FILE), table);
        System.out.println("Прогноз сохранён в " + OUTPUT_FILE);
    }

    private static String buildUrl() {
        return new StringBuilder("https://api.open-meteo.com/v1/forecast")
                .append("?latitude=").append(LATITUDE)
                .append("&longitude=").append(LONGITUDE)
                .append("&hourly=temperature_2m,rain")
                .append("&current=cloud_cover")
                .append("&timezone=Europe%2FMoscow")
                .append("&forecast_days=1")
                .append("&wind_speed_unit=ms")
                .toString();
    }

    static String buildTable(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);
        JSONObject hourly = (JSONObject) obj.get("hourly");
        JSONArray times = (JSONArray) hourly.get("time");
        JSONArray temps = (JSONArray) hourly.get("temperature_2m");
        JSONArray rains = (JSONArray) hourly.get("rain");

        String sep = "+-----+----------------------+--------------------+---------------+";
        String headerFmt = "| %-3s | %-20s | %-18s | %-13s |";
        String rowFmt    = "| %-3d | %-20s | %-18s | %-13s |";

        double minTemp = Double.MAX_VALUE;
        double maxTemp = -Double.MAX_VALUE;

        StringBuilder sb = new StringBuilder();
        sb.append(sep).append(System.lineSeparator());
        sb.append(String.format(headerFmt, "№", "Дата/время", "Температура (°C)", "Осадки (мм)"))
          .append(System.lineSeparator());
        sb.append(sep).append(System.lineSeparator());

        for (int i = 0; i < times.size(); i++) {
            double t = ((Number) temps.get(i)).doubleValue();
            if (t < minTemp) minTemp = t;
            if (t > maxTemp) maxTemp = t;
            sb.append(String.format(rowFmt, i + 1, times.get(i), temps.get(i), rains.get(i)))
              .append(System.lineSeparator());
        }

        sb.append(sep).append(System.lineSeparator());
        sb.append(String.format("Мин. температура: %.1f °C   Макс. температура: %.1f °C%n",
                minTemp, maxTemp));
        return sb.toString();
    }
}
