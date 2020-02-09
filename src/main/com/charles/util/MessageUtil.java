package com.charles.util;

import com.charles.po.News;
import com.charles.po.NewsMessage;
import com.charles.po.TextMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by trcay on 2020/2/8.
 * 进行消息的格式转换
 */
public class MessageUtil {


    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "click";
    public static final String MESSAGE_VIEW = "view";
    public static final String MESSAGE_NEWS = "news";

    /**
     * 将XML格式转换成Map集合
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {

        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        InputStream ins = request.getInputStream();
        //将xml是输入流转成Document
        Document doc = reader.read(ins);
        //得到根节点
        Element root = doc.getRootElement();
        //得到XML的所有结点
        List<Element> list = root.elements();
        //循环装载到Map对象中
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }

        return map;
    }


    /**
     * 将对象转换成xml
     *
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {

        XStream xStream = new XStream();
        //将根元素设置成xml（默认是别名）
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);

    }

    /**
     * 封装自定义回复消息方法
     *
     * @param FromUserName
     * @param ToUserName
     * @param Content
     * @return
     */
    public static String initText(String FromUserName, String ToUserName, String Content) {

        TextMessage text = new TextMessage();

        text.setToUserName(FromUserName);
        text.setFromUserName(ToUserName);
        text.setContent(Content);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(String.valueOf(new Date()));

        return textMessageToXml(text);
    }

    public static String initMenuText() {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("欢迎您的关注！请按照菜单提示进行操作：\n\n");
        stringBuffer.append("1、讲个笑话\n");
        stringBuffer.append("2、今日推荐\n");
        stringBuffer.append("3、回复?调出菜单\n");

        return stringBuffer.toString();
    }

    public static String FirstMenuText() {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("你是世界上最可爱的人！");
        return stringBuffer.toString();
    }

    public static String SecondMenuText() {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("该功能暂不对外开放！");
        return stringBuffer.toString();
    }

    public static String HelpMenuText() {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("请按照菜单提示进行操作：\n");
        stringBuffer.append("1、讲个笑话\n");
        stringBuffer.append("2、今日推荐\n");
        return stringBuffer.toString();
    }

    /**
     * 将图文信息转换成XML
     *
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {

        XStream xStream = new XStream();
        //将根元素设置成xml（默认是别名）
        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", new News().getClass());
        return xStream.toXML(newsMessage);

    }


    public static String initNewsMessage(String FromUserName, String ToUserName) {

        NewsMessage newsMessage = new NewsMessage();
        List<News> articles = new ArrayList<>();

        News news = new News();

        news.setDescription("这是描述");
        news.setTitle("这是标题");
        news.setPicUrl("http://e8046c56.ngrok.io//image//v.jpg");
        news.setUrl("www.baidu.com");

        News news1 = new News();

        news1.setDescription("这是描述1");
        news1.setTitle("这是标题1");
        news1.setPicUrl("http://e8046c56.ngrok.io//image//v.jpg");
        news1.setUrl("www.baidu.com");


        articles.add(news);
        articles.add(news1);

        newsMessage.setFromUserName(ToUserName);
        newsMessage.setToUserName(FromUserName);
        newsMessage.setArticles(articles);
        newsMessage.setArticleCount(String.valueOf(articles.size()));
        newsMessage.setCreateTime(String.valueOf(new Date()));
        newsMessage.setMsgType(MESSAGE_NEWS);

        return newsMessageToXml(newsMessage);
    }
}
