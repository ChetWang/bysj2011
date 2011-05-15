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

		// ��ȡ����ťʧЧ
		HyperLinkManager.getInstance().removeHyperLinks(
				AllConstatns.FSINTERRUPTCOMMAND + fileName);

		// ͬ������ļ�����ʼ�������ݱ��׽���
		if (AllConstatns.FSAGREECOMMAND.equals(command)
				|| AllConstatns.FSAGREETOCOMMAND.equals(command)) {
			ServerObservable.getInstance().changedMsg(
					"\n�Է�ͬ������ļ������ڽ���TCP����......", AllConstatns.FSAGREECOMMAND);
			
			// ����TCP�����ļ��߳�
			TcpFileSend fileSend = new TcpFileSend(ip, Integer.parseInt(port),
					fileName);
			(new Thread(fileSend)).start();

		} else if (AllConstatns.FSREFUSECOMMAND.equals(command)) {
			TransferFilesManager.getInstance().removeTransferingFile(fileName);
			ServerObservable.getInstance().changedMsg(
					"\n�Է��ܾ������ļ�\"" + fileName + "\"!",
					AllConstatns.FSAGREECOMMAND);
			
		}
	}

}
