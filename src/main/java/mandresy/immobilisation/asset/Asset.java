package mandresy.immobilisation.asset;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import mandresy.immobilisation.deprecationType.DeprecationType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entité du table contenant les actifs immobilisés
 *
 * @author Mandresy
 */
@Data @AllArgsConstructor @NoArgsConstructor @Builder @ToString
@Entity
public class Asset {

    @Id
    @SequenceGenerator(
            name = "sequence_asset",
            sequenceName = "sequence_asset"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sequence_asset"
    )
    private BigDecimal id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double purchasePrice;

    @Column(nullable = false)
    private LocalDate purchaseDate;

    @Column(nullable = false)
    private LocalDate commissioningDate;

    @Column(nullable = false)
    private byte usage;

    @org.springframework.data.annotation.Transient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deprecation_type_id")
    @JsonBackReference("asset_deprecation")
    private DeprecationType deprecationType;

}
