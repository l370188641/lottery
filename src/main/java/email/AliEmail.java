package email;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

/**
 * 版本：2019/4/4 V1.0
 * 成员：李晋
 */
public class AliEmail {
    private static final Properties prop = new Properties();

    static {
        try (InputStream inputStream = AliEmail.class.getClassLoader().getResourceAsStream("conf.properties")) {
            prop.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sample(String text) {
        IClientProfile profile = DefaultProfile.getProfile(get("regionId"), get("accessKeyId"), get("secret"));

        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName(get("accountName"));
            request.setFromAlias(get("fromAlias"));
            request.setAddressType(1);
            request.setTagName("test");
            request.setReplyToAddress(true);
            request.setToAddress(get("toAddress"));
            request.setSubject(get("subject"));
            request.setHtmlBody(text);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    private static String get(String key) {
        String value = (String) prop.get(key);

        return Optional.ofNullable(value).map(v -> {
            try {
                return new String(v.getBytes("ISO8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }).orElse("");
    }
}
