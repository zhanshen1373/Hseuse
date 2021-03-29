package com.hd.hse.dc.business.common.weblistener;

/**
 * Created by dubojian on 2018/5/19.
 */

public class MessageEvent {
    public static final int APP_MODULE_TYPE=1;
    private int messageType;
    private String message;

    public MessageEvent(int messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }


}
