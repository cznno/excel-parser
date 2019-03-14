import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author cznno
 * Date: 2019/3/7
 */
class WorkBookInfo {

    static Map<String, Sheet> parseSheet(ZipFile zipFile) throws XMLStreamException, IOException {
        ZipEntry entry = zipFile.getEntry("xl/workbook.xml");
        return parseSheet(zipFile.getInputStream(entry));
    }

    private static Map<String, Sheet> parseSheet(InputStream zis) throws XMLStreamException {
        Map<String, Sheet> sheetMap = new HashMap<>();
        XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(zis);
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement se = event.asStartElement();
                if (se.getName().getLocalPart().equals("sheet")) {
                    Attribute name = se.getAttributeByName(new QName("name"));
                    Attribute sheetId = se.getAttributeByName(new QName("sheetId"));
                    sheetMap.put(name.getValue(), new Sheet(name.getValue(), sheetId.getValue()));
                }
            }
        }
        return sheetMap;
    }

    static String[] parseSharedStrings(ZipFile zipFile) throws IOException, XMLStreamException {
        String[] sharedStrings = new String[0];

        ZipEntry sharedStringsEntry = zipFile.getEntry("xl/sharedStrings.xml");
        XMLEventReader xmlEventReader = XMLInputFactory.newInstance()
                                                       .createXMLEventReader(zipFile.getInputStream(sharedStringsEntry));

        int i = 0;
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement se = event.asStartElement();
                if (se.getName().getLocalPart().equalsIgnoreCase("sst")) {
                    int count = Integer.parseInt(se.getAttributeByName(new QName("count")).getValue());
                    sharedStrings = new String[count];
                }
            } else if (event.isCharacters()) {
                try {
                    int length = sharedStrings.length;
                    if (i >= length) {
                        sharedStrings = Arrays.copyOf(sharedStrings, length + (length >> 1));
                    }
                    sharedStrings[i] = event.asCharacters().getData();
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(event.asCharacters().getData());
                }
            }
        }
        return sharedStrings;
    }
}
