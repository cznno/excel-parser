import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author cznno
 * Date: 2019/3/7
 */
class ExcelParserTest {
    @Test
    void load() throws IOException, XMLStreamException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("test-template.xlsx");
        new ExcelParser().load(stream);
    }

    @Test
    void readSheet() throws IOException, XMLStreamException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("test-template.xlsx");
        ExcelParser excelParser = new ExcelParser();
        excelParser.load(stream);
        excelParser.readSheet("app");
    }
}
