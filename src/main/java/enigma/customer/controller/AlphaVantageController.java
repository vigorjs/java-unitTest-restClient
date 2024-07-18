package enigma.customer.controller;

import enigma.customer.dto.TimeSeriesDailyDTO;
import enigma.customer.service.AlphaVantageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alpha-vantage")
public class AlphaVantageController {

    private final AlphaVantageService alphaVantageService;

    public AlphaVantageController(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    @GetMapping
    public TimeSeriesDailyDTO getAllDataFromAlphaVantage() {
        TimeSeriesDailyDTO data = alphaVantageService.getAllDataFromAlphaVantage();
        return data;
    }
}
