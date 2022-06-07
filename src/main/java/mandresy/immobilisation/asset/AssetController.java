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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping("/{id}/pdf")
    public void generator(
            @PathVariable BigDecimal id,
            HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
        String currentDateTime = dateFormat.format(new Date());
        String headerkey = "Content-Disposition";
        String headervalue = "attachment; filename=pdf_"+currentDateTime+".pdf";
        response.setHeader(headerkey, headervalue);

        List<AssetDeprecation> deprecations = assetService.getAssetDeprecation(id);
        AssetDeprecationPDFGenerator pdfGeneretor = new AssetDeprecationPDFGenerator(deprecations);
        pdfGeneretor.generate(response);
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
