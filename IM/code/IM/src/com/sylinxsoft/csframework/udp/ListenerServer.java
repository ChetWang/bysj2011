package com.sylinxsoft.csframework.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.util.StringTokenizer;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.client.ClientConfig;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ClientInfor;
import com.sylinxsoft.csframework.server.ClientInforInterface;
import com.sylinxsoft.csframework.server.ServerConfig;
import com.sylinxsoft.csframework.server.ServerObservable;

public class ListenerServer {

	public ListenerServer() {
		AbstractUdpTransfer.getInstatnce().addTransferListener(
				new NewPcEvent() {
					public void addNewPc(String ip, Object obj) {
						try {

							// 先判断是否使向大家广播消息
							String msg = (String) obj;
							// 表示不是发送初始化信息
							if (msg.indexOf(AllConstatns.GENERALMSGPCPROTOCOL) != -1) {
								// 提取消息体的内容
								msg = msg.substring(msg
										.indexOf(AllConstatns.MSGTOKEN)
										+ AllConstatns.MSGTOKEN.length(), msg
										.length());

								ServerObservable.getInstance().changedMsg(msg,
										AllConstatns.MSGCHANGED);
								return;
							}

							// 新添加进来一个客户端
							StringTokenizer msgBuffer = new StringTokenizer(
									(String) obj, AllConstatns.MSGTOKEN);
							// 创建一个客户端
							ClientInforInterface clientInfor = new ClientInfor();
							while (msgBuffer.hasMoreTokens()) {
								String key = msgBuffer.nextToken();
								String value = new String();
								if (msgBuffer.hasMoreTokens()) {
									value = msgBuffer.nextToken();
								}
								clientInfor.setInfor(key, value);
							}
							//这里需要先判断下是否已经包含了该客户端了，否则会循环扫描
							//if (ClientGroupManager.getInstance().contains(
							//		clientInfor)) {
							//	return;
							//}
							ClientGroupManager.getInstance().addClient(
									clientInfor);
							
							System.out.println(InetAddress.getLocalHost()
									.getHostAddress() + " --  " + clientInfor
									.getInfor(AllConstatns.CLIENTIP));
							
							// 如果收到的使自己发的，就返回，不要继续发送，否则出现死循环
							if (((String) (clientInfor
									.getInfor(AllConstatns.CLIENTIP)))
									.equals(InetAddress.getLocalHost()
											.getHostAddress())) {
								System.out.println("scan self!");
								return;
							}
							
							String replayMark =(String) clientInfor.getInfor(AllConstatns.SCANMARK);
							//如果是回复的，则不要再次发送，否则会造成死循环
							if (AllConstatns.SCANREPLYMARK.equals(replayMark)) {
								return;
							}
							
							// 回发给对方自己的信息
							ServerConfig config = ServerConfig.getConfig();
							ClientConfig clientConfig = ClientConfig
									.getConfig();
							String body = AllConstatns.MSGTOKEN
									+ AllConstatns.RECVMSGPORT
									+ AllConstatns.MSGTOKEN
									+ config.get(AllConstatns.RECVMSGPORT)
									+ AllConstatns.MSGTOKEN
									+ AllConstatns.CLIENTNAME
									+ AllConstatns.MSGTOKEN
									+ clientConfig.get(AllConstatns.CLIENTNAME)
									+ AllConstatns.MSGTOKEN
									+ AllConstatns.CLIENTIP
									+ AllConstatns.MSGTOKEN
									+ InetAddress.getLocalHost()
											.getHostAddress()
									+ AllConstatns.MSGTOKEN + AllConstatns.SCANMARK
									+ AllConstatns.MSGTOKEN
									+ AllConstatns.SCANREPLYMARK;
							
							String hearder = AllConstatns.PROTOCOLSTARTMARK
									+ AllConstatns.INITONLINEPCPROTOCOL
									+ AllConstatns.PROTOCOLENDMARK;
							String content = hearder + body;
							AbstractUdpTransfer
									.getInstatnce()
									.setRemoteIpAddress(
											(String) (clientInfor
													.getInfor(AllConstatns.CLIENTIP)));
							int remoteport = Integer
									.parseInt((String) ClientConfig.getConfig()
											.get("multicast-localport"));
							AbstractUdpTransfer.getInstatnce().setRemoteIpPort(
									remoteport);
							AbstractUdpTransfer.getInstatnce().setSendData(
									content);
							AbstractUdpTransfer.getInstatnce().startSend();

							// output.writeUTF(hearder + body);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});

	}

	/**
	 * 开始监听接收
	 */
	public void start() {
		// AbstractUdpTransfer.getInstatnce().setRemoteIpAddress("127.0.0.1");
		AbstractUdpTransfer.getInstatnce().sratReceive();
	}

}
