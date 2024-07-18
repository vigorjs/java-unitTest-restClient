package enigma.customer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TimeSeriesDailyDTO {
    // Getters and Setters
    @JsonProperty("Meta Data")
    private MetaDataDTO metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, TimeSeriesDataDTO> timeSeries;
}