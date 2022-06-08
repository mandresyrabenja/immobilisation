package mandresy.immobilisation.asset;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mandresy.immobilisation.pdf.AssetDeprecationPDFGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    @GetMapping("/advanced-search")
    public List<Asset> advancedSearch(@RequestParam String keyword,
                                      @RequestParam(name = "startDate") String SStartDate,
                                      @RequestParam(name = "endDate") String SEndDate) {
        LocalDate startDate = LocalDate.parse(SStartDate);
        LocalDate endDate = LocalDate.parse(SEndDate);

        return assetService.advancedSearch(keyword, startDate, endDate);
    }

    @GetMapping("/{id}/pdf")
    public void generator(
            @PathVariable BigDecimal id,
            HttpServletResponse response) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ammortissement-article-numero-"+id+".pdf";
        response.setHeader(headerKey, headerValue);

        List<AssetDeprecation> deprecations = assetService.getAssetDeprecation(id);
        AssetDeprecationPDFGenerator pdfGenerator = new AssetDeprecationPDFGenerator(deprecations, id);
        pdfGenerator.generate(response);
    }

    @GetMapping(path = "/search")
    public List<Asset> searchAsset(@RequestParam String keyword) {
        return assetService.searchAsset(keyword);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteAsset(@PathVariable BigDecimal id) {
        try{
            assetService.deleteProduct(id);
            String msg = "Actif numero " + id + " effacé avec succès";
            log.info(msg);
            return msg;
        } catch (IllegalStateException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping(path = "/{id}/deprecation")
    public List<AssetDeprecation> getAssetDeprecation(@PathVariable BigDecimal id){
        try {
            return assetService.getAssetDeprecation(id);
        }catch (IllegalStateException e) {
            log.warn(e.getMessage());
            return null;
        }
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
