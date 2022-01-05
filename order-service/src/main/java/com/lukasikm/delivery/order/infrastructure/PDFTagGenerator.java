package com.lukasikm.delivery.order.infrastructure;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.lukasikm.delivery.order.domain.TagGenerator;
import com.lukasikm.delivery.order.domain.exception.PDFGenerationException;
import com.lukasikm.delivery.orderserviceclient.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;

@Component
public class PDFTagGenerator implements TagGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PDFTagGenerator.class);

    @Override
    public byte[] generateTag(OrderDTO order) {
        var document = new Document();
        var output = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, output);
            document.open();

            var details = new Paragraph();
            var fontHeader = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            var fontHeaderCodeDest = FontFactory.getFont(FontFactory.COURIER_BOLD, 28, BaseColor.BLACK);
            var fontBody = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

            details.add(new Paragraph(String.format("Code: %s", order.getId()), fontBody));
            details.add(new Paragraph("Deliver to:", fontHeader));
            details.add(new Paragraph(order.getZoneToCode(), fontHeaderCodeDest));
            details.add(new Paragraph(String.format("City: %s", order.getTo().getCity()), fontBody));
            details.add(new Paragraph(String.format("Street: %s", order.getTo().getStreet()), fontBody));
            details.add(new Paragraph(String.format("Postal code: %s", order.getTo().getPostalCode()), fontBody));
            details.add(new Paragraph(String.format("%n%nWeight: %.2fkg", order.getWeightKg()), fontBody));
            if (order.isFragile()) {
                details.add(new Paragraph("Be careful: Fragile!!!", fontBody));
            }
            document.add(details);

            var qrCode = generateQrCodeImage(order.getId().toString());
            document.add(qrCode);

            document.close();
            return output.toByteArray();
        }
        catch (Exception ex) {
            logger.error(ex.getMessage());
            ex.printStackTrace();

            throw new PDFGenerationException();
        }
    }

    private Image generateQrCodeImage(String barcodeText) throws Exception {
        var barcodeWriter = new QRCodeWriter();
        var bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        var rawImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        var baos = new ByteArrayOutputStream();
        ImageIO.write(rawImage, "png", baos);

        return Image.getInstance(baos.toByteArray());
    }


}
