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
 * ͨ��httpclient��֤���Žӿ�
 * Created by cpc on 2018/1/5.
 */
public class TestHttpClientMessage {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String time = new Date().getTime() + "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = "http://211.159.160.110/admin/message/read";
        //��װ�������

        String encodeParam = "appid=first&content=second&mobile=third&timestamp=forth";
        encodeParam=encodeParam.replace("first", "b228f669d036c1ab00c1a3efeec47657ac600c77");
        encodeParam=encodeParam.replace("second", URLEncoder.encode("���Գ���", "utf-8"));
        encodeParam=encodeParam.replace("third","17602987789");
        encodeParam=encodeParam.replace("forth", time);

        String md5 = getMD5("chinasoftipdmi" + URLEncoder.encode(encodeParam, "utf-8").toLowerCase()+ "chinasoftipdmi");
        String str = "";
        try {
            //����Get����
            HttpGet httpGet = new HttpGet(url+"?"+"appid=b228f669d036c1ab00c1a3efeec47657ac600c77&content=���Գ���&mobile=17602987789&timestamp="+time+"&sign="+ md5.toLowerCase() +"");
            //ִ��Get����
            response = httpClient.execute(httpGet);
            //�õ���Ӧ��
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result= EntityUtils.toString(response.getEntity(),"utf-8");
                result = result.replace("\"", "\"");
                System.out.println(result);
//                JsonObject jo = (JsonObject) new JsonParser().parse(result);
//                String statue = jo.get("statue").getAsString();
//                System.out.println("statue==" + statue);
//                //�������󣬽����װ
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
//                    System.out.println("��ȷ���");
//                } else {
//                    System.out.println("�쳣���");
//                }
            } else {
                System.out.println("�쳣���");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //����ʵ������
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //�ر���Ӧ ����http����
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //����MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // ����һ��md5�㷨����
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // ���MD5�ֽ�����,16*8=128λ
            md5 = bytesToHex(md5Byte);                            // ת��Ϊ16�����ַ���
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    // ������תʮ������
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
