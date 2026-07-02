package dev.moises.marketstream.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.Instant;


@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Table(name = "_transactions")
public class Transaction {
    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("asset_symbol")
    private String assetSymbol;
    @Column("transaction_type")
    private String transactionType;

    private BigDecimal quantity;
    @Column("price_per_unit")
    private BigDecimal pricePerUnit;
    @Column("total_cost")
    private BigDecimal totalCost;

    private Instant timestamp;
}
