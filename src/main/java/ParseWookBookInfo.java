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
class ParseWookBookInfo {

    static List<Sheet> parse(ZipInputStream zis) throws XMLStreamException {
        List<Sheet> sheetList = new ArrayList<>();
        XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(zis);
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement se = event.asStartElement();
                if (se.getName().getLocalPart().equals("sheet")) {
                    Attribute name = se.getAttributeByName(new QName("name"));
                    Attribute sheetId = se.getAttributeByName(new QName("sheetId"));
                    sheetList.add(new Sheet(name.getValue(), sheetId.getValue()));
                }
            }
        }
        return sheetList;
    }
}
