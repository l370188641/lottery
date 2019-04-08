package lottery;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 处理XML配置文件
 */
public final class LotteXMLUtil {
    private static final URL XML_URL = LotteXMLUtil.class.getClassLoader().getResource("conf.xml");
    private static SAXReader reader = new SAXReader();
    private static Document document;

    public static final String DALETOU_NAME = "daletou";
    public static final String SHUANGSEQIU_NAME = "shuangseqiu";
    public static final String QIXINGCAI_NAME = "qixingcai";
    public static final String RED_BOLL_NAME = "red";
    public static final String BLUE_BOLL_NAME = "blue";

    static {
        try {
            document = reader.read(XML_URL);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当日开奖的彩票名称
     *
     * @param weekName 当前星期几，英文
     * @return 当日开奖彩票，可能有多个以","隔开
     */
    public static String getWeek(String weekName) {
        return Optional.ofNullable(document.selectSingleNode("//" + weekName.toLowerCase()))
                .map(Node::getText)
                .orElse(null);
    }

    /**
     * 获取中奖规则
     *
     * @param type 彩票名
     * @return 中奖规则
     */
    public static Map<String, String> getBonusMap(LotteType type) {
        Node node = document.selectSingleNode("//code[@name='" + type + "']");
        List<Node> bounses = Optional.ofNullable(node)
                .map(n -> n.selectNodes("./bonuses/bouns")
                )
                .orElse(null);
        return Optional.ofNullable(bounses).map(bes -> {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < bes.size(); i++) {
                Node bonus = bes.get(i);
                String red_num = bonus.valueOf("@red");
                String blue_num = bonus.valueOf("@blue");
                String money = bonus.valueOf("@money");
                map.put(red_num + "+" + blue_num, money);
            }
            return map;
        }).orElse(null);
    }

    /**
     * 获取球数
     *
     * @param type 彩种名
     * @param bollType  red 或者 blue
     * @return 球数
     */
    public static int getBollNum(LotteType type, BollType bollType) {
        return Optional.ofNullable(document.selectSingleNode("//code[@name='" + type + "']"))
                .map(n -> Integer.parseInt(n.valueOf("@" + bollType)))
                .orElse(null);
    }

    /**
     * 获取彩票奖金过滤器
     *
     * @param type 彩种名
     * @return 过滤器
     */
    public static String getFilter(LotteType type) {
        return Optional.ofNullable(document.selectSingleNode("//code[@name='" + type + "']"))
                .map(n -> n.valueOf("@filter"))
                .orElse("lottery.DisorderFilter");
    }

    /**
     * 获取开奖地址
     *
     * @param type 彩种名
     * @return 开奖地址
     */
    public static String getURL(LotteType type) {
        return Optional.ofNullable(document.selectSingleNode("//code[@name='" + type + "']"))
                .map(n -> n.selectSingleNode("./url").getText())
                .orElse(null);
    }

    /**
     * 获取你买的号码
     *
     * @param type 彩票名
     * @return 号码
     */
    public static List<String> getYourLottes(LotteType type) {
        return Optional.ofNullable(document.selectNodes("//lotte[@name='" + type + "']"))
                .map(nodes -> nodes.stream().map(node -> node.valueOf("@code")).collect(Collectors.toList()))
                .orElse(null);
    }
}
