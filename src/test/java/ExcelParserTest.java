import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.*;

/**
 * @author cznno
 * Date: 2019/3/7
 */
class ExcelParserTest {
    @Test
    void readFile() throws IOException, XMLStreamException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("test-template.xlsx");
        new ExcelParser().read(stream);
    }
}
