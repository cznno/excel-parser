import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cznno
 * Date: 2019/3/7
 */
class ParseWorkBookInfo {

    static Map<String, Sheet> parse(InputStream zis) throws XMLStreamException {
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
}
