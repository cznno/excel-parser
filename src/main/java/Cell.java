/**
 * @author cznno
 * Date: 2019/3/13
 */
class Cell {
    private String value;

    private boolean isString;

    public Cell(String value, boolean isString) {
        this.value = value;
        this.isString = isString;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isString() {
        return isString;
    }

    public void setString(boolean string) {
        isString = string;
    }
}
