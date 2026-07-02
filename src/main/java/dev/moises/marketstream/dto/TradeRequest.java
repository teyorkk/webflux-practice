package dev.moises.marketstream.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TradeRequest(
        @NotBlank(message = "Asset symbol is required")
        String assetSymbol,

        @NotBlank(message = "Transaction type is required")
        @Pattern(regexp = "^(BUY|SELL)$", message = "Type must be either BUY or SELL")
        String transactionType,

        @NotNull(message = "Quantity is required")
        @DecimalMin(value = "0.00000001", message = "Quantity must be greater than zero")
        BigDecimal quantity,

        @NotNull(message = "Execution price is required")
        @DecimalMin(value = "0.0001", message = "Price must be realistic")
        BigDecimal executionPrice
) {
}
