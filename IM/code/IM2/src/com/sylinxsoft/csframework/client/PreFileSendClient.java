package com.sylinxsoft.csframework.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ClientInfor;
import com.sylinxsoft.csframework.server.ClientInforInterface;

public class PreFileSendClient extends AbstractClient {
	
	private String msg = new String();
	
	/** 用来处理连接的出现异常 */
	public void connectException(Exception e) {
		System.out.println("连接出错：" + e.toString());
	}
	
	
	/**
	 * 发送数据到服务器端
	 * 
	 * @return
	 */
	protected void sendToServer(OutputStream msgOut) {
		// 发送消息接口
		DataOutputStream output = new DataOutputStream(msgOut);
		try {
			output.writeUTF(msg);
			output.close();
		} catch (IOException e) {
			ioException(e);
		}
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
	
	/** 设置待发送的消息 */
	public void setSendMsg(String msg) {
		// 消息加上协议头标记
		this.msg = msg;
	}
}
