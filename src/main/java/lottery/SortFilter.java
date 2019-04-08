package lottery;

import java.awt.font.OpenType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 用于计算有序的彩票开奖结果，如：七星彩
 */
public class SortFilter implements LotteFilter {
    @Override
    public String getBonus(Lotte one, Lotte two) {
        int redSameNum = getSameNum(one.getRedList(), two.getRedList(), 0, 0);
        int blueSameNum = getSameNum(one.getBlueList(), two.getBlueList(), 0, 0);
        return one.getBonusMap().get(redSameNum + "+" + blueSameNum);
    }

    /**
     * 计算两个列表最大连续相同数，
     *
     * @param list1 列表一
     * @param list2 列表二
     * @param i     列表一的起始下标
     * @param j     列表二的起始下标
     * @return 相同数
     */
    private static int getSameNum(List<Integer> list1, List<Integer> list2, int i, int j) {
        return Optional.ofNullable(list1).map(l1 -> Optional.ofNullable(list2).map(l2 -> {
                    if (list1.size() != list2.size()) {
                        return 0;
                    }

                    int i1 = i, j1 = j, num = 0;
                    while (i1 < list1.size() && j1 < list2.size()) {
                        int redNum1 = list1.get(i1);
                        int redNum2 = list2.get(j1);
                        if (redNum1 == redNum2) {
                            num++;
                            i1++;
                            j1++;
                        } else {
                            break;
                        }
                    }
                    if (num >= list1.size() - i - 1) {
                        return num;
                    } else {
                        return Math.max(num, getSameNum(list1, list2, i + 1, j + 1));
                    }
                }).orElse(0)
        ).orElse(0);

    }
}
