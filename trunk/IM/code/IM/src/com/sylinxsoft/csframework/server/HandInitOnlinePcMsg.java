package com.sylinxsoft.csframework.server;

import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;

public class HandInitOnlinePcMsg implements HandMsgInterface {

	/** 实现处理消息接口 */
	public void handMessage(ClientGroupManager clientManager,
			ClientInforInterface client, String msg, ServerObservable serverObservable) {
		
		try {
			StringTokenizer msgBuffer = new StringTokenizer(msg,
					AllConstatns.MSGTOKEN);
			// 创建一个客户端
			ClientInforInterface clientInfor = new ClientInfor();
			while (msgBuffer.hasMoreTokens()) {
				String key = msgBuffer.nextToken();
				String value = new String();
				if (msgBuffer.hasMoreTokens()) {
					value = msgBuffer.nextToken();
				}
				clientInfor.setInfor(key, value);
			}
			clientManager.addClient(clientInfor);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
