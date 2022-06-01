package mandresy.immobilisation.asset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

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