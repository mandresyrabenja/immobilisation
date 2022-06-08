package mandresy.immobilisation.asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * JPA Repository de l'entité Asset
 *
 * @author Mandresy
 */
public interface AssetRepository extends JpaRepository<Asset, BigDecimal> {

    List<Asset> findByNameContainsIgnoreCase(String keyword);

    @Query("select a from Asset a where upper(a.name) like upper(concat('%', ?1, '%'))" +
            " and a.commissioningDate between ?2 and ?3")
    List<Asset> advancedSearch(String keyword, LocalDate startDate, LocalDate endDate);
}
