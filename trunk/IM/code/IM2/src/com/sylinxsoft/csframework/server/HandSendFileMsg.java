package com.sylinxsoft.csframework.server;

import com.sylinxsoft.csframework.AllConstatns;

public class HandSendFileMsg implements HandMsgInterface {

	public void handMessage(ClientGroupManager clientManager,
			ClientInforInterface client, String msg,
			ServerObservable serverObservable) {
		
		serverObservable.changedMsg(msg,AllConstatns.MSGSENDFILE);
		System.out.println("文件服务器:" + msg.toString());
	}

}
