package com.charles.test;


import com.charles.po.AccessToken;
import com.charles.util.WeiXinUtil;

/**
 * Created by trcay on 2020/2/9.
 */
public class Test {

    public static void main(String[] args) {


        AccessToken accessToken = WeiXinUtil.getAccessToken();
        System.out.println(accessToken.getAccess_token() + "------" + accessToken.getExpires_in());

    }


}
