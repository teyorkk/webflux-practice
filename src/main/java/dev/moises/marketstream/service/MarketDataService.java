package dev.moises.marketstream.service;


import dev.moises.marketstream.dto.MarketPriceTrick;
import dev.moises.marketstream.exception.ApiDownException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketDataService {
    private final WebClient webClient;
    private Flux<MarketPriceTrick> sharedMarketStream;
    @Value("${api.uri}")
    private String apiUri;

    @PostConstruct
    public void initStream() {
        this.sharedMarketStream = Flux.interval(Duration.ofSeconds(3))
                .onBackpressureDrop()
                .flatMap(tick -> fetchBitcoinPrice())
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(3))
                        .onRetryExhaustedThrow(((retryBackoffSpec,
                                                 retrySignal) ->
                                new ApiDownException(503, "API is down"))))
                .share();

    }

    public Flux<MarketPriceTrick> getLivePrices() {
        return this.sharedMarketStream;
    }

    private Mono<MarketPriceTrick> fetchBitcoinPrice() {
        return webClient.get()
                .uri(apiUri)
                .retrieve()
                //thanks gpt here lmao note: transform JSON to map
                .bodyToMono(new ParameterizedTypeReference<Map<String, Map<String, Object>>>() {
                })
                .map(res -> {
                    Map<String, Object> btcData = res.get("bitcoin");
                    BigDecimal price = new BigDecimal(btcData.get("usd").toString());
                    BigDecimal change = new BigDecimal(btcData.get("usd_24h_change").toString());
                    log.debug("FETCHED BTC Price:${}", price);

                    return new MarketPriceTrick("BTC", price, change, Instant.now());
                });
    }
}
