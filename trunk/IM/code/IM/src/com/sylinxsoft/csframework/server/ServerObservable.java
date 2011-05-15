package com.sylinxsoft.csframework.server;

import java.util.Observable;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * 该类使服务器的状态能被外界观察者观察， <br>
 * 这里使用java自带的观察者模式的实现. 所有观察者需要实现Observer接口.
 * 
 * @author yqg
 * 
 */
public class ServerObservable extends Observable {

	/** 存储最新的消息 */
	private String msg = new String();

	private static ServerObservable observerable = new ServerObservable();

	public static ServerObservable getInstance() {
		return observerable;
	}

	private ServerObservable() {

	}

	/** 取得更新后的消息 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 通知观察者服务器此时接收到新的消息或消息被改变
	 * 
	 * @param msg
	 */
	public void changedMsg(String msg, String arg) {
		this.msg = msg;
		// 标记被观察者状态已经改变
		setChanged();
		// 通知观察者消息改变
		notifyObservers(arg);
	}

}
