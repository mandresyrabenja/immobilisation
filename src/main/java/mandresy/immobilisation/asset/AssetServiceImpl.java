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
    public List<Asset> searchAsset(String keyword) {
        return assetRepository.findByNameContainsIgnoreCase(keyword);
    }

    @Override
    public List<AssetDeprecation> getAssetDeprecation(BigDecimal id) {
        Asset asset = assetRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("Aucun actif n'a %d comme ID", id)) );

        // Contrôle unitaire du type d'ammortissement de l'actif immobilisé
        if(!DeprecationType.isValidDeprecationType(asset.getDeprecationType())) {
            throw new IllegalStateException(
                    "Type d'ammortissement \"" + asset.getDeprecationType() + "\" invalide. " +
                    "Il doît être \"linear\" pour linéaire ou \"degressive\" pour degréssif."
            );
        }

        if("linear".equalsIgnoreCase(asset.getDeprecationType()) )
            return getLinearDeprecation(asset);
        else
            return getDegressiveDeprecation(asset);
    }

    /***
     * Calculer le tableau d'ammortissement degressif d'un actif immobilisé
     * @param asset Actif immobilisé
     * @return Tableau d'ammortissement degressif de l'actif immobilisé
     */
    private List<AssetDeprecation> getDegressiveDeprecation(Asset asset) {
        // Liste des ammortissements annuels de l'actif immobilisé
        List<AssetDeprecation> assetDeprecations = new Vector<>();

        // Calcul du nombre d'années en dégressif et du nombre d'années en linéaire
        double linearYearNb = 0;
        if(asset.getUsage() <= 5) linearYearNb = 2;
        else if(asset.getUsage() <= 9) linearYearNb = 3;
        else if(asset.getUsage() <= 11) linearYearNb = 4;
        else if(asset.getUsage() <= 13) linearYearNb = 5;
        else if(asset.getUsage() <= 15) linearYearNb = 6;
        else if(asset.getUsage() <= 18) linearYearNb = 7;
        else linearYearNb = 8;
        double degressiveYearNb = asset.getUsage() - linearYearNb;

        double firstNetBookValue = asset.getPurchasePrice();
        int currentYear = asset.getCommissioningDate().getYear();

        // Les premières années, on applique un amortissement dégressif
        double coeff = 1;
        if(asset.getUsage() < 5) coeff = 1.25;
        else if(asset.getUsage() <= 6) coeff = 1.75;
        else coeff = 2.25;

        double firstMonth = asset.getCommissioningDate().getMonthValue() - 1;
        while(degressiveYearNb > 0){
            // La première et dernière année, je ne pourrai peut-être pas amortir sur 12 mois
            double currentMonth = Math.min(12, 12 - firstMonth);

            double deprecationValue = (firstNetBookValue / asset.getUsage()) * (currentMonth / 12) * coeff;
            double yearEndNetbookValue = firstNetBookValue - deprecationValue;

            assetDeprecations.add(new AssetDeprecation(currentYear, deprecationValue, yearEndNetbookValue) );

            // Positionnement des valeurs pour l'année suivante
            firstMonth = 0; // A partir de la 2ème année on commence forcément en Janvier
            currentYear++;
            firstNetBookValue = yearEndNetbookValue;
            degressiveYearNb--;
        }

        // Les dernières années, on revient sur un amortissement linéaire
        // Le montant de l'amortissement est le même sur toutes les années (même la dernière)
        double yearlyLinearDeprecation = firstNetBookValue / linearYearNb;
        while(linearYearNb > 0){
            double yearEndNetbookValue = firstNetBookValue - yearlyLinearDeprecation;
            assetDeprecations.add( new AssetDeprecation(currentYear, yearlyLinearDeprecation,yearEndNetbookValue) );

            // Positionnement des valeurs pour l'année suivante
            currentYear++;
            firstNetBookValue = yearEndNetbookValue;
            linearYearNb--;
        }

        return assetDeprecations;
    }

    /**
     * Calculer le tableau d'ammortissement linéaire d'un actif immobilisé
     * @param asset Actif immobilisé
     * @return Tableau d'ammortissement de l'actif immobilisé
     */
    private List<AssetDeprecation> getLinearDeprecation(Asset asset) {
        // Liste des ammortissements annuels de l'actif immobilisé
        List<AssetDeprecation> assetDeprecations = new Vector<>();
        // Année actuel de l'ammortissement de l'actif immobilisé
        int year = asset.getCommissioningDate().getYear();

        // Mois d'activité du première année de l'actif immobilisé = 12mois - numero du mois de mise en service + 1
        // parce que le mois de mise en service est déjà un mois d'activité
        double firstYearActivityMonth = 13 - asset.getCommissioningDate().getMonthValue();

        // Valeur comptable actuel de l'actif immobilisé
        double netBookValue = asset.getPurchasePrice();

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
