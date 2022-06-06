package mandresy.immobilisation.asset;

import lombok.*;

/**
 * Ammortissement d'un actif immobilisé
 *
 * @author Mandresy
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AssetDeprecation {

    // Année
    int year;
    // Prix d'ammortissement
    double deprecationValue;
    // Valeur net comptable au 31 décembre
    double netBookValue;

}
