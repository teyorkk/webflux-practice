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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "_portfolio_assets")
public class PortfolioAsset {

    @Id
    private Long id;
    @Column("user_id")
    private Long userId;
    @Column("asset_symbol")
    private String assetSymbol;

    private BigDecimal quantity;

    @Column("created_at")
    private Instant createdAt;
}
