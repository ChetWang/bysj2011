package com.sylinxsoft.csframework.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.client.ClientConfig;
import com.sylinxsoft.csframework.client.SendMessage;

/**
 * 监听服务器线程
 * 
 * @author Administrator 2007-9-28 ListenerServer.java
 */
public class ListenerServer extends AbstarctServer {

	public ListenerServer(ServerObservable serverObservable) {
		super(serverObservable);
	}

	/** 初始化监听参数，并根据参数创建套接字 */
	public void initListen() {
		try {
			// 获得监听端口
			ServerConfig config = ServerConfig.getConfig();
			// 打开始终监听端口
			socker = new ServerSocket(Integer.parseInt((String) config
					.get(AllConstatns.INITLISTENPORT)));

		} catch (SecurityException se) {
			listeningException(se);
		} catch (IOException e) {
			listeningException(e);
		}
	}

	/** 服务器开始监听之前被调用 */
	public void serverStarted() {
		System.out.println("服务器开始运行！");
	}

	/** 服务器停止时被调用 */

	public void serverStoped() {
		System.out.println("服务器停止运行！");
	}

	/** 发送消息到客户端 */
	public void sendMsgToClient(OutputStream out) {
		DataOutputStream output = new DataOutputStream(out);
		try {
			ServerConfig config = ServerConfig.getConfig();
			ClientConfig clientConfig = ClientConfig.getConfig();
			String body = AllConstatns.MSGTOKEN + AllConstatns.RECVMSGPORT
					+ AllConstatns.MSGTOKEN
					+ config.get(AllConstatns.RECVMSGPORT)
					+ AllConstatns.MSGTOKEN + AllConstatns.CLIENTNAME
					+ AllConstatns.MSGTOKEN
					+ clientConfig.get(AllConstatns.CLIENTNAME)
					+ AllConstatns.MSGTOKEN + AllConstatns.CLIENTIP
					+ AllConstatns.MSGTOKEN
					+ InetAddress.getLocalHost().getHostAddress();
			String hearder = AllConstatns.PROTOCOLSTARTMARK
					+ AllConstatns.INITONLINEPCPROTOCOL
					+ AllConstatns.PROTOCOLENDMARK;
			output.writeUTF(hearder + body);
			output.close();
		} catch (IOException e) {
			ioException(e);
		}
	}

	public void listeningException(Exception e) {
		System.out.println("监听出错！:" + e.toString());
	}

	/** 处理监听出现的异常 */
	public void ioException(IOException e) {
		System.out.println("服务器端读写出错！:" + e.toString());
	}

}
