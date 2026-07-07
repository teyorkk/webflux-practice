package dev.moises.marketstream.repository;

import dev.moises.marketstream.entity.PortfolioAsset;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
@Repository
public interface PortfolioAssetRepository
        extends ReactiveCrudRepository<PortfolioAsset, Long> {
    Mono<PortfolioAsset> findByUserIdAndAssetSymbol(Long userId,
                                                    String assetSymbol);

}
