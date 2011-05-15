package com.sylinxsoft.csframework.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.IErrorReport;
import com.sylinxsoft.csframework.server.ServerConfig;

/**
 * 发送消息线程
 * 
 * 
 */
public class SendMsgClient extends AbstractClient {

	private String msg = new String();
    private List<IErrorReport> lisList = new ArrayList<IErrorReport>();
    
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
			ServerConfig config = ServerConfig.getConfig();
			try {
				// 监听服务器的端口
				String listenerserver = (String) config
						.get(AllConstatns.LISTENER_SERVER);
				String listenerserverPort = (String) config
						.get(AllConstatns.LISTENER_SERVER_PORT);
				InetAddress address = InetAddress.getByName(listenerserver);
				DatagramSocket socket = new DatagramSocket();

				byte[] buffer = (msg.getBytes());
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length, address, Integer
								.parseInt(listenerserverPort));
				socket.send(packet); // 发送报文
			} catch (Exception e) {
				e.printStackTrace();
				reportError("发送消息失败");
			}
		} catch (IOException e) {
			ioException(e);
			reportError("发送消息失败");
		}
	}

	/** 设置待发送的消息 */
	public void setSendMsg(String msg) {
		// 消息加上协议头标记
		this.msg = msg;
	}
	
	public void addErrorReport(IErrorReport r) {
		lisList.add(r);
	}
	
	/**
	 * 报告发送失败
	 * @param msg
	 */
	public void reportError(String errorMsg) {
		for (IErrorReport re : lisList) {
			re.reportError(msg,errorMsg);
		}
	}

}
