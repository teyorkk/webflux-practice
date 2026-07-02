package dev.moises.marketstream.dto;

import java.math.BigDecimal;

public record MarketPriceTrick(String assetSymbol, BigDecimal price,
                               BigDecimal change24h, Instant timestamp
) {
}
