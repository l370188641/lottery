package lottery;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用于生成彩票类
 */
public final class LotteFactory {

    private LotteFactory() {
    }

    /**
     * 生成彩票
     *
     * @param lotteType 彩票种类
     * @param code      号码
     * @return 彩票类
     */
    public static Lotte getLotte(LotteType lotteType, String code) {
        Lotte lotte = new LotteChild();
        lotte.setName(lotteType.name());
        lotte.setRedNum(LotteXMLUtil.getBollNum(lotteType, BollType.red));
        lotte.setBlueNum(LotteXMLUtil.getBollNum(lotteType, BollType.blue));
        lotte.setBonusMap(LotteXMLUtil.getBonusMap(lotteType));
        lotte.setUrl(LotteXMLUtil.getURL(lotteType));
        lotte.setRedList(getBollList(code, BollType.red));
        lotte.setBlueList(getBollList(code, BollType.blue));

        String filterClassPath = LotteXMLUtil.getFilter(lotteType);
        try {
            Class<?> filterClass = Class.forName(filterClassPath);
            LotteFilter filter = (LotteFilter) filterClass.newInstance();
            lotte.setFilter(filter);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return lotte;
    }

    /**
     * 生成彩票
     *
     * @param lotte 已有的同类型彩票
     * @param code  号码
     * @return 彩票类
     */
    public static Lotte getLotte(Lotte lotte, String code) {
        Lotte newLotte = lotte.clone();
        newLotte.setRedList(getBollList(code, BollType.red));
        newLotte.setBlueList(getBollList(code, BollType.blue));
        return newLotte;
    }

    /**
     * 从号码字符串中解析出号码列表
     *
     * @param code     号码字符串
     * @param bollType 球类型
     * @return 号码列表
     */
    private static List<Integer> getBollList(String code, BollType bollType) {
        return Optional.ofNullable(code).map(s -> {

            String[] split = s.split("\\+");
            if (split.length <= bollType.getNum()) {
                return null;
            }
            return Arrays.asList(split[bollType.getNum()].split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
        }).orElse(null);

    }

    private static class LotteChild extends Lotte {

    }
}
