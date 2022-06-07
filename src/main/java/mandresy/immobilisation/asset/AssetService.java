package mandresy.immobilisation.asset;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service d'accès au base de données de l'entité Asset
 *
 * @author Mandresy
 */
public interface AssetService {
    void createAsset(Asset asset);

    List<Asset> listAsset(int pageNumber, int pageSize);

    /**
     * Avoir le tableau d'ammortissement d'un actif immobilisé
     * @param id ID de l'actif immobilisé
     * @return Le tableau d'ammortissement d'un actif immobilisé
     */
    List<AssetDeprecation> getAssetDeprecation(BigDecimal id);
}