package com.sylinxsoft.csframework.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import ui.MskFrame;


/**
 * 抽象服务器类，此类提供服务器类所需要的钩子，插槽，以及一些必要的方法。
 * <P>
 * AbstarctServer.java
 */
public abstract class AbstarctServer implements Runnable {

	/** 监听套接字,此套接字在initListen被处始化 */
	protected ServerSocket socker = null;

	private boolean closed = false;

	protected int port = -1;

	/** 记录服务次数 */
	private int serverTimes = 0;

	/** 最大服务次数 */
	private int maxServerTimes = 1;

	/** 是否设置最大服务次数 */
	private boolean isSetMaxServerTimes = false;

	/**
	 * 此方法有机会改变服务器监听的端口
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/** 客户端组管理器 */
	protected ClientGroupManager clientGroupManager = null;

	/** 服务器端被观察者 */
	protected ServerObservable serverObservable = null;

	public AbstarctServer() {
		this(null);

	}

	/**
	 * @param clientManager
	 *            客户端组管理
	 */
	public AbstarctServer(ServerObservable serverObservable) {
		this.serverObservable = serverObservable;
		clientGroupManager = ClientGroupManager.getInstance();
	}

	/** 关闭服务器,这里将彻底关闭连接 */
	public void closeServer() {
		if (socker != null) {
			try {
				socker.close();
			} catch (IOException e) {
				ioException(e);
			}
		}
		// 如果设置最大服务次数，说明此端口有可能需要被回收
		if (isSetMaxServerTimes) {
			ServerConfig.getConfig().releasePort(port);
		}
	}

	/** 初始化监听，在此可以完成套接字的建立等，要使用必须重载此方法 */
	public void initListen() {
	}

	/** 服务器开始监听之前被调用 */
	public void serverStarted() {
	}

	/** 服务器关闭时被调用 */
	public void serverClosed() {
	}

	/** 服务器停止时被调用 */
	public void serverStoped() {
	}

	/** 处理监听出现的异常 */
	public void listeningException(Exception e) {
	}

	/** 处理监听出现的异常 */
	public void ioException(IOException e) {
	}

	/** 关闭连接 */
	public void clientConnected() {
	}

	/** 停止服务器，此方法停止监听 */
	public void stopServer() {
		closed = true;
	}

	/**
	 * 线程循环体,不建议重载此方法．
	 * <p>
	 * 若要重载，请将initListen和handleMsgFromClient的实现包含在这个方法体里面，否则
	 * 不能正常工作．注意handleMsgFromClient是在这个方法体内实现
	 * 
	 */
	public void run() {
		serverStarted();
		initListen();
		if (socker != null) {
			try {
				// 在关闭之前还得等一个用户连接上来，此时连接才会关闭，此地方需要改善
				while (!closed && serverTimes < maxServerTimes) {
					// 等待一个用户连接
					final Socket soc = socker.accept();
					// 已经设置最大服务次数，则记录服务次数加1
					if (isSetMaxServerTimes) {
						serverTimes++;
					}
					Thread thread = new Thread(new Runnable() {
						public void run() {
							try {
								// 得到对方的IP
								InetAddress ip = soc.getInetAddress();
								// 通过客户端管理器获得此客户端信息
								ClientInforInterface client = clientGroupManager
										.getClientByIp(ip.getHostAddress());

								//接收到消息，响下声音,并显出窗口来
								for (int i = 0; i < 5; i++) { //  发 声 提 示
									java.awt.Toolkit.getDefaultToolkit().beep();
								}
								MskFrame.getInstance().setLastMsgIpAddress(ip.getHostAddress());
								if (JFrame.ICONIFIED == MskFrame.getInstance().getExtendedState()){
									MskFrame.getInstance().setExtendedState(JFrame.NORMAL);
								}
								MskFrame.getInstance().setVisible(true);
								// 交给具体类处理
								handleMsgFromClient(client, soc
										.getInputStream());
								sendMsgToClient(soc.getOutputStream());
							} catch (Exception e) {
								listeningException(e);
							} finally {
								try {
									// 关闭连接
									soc.close();
								} catch (Exception e) {
								}
							}
						}

					});
					thread.start();
				}
				// 关闭服务器
				closeServer();
				serverStoped();
			} catch (Exception e) {
				listeningException(e);
			}

		}
	}

	/**
	 * 设置最大的服务次数，超过此服务次数，则服务器自动关闭。 可以通过此方法预先决定服务器什么时候停止。
	 * 
	 * @param maxServerTimes
	 *            最大的服务次数
	 */
	public void setMaxServerTimes(int maxServerTimes) {
		this.maxServerTimes = maxServerTimes;
		isSetMaxServerTimes = true;
	}

	/** 发送消息到客户端 */
	public void sendMsgToClient(OutputStream out) {
	}

	/**
	 * 创建一个线程并启动,这个线程外界不能控制，若想 外界控制，请在外面建个线程来启动，不要用此方法。
	 */
	public void start() {
		Thread serverThread = new Thread(this);
		serverThread.start();
	}

	/**
	 * 处理客户端发送过来的消息，此方法由服务器线程在接收到消息时自动回调。
	 * 
	 */
	protected void handleMsgFromClient(ClientInforInterface client,
			InputStream msgIn) {
		// 创建一个处理消息接口
		HandMsgInterface handMsg = HandMsgFactory.Create(clientGroupManager,
				client, msgIn);
		if (null != handMsg) {
			handMsg.handMessage(clientGroupManager, client, HandMsgFactory
					.getMsg(), serverObservable);
		}
	}

	protected void finalize() throws IOException {
		if (socker != null) {
			socker.close();
		}
	}

}
