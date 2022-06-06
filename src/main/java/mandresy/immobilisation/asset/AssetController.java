package mandresy.immobilisation.asset;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlleur HTTP de l'entité Asset
 *
 * @author Mandresy
 */
@RestController
@RequestMapping("api/v1/assets")
@RequiredArgsConstructor
@Slf4j
public class AssetController {

    private final AssetService assetService;

    @GetMapping(path = "/{id}/deprecation")
    public List<AssetDeprecation> getAssetDeprecation(@PathVariable BigDecimal id){
        return assetService.getAssetDeprecation(id);
    }

    @PostMapping
    public ResponseEntity<String> createAsset(@RequestBody Asset asset) {

        try{
            assetService.createAsset(asset);
            return new ResponseEntity<>(
                    String.format("Actif immobilisé %s crée avec succès", asset.getName()),
                    HttpStatus.CREATED
            );

        } catch (IllegalStateException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @GetMapping
    public List<Asset> listAssets(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return assetService.listAsset(page, size);
    }

}
