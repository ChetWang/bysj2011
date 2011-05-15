package com.sylinxsoft.csframework.server;

import ui.HyperLinkManager;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.TransferFilesManager;
import com.sylinxsoft.csframework.tcp.TcpFileSend;

public class HandAnswerSendFile implements HandMsgInterface {

	public void handMessage(ClientGroupManager clientManager,
			ClientInforInterface client, String msg,
			ServerObservable serverObservable) {

		String ip = msg.substring(0, msg.indexOf(AllConstatns.IPHEADERMARK));
		String message = msg.substring(msg.indexOf(AllConstatns.IPHEADERMARK)
				+ AllConstatns.IPHEADERMARK.length());
		String fileName = message.substring(0, message
				.indexOf(AllConstatns.SENDFILENAME));
		String command = message.substring(message
				.indexOf(AllConstatns.SENDFILENAME)
				+ AllConstatns.SENDFILENAME.length(), message
				.indexOf(AllConstatns.FILECOMMAND));

		String port = message.substring(message
				.indexOf(AllConstatns.FILECOMMAND)
				+ AllConstatns.FILECOMMAND.length(), message
				.indexOf(AllConstatns.FILESERVERPORT));

		// 将取消按钮失效
		HyperLinkManager.getInstance().removeHyperLinks(
				AllConstatns.FSINTERRUPTCOMMAND + fileName);

		// 同意接收文件，开始创建数据报套接字
		if (AllConstatns.FSAGREECOMMAND.equals(command)
				|| AllConstatns.FSAGREETOCOMMAND.equals(command)) {
			ServerObservable.getInstance().changedMsg(
					"\n对方同意接收文件，正在建立TCP连接......", AllConstatns.FSAGREECOMMAND);
			
			// 启动TCP发送文件线程
			TcpFileSend fileSend = new TcpFileSend(ip, Integer.parseInt(port),
					fileName);
			(new Thread(fileSend)).start();

		} else if (AllConstatns.FSREFUSECOMMAND.equals(command)) {
			TransferFilesManager.getInstance().removeTransferingFile(fileName);
			ServerObservable.getInstance().changedMsg(
					"\n对方拒绝接受文件\"" + fileName + "\"!",
					AllConstatns.FSAGREECOMMAND);
			
		}
	}

}
