package mandresy.immobilisation.http;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

/**
 * Une classe utilisée pour encapsuler la réponse HTTP d'une requêtte HTTP
 *
 * @author Mandresy
 */
@Data
@RequiredArgsConstructor
public class HttpReponse {
    private LocalDate date;
    // Status HTTP: 404, 200, ...
    private int status;
    private boolean error;
    private String msg;
}
