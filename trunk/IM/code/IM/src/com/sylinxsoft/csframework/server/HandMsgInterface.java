package com.sylinxsoft.csframework.server;

import java.io.InputStream;
import java.io.OutputStream;



/**
 * 定义处理消息的接口
 * @author Administrator
 * 2007-9-29
 * HandMsgInterface.java
 */
public interface HandMsgInterface {
	
	
	/**
	 * 
	 * @param clientManager 客户端管理器
	 * @param client 哪个客户端传送过来
	 * @param msg    消息内容
	 * @param target 传入显示消息的对象
	 */
	public void handMessage(ClientGroupManager clientManager,ClientInforInterface client,String msg,ServerObservable serverObservable);
	
	
}
