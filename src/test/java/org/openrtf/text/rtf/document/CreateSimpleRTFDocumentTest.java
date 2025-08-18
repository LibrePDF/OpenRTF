/*
 * CreateSimpleRTFDocumentTest.java
 */
package org.openrtf.text.rtf.document;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.junit.Test;
import org.openpdf.text.Document;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.Table;
import org.openrtf.text.rtf.RtfWriter2;
import org.openrtf.text.rtf.style.RtfFont;

/**
 * Test creation of a simple RTF document.
 *
 * @author <A HREF="mailto:cursor42@gmx.de">Chris</A>
 */
public class CreateSimpleRTFDocumentTest {

    @Test
    public void createDocument() throws FileNotFoundException {

        final File OUTPUT_FILE = new File("target/CreateSimpleRTFDocumentTest.rtf");

        // Delete output file if it already exists
        if (OUTPUT_FILE.exists()) {
            assertTrue("Could NOT delete existing output file!", OUTPUT_FILE.delete());
        }

        // Create a document using A4 paper format
        Document document = new Document(PageSize.A4);
        RtfWriter2 rtf = RtfWriter2.getInstance(document, new FileOutputStream(OUTPUT_FILE));
        document.open();
        document.setMargins(50, 50, 50, 50);

        // Add a paragraph with some text.
        document.add(new Paragraph("Just a test to write RTF!", new RtfFont("SansSerif", 14, RtfFont.BOLD)));

        // Add a table with 2 columns.
        RtfFont headerFont = new RtfFont("SansSerif", 10, RtfFont.BOLD);
        RtfFont tableFont = new RtfFont("SansSerif", 9);
        int[] aWidths = {10, 10};
        Table table = new Table(2);
        table.setWidth(80);
        table.setWidths(aWidths);
        table.addCell(new Phrase("Column", headerFont));
        table.addCell(new Phrase("Another Column", headerFont));
        table.addCell(new Phrase("Table data", tableFont));
        table.addCell(new Phrase("Other data", tableFont));
        table.addCell(new Phrase("Another row..", tableFont));
        table.addCell(new Phrase("with data", tableFont));
        document.add(table);

        // Write document to OUTPUT_FILE.
        rtf.close();
        assertTrue("RTF document was NOT created!", OUTPUT_FILE.exists());
    }
}
