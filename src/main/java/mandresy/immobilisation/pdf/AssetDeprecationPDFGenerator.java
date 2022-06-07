package mandresy.immobilisation.pdf;


import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import mandresy.immobilisation.asset.AssetDeprecation;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Generateur de PDF pour le tableau d'ammortissement d'un actif
 *
 * @author Mandresy
 */
@AllArgsConstructor
public class AssetDeprecationPDFGenerator {

    private List<AssetDeprecation> deprecations;
    private BigDecimal assetId;

    public void generate(HttpServletResponse response) throws DocumentException, IOException {
        // Création de l'objet document
        Document document = new Document(PageSize.A4);
        // recuperation du document et écriture du reponse dans le OutputStream
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Police des caractères
        Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTiltle.setSize(20);
        // Creation de l'objet contenant le paragraphe
        Paragraph paragraph = new Paragraph("Tableau d'ammortissement de l'article numero " + assetId, fontTiltle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        // Ajout dans le document
        document.add(paragraph);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 4, 4, 4 });
        table.setSpacingBefore(5);
        // Creation du header du table
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GREEN);
        cell.setPadding(5);
        // Style des caractères
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Année", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Montant de l'amortissement", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Valeur nette comptable au 31/12", font));
        table.addCell(cell);

        for (AssetDeprecation deprecation : deprecations) {
            table.addCell(String.valueOf(deprecation.getYear()));
            table.addCell(String.valueOf(deprecation.getDeprecationValue()));
            table.addCell(String.valueOf(deprecation.getNetBookValue()));
        }
        // Ajout au document
        document.add(table);
        document.close();
    }
}