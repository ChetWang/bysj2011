package com.sylinxsoft.csframework.server;

import java.util.Observable;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * ����ʹ��������״̬�ܱ����۲��߹۲죬 <br>
 * ����ʹ��java�Դ��Ĺ۲���ģʽ��ʵ��. ���й۲�����Ҫʵ��Observer�ӿ�.
 * 
 * @author yqg
 * 
 */
public class ServerObservable extends Observable {

	/** �洢���µ���Ϣ */
	private String msg = new String();

	private static ServerObservable observerable = new ServerObservable();

	public static ServerObservable getInstance() {
		return observerable;
	}

	private ServerObservable() {

	}

	/** ȡ�ø��º����Ϣ */
	public String getMsg() {
		return msg;
	}

	/**
	 * ֪ͨ�۲��߷�������ʱ���յ��µ���Ϣ����Ϣ���ı�
	 * 
	 * @param msg
	 */
	public void changedMsg(String msg, String arg) {
		this.msg = msg;
		// ��Ǳ��۲���״̬�Ѿ��ı�
		setChanged();
		// ֪ͨ�۲�����Ϣ�ı�
		notifyObservers(arg);
	}

}
