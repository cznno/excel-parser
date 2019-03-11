import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author cznno
 * Date: 2019/3/7
 */
public class ExcelParser {

    private List<Sheet> sheetList;

    public ExcelParser() {
        this.sheetList = new ArrayList<>();
    }

    public void read(InputStream stream) throws IOException, XMLStreamException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(stream);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            System.out.println(zipEntry.getName());
            if (zipEntry.getName().equals("xl/workbook.xml")) {
//                sheetList = ParseWookBookInfo.parse(zis);
            }

            if (zipEntry.getName().equals("xl/worksheets/sheet1.xml")) {
                XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(zis);
                while (xmlEventReader.hasNext()) {
                    XMLEvent event = xmlEventReader.nextEvent();
                    if (event.isCharacters()) {
                        Characters se = event.asCharacters();
//                        System.out.println(se.getData());
                    }
                }
            }

//            zis.closeEntry();
        }
        zis.close();
    }
}
