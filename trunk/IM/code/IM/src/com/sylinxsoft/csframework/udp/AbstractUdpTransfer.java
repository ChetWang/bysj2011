package com.sylinxsoft.csframework.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.sylinxsoft.csframework.client.ClientConfig;
import com.sylinxsoft.csframework.server.ServerObservable;

/**
 * Udp��������࣬������Թ������ͺͽ���UDP���ݱ� ע����ಢ������UDP����ʧ����������Դ���Ķ������ֽ�Ӧ��С��MTU��
 * ������MTU�İ����ᱻ����,MTU�ɸ����Լ�ʹ�õ�����ƽ̨Э��ȷ����
 * 
 * @author Administrator
 * 
 */
public class AbstractUdpTransfer {

	/** �洢�����ļ������� */
	private List<NewPcEvent> listeners = new ArrayList<NewPcEvent>();

	/** localPort����UDP�˿�,remotePortԶ��UDP�˿� */
	private int localPort, remotePort;
	/** ע������Ҫͬʱ���ͺͽ��գ�����ָ��localPort */
	private MulticastSocket datagramScoket = null;

	/** Զ��������ַ */
	private String remoteIpAddress = null;

	/** �����߳� */
	private Thread receiverThread = null;

	/** �����߳� */
	private Thread senderThread = null;

	/** �����͵����� */
	private Object obj;

	private static AbstractUdpTransfer udpTransfer = null;

	public AbstractUdpTransfer() {

	}

	public AbstractUdpTransfer(int port) {
		setLocalPort(port);
	}

	/**
	 * ��̬����������ȫ��ʹ�ã��˲����ϸ���ģʽ
	 * 
	 * @param port
	 * @return
	 */
	public static AbstractUdpTransfer getInstatnce() {
		if (null == udpTransfer) {
			int port = Integer.parseInt((String) ClientConfig.getConfig().get(
					"multicast-localport"));
			
			udpTransfer = new AbstractUdpTransfer(port);
		}
		return udpTransfer;
	}

	/** �����˿� */
	public void setLocalPort(int port) {
		this.localPort = port;
		// �ڱ��ض˿ڼ���
		try {
			InetAddress group = null; // �鲥��ĵ�ַ.
			MulticastSocket socket = null; // ���㲥�׽���.
			group = InetAddress.getByName((String) ClientConfig.getConfig()
					.get("client-group")); // ���ù㲥��ĵ�ַΪ239.255.8.0��
			datagramScoket = new MulticastSocket(port); // ���㲥�׽��ֽ���port�˿ڹ㲥��
			datagramScoket.setTimeToLive(1); // ���㲥�׽��ַ������ݱ���ΧΪ�������硣
			datagramScoket.joinGroup(group); // ����㲥��,����group��,socket���͵����ݱ�
			datagramScoket.setBroadcast(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����һ�����ݰ�
	 */
	public void sendPacket(Object packet) {
		if (datagramScoket != null) {
			// �ֽ���
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				// װ�γɶ�����
				oos = new ObjectOutputStream(baos);
				// �����������л�
				oos.writeObject(packet);
				oos.flush();
				// ������л��ĵ��ֽ�
				byte[] arr = baos.toByteArray();

				if (baos != null) {
					baos.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (arr == null) {
					System.out.println("sendPacket error!");
					return;
				}
				// System.out.println("д�볤��:" + arr.length);
				// ����Щ�ֽڷ�װ�����ݱ�
				DatagramPacket dataPacket = new DatagramPacket(arr, arr.length,
						InetAddress.getByName(remoteIpAddress), remotePort);
				// ���͸����ݱ���
				datagramScoket.send(dataPacket);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ý��շ��������ַ
	 * 
	 * @param remoteIpAddress
	 */
	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	/**
	 * ���ý��շ��Ķ˿�
	 * 
	 * @param port
	 */
	public void setRemoteIpPort(int port) {
		this.remotePort = port;
	}

	/** ���һ�������� */
	public void addTransferListener(NewPcEvent listener) {
		for (int i = 0; i < listeners.size(); i++) {
			if (listeners.get(i) == listener) {
				return;
			}
		}
		listeners.add(listener);
	}

	/**
	 * ֪ͨ���еļ�����
	 * 
	 * @param packetSend
	 * @param packet
	 */
	private void receivePacket(String ip, Object packet) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).addNewPc(ip, packet);
		}
		// System.out.println(packet);
	}

	/**
	 * ��ʼ����
	 * 
	 */
	public void sratReceive() {
		receiverThread = new Thread(new ReceiverData());
		receiverThread.start();
	}

	/**
	 * ��ʼ����
	 * 
	 */
	public void startSend() {
		senderThread = new Thread(new SenderData());
		senderThread.start();
	}

	/**
	 * ���͵�����
	 */
	public void setSendData(Object obj) {
		this.obj = obj;
	}

	/**
	 * ���������߳�
	 * 
	 * @author yqg
	 * 
	 */
	class ReceiverData implements Runnable {

		/**
		 * ѭ����������
		 */
		public void run() {
			try {
				InetAddress group = InetAddress.getByName((String) ClientConfig
						.getConfig().get("client-group"));
				byte[] buffer = new byte[PacketUnit.MTU + 300];

				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length, group, localPort);

				if (datagramScoket != null) {
					while (true) {
						// ���յ�����
						datagramScoket.receive(packet);
						ByteArrayInputStream bais = new ByteArrayInputStream(
								packet.getData());
						ObjectInputStream ois = null;
						// ���ֽ���װ�γɶ�����
						ois = new ObjectInputStream(bais);
						Object packetUnit = ois.readObject();
						// System.out.println(packetUnit.toString());
						// ���ܸö���
						// System.out.println(packet.getAddress().getHostAddress());
						receivePacket(packet.getAddress().getHostAddress(),
								packetUnit);

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * ���������߳�
	 * 
	 * @author yqg
	 * 
	 */
	class SenderData implements Runnable {
		public void run() {
			sendPacket(obj);
		}
	}

	public static void main(String[] args) {
		/*
		 * AbstractUdpTransfer fer1 = new AbstractUdpTransfer();
		 * fer1.setSendData("fdsafda"); fer1.setLocalPort(10008);
		 * fer1.setRemoteIpPort(10008); fer1.setRemoteIpAddress("127.0.0.1");
		 * fer1.sratReceive(); fer1.startSend(); fer1.startSend();
		 * fer1.startSend();
		 */
		/*
		 * FileTransfer fer2 = new FileTransfer(); fer2.setLocalPort(10009);
		 * fer2.setRemoteIpPort(10008); fer2.setRemoteIpAddress("127.0.0.1");
		 * fer2.startSend();
		 * 
		 */

	}

}
