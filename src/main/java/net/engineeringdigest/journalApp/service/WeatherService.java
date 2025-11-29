package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.apiresponse.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
	
	@Value("${weather.api.key}")
	private String apikey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AppCache appCache;
	
	@Autowired
	private RedisService redisService;
	
	
	public WeatherResponse getWeather(String city){
		try {
			WeatherResponse weatherResponse=redisService.get("weather_of_"+city,WeatherResponse.class);
			if(weatherResponse!=null){
				return weatherResponse;
			}
			else {
				String finalApi = appCache.APP_CACHE.get("weather_api").replace("<apikey>", apikey).replace("<city>", city);
				ResponseEntity<WeatherResponse> data = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
				redisService.set("weather_of_"+city,data.getBody(),300l);
				return data.getBody();
			}
		} catch (Exception e) {
			// Log and handle the exception
			System.err.println("Error fetching weather data: " + e.getMessage());
			return null; // Or throw a custom exception
		}
	}
}
