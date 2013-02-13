package com.imstarter.servlet;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class MessageManager {
//	private static Logger logger = Logger.getGlobal();
	/**
	 * Message Queue 消息队列
	 * */
	protected static ConcurrentHashMap<String, MessageManager> msgs = new ConcurrentHashMap<String, MessageManager>();
	
	private ConcurrentHashMap<String, IMMessage> msgsIn = new ConcurrentHashMap<String, IMMessage>();
	private ConcurrentHashMap<String, IMMessage> msgsInTmp = new ConcurrentHashMap<String, IMMessage>();
	//private ConcurrentHashMap<String, IMMessage> msgsOut = new ConcurrentHashMap<String, IMMessage>();
	public  String clientName; 

	public static void main(String[] args) {
		Thread th = new Thread() {
			@Override
			public void run() {
				Long id = this.getId();
				System.out.println("id:" + id + MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
				MessageManager.sendMessage("张三", "李四", "Hello");
				MessageManager.sendMessage("李四", "张三", "Hello2");
				MessageManager.sendMessage("张三", "李四", "Hello 你好");
				System.out.println("id:" + id +  MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
			}
		};
		
		Thread th1 = new Thread() {
			@Override
			public void run() {
				Long id = this.getId();
				System.out.println("id:" + id + MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
				MessageManager.sendMessage("张三", "李四", "Hello");
				MessageManager.sendMessage("李四", "张三", "Hello2");
				MessageManager.sendMessage("张三", "李四", "Hello 你好");
				System.out.println("id:" + id +  MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
			}
		};
		
		Thread th2 = new Thread() {
			@Override
			public void run() {
				Long id = this.getId();
				System.out.println("id:" + id + MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
				MessageManager.sendMessage("张三", "李四", "Hello");
				MessageManager.sendMessage("李四", "张三", "Hello2");
				MessageManager.sendMessage("张三", "李四", "Hello 你好");
				System.out.println("id:" + id +  MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("张三"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
				System.out.println("id:" + id +  MessageManager.readmsg("李四"));
			}
		};
		
		th2.run();
		th1.run();
		th.run();
	}
	public MessageManager(String clientName) {
		this.clientName = clientName;
	}
	public boolean inMessage(String from, String message) {
		IMMessage msg = new IMMessage(from, clientName, message);
		msgsIn.put(msg.msgId, msg);
		//logger.info(msg.toString());
		System.out.println(msg.toString());
		return true;
	}
	public boolean outMessage(String to, String message) {
//		IMMessage msg = new IMMessage(clientName, to, message);
		//msgsOut.put(msg.msgId, msg);
		//System.out.print("Message Out from " + this.clientName + " to " + to + message);
		
		MessageManager dest = MessageManager.getIMMessageByName(to);
		if (dest != null) {
			dest.inMessage(clientName, message);
		}
		return true;
	}
	public String readJsonMessage() {
		
		try {
			ConcurrentHashMap<String, IMMessage> msgsInTmpLocal = msgsIn;
			msgsIn = msgsInTmp;
			msgsInTmp = msgsInTmpLocal;
			
			JSONArray oJsonArray = new JSONArray(); 
			if (!msgsInTmpLocal.isEmpty()) {
				for (Iterator iterator = msgsInTmpLocal.values().iterator(); iterator.hasNext();) {
					//IMMessage msg = (IMMessage) iterator.next();
					Object oMsg = iterator.next();
					IMMessage msg = (IMMessage)oMsg;
					JSONObject oJson = new JSONObject();
					oJson.put("msgId", msg.msgId);
					oJson.put("msgTime", msg.msgTime);
					oJson.put("msgFrom", msg.msgFrom);
					oJson.put("msgTo", msg.msgTo);
					oJson.put("msgMessage", msg.msgMessage);
					oJsonArray.add(oJson);
				}
				//logger.info(oJsonArray.toString());
			}
			msgsInTmpLocal.clear();
			return oJsonArray.toJSONString();
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return "";
	}

	public static MessageManager getIMMessageByName(String clientName) {
		if (clientName != null && clientName.length() > 0) {
			if (!msgs.containsKey(clientName)) {
				MessageManager value = new MessageManager(clientName);
				msgs.put(clientName, value);
				return value;
			} else {
				return msgs.get(clientName);
			}
		} 
		return null;
	}
	public static boolean sendMessage(String from, String to, String message) {
		MessageManager oFrom = MessageManager.getIMMessageByName(from);
		if (oFrom != null) {
			return oFrom.outMessage(to, message);
		}
		return false;
	}
	public static String readmsg(String clientName) {
		MessageManager msgInfo = MessageManager.getIMMessageByName(clientName);
		if (msgInfo != null) {
			String sJsonMsg = msgInfo.readJsonMessage() ;
			return sJsonMsg;
		}
		return "";
	}

}
