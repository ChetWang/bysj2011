package com.sylinxsoft.csframework.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ServerObservable;

/**
 * 规定客户端的抽象接口，此类提供客户端类所需要的钩子，插槽，以及一些必要的方法。
 * <p>
 * 
 * @author Administrator 2007-9-28 AbstractClient.java
 */
public abstract class AbstractClient implements Runnable {

	protected Socket socker = null;

	protected SocketAddress address = null;



	/** 标记是否关闭 */
	private boolean closed = false;

	/**
	 * @param clientManager
	 *            客户端组管理
	 */
	public void run() {
		connectionStarted();
		// 初始化连接地址
		initSocketAddress();
		if (address != null) {
			try {
				// 建立一个空连接
				Socket socker = new Socket();
				// 连接并置超过5秒返回，此时若超时，则被捕获
				socker.connect(address, 2000);
				// 如果没有关闭，其将不断发送和接收数据.
				OutputStream msgOut = socker.getOutputStream();
				InputStream msgIn = socker.getInputStream();
				// 发送消息
				sendToServer(msgOut);
				// 接受消息
				handleMsgFromServer(msgIn);
				// 关闭连接
				socker.close();
				connectionClosed();
			} catch (SocketException e) {
				reportError("网络连接失败!");
				connectException(e);
			} catch (IOException e) {
				reportError("网络连接失败!");
				ioException(e);
			}
		}

	}

	/** 初始化连接地址和端口 */
	protected void initSocketAddress() {

	}

	/**
	 * 先判断IP是否可达，不可达则返回false,否则返回true. 如果可达，则创建SocketAddress对象
	 */
	public boolean setIpAddress(String ipAddress, int port) {
		address = new InetSocketAddress(ipAddress, port);
		return true;
		/*
		 * InetAddress ip; try { ip = InetAddress.getByName(ipAddress); //
		 * 先判断IP是否可达，不可达则返回false if (ip.isReachable(2000)) { address = new
		 * InetSocketAddress(ipAddress, port); return true; } } catch
		 * (UnknownHostException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); } return false;
		 */
	}

	/** 用来处理连接的出现异常 */
	public void connectException(Exception e) {
	}

	/** 用来处理连接的出现异常 */
	public void ioException(IOException e) {
	}

	/** 处理连接之前 */
	public void connectionStarted() {
	}

	/** 关闭连接时，此方法被调用 */
	public void connectionClosed() {
	}

	/** 关闭连接 */
	public void closeConnection() {
		closed = true;
	}

	/**
	 * 发送数据到服务器端
	 * 
	 * @return
	 */
	protected void sendToServer(OutputStream msgOut) {

	}

	/** 设置待发送的消息 */
	public void setSendMsg(String mag) {

	}

	/**
	 * 处理从服务器端发送过来的消息
	 * 
	 */
	public void handleMsgFromServer(InputStream msgIn) {
	}

	/**
	 * 创建一个线程并启动,这个线程外界不能控制，若想 外界控制，请在外面建个线程来启动，不要用此方法。
	 */
	public void start() {
		Thread clientThread = new Thread(this);
		clientThread.start();
	}

	/**
	 * 报告错误
	 * @param msg
	 */
	protected void reportError(String errorMsg) {
		 
	}
}
