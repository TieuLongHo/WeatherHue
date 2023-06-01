package org.WeatherLight.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherApiClient {

    String apiKey;
    String location;
    int days;
    public WeatherApiClient(final String apikey, final String location, final int days) {
        this.apiKey = apikey;
        this.location = location;
        this.days = days;
    }
    public List<String> getWeather() {
        List<String> weatherConditions = new ArrayList<>();
        try {
            String apiUrl = "https://api.weatherapi.com/v1/forecast.json?q=" + location + "&days=" + days + "&key=" + apiKey;
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());

            JSONArray forecastDays = jsonObject.getJSONObject("forecast").getJSONArray("forecastday");
            for (int i = 0; i < forecastDays.length(); i++) {
                JSONObject forecastDay = forecastDays.getJSONObject(i);
                JSONArray hourArray = forecastDay.getJSONArray("hour");
                for (int j = 0; j < hourArray.length(); j++) {
                    JSONObject hour = hourArray.getJSONObject(j);
                    String condition = hour.getJSONObject("condition").getString("text");
                    weatherConditions.add(condition);
                }
            }


            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherConditions;
    }

    public static void main(String[] args) {
        WeatherApiClient weatherApiClient = new WeatherApiClient(System.getenv("API_KEY"), System.getenv("LOCATION"), 4);
        List<String> weather = weatherApiClient.getWeather();
        System.out.println(weather);
    }
}
