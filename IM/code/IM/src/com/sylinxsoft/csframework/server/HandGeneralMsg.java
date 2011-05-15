package com.sylinxsoft.csframework.server;

import com.sylinxsoft.csframework.AllConstatns;

public class HandGeneralMsg implements HandMsgInterface {

	public void handMessage(ClientGroupManager clientManager,
			ClientInforInterface client, String msg,
			ServerObservable serverObservable) {
		serverObservable.changedMsg(msg,AllConstatns.MSGCHANGED);
		//System.out.println("消息服务器:" + msg.toString());
	}

}
