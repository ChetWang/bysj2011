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
 * ��ʼ��ʱ�����������������໥����
 * 
 * @author Administrator 2007-9-29 InitOnlinePc.java
 */
public class InitOnlinePc extends AbstractClient {

	/** ��ʼ�����ӵ�ַ�Ͷ˿� */
	protected void initSocketAddress() {
		// System.out.println("fff");
	}

	/** �����������ӵĳ����쳣 */
	public void connectException(Exception e) {
		System.out.println("���ӳ���" + e.toString());
	}

	/**
	 * �������ݵ���������
	 * 
	 * @return
	 */
	public void sendToServer(OutputStream msgOut) {
		// ������Ϣ�ӿ�
		SendMessage send = new SendMsg();
		send.sendMessage(msgOut);
	}

	/**
	 * ����ӷ������˷��͹�������Ϣ
	 * 
	 */
	public void handleMsgFromServer(InputStream msgIn) {
		String msg = new String();
		try {
			DataInputStream in = new DataInputStream(msgIn);
			msg = in.readUTF();
			// ��ȡ������ϢЭ��
			String protocol = msg.substring(msg
					.indexOf(AllConstatns.PROTOCOLSTARTMARK)
					+ AllConstatns.PROTOCOLSTARTMARK.length(), msg
					.indexOf(AllConstatns.PROTOCOLENDMARK));
			// ��ȡ��Ϣ�������
			msg = msg.substring(msg.indexOf(AllConstatns.MSGTOKEN)
					+ AllConstatns.MSGTOKEN.length(), msg.length());
			StringTokenizer msgBuffer = new StringTokenizer(msg,
					AllConstatns.MSGTOKEN);
			// ����һ���ͻ���
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
			System.out.println("����ӷ������˷��͹�������Ϣ����:" + e.toString());
		}
	}

	/** �����������ӵĳ����쳣 */
	public void ioException(IOException e) {
		System.out.println("��д����!" + e.toString());
	}

	/**
	 * ��װ������Ϣ
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
