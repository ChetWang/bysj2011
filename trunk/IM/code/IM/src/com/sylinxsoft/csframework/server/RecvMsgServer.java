package com.sylinxsoft.csframework.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * ������Ϣ�߳�
 * 
 * @author Administrator 2007-9-28 RecvMsgServer.java
 */
public class RecvMsgServer extends AbstarctServer {

	public RecvMsgServer(ServerObservable serverObservable) {
		super(serverObservable);
	}

	/** ��ʼ�����������������ݲ��������׽��� */
	public void initListen() {
		try {
			// ��ü����˿�
			ServerConfig config = ServerConfig.getConfig();
			// �򿪽�����Ϣ�˿�
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

	/** ����������ֵ��쳣 */
	public void ioException(IOException e) {
		System.out.println("��Ϣ�������˶�д����:" + e.toString());
	}

	/** ��������ʼ����֮ǰ������ */
	public void serverStarted() {
		System.out.println("��Ϣ��������ʼ���У�");
	}

	/** ������ֹͣʱ������ */
	public void serverStoped() {
		System.out.println("��Ϣ������ֹͣ���У�");
	}

}
