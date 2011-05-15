package com.sylinxsoft.csframework.server;

import java.io.DataInputStream;
import java.io.InputStream;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * 处理消息的工厂,工厂模式应用
 * 
 * 
 */
public class HandMsgFactory {

	/** 消息 */
	private static String msg = new String();

	/** 获得消息 */
	public static String getMsg() {
		return msg;
	}

	/** 返回一个消息处理实例 */
	public static HandMsgInterface Create(ClientGroupManager clientManager,
			ClientInforInterface client, InputStream msgIn) {
		HandMsgInterface handMsg = null;
		try {
			DataInputStream in = new DataInputStream(msgIn);
			msg = in.readUTF();
			// 提取处理消息协议
			String protocol = msg.substring(msg
					.indexOf(AllConstatns.PROTOCOLSTARTMARK)
					+ AllConstatns.PROTOCOLSTARTMARK.length(), msg
					.indexOf(AllConstatns.PROTOCOLENDMARK));
			// 提取消息体的内容
			msg = msg.substring(msg.indexOf(AllConstatns.MSGTOKEN)
					+ AllConstatns.MSGTOKEN.length(), msg.length());
			//msgIn.close();

			// 创建处理消息的协议类
			handMsg = Create(protocol);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return handMsg;
	}

	private static HandMsgInterface Create(String protocol) {

		// 获得消息协议映射
		MsgMapping msgMapping = MsgMapping.getConfig();
		// 取得该协议对应的处理类
		String handMsgClass = (String) (msgMapping.get(protocol));
		// 动态加载该协议处理类
		try {
			// 加载该处理类
			Class o = Class.forName(handMsgClass);
			// 向上转型返回
			return (HandMsgInterface) (o.newInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
