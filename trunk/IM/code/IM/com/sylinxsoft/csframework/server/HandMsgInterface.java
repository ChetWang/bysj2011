package com.sylinxsoft.csframework.server;

import java.io.InputStream;
import java.io.OutputStream;



/**
 * ���崦����Ϣ�Ľӿ�
 * HandMsgInterface.java
 */
public interface HandMsgInterface {
	
	
	/**
	 * 
	 * @param clientManager �ͻ��˹�����
	 * @param client �ĸ��ͻ��˴��͹���
	 * @param msg    ��Ϣ����
	 * @param target ������ʾ��Ϣ�Ķ���
	 */
	public void handMessage(ClientGroupManager clientManager,ClientInforInterface client,String msg,ServerObservable serverObservable);
	
	
}
