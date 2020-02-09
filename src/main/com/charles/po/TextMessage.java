package com.charles.po;

import com.charles.po.BaseMessage;

/**
 * Created by trcay on 2020/2/8.
 */
public class TextMessage extends BaseMessage {


    private String Content;
    private String MsgId;


    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }



}
