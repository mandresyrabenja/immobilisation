package mandresy.immobilisation.asset;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Period;
import java.util.List;
import java.util.Vector;

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
    public List<AssetDeprecation> getAssetDeprecation(BigDecimal id) {
        Asset asset = assetRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Aucun actif n'a %d comme ID", id)) );

        // Liste des ammortissements annuels de l'actif immobilisé
        List<AssetDeprecation> assetDeprecations = new Vector<>();

        // Année actuel de l'ammortissement de l'actif immobilisé
        int year = asset.getCommissioningDate().getYear();

        // Mois d'activité du première année de l'actif immobilisé = 12mois - numero du mois de mise en service + 1
        // parce que le mois de mise en service est déjà un mois d'activité
        double firstYearActivityMonth = 13 - asset.getCommissioningDate().getMonthValue();

        // Valeur comptable actuel de l'actif immobilisé
        double netBookValue = asset.getPurchasePrice();

        // Si l'ammortissement est linéaire
        if("linear".equalsIgnoreCase(asset.getDeprecationType()) ) {
            // Montant de l'ammortissement annuel
            double yearlyDeprecationValue = netBookValue / asset.getUsage();

            // Montant de l'ammortissement durant la première année
            firstYearActivityMonth /= 12;
            double coeff =  firstYearActivityMonth/ (double)asset.getUsage();
            double firstYearDeprecationValue = netBookValue * coeff;
            netBookValue -= firstYearDeprecationValue;

            // Ammortissement du première année
            AssetDeprecation firstYearDeprecation = new AssetDeprecation();
            firstYearDeprecation.setYear(year);
            firstYearDeprecation.setDeprecationValue(firstYearDeprecationValue);
            firstYearDeprecation.setNetBookValue(netBookValue);
            assetDeprecations.add(firstYearDeprecation);

            for(int i = 1; i < asset.getUsage(); i++) {
                // le valeur comptable de l'actif diminue chaque année
                year++;
                netBookValue -= yearlyDeprecationValue;

                // Ammortissement annuel
                AssetDeprecation yearlyDeprecation = new AssetDeprecation();
                yearlyDeprecation.setYear(year);
                yearlyDeprecation.setDeprecationValue(yearlyDeprecationValue);
                yearlyDeprecation.setNetBookValue(netBookValue);
                assetDeprecations.add(yearlyDeprecation);
            }

            // Dernière année
            year++;
            yearlyDeprecationValue = netBookValue;
            netBookValue = 0;

            AssetDeprecation lastYearDeprecation = new AssetDeprecation();
            lastYearDeprecation.setYear(year);
            lastYearDeprecation.setDeprecationValue(yearlyDeprecationValue);
            lastYearDeprecation.setNetBookValue(netBookValue);
            assetDeprecations.add(lastYearDeprecation);
        }

        return assetDeprecations;
    }

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
                "Il doît être \"linear\" pour linéaire ou \"degressive\" pour degréssif."
            );
        }

        assetRepository.save(asset);
    }

    @Override
    public List<Asset> listAsset(int pageNumber, int pageSize) {
        return assetRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

}
