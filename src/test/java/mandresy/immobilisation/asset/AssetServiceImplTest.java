package mandresy.immobilisation.asset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssetServiceImplTest {

    @Mock
    private AssetRepository assetRepository;
    private AssetServiceImpl underTest;
    private Asset asset;

    @BeforeEach
    void setUp() {
        underTest = new AssetServiceImpl(assetRepository);
        asset = Asset.builder()
                .id(BigDecimal.valueOf(1))
                .name("foo")
                .purchaseDate(LocalDate.now())
                .purchasePrice(1.1)
                .commissioningDate(LocalDate.now())
                .deprecationType("linear")
                .usage((byte) 4)
                .build();
    }

    @AfterEach
    void tearDown() {
        asset = null;
    }


    @Test
    @DisplayName("Degressive deprecation calculus")
    void itShouldCalculateAssetYearlyDegressiveDeprecation() {
        //given
        asset = new Asset(
                BigDecimal.valueOf(1L),
                "foo",
                100_000,
                LocalDate.of(2017, Month.JANUARY, 3),
                LocalDate.of(2017, Month.JULY, 1),
                (byte)5,
                "degressive"
        );
        when(assetRepository.findById(any(BigDecimal.class))).thenReturn(Optional.of(asset));
        List<AssetDeprecation> expected = List.of(
                new AssetDeprecation(2017, 17500, 82500 ),
                new AssetDeprecation(2018, 28875 , 53625 ),
                new AssetDeprecation(2019, 18768.75, 34856.25),
                new AssetDeprecation(2020, 17428.125, 17428.125 ),
                new AssetDeprecation(2021, 17428.125, 0)
        );

        //when
        List<AssetDeprecation> actual = underTest.getAssetDeprecation(BigDecimal.valueOf(1L));

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Linear deprecation calculus")
    void itShouldCalculateAssetYearlyLinearDeprecation() {
        //given
        asset = new Asset(
                BigDecimal.valueOf(1L),
                "voiture",
                10_000,
                LocalDate.of(2017, Month.JANUARY, 3),
                LocalDate.of(2017, Month.APRIL, 6),
                (byte)5,
                "linear"
        );
        when(assetRepository.findById(any(BigDecimal.class))).thenReturn(Optional.of(asset));
        List<AssetDeprecation> expected = List.of(
                new AssetDeprecation(2017, 1500.0, 8500.0),
                new AssetDeprecation(2018, 2000.0, 6500.0),
                new AssetDeprecation(2019, 2000.0, 4500.0),
                new AssetDeprecation(2020, 2000.0, 2500.0),
                new AssetDeprecation(2021, 2000.0, 500.0),
                new AssetDeprecation(2022, 500.0, 0.0)
        );

        //when
        List<AssetDeprecation> actual = underTest.getAssetDeprecation(BigDecimal.valueOf(1L));

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void itShouldThrowAnExceptionWhenDeprecationTypeIsNotValid() {
        //given
        asset.setDeprecationType("foo");

        //when
        //then
        assertThatThrownBy(() -> underTest.createAsset(asset))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("ammortissement");
    }

    @Test
    void itShouldThrowAnExceptionWhenPurchaseDateIsAfterCommissioningDate() {
        //given
        asset.setPurchaseDate(LocalDate.of(2022, Month.MARCH, 8));
        asset.setCommissioningDate(LocalDate.of(2021, Month.MARCH, 8));

        //when
        //then
        assertThatThrownBy(() -> underTest.createAsset(asset))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("date d'acquisition");
    }

    @Test
    void itShouldInsertANewAssetToDatabase() {
        //when
        underTest.createAsset(asset);

        //then
        ArgumentCaptor<Asset> argumentCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue())
                .isEqualTo(asset);

    }

}