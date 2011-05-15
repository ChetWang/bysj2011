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
 * Udp传输抽象类，该类可以构建发送和接受UDP数据报 注意此类并不处理UDP包丢失的情况，所以传输的对象总字节应该小于MTU。
 * （大于MTU的包将会被丢弃,MTU可根据自己使用的网络平台协议确定）
 * 
 * @author Administrator
 * 
 */
public class AbstractUdpTransfer {

	/** 存储传输文件监听者 */
	private List<NewPcEvent> listeners = new ArrayList<NewPcEvent>();

	/** localPort本地UDP端口,remotePort远程UDP端口 */
	private int localPort, remotePort;
	/** 注意这里要同时发送和接收，必须指定localPort */
	private MulticastSocket datagramScoket = null;

	/** 远程主机地址 */
	private String remoteIpAddress = null;

	/** 接收线程 */
	private Thread receiverThread = null;

	/** 发送线程 */
	private Thread senderThread = null;

	/** 待发送的数据 */
	private Object obj;

	private static AbstractUdpTransfer udpTransfer = null;

	public AbstractUdpTransfer() {

	}

	public AbstractUdpTransfer(int port) {
		setLocalPort(port);
	}

	/**
	 * 静态方法，方便全局使用，此不是严格单例模式
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

	/** 监听端口 */
	public void setLocalPort(int port) {
		this.localPort = port;
		// 在本地端口监听
		try {
			InetAddress group = null; // 组播组的地址.
			MulticastSocket socket = null; // 多点广播套接字.
			group = InetAddress.getByName((String) ClientConfig.getConfig()
					.get("client-group")); // 设置广播组的地址为239.255.8.0。
			datagramScoket = new MulticastSocket(port); // 多点广播套接字将在port端口广播。
			datagramScoket.setTimeToLive(1); // 多点广播套接字发送数据报范围为本地网络。
			datagramScoket.joinGroup(group); // 加入广播组,加入group后,socket发送的数据报
			datagramScoket.setBroadcast(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送一个数据包
	 */
	public void sendPacket(Object packet) {
		if (datagramScoket != null) {
			// 字节流
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = null;
			try {
				// 装饰成对象流
				oos = new ObjectOutputStream(baos);
				// 将对象正序列化
				oos.writeObject(packet);
				oos.flush();
				// 获得序列化的的字节
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
				// System.out.println("写入长度:" + arr.length);
				// 将这些字节封装成数据报
				DatagramPacket dataPacket = new DatagramPacket(arr, arr.length,
						InetAddress.getByName(remoteIpAddress), remotePort);
				// 发送该数据报包
				datagramScoket.send(dataPacket);

			} catch (Exception e) {
				e.printStackTrace();
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
	 * 设置接收方的端口
	 * 
	 * @param port
	 */
	public void setRemoteIpPort(int port) {
		this.remotePort = port;
	}

	/** 添加一个监听者 */
	public void addTransferListener(NewPcEvent listener) {
		for (int i = 0; i < listeners.size(); i++) {
			if (listeners.get(i) == listener) {
				return;
			}
		}
		listeners.add(listener);
	}

	/**
	 * 通知所有的监听者
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
	 * 发送的内容
	 */
	public void setSendData(Object obj) {
		this.obj = obj;
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
			try {
				InetAddress group = InetAddress.getByName((String) ClientConfig
						.getConfig().get("client-group"));
				byte[] buffer = new byte[PacketUnit.MTU + 300];

				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length, group, localPort);

				if (datagramScoket != null) {
					while (true) {
						// 接收到数据
						datagramScoket.receive(packet);
						ByteArrayInputStream bais = new ByteArrayInputStream(
								packet.getData());
						ObjectInputStream ois = null;
						// 将字节流装饰成对象流
						ois = new ObjectInputStream(bais);
						Object packetUnit = ois.readObject();
						// System.out.println(packetUnit.toString());
						// 接受该对象
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
	 * 发送数据线程
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
