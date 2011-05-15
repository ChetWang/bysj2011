package com.sylinxsoft.csframework.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * 接收消息线程
 * 
 * @author Administrator 2007-9-28 RecvMsgServer.java
 */
public class RecvMsgServer extends AbstarctServer {

	public RecvMsgServer(ServerObservable serverObservable) {
		super(serverObservable);
	}

	/** 初始化监听参数，并根据参数创建套接字 */
	public void initListen() {
		try {
			// 获得监听端口
			ServerConfig config = ServerConfig.getConfig();
			// 打开接收消息端口
			if (port == -1) {
				socker = new ServerSocket(Integer.parseInt((String) config
						.get(AllConstatns.RECVMSGPORT)));
			} else {
				socker = new ServerSocket(port);
			}
		} catch (SecurityException se) {
			listeningException(se);
		} catch (IOException e) {
			listeningException(e);
		}
	}

	/** 处理监听出现的异常 */
	public void ioException(IOException e) {
		System.out.println("消息服务器端读写出错！:" + e.toString());
	}

	/** 服务器开始监听之前被调用 */
	public void serverStarted() {
		System.out.println("消息服务器开始运行！");
	}

	/** 服务器停止时被调用 */
	public void serverStoped() {
		System.out.println("消息服务器停止运行！");
	}

}
