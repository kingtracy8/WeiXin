package com.charles.util;

import com.alibaba.fastjson.JSONObject;
import com.charles.po.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class WeiXinUtil {


    public static final String APPID = "wx3893296bcfbb6a8f";

    public static final String APPSECRET = "894e2a30148965cf258bfdd948fd5c39";

    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    /**
     * 通过GET方法获取access_token
     *
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url) {


        DefaultHttpClient client = new DefaultHttpClient();
        //使用url构造HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;

        try {

//    如果不加会提示，原因暂不明    javax.net.ssl.SSLException: hostname in certificate didn't match: <183.57.48.62> != <mp.weixin.qq.com>
            SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
            //执行GET方法获得HttpResponse
            HttpResponse response = client.execute(httpGet);
            //从response里获取Entity
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                //将Entity对象转换成String类型，并指定编码格式
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                //将String类型的数据转换成JSONObject类型
                jsonObject = JSONObject.parseObject(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /**
     * 通过post方式提交获取acces_token
     *
     * @param url
     * @param outStr 需要提交的参数
     * @return
     */
    public static JSONObject doPostStr(String url, String outStr) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;

        try {
            //将需要提交的数据设置到HttpPost对象里
            httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            jsonObject = JSONObject.parseObject(result);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static AccessToken getAccessToken() {

        AccessToken accessToken = new AccessToken();
        //构造获取ACCESS_TOKEN的URL，填入APPID和APPSECRET
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);

        JSONObject jsonObject = doGetStr(url);

        if (jsonObject != null) {

            accessToken.setAccess_token(jsonObject.getString("access_token"));
            accessToken.setExpires_in(jsonObject.getInteger("expires_in"));

        }

        return accessToken;

    }


}
