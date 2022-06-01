package mandresy.immobilisation.deprecationType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import mandresy.immobilisation.asset.Asset;

import javax.persistence.*;
import java.util.Collection;

/**
 * Entité du table contenant les types d'ammortissement des actifs immobilisés
 *
 * @author Mandresy
 */
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class DeprecationType {

    @Id
    @SequenceGenerator(
            name = "sequence_deprecation_type",
            sequenceName = "sequence_deprecation_type"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_deprecation_type"
    )
    private Byte id;

    @Column(nullable = false)
    private String name;

    @org.springframework.data.annotation.Transient
    @OneToMany(mappedBy = "deprecationType")
    @JsonManagedReference("asset_deprecation")
    private Collection<Asset> assets;

}
