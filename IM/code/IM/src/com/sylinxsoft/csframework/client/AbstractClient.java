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
 * �涨�ͻ��˵ĳ���ӿڣ������ṩ�ͻ���������Ҫ�Ĺ��ӣ���ۣ��Լ�һЩ��Ҫ�ķ�����
 * <p>
 * 
 * @author Administrator 2007-9-28 AbstractClient.java
 */
public abstract class AbstractClient implements Runnable {

	protected Socket socker = null;

	protected SocketAddress address = null;



	/** ����Ƿ�ر� */
	private boolean closed = false;

	/**
	 * @param clientManager
	 *            �ͻ��������
	 */
	public void run() {
		connectionStarted();
		// ��ʼ�����ӵ�ַ
		initSocketAddress();
		if (address != null) {
			try {
				// ����һ��������
				Socket socker = new Socket();
				// ���Ӳ��ó���5�뷵�أ���ʱ����ʱ���򱻲���
				socker.connect(address, 2000);
				// ���û�йرգ��佫���Ϸ��ͺͽ�������.
				OutputStream msgOut = socker.getOutputStream();
				InputStream msgIn = socker.getInputStream();
				// ������Ϣ
				sendToServer(msgOut);
				// ������Ϣ
				handleMsgFromServer(msgIn);
				// �ر�����
				socker.close();
				connectionClosed();
			} catch (SocketException e) {
				reportError("��������ʧ��!");
				connectException(e);
			} catch (IOException e) {
				reportError("��������ʧ��!");
				ioException(e);
			}
		}

	}

	/** ��ʼ�����ӵ�ַ�Ͷ˿� */
	protected void initSocketAddress() {

	}

	/**
	 * ���ж�IP�Ƿ�ɴ���ɴ��򷵻�false,���򷵻�true. ����ɴ�򴴽�SocketAddress����
	 */
	public boolean setIpAddress(String ipAddress, int port) {
		address = new InetSocketAddress(ipAddress, port);
		return true;
		/*
		 * InetAddress ip; try { ip = InetAddress.getByName(ipAddress); //
		 * ���ж�IP�Ƿ�ɴ���ɴ��򷵻�false if (ip.isReachable(2000)) { address = new
		 * InetSocketAddress(ipAddress, port); return true; } } catch
		 * (UnknownHostException e) { e.printStackTrace(); } catch (IOException
		 * e) { e.printStackTrace(); } return false;
		 */
	}

	/** �����������ӵĳ����쳣 */
	public void connectException(Exception e) {
	}

	/** �����������ӵĳ����쳣 */
	public void ioException(IOException e) {
	}

	/** ��������֮ǰ */
	public void connectionStarted() {
	}

	/** �ر�����ʱ���˷��������� */
	public void connectionClosed() {
	}

	/** �ر����� */
	public void closeConnection() {
		closed = true;
	}

	/**
	 * �������ݵ���������
	 * 
	 * @return
	 */
	protected void sendToServer(OutputStream msgOut) {

	}

	/** ���ô����͵���Ϣ */
	public void setSendMsg(String mag) {

	}

	/**
	 * ����ӷ������˷��͹�������Ϣ
	 * 
	 */
	public void handleMsgFromServer(InputStream msgIn) {
	}

	/**
	 * ����һ���̲߳�����,����߳���粻�ܿ��ƣ����� �����ƣ��������潨���߳�����������Ҫ�ô˷�����
	 */
	public void start() {
		Thread clientThread = new Thread(this);
		clientThread.start();
	}

	/**
	 * �������
	 * @param msg
	 */
	protected void reportError(String errorMsg) {
		 
	}
}
