package org.pcchen.epaper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 对短信内容返回值进行解析
 * Created by cpc on 2018/1/5.
 */
public class ParseResultJson {
    public static void main(String[] args) {
        String result = "{\"statue\":1,\"result\":{\"mobile\":\"17602987789\",\"content\":\"\\u6d4b\\u8bd5\\u7a0b\\u5e8f\",\"ctime\":1515117527,\"res\":\"\\u6210\\u529f\"}}";
        result = result.replace("\"", "\"");
        System.out.println(result);

        JsonObject jo = (JsonObject) new JsonParser().parse(result);
        String statue = jo.get("statue").getAsString();
        System.out.println("statue==" + statue);
        //正常请求
        JsonObject asJsonObject = jo.get("result").getAsJsonObject();
        String content = asJsonObject.get("content").getAsString();
        String mobile = asJsonObject.get("mobile").getAsString();
        Long ctime = asJsonObject.get("ctime").getAsLong();
        String res = asJsonObject.get("res").getAsString();
        System.out.println(content);
        System.out.println(mobile);
        System.out.println(ctime);
        System.out.println(res);
        if("1".equals(statue)) {
            System.out.println("正确输出");
        } else {
            System.out.println("异常输出");
        }
    }
}
