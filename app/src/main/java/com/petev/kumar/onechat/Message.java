package com.petev.kumar.onechat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Peter S. Petev (Ice) on 1/29/2016.
 */
public class Message {
    public String messageSender = "Sender";
    public String messageContents = "Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message Message";
    public String messageTime;

    public Message(String sender, String message){
        this.messageSender = sender;
        this.messageContents = message;
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        messageTime = df.format(c.getTime());
    }
}
