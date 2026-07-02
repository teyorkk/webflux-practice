package dev.moises.marketstream.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record MarketPriceTrick(String assetSymbol, BigDecimal price,
                               BigDecimal change24h, Instant timestamp
) {
}
