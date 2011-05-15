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
 * 文件传输对象
 * 
 * @author yqg
 * 
 */
public class FileTransfer implements PacketSendInterface {

	/** 存储传输文件监听者 */
	private List<TransferListener> listeners = new ArrayList<TransferListener>();

	private int localPort, remotePort;

	private DatagramSocket datagramScoket = null;

	private String remoteIpAddress = null;

	/** 接收线程 */
	private Thread receiverThread = null;

	/** 发送线程 */
	private Thread senderThread = null;

	public FileTransfer() {

	}

	public FileTransfer(int port) {
		setLocalPort(port);
	}

	/** 监听端口 */
	public void setLocalPort(int port) {
		this.localPort = port;
		// 在本地端口监听
		try {
			datagramScoket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 发送一个数据包
	 */
	public void sendPacket(PacketUnit packet) {

		if (datagramScoket != null) {
			//字节流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				//装饰成对象流
				oos = new ObjectOutputStream(baos);
				//将对象正序列化
				oos.writeObject(packet);
				oos.flush();
				//获得序列化的的字节
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
				System.out.println("写入长度:" + arr.length);
				//将这些字节封装成数据报
				DatagramPacket dataPacket = new DatagramPacket(arr, arr.length,
						InetAddress.getByName(remoteIpAddress), remotePort);
				//发送该数据报包
				datagramScoket.send(dataPacket);

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * 设置接收方的网络地址
	 * 
	 * @param remoteIpAddress
	 */
	public void setRemoteIpAddress(String remoteIpAddress) {
		this.remoteIpAddress = remoteIpAddress;
	}

	/**
	 * 设置接收放的端口
	 * 
	 * @param port
	 */
	public void setRemoteIpPort(int port) {
		this.remotePort = port;
	}

	/** 添加一个监听者 */
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
	 * 通知所有的监听者
	 * 
	 * @param packetSend
	 * @param packet
	 */
	private void receivePacket(PacketSendInterface packetSend, PacketUnit packet) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).receivePacket(packetSend, packet);
		}
		
		System.out.println("收到数据");
		
		if (packet != null) {
			System.out.println(packet.getCommand());
			System.out.println(new String(packet.getData()));
		}
	}

	/**
	 * 开始接收
	 * 
	 */
	public void sratReceive() {
		receiverThread = new Thread(new ReceiverData());
		receiverThread.start();
	}

	
	
	/**
	 * 开始发送
	 * 
	 */
	public void startSend() {
		senderThread = new Thread(new SenderData());
		senderThread.start();
	}

	
	
	/**
	 * 接收数据线程
	 * 
	 * @author yqg
	 * 
	 */
	class ReceiverData implements Runnable {
		
		/**
		 * 循环接收数据
		 */
		public void run() {
			byte[] buffer = new byte[PacketUnit.MTU + 300];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			if (datagramScoket != null) {
				while (true) {
					try {
						// 接收到数据
						datagramScoket.receive(packet);
						System.out.println("读出长度:" + packet.getData().length);
						ByteArrayInputStream bais = new ByteArrayInputStream(
								packet.getData());
						// System.out.println(new String(packet.getData()));
						ObjectInputStream ois = null;
						//将字节流装饰成对象流
						ois = new ObjectInputStream(bais);
						PacketUnit packetUnit = (PacketUnit) ois.readObject();
						
						//接受该对象
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
	 * 发送数据线程
	 * 
	 * @author yqg
	 * 
	 */
	class SenderData implements Runnable {
		/**
		 * 发送数据
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
			
			System.out.println("发送数据");
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
