package com.sylinxsoft.csframework.server;

import java.io.DataInputStream;
import java.io.InputStream;

import com.sylinxsoft.csframework.AllConstatns;

/**
 * ������Ϣ�Ĺ���,����ģʽӦ��
 * 
 * 
 */
public class HandMsgFactory {

	/** ��Ϣ */
	private static String msg = new String();

	/** �����Ϣ */
	public static String getMsg() {
		return msg;
	}

	/** ����һ����Ϣ����ʵ�� */
	public static HandMsgInterface Create(ClientGroupManager clientManager,
			ClientInforInterface client, InputStream msgIn) {
		HandMsgInterface handMsg = null;
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
			//msgIn.close();

			// ����������Ϣ��Э����
			handMsg = Create(protocol);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return handMsg;
	}

	private static HandMsgInterface Create(String protocol) {

		// �����ϢЭ��ӳ��
		MsgMapping msgMapping = MsgMapping.getConfig();
		// ȡ�ø�Э���Ӧ�Ĵ�����
		String handMsgClass = (String) (msgMapping.get(protocol));
		// ��̬���ظ�Э�鴦����
		try {
			// ���ظô�����
			Class o = Class.forName(handMsgClass);
			// ����ת�ͷ���
			return (HandMsgInterface) (o.newInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
