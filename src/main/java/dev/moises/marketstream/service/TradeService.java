package dev.moises.marketstream.service;

import dev.moises.marketstream.dto.TradeRequest;
import dev.moises.marketstream.entity.PortfolioAsset;
import dev.moises.marketstream.entity.Transaction;
import dev.moises.marketstream.exception.InsufficientBalanceException;
import dev.moises.marketstream.repository.PortfolioAssetRepository;
import dev.moises.marketstream.repository.TransactionRepository;
import dev.moises.marketstream.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeService {
  private final UserRepository userRepository;
  private final PortfolioAssetRepository assetRepository;
  private final TransactionRepository transactionRepository;

  @Transactional
  public Mono<Transaction> executeBuyOrder(Long userId, TradeRequest req) {
    BigDecimal totalCost = req.quantity().multiply(req.executionPrice());
    return userRepository.findById(userId)
            .flatMap(user -> {
              if (user.getFiatBalance().compareTo(totalCost) < 0) {
                return Mono.error
                        (new InsufficientBalanceException(402,
                                "Insufficient fiat balance"));
              }
              user.setFiatBalance(user.getFiatBalance().subtract(totalCost));
              return userRepository.save(user);
            })
            .flatMap(savedUser ->
                    assetRepository.findByUserIdAndAssetSymbol(userId, req.assetSymbol())
                            .defaultIfEmpty(PortfolioAsset.builder()
                                    .userId(userId)
                                    .quantity(BigDecimal.ZERO)
                                    .assetSymbol(req.assetSymbol())
                                    .build()
                            )
            ).flatMap(asset -> {
              asset.setQuantity(asset.getQuantity().add(req.quantity()));
              return assetRepository.save(asset);
            })
            .flatMap(savedAsset -> {
              Transaction t = Transaction.builder()
                      .userId(userId)
                      .assetSymbol(req.assetSymbol())
                      .quantity(req.quantity())
                      .pricePerUnit(req.executionPrice())
                      .totalCost(totalCost)
                      .timestamp(Instant.now())
                      .transactionType("BUY")
                      .build();
              return transactionRepository.save(t);
            })
            .doOnSuccess(t -> log.info("Successfully executed BUY order" +
                    "for user {} on asset {}", userId, req.assetSymbol()));
  }
}
