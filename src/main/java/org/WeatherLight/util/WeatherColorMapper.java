package org.WeatherLight.util;
import java.util.*;

public class WeatherColorMapper {
    public int[] getColor(List<String> weatherConditions) {
        // Create a map to store the weather conditions and their corresponding colors
        Map<String, int[]> colorMap = new HashMap<>();

        // Add weather conditions and their colors to the map
        colorMap.put("Clear", new int[] {255, 255, 0}); // Yellow
        colorMap.put("Sunny", new int[] {255, 255, 0}); // Yellow
        colorMap.put("Partly cloudy", new int[] {192, 192, 192}); // Light Gray
        colorMap.put("Cloudy", new int[] {64, 64, 64}); // Dark Gray
        colorMap.put("Overcast", new int[] {128, 128, 128}); // Gray
        colorMap.put("Mist", new int[] {128, 128, 128}); // Gray
        colorMap.put("Patchy rain possible", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Patchy snow possible", new int[] {255, 255, 255}); // White
        colorMap.put("Patchy sleet possible", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Patchy freezing drizzle possible", new int[] {192, 192, 192}); // Light Gray
        colorMap.put("Thundery outbreaks possible", new int[] {128, 0, 128}); // Purple
        colorMap.put("Blowing snow", new int[] {255, 255, 255}); // White
        colorMap.put("Blizzard", new int[] {255, 255, 255}); // White
        colorMap.put("Fog", new int[] {128, 128, 128}); // Gray
        colorMap.put("Freezing fog", new int[] {192, 192, 192}); // Light Gray
        colorMap.put("Patchy light drizzle", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Light drizzle", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Freezing drizzle", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Heavy freezing drizzle", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Patchy light rain", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Light rain", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Moderate rain at times", new int[] {0, 0, 255}); // Blue
        colorMap.put("Moderate rain", new int[] {0, 0, 255}); // Blue
        colorMap.put("Heavy rain at times", new int[] {0, 0, 139}); // Dark Blue
        colorMap.put("Heavy rain", new int[] {0, 0, 139}); // Dark Blue
        colorMap.put("Light freezing rain", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Moderate or heavy freezing rain", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Light sleet", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Moderate or heavy sleet", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Patchy light snow", new int[] {255, 255, 255}); // White
        colorMap.put("Light snow", new int[] {255, 255, 255}); // White
        colorMap.put("Patchy moderate snow", new int[] {255, 255, 255}); // White
        colorMap.put("Moderate snow", new int[] {255, 255, 255}); // White
        colorMap.put("Patchy heavy snow", new int[] {255, 255, 255}); // White
        colorMap.put("Heavy snow", new int[] {255, 255, 255}); // White
        colorMap.put("Ice pellets", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Light rain shower", new int[] {0, 0, 255}); // Blue
        colorMap.put("Moderate or heavy rain shower", new int[] {0, 0, 139}); // Dark Blue
        colorMap.put("Torrential rain shower", new int[] {0, 0, 139}); // Dark Blue
        colorMap.put("Light sleet showers", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Moderate or heavy sleet showers", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Light snow showers", new int[] {255, 255, 255}); // White
        colorMap.put("Moderate or heavy snow showers", new int[] {255, 255, 255}); // White
        colorMap.put("Light showers of ice pellets", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Moderate or heavy showers of ice pellets", new int[] {173, 216, 230}); // Light Blue
        colorMap.put("Patchy light rain with thunder", new int[] {0, 0, 255}); // Blue
        colorMap.put("Moderate or heavy rain with thunder", new int[] {0, 0, 139}); // Dark Blue
        colorMap.put("Patchy light snow with thunder", new int[] {255, 255, 255}); // White
        colorMap.put("Moderate or heavy snow with thunder", new int[] {255, 255, 255}); // White


        int[] colorList = new int[(weatherConditions.size() + 1) * 6];
        colorList[0] = 0;
        colorList[1] = 0;
        colorList[2] = 255;

        int index = 3;

        for (String weatherCondition : weatherConditions) {
            int[] color = colorMap.get(weatherCondition);
            colorList[index++] = color[2];
            colorList[index++] = color[1];
            colorList[index++] = color[0];
        }

        if (colorList.length != 50 * 3) {
            return null;
        }

        return colorList;

    }
}
