package lottery;

import java.util.Arrays;

public class LotteBean {
    private int rows;
    private String code;
    private String info;
    private LotteData[] data;

    public LotteBean() {
    }

    public LotteBean(int rows, String code, String info, LotteData[] data) {
        this.rows = rows;
        this.code = code;
        this.info = info;
        this.data = data;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LotteData[] getData() {
        return data;
    }

    public void setData(LotteData[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LotteBean{" +
                "rows=" + rows +
                ", code='" + code + '\'' +
                ", info='" + info + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
