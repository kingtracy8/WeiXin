package com.charles.servlet;

import com.charles.po.NewsMessage;
import com.charles.po.TextMessage;
import com.charles.util.CheckUtil;
import com.charles.util.MessageUtil;
import org.dom4j.DocumentException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by trcay on 2020/2/8.
 */
public class WeixinSerlvet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        try {

            PrintWriter out = resp.getWriter();
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();

        try {

            Map<String, String> map = MessageUtil.xmlToMap(req);
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");

            String replyMsg = null;

            TextMessage replyText = new TextMessage();

            if (MessageUtil.MESSAGE_TEXT.equals(MsgType)) {

                if ("1".equals(Content)) {

                    replyMsg = MessageUtil.initText(FromUserName, ToUserName, MessageUtil.FirstMenuText());

                } else if ("2".equals(Content)) {

//                    replyMsg = MessageUtil.initText(FromUserName, ToUserName, MessageUtil.SecondMenuText());

                    replyMsg = MessageUtil.initNewsMessage(FromUserName, ToUserName);

                    System.out.println(replyMsg);
                } else if ("?".equals(Content) || "？".equals(Content)) {
                    replyMsg = MessageUtil.initText(FromUserName, ToUserName, MessageUtil.HelpMenuText());
                } else {
                    replyMsg = MessageUtil.initText(FromUserName, ToUserName, "对不起，没有这个选项，请回复？按照菜单提示操作。");
                }


            } else if (MessageUtil.MESSAGE_EVENT.equals(MsgType)) {
                //假如是关注事件
                String eventType = map.get("Event");
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                    replyMsg = MessageUtil.initText(FromUserName, ToUserName, MessageUtil.initMenuText());
                }

            }
            out.print(replyMsg);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }
}
