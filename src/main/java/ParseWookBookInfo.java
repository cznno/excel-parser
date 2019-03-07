import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author cznno
 * Date: 2019/3/7
 */
public class ParseWookBookInfo {

    public static List<Sheet> parse(ZipInputStream zis) throws XMLStreamException {
        List<Sheet> sheetList = new ArrayList<>();
        XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(zis);
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                if (((StartElement) event).getName().getLocalPart().equals("sheet")) {
                    Attribute name = ((StartElement) event).getAttributeByName(new QName("name"));
                    Attribute sheetId = ((StartElement) event).getAttributeByName(new QName("sheetId"));
                    sheetList.add(new Sheet(name.getValue(), sheetId.getValue()));
                }
            }
        }
        return sheetList;
    }
}
