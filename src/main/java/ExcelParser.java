import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author cznno
 * Date: 2019/3/7
 */
public class ExcelParser {

    private Map<String, Sheet> sheetMap;

    private ZipFile zipFile;

    public ExcelParser() {
    }

    public void load(InputStream stream) throws IOException, XMLStreamException {

        File zip = File.createTempFile("excel-zip", "zip");
        Files.copy(stream, zip.toPath(), REPLACE_EXISTING);
        this.zipFile = new ZipFile(zip);


        ZipEntry entry = this.zipFile.getEntry("xl/workbook.xml");
        this.sheetMap = ParseWorkBookInfo.parse(this.zipFile.getInputStream(entry));

      /*  if (true) return;

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
        zis.close();*/
    }

    public void readSheet(String sheetName) throws IOException, XMLStreamException {
        ZipEntry entry = this.zipFile.getEntry("xl/worksheets/sheet" + sheetMap.get(sheetName).getSheetId() + ".xml");
        XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(this.zipFile.getInputStream(
            entry));

        boolean cellValueFound = false;

        List<List<String>> row = new LinkedList<>();
        List<String> column = new ArrayList<>();
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement se = event.asStartElement();
                if (se.getName().getLocalPart().equalsIgnoreCase("row")) {
                    row.add(column);
                    column = new LinkedList<>();
                } else if (se.getName().getLocalPart().equalsIgnoreCase("v")) {
                    cellValueFound = true;
                }
//                if (se.getName().getLocalPart().equals("sheet")) {
//                    Attribute name = se.getAttributeByName(new QName("name"));
//                    Attribute sheetId = se.getAttributeByName(new QName("sheetId"));
//                    sheetMap.put(name.getValue(), new Sheet(name.getValue(), sheetId.getValue()));
//                }
            } else if (event.isCharacters() && (cellValueFound)) {
                column.add(event.asCharacters().getData());
            }
        }

        for (final List<String> strings : row) {
            System.out.println(strings);
        }
    }
}
