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
 * �����������߳�
 * 
 * @author Administrator 2007-9-28 ListenerServer.java
 */
public class ListenerServer extends AbstarctServer {

	public ListenerServer(ServerObservable serverObservable) {
		super(serverObservable);
	}

	/** ��ʼ�����������������ݲ��������׽��� */
	public void initListen() {
		try {
			// ��ü����˿�
			ServerConfig config = ServerConfig.getConfig();
			// ��ʼ�ռ����˿�
			socker = new ServerSocket(Integer.parseInt((String) config
					.get(AllConstatns.INITLISTENPORT)));

		} catch (SecurityException se) {
			listeningException(se);
		} catch (IOException e) {
			listeningException(e);
		}
	}

	/** ��������ʼ����֮ǰ������ */
	public void serverStarted() {
		System.out.println("��������ʼ���У�");
	}

	/** ������ֹͣʱ������ */

	public void serverStoped() {
		System.out.println("������ֹͣ���У�");
	}

	/** ������Ϣ���ͻ��� */
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
		System.out.println("��������:" + e.toString());
	}

	/** ����������ֵ��쳣 */
	public void ioException(IOException e) {
		System.out.println("�������˶�д����:" + e.toString());
	}

}
