package org.pcchen.epaper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;

/**
 * 通过httpclient验证短信接口
 * Created by cpc on 2018/1/5.
 */
public class TestHttpClientMessage {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String time = new Date().getTime() + "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = "http://211.159.160.110/admin/message/read";
        //封装请求参数

        String encodeParam = "appid=first&content=second&mobile=third&timestamp=forth";
        encodeParam=encodeParam.replace("first", "b228f669d036c1ab00c1a3efeec47657ac600c77");
        encodeParam=encodeParam.replace("second", URLEncoder.encode("测试程序", "utf-8"));
        encodeParam=encodeParam.replace("third","17602987789");
        encodeParam=encodeParam.replace("forth", time);

        String md5 = getMD5("chinasoftipdmi" + URLEncoder.encode(encodeParam, "utf-8").toLowerCase()+ "chinasoftipdmi");
        String str = "";
        try {
            //创建Get请求
            HttpGet httpGet = new HttpGet(url+"?"+"appid=b228f669d036c1ab00c1a3efeec47657ac600c77&content=测试程序&mobile=17602987789&timestamp="+time+"&sign="+ md5.toLowerCase() +"");
            //执行Get请求，
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result= EntityUtils.toString(response.getEntity(),"utf-8");
                result = result.replace("\"", "\"");
                System.out.println(result);
//                JsonObject jo = (JsonObject) new JsonParser().parse(result);
//                String statue = jo.get("statue").getAsString();
//                System.out.println("statue==" + statue);
//                //正常请求，结果拆装
//                JsonObject asJsonObject = jo.get("result").getAsJsonObject();
//                String content = asJsonObject.get("content").getAsString();
//                String mobile = asJsonObject.get("mobile").getAsString();
//                Long ctime = asJsonObject.get("ctime").getAsLong();
//                String res = asJsonObject.get("res").getAsString();
//                System.out.println(content);
//                System.out.println(mobile);
//                System.out.println(ctime);
//                System.out.println(res);
//                if("1".equals(statue)) {
//                    System.out.println("正确输出");
//                } else {
//                    System.out.println("异常输出");
//                }
            } else {
                System.out.println("异常输出");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //消耗实体内容
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //生成MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    // 二进制转十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
