package com.sylinxsoft.csframework.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ClientInfor;
import com.sylinxsoft.csframework.server.ClientInforInterface;
import com.sylinxsoft.csframework.server.ServerConfig;

/**
 * 初始化时，将所有在线主机相互连接
 * 
 * @author Administrator 2007-9-29 InitOnlinePc.java
 */
public class InitOnlinePc extends AbstractClient {

	/** 初始化连接地址和端口 */
	protected void initSocketAddress() {
		// System.out.println("fff");
	}

	/** 用来处理连接的出现异常 */
	public void connectException(Exception e) {
		System.out.println("连接出错：" + e.toString());
	}

	/**
	 * 发送数据到服务器端
	 * 
	 * @return
	 */
	public void sendToServer(OutputStream msgOut) {
		// 发送消息接口
		SendMessage send = new SendMsg();
		send.sendMessage(msgOut);
	}

	/**
	 * 处理从服务器端发送过来的消息
	 * 
	 */
	public void handleMsgFromServer(InputStream msgIn) {
		String msg = new String();
		try {
			DataInputStream in = new DataInputStream(msgIn);
			msg = in.readUTF();
			// 提取处理消息协议
			String protocol = msg.substring(msg
					.indexOf(AllConstatns.PROTOCOLSTARTMARK)
					+ AllConstatns.PROTOCOLSTARTMARK.length(), msg
					.indexOf(AllConstatns.PROTOCOLENDMARK));
			// 提取消息体的内容
			msg = msg.substring(msg.indexOf(AllConstatns.MSGTOKEN)
					+ AllConstatns.MSGTOKEN.length(), msg.length());
			StringTokenizer msgBuffer = new StringTokenizer(msg,
					AllConstatns.MSGTOKEN);
			// 创建一个客户端
			ClientInforInterface clientInfor = new ClientInfor();
			while (msgBuffer.hasMoreTokens()) {
				String key = msgBuffer.nextToken();
				String value = new String();
				if (msgBuffer.hasMoreTokens()) {
					value = msgBuffer.nextToken();
				}
				clientInfor.setInfor(key, value);
			}
			ClientGroupManager.getInstance().addClient(clientInfor);
		} catch (Exception e) {
			System.out.println("处理从服务器端发送过来的消息出错:" + e.toString());
		}
	}

	/** 用来处理连接的出现异常 */
	public void ioException(IOException e) {
		System.out.println("读写出错!" + e.toString());
	}

	/**
	 * 封装发送消息
	 * 
	 * @author Administrator 2007-9-29 InitOnlinePc.java
	 */
	class SendMsg implements SendMessage {
		public void sendMessage(OutputStream msgOut) {
			DataOutputStream output = new DataOutputStream(msgOut);
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
			} catch (IOException e) {
				ioException(e);
			}
		}
	}

}
