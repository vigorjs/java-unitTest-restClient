package enigma.customer.service;

import enigma.customer.dto.TimeSeriesDailyDTO;


public interface AlphaVantageService {
    TimeSeriesDailyDTO getAllDataFromAlphaVantage();
}
