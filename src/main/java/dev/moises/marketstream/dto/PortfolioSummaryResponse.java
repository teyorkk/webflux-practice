package dev.moises.marketstream.dto;

import java.math.BigDecimal;
import java.util.List;

public record PortfolioSummaryResponse(
        String username,
        BigDecimal fiatBalance,
        List<AssetPosition> assets

) {
    public record AssetPosition(String assetSymbol, BigDecimal quantity) {
    }
}
