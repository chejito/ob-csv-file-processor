import java.util.Arrays;

public class ErrorUser {

    private Integer line;
    private String error;
    private String[] values;

    public ErrorUser(Integer line, String error, String[] values) {
        this.line = line;
        this.error = error;
        this.values = values;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "ErrorUser{" +
                "line=" + line +
                ", error='" + error + '\'' +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
