package mandresy.immobilisation.asset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Type d'ammortissement d'un actif immobilisé
 *
 * @author Mandresy
 */
@Getter
@RequiredArgsConstructor
public enum DeprecationType {
    LINEAR("linear"),
    DEGRESSIVE("degressive");

    private final String deprecationType;

    /**
     * Vérifier s'une type d'ammortissement est valide
     * @param s Type d'ammortissement à vérifier
     * @return <code>true</code> si le type d'ammortissement entré est valide
     */
    public static boolean isValidDeprecationType(String s) {
        return Arrays.stream(DeprecationType.values())
                .anyMatch( d ->  s.equalsIgnoreCase(d.getDeprecationType()) );
    }
}
