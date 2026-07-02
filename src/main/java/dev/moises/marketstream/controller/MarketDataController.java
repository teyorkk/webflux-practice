package dev.moises.marketstream.controller;


import dev.moises.marketstream.dto.MarketPriceTrick;
import dev.moises.marketstream.service.MarketDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/stream")
@Slf4j
@RequiredArgsConstructor
public class MarketDataController {
    private final MarketDataService marketDataService;

    @GetMapping(path = "/prices", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MarketPriceTrick> streamPrices(){
        log.info("New client connected to the price stream");

        return  marketDataService.getLivePrices();
    }
}
