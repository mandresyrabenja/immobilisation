package mandresy.immobilisation.asset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * JPA Repository de l'entité Asset
 *
 * @author Mandresy
 */
public interface AssetRepository extends JpaRepository<Asset, BigDecimal> {
}
