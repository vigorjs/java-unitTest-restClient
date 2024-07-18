package enigma.customer.service.implementation;

import enigma.customer.dto.TimeSeriesDailyDTO;
import enigma.customer.service.AlphaVantageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class AlphaVantageServiceImpl implements AlphaVantageService {

    private final RestClient restClient;

    @Value("${alphavantage.apikey}")
    private String apiKey;

    public AlphaVantageServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public TimeSeriesDailyDTO getAllDataFromAlphaVantage() {
        try {
            return restClient.get()
                    .uri(UriComponentsBuilder.fromHttpUrl("https://www.alphavantage.co").toUriString(), uriBuilder -> uriBuilder
                            .path("/query")
                            .queryParam("function", "TIME_SERIES_DAILY")
                            .queryParam("symbol", "IBM")
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .body(TimeSeriesDailyDTO.class);
        } catch (RestClientException e) {
            // Handle exceptions
            e.printStackTrace();
            return null;
        }
    }
}
