package mandresy.immobilisation.asset;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    @JsonProperty("purchaseDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @Column(nullable = false)
    @JsonProperty("commissioningDate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate commissioningDate;

    @Column(nullable = false)
    private byte usage;

    @Column(nullable = false)
    private String deprecationType;

}
