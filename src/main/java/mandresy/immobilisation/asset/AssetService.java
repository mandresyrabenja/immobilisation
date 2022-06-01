package mandresy.immobilisation.asset;

import java.util.List;

/**
 * Service d'accès au base de données de l'entité Asset
 *
 * @author Mandresy
 */
public interface AssetService {
    void createAsset(Asset asset);

    List<Asset> listAsset(int pageNumber, int pageSize);
}
