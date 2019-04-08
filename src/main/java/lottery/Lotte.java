package lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 彩票类，使用LotteFactory生成实例
 */
public abstract class Lotte implements Cloneable {
    // 彩票名
    private String name;
    // 红球个数
    private int redNum;
    // 蓝球个数
    private int blueNum;
    // 红球
    private List<Integer> redList;
    // 蓝球
    private List<Integer> blueList;
    // 中奖规则
    private Map<String, String> bonusMap;
    // 奖金计算器
    private LotteFilter filter;
    // 开奖结果获取地址
    private String url;

    /**
     * 获得中奖金额
     *
     * @param lotte 如果this是开奖号码，那么lotte就是你的号码；如果this是你的号码，那么lotte就是开奖号码
     * @return 奖金
     */
    public String getBonus(Lotte lotte) {
        return Optional.ofNullable(filter.getBonus(this, lotte)).orElse("零");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRedNum() {
        return redNum;
    }

    public void setRedNum(int redNum) {
        this.redNum = redNum;
    }

    public int getBlueNum() {
        return blueNum;
    }

    public void setBlueNum(int blueNum) {
        this.blueNum = blueNum;
    }

    public List<Integer> getRedList() {
        return redList;
    }

    public void setRedList(List<Integer> redList) {
        this.redList = redList;
    }

    public List<Integer> getBlueList() {
        return blueList;
    }

    public void setBlueList(List<Integer> blueList) {
        this.blueList = blueList;
    }

    public Map<String, String> getBonusMap() {
        return bonusMap;
    }

    public void setBonusMap(Map<String, String> bonusMap) {
        this.bonusMap = bonusMap;
    }

    public LotteFilter getFilter() {
        return filter;
    }

    public void setFilter(LotteFilter filter) {
        this.filter = filter;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return (redList+ "+" + blueList).replaceAll("\\[|\\]", "").replace("+null", "");
    }

    @Override
    public Lotte clone() {
        Lotte lotte = null;
        try {
            // 直接浅拷贝，除了号码别的属性都一样，后面再修改号码即可
            lotte = (Lotte)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return lotte;
    }

    @Override
    public String toString() {
        return "Lotte{" +
                "name='" + name + '\'' +
                ", redList=" + redList +
                ", blueList=" + blueList +
                '}';
    }
}
