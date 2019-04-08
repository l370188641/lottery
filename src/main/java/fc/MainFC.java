package fc;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.StreamRequestHandler;
import email.AliEmail;
import http.HttpConnectionUtil;
import http.SybUtil;
import lottery.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;

/**
 * 函数计算入口
 */
public class MainFC implements StreamRequestHandler {

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        // 今天是星期几
        String today = LocalDate.now().getDayOfWeek().name().toLowerCase();
        // 今天要开的奖
        String[] todayLottes = LotteXMLUtil.getWeek(today).split(",");
        Arrays.asList(todayLottes).stream().map(LotteType::getLotteType).forEach(MainFC::start);
    }

    /**
     * 启动程序
     *
     * @param lotte 彩种
     */
    public static void start(LotteType lotte) {
        String url = LotteXMLUtil.getURL(lotte);
        LotteBean lotteBean = getLotteBean(url);
        // 开奖号码
        String openCode = Optional.ofNullable(lotteBean.getData()).map(datas -> datas[0].getOpencode()).orElse(null);
        // 官方开奖的彩票
        Lotte openLotte = Optional.ofNullable(openCode).map(code -> LotteFactory.getLotte(lotte, code)).orElse(null);
        // 你的号码
        List<String> yourLottes = LotteXMLUtil.getYourLottes(lotte);

        // 你的中奖信息
        String bonus = Optional.ofNullable(yourLottes).map(list -> yourBonus(openLotte, list)).orElse(null);

        AliEmail.sample("今日号码:" + openCode + "</br>" + bonus);
    }

    /**
     * 获得中奖信息
     *
     * @param openLotte  官方开奖信息
     * @param yourLottes 你的号码
     * @return 中奖信息
     */
    public static String yourBonus(Lotte openLotte, List<String> yourLottes) {
        StringBuilder sb = new StringBuilder();
        yourLottes.forEach(youCode -> {
            Lotte youLotte = LotteFactory.getLotte(openLotte, youCode);
            String bonus = openLotte.getBonus(youLotte);
            sb.append("你的号码：").append(youLotte.getCode()).append(" -> ").append(bonus).append("</br>");
        });
        return sb.toString();
    }

    /**
     * 获得开奖结果
     *
     * @param url 开奖url
     * @return 开奖结果
     */
    public static LotteBean getLotteBean(String url) {
        HttpConnectionUtil http = new HttpConnectionUtil(url);
        try {
            http.init();
            TreeMap<String, String> params = new TreeMap();

            byte[] bys = http.postParams(params, true);
            String result = new String(bys, "UTF-8");
            return SybUtil.<LotteBean>json2Obj(result, LotteBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}