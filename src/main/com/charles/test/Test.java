package com.charles.test;

import com.charles.po.News;
import com.charles.po.NewsMessage;
import com.charles.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by trcay on 2020/2/9.
 */
public class Test {

    public static void main(String[] args) {

        NewsMessage newsMessage = new NewsMessage();
        List<News> articles = new ArrayList<News>();

        News news = new News();

        news.setDescription("这是描述");
        news.setTitle("这是标题");
        news.setPicUrl("F:\\coding\\IntelliJ IDEA\\porject\\WeiXin\\src\\main\\webapp\\v.jpg");
        news.setUrl("www.baidu.com");

        articles.add(news);

        newsMessage.setFromUserName("tracy");
        newsMessage.setToUserName("charles");
        newsMessage.setArticles(articles);
        newsMessage.setArticleCount(String.valueOf(articles.size()));
        newsMessage.setCreateTime(String.valueOf(new Date()));
        newsMessage.setMsgType("news");


        String result = MessageUtil.initText("11","22","333");


    }

}
