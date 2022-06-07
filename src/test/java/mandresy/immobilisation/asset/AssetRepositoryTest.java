package mandresy.immobilisation.asset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AssetRepositoryTest {

    @Autowired
    AssetRepository underTest;
    Asset asset;

    @BeforeEach
    void setUp() {
        asset = Asset.builder()
                .id(BigDecimal.valueOf(1))
                .name("foo bar")
                .purchaseDate(LocalDate.now())
                .purchasePrice(1.1)
                .commissioningDate(LocalDate.now())
                .deprecationType("linear")
                .usage((byte) 4)
                .build();
        underTest.save(asset);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldSearchAssetUsingKeyword() {
        //given
        String keyword = "BA";

        //when
        List<Asset> actual = underTest.findByNameContainsIgnoreCase(keyword);

        //then
        assertThat(actual).hasSize(1)
            .contains(asset);
    }
}