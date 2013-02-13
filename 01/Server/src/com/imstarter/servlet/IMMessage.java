package com.imstarter.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;


public class IMMessage {

	private static Double messageId = 0D;
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public String msgId;
	public String msgTime;
	public String msgFrom;
	public String msgTo;
	public String msgMessage;
	public IMMessage(String from, String to, String message) {
		doini(from, to, message);
	}
	public void doini(String from, String to, String message) {
		this.msgId = Double.toString( messageId ++ );
		this.msgTime = df.format(new Date());
		this.msgFrom = from;
		this.msgTo = to;
		this.msgMessage = message;
	}

	public String toString() {
		return msgTime + "--" + msgFrom + " said to " + msgTo + ": " + msgMessage;
	}
}
