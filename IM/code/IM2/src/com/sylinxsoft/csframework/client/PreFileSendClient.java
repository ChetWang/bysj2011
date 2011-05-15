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
	
	/** �����������ӵĳ����쳣 */
	public void connectException(Exception e) {
		System.out.println("���ӳ���" + e.toString());
	}
	
	
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
		} catch (IOException e) {
			ioException(e);
		}
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
	
	/** ���ô����͵���Ϣ */
	public void setSendMsg(String msg) {
		// ��Ϣ����Э��ͷ���
		this.msg = msg;
	}
}
