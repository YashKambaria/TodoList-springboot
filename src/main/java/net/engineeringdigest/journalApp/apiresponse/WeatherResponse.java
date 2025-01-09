package net.engineeringdigest.journalApp.apiresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WeatherResponse {
	private Current current;
	
	@Data
	@NoArgsConstructor
	public class Current{
		@JsonProperty("observation_time")
		private String observationTime;
		private int temperature;
		@JsonProperty("weather_descriptions")
		private List<String> weatherDescriptions;
	}
}
