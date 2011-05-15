package com.sylinxsoft.csframework.server;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.client.AbstractClient;
import com.sylinxsoft.csframework.client.SendMsgClient;

public class HandCannelSendFile implements HandMsgInterface {

	public void handMessage(ClientGroupManager clientManager,
			ClientInforInterface client, String msg,
			ServerObservable serverObservable) {
		serverObservable.changedMsg(msg,AllConstatns.CANNELSENDFILEPROTOCOL);
		
		String ip = msg.substring(0, msg.indexOf(AllConstatns.IPHEADERMARK));
		String message = msg.substring(msg.indexOf(AllConstatns.IPHEADERMARK)
				+ AllConstatns.IPHEADERMARK.length());

		String fileName = message.substring(0, message
				.indexOf(AllConstatns.SENDFILENAME));

		String serverPort = message.substring(message
				.indexOf(AllConstatns.FILESIZE)
				+ AllConstatns.FILESIZE.length(), message
				.indexOf(AllConstatns.FILESERVERPORT));
		String fsMsg = AllConstatns.PROTOCOLSTARTMARK
				+ AllConstatns.ANSWERSENDFILEPROTOCOL
				+ AllConstatns.PROTOCOLENDMARK + AllConstatns.MSGTOKEN
				+ "0.0.0.0" + AllConstatns.IPHEADERMARK + fileName
				+ AllConstatns.SENDFILENAME + AllConstatns.FSINTERRUPTCOMMAND
				+ AllConstatns.FILECOMMAND + 0 + AllConstatns.FILESERVERPORT;

		// 发送消息客户端线程
		AbstractClient msgClient = new SendMsgClient();
		msgClient.setIpAddress(ip, Integer.parseInt(serverPort));
		// 发送确认接收文件请求
		msgClient.setSendMsg(fsMsg);
		msgClient.start();

	}

}
