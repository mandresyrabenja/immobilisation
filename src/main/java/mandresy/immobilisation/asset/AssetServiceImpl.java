package mandresy.immobilisation.asset;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;

/**
 * Une implémentation des services liées à l'entité Asset
 *
 * @author Mandresy
 */
@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    @Override
    public void createAsset(Asset asset) {
        // Le date de d'achat de l'actif doît être avant son date de mise en service
        if (Period.between(asset.getPurchaseDate(), asset.getCommissioningDate()).isNegative()) {
            throw new IllegalStateException(
                    String.format(
                            "Le date d'acquisition(%s) doît être avant le date de mise en service(%s)",
                            asset.getPurchaseDate().toString(),
                            asset.getCommissioningDate().toString()
                    )
            );
        }

        // Le type d'ammortissement doît être linéaire ou regressif
        if( !DeprecationType.isValidDeprecationType(asset.getDeprecationType()) ) {
            throw new IllegalStateException(
                "Type d'ammortissement \"" + asset.getDeprecationType() + "\" invalide. " +
                "Il doît être \"linear\" pour linéaire ou \"regressive\" pour regressif."
            );
        }

        assetRepository.save(asset);
    }

    @Override
    public List<Asset> listAsset(int pageNumber, int pageSize) {
        return null;
    }
}
