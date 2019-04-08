package lottery;

import java.util.List;
import java.util.Optional;

/**
 * 用于计算无序的彩票开奖结果，如双色球、大乐透
 */
public class DisorderFilter implements LotteFilter {
    @Override
    public String getBonus(Lotte one, Lotte two) {
        int redSameNum = getSameNum(one.getRedList(), two.getRedList());
        int blueSameNum = getSameNum(one.getBlueList(), two.getBlueList());
        return one.getBonusMap().get(redSameNum + "+" + blueSameNum);
    }

    /**
     * 计算两个列表有多少个数相同
     *
     * @param list1 列表一
     * @param list2 列表二
     * @return 相同数
     */
    private static int getSameNum(List<Integer> list1, List<Integer> list2) {
        return Optional.ofNullable(list1).map(l1 -> {
            return Optional.ofNullable(list2).map(l2 -> {
                if (list1.size() != list2.size()) {
                    return 0;
                }

                int num = 0;

                for (int i = 0; i < list1.size(); i++) {
                    for (int j = 0; j < list2.size(); j++) {
                        if (list1.get(i) == list2.get(j)) {
                            num++;
                        }
                    }
                }
                return num;
            }).orElse(0);
        }).orElse(0);
    }
}
