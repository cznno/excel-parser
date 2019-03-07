/**
 * @author cznno
 * Date: 2019/3/7
 */
public class Sheet {
    private String name;

    private String sheetId;

    public Sheet(String name, String sheetId) {
        this.name = name;
        this.sheetId = sheetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }
}
