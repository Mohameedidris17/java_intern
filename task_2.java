package java_intern;
import java.util.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class task_2{
    private static final String API_KEY = "YOUR_API_KEY"; // enter your API key 
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        String city = "London";
        try {
            String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric"; 

            URL url = new URL(urlString);
            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int statusCode = connection.getResponseCode();
            if (statusCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                int i;
                while ((i = reader.read()) != -1) {
                    response.append((char) i);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                JSONObject main = jsonResponse.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double pressure = main.getDouble("pressure");
                int humidity = main.getInt("humidity");

                JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");

                System.out.println("Weather Data for " + city + ":");
                System.out.println("Temperature: " + temperature + "°C");
                System.out.println("Pressure: " + pressure + " hPa");
                System.out.println("Humidity: " + humidity + "%");
                System.out.println("Description: " + description);
            } else {
                System.out.println("Error: Unable to fetch weather data. HTTP status code: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
