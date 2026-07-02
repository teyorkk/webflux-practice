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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_users")
public class User {

    @Id
    private Long id;

    private String username;
    private String email;
    @Column("password_hash")
    private String passwordHash;
    @Column("fiat_balance")
    private BigDecimal fiatBalance;
    @Column("created_at")
    private Instant createdAt;
}
