package mandresy.immobilisation.asset;

import mandresy.immobilisation.http.HttpReponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlleur HTTP de l'entité Asset
 *
 * @author Mandresy
 */
@RestController
@RequestMapping("api/v1/assets")
public class AssetController {

    @PostMapping
    public HttpReponse createAsset(@RequestBody Asset asset) {
        return null;
    }

}
