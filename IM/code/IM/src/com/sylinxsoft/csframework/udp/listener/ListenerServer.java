package com.sylinxsoft.csframework.udp.listener;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.server.ServerConfig;

public class ListenerServer {

	static ListenerServer listener = new ListenerServer();

	private ListenerServer() {
		try {
			ServerConfig config = ServerConfig.getConfig();
			// �����������Ķ˿�
			String listenerserver = (String) config
					.get(AllConstatns.LISTENER_SERVER);
			String listenerserverPort = (String) config
					.get(AllConstatns.LISTENER_SERVER_PORT);
			InetAddress address = InetAddress.getByName(listenerserver);
			DatagramSocket socket = new DatagramSocket(Integer.parseInt(listenerserverPort));
			while (true) {
				byte[] buffer = new byte[1040];
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length);
				socket.receive(packet);// ���ջ�Ӧ
				String message = new String(packet.getData()); // �õ�������Ϣ
				System.out.println("######:" + message.trim()); // ��ʾ�Է����ص���Ϣ
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ListenerServer getInstance() {
		return listener;
	}

	
	
	public static void main(String[] args) {
		ListenerServer.getInstance();
		System.out.println("��������...");
	}
	
	
	
}
