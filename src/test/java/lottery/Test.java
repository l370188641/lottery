package lottery;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 版本：2019/4/4 V1.0
 * 成员：李晋
 */
public class Test {
    public static void main(String[] args) {
        LocalDate ld = LocalDate.now();
        DayOfWeek today = ld.getDayOfWeek();
        System.out.println(today.name());
    }
}
