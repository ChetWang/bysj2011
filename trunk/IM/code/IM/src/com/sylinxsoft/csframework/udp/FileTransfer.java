package com.sylinxsoft.csframework.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.net.InetAddress;

/**
 * �ļ��������
 * 
 * @author yqg
 * 
 */
public class FileTransfer implements PacketSendInterface {

	/** �洢�����ļ������� */
	private List<TransferListener> listeners = new ArrayList<TransferListener>();

	private int localPort, remotePort;

	private DatagramSocket datagramScoket = null;

	private String remoteIpAddress = null;

	/** �����߳� */
	private Thread receiverThread = null;

	/** �����߳� */
	private Thread senderThread = null;

	public FileTransfer() {

	}

	public FileTransfer(int port) {
		setLocalPort(port);
	}

	/** �����˿� */
	public void setLocalPort(int port) {
		this.localPort = port;
		// �ڱ��ض˿ڼ���
		try {
			datagramScoket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * ����һ�����ݰ�
	 */
	public void sendPacket(PacketUnit packet) {

		if (datagramScoket != null) {
			//�ֽ���
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				//װ�γɶ�����
				oos = new ObjectOutputStream(baos);
				//�����������л�
				oos.writeObject(packet);
				oos.flush();
				//������л��ĵ��ֽ�
				byte[] arr = baos.toByteArray();

				if (baos != null) {
					baos.close();
				}
				if (oos != null) {
					oos.close();
				}
				if (arr == null) {
					System.out.println("5555");
					return;
				}
				System.out.println("д�볤��:" + arr.length);
				//����Щ�ֽڷ�װ�����ݱ�
				DatagramPacket dataPacket = new DatagramPacket(arr, arr.length,
						InetAddress.getByName(remoteIpAddress), remotePort);
				//���͸����ݱ���
				datagramScoket.send(dataPacket);

			} catch (Exception e) {
				System.out.println(e);
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
	 * ���ý��շŵĶ˿�
	 * 
	 * @param port
	 */
	public void setRemoteIpPort(int port) {
		this.remotePort = port;
	}

	/** ���һ�������� */
	public void addTransferListener(TransferListener listener) {
		for (int i = 0; i < listeners.size(); i++) {
			if (listeners.get(i) == listener) {
				return;
			}
		}
		listeners.add(listener);
	}

	public PacketSendInterface getInstanse() {
		return this;
	}

	/**
	 * ֪ͨ���еļ�����
	 * 
	 * @param packetSend
	 * @param packet
	 */
	private void receivePacket(PacketSendInterface packetSend, PacketUnit packet) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).receivePacket(packetSend, packet);
		}
		
		System.out.println("�յ�����");
		
		if (packet != null) {
			System.out.println(packet.getCommand());
			System.out.println(new String(packet.getData()));
		}
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
			byte[] buffer = new byte[PacketUnit.MTU + 300];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			if (datagramScoket != null) {
				while (true) {
					try {
						// ���յ�����
						datagramScoket.receive(packet);
						System.out.println("��������:" + packet.getData().length);
						ByteArrayInputStream bais = new ByteArrayInputStream(
								packet.getData());
						// System.out.println(new String(packet.getData()));
						ObjectInputStream ois = null;
						//���ֽ���װ�γɶ�����
						ois = new ObjectInputStream(bais);
						PacketUnit packetUnit = (PacketUnit) ois.readObject();
						
						//���ܸö���
						receivePacket(getInstanse(), packetUnit);
						

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
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
		/**
		 * ��������
		 */
		public void run() {
		 while (true) {
			PacketUnit pack = new PacketUnit();
			pack.setCommand(FileCommand.FrCanc);
			pack.setDataSize(Long.MAX_VALUE);
			pack.setFileMark(Short.MAX_VALUE);
			pack.setPosition(Long.MAX_VALUE);
			byte[] temp = new byte[PacketUnit.DATAMTU];
			temp[0]='f';
			temp[PacketUnit.DATAMTU-1]='f';
			pack.setData(temp);
			
			System.out.println("��������");
			sendPacket(pack);
			try {
			   Thread.sleep(2000);
			} catch (Exception e) {}
			}

		}
	}

	public static void main(String[] args) {
		FileTransfer fer1 = new FileTransfer();
		fer1.setLocalPort(10008);
		fer1.setRemoteIpPort(10009);
		fer1.setRemoteIpAddress("127.0.0.1");
		fer1.sratReceive();

		FileTransfer fer2 = new FileTransfer();
		fer2.setLocalPort(10009);
		fer2.setRemoteIpPort(10008);
		fer2.setRemoteIpAddress("127.0.0.1");
		fer2.startSend();

	}

}
