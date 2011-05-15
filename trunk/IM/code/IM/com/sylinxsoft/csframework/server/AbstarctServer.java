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
 * ����������࣬�����ṩ������������Ҫ�Ĺ��ӣ���ۣ��Լ�һЩ��Ҫ�ķ�����
 * <P>
 * AbstarctServer.java
 */
public abstract class AbstarctServer implements Runnable {

	/** �����׽���,���׽�����initListen����ʼ�� */
	protected ServerSocket socker = null;

	private boolean closed = false;

	protected int port = -1;

	/** ��¼������� */
	private int serverTimes = 0;

	/** ��������� */
	private int maxServerTimes = 1;

	/** �Ƿ�������������� */
	private boolean isSetMaxServerTimes = false;

	/**
	 * �˷����л���ı�����������Ķ˿�
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/** �ͻ���������� */
	protected ClientGroupManager clientGroupManager = null;

	/** �������˱��۲��� */
	protected ServerObservable serverObservable = null;

	public AbstarctServer() {
		this(null);

	}

	/**
	 * @param clientManager
	 *            �ͻ��������
	 */
	public AbstarctServer(ServerObservable serverObservable) {
		this.serverObservable = serverObservable;
		clientGroupManager = ClientGroupManager.getInstance();
	}

	/** �رշ�����,���ｫ���׹ر����� */
	public void closeServer() {
		if (socker != null) {
			try {
				socker.close();
			} catch (IOException e) {
				ioException(e);
			}
		}
		// ������������������˵���˶˿��п�����Ҫ������
		if (isSetMaxServerTimes) {
			ServerConfig.getConfig().releasePort(port);
		}
	}

	/** ��ʼ���������ڴ˿�������׽��ֵĽ����ȣ�Ҫʹ�ñ������ش˷��� */
	public void initListen() {
	}

	/** ��������ʼ����֮ǰ������ */
	public void serverStarted() {
	}

	/** �������ر�ʱ������ */
	public void serverClosed() {
	}

	/** ������ֹͣʱ������ */
	public void serverStoped() {
	}

	/** ����������ֵ��쳣 */
	public void listeningException(Exception e) {
	}

	/** ����������ֵ��쳣 */
	public void ioException(IOException e) {
	}

	/** �ر����� */
	public void clientConnected() {
	}

	/** ֹͣ���������˷���ֹͣ���� */
	public void stopServer() {
		closed = true;
	}

	/**
	 * �߳�ѭ����,���������ش˷�����
	 * <p>
	 * ��Ҫ���أ��뽫initListen��handleMsgFromClient��ʵ�ְ�����������������棬����
	 * ��������������ע��handleMsgFromClient���������������ʵ��
	 * 
	 */
	public void run() {
		serverStarted();
		initListen();
		if (socker != null) {
			try {
				// �ڹر�֮ǰ���õ�һ���û�������������ʱ���ӲŻ�رգ��˵ط���Ҫ����
				while (!closed && serverTimes < maxServerTimes) {
					// �ȴ�һ���û�����
					final Socket soc = socker.accept();
					// �Ѿ�������������������¼���������1
					if (isSetMaxServerTimes) {
						serverTimes++;
					}
					Thread thread = new Thread(new Runnable() {
						public void run() {
							try {
								// �õ��Է���IP
								InetAddress ip = soc.getInetAddress();
								// ͨ���ͻ��˹�������ô˿ͻ�����Ϣ
								ClientInforInterface client = clientGroupManager
										.getClientByIp(ip.getHostAddress());

								//���յ���Ϣ����������,���Գ�������
								for (int i = 0; i < 5; i++) { //  �� �� �� ʾ
									java.awt.Toolkit.getDefaultToolkit().beep();
								}
								MskFrame.getInstance().setLastMsgIpAddress(ip.getHostAddress());
								if (JFrame.ICONIFIED == MskFrame.getInstance().getExtendedState()){
									MskFrame.getInstance().setExtendedState(JFrame.NORMAL);
								}
								MskFrame.getInstance().setVisible(true);
								// ���������ദ��
								handleMsgFromClient(client, soc
										.getInputStream());
								sendMsgToClient(soc.getOutputStream());
							} catch (Exception e) {
								listeningException(e);
							} finally {
								try {
									// �ر�����
									soc.close();
								} catch (Exception e) {
								}
							}
						}

					});
					thread.start();
				}
				// �رշ�����
				closeServer();
				serverStoped();
			} catch (Exception e) {
				listeningException(e);
			}

		}
	}

	/**
	 * �������ķ�������������˷����������������Զ��رա� ����ͨ���˷���Ԥ�Ⱦ���������ʲôʱ��ֹͣ��
	 * 
	 * @param maxServerTimes
	 *            ���ķ������
	 */
	public void setMaxServerTimes(int maxServerTimes) {
		this.maxServerTimes = maxServerTimes;
		isSetMaxServerTimes = true;
	}

	/** ������Ϣ���ͻ��� */
	public void sendMsgToClient(OutputStream out) {
	}

	/**
	 * ����һ���̲߳�����,����߳���粻�ܿ��ƣ����� �����ƣ��������潨���߳�����������Ҫ�ô˷�����
	 */
	public void start() {
		Thread serverThread = new Thread(this);
		serverThread.start();
	}

	/**
	 * ����ͻ��˷��͹�������Ϣ���˷����ɷ������߳��ڽ��յ���Ϣʱ�Զ��ص���
	 * 
	 */
	protected void handleMsgFromClient(ClientInforInterface client,
			InputStream msgIn) {
		// ����һ��������Ϣ�ӿ�
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
