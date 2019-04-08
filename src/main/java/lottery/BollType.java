package lottery;

/**
 * 球类型
 */
public enum BollType {
    red(0),blue(1);
    private int num;
    BollType(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
