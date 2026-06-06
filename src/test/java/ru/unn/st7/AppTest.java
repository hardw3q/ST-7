package ru.unn.st7;

import org.junit.Test;
import ru.unn.st7.task.IpAddressTask;
import ru.unn.st7.task.WeatherForecastTask;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testExtractIpReturnsCorrectValue() throws Exception {
        String json = "{\"ip\":\"192.168.1.1\"}";
        assertEquals("192.168.1.1", IpAddressTask.extractIp(json));
    }

    @Test
    public void testExtractIpThrowsWhenFieldMissing() {
        try {
            IpAddressTask.extractIp("{\"foo\":\"bar\"}");
            fail("Ожидалось исключение при отсутствии поля ip");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("ip"));
        }
    }

    @Test
    public void testBuildTableContainsAllColumns() throws Exception {
        String json = "{\"hourly\":{"
                + "\"time\":[\"2026-06-06T00:00\",\"2026-06-06T01:00\"],"
                + "\"temperature_2m\":[15.5,16.0],"
                + "\"rain\":[0.0,0.1]}}";
        String table = WeatherForecastTask.buildTable(json);
        assertTrue(table.contains("Дата/время"));
        assertTrue(table.contains("Температура"));
        assertTrue(table.contains("Осадки"));
        assertTrue(table.contains("2026-06-06T00:00"));
        assertTrue(table.contains("Мин. температура: 15.5"));
        assertTrue(table.contains("Макс. температура: 16.0"));
    }
}
