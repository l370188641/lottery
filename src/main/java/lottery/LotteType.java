package lottery;

public enum LotteType {
    daletou, shuangseqiu, qixingcai;

    public static LotteType getLotteType(String name) {
        if (daletou.name().equals(name)) {
            return daletou;
        } else if (shuangseqiu.name().equals(name)) {
            return shuangseqiu;
        } else if (qixingcai.name().equals(name)) {
            return qixingcai;
        } else {
            return null;
        }
    }
}
