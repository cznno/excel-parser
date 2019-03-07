import javax.xml.stream.XMLStreamException;
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
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            if (zipEntry.getName().equals("xl/workbook.xml")) {
                sheetList = ParseWookBookInfo.parse(zis);
            }


            zipEntry = zis.getNextEntry();
        }
        System.out.println();
    }
}
