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
 * ������Ϣ�߳�
 * 
 * 
 */
public class SendMsgClient extends AbstractClient {

	private String msg = new String();
    private List<IErrorReport> lisList = new ArrayList<IErrorReport>();
    
	/**
	 * �������ݵ���������
	 * 
	 * @return
	 */
	protected void sendToServer(OutputStream msgOut) {
		// ������Ϣ�ӿ�
		DataOutputStream output = new DataOutputStream(msgOut);
		try {
			output.writeUTF(msg);
			output.close();
			ServerConfig config = ServerConfig.getConfig();
			try {
				// �����������Ķ˿�
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
				socket.send(packet); // ���ͱ���
			} catch (Exception e) {
				e.printStackTrace();
				reportError("������Ϣʧ��");
			}
		} catch (IOException e) {
			ioException(e);
			reportError("������Ϣʧ��");
		}
	}

	/** ���ô����͵���Ϣ */
	public void setSendMsg(String msg) {
		// ��Ϣ����Э��ͷ���
		this.msg = msg;
	}
	
	public void addErrorReport(IErrorReport r) {
		lisList.add(r);
	}
	
	/**
	 * ���淢��ʧ��
	 * @param msg
	 */
	public void reportError(String errorMsg) {
		for (IErrorReport re : lisList) {
			re.reportError(msg,errorMsg);
		}
	}

}
