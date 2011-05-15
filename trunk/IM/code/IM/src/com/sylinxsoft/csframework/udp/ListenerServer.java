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

							// ���ж��Ƿ�ʹ���ҹ㲥��Ϣ
							String msg = (String) obj;
							// ��ʾ���Ƿ��ͳ�ʼ����Ϣ
							if (msg.indexOf(AllConstatns.GENERALMSGPCPROTOCOL) != -1) {
								// ��ȡ��Ϣ�������
								msg = msg.substring(msg
										.indexOf(AllConstatns.MSGTOKEN)
										+ AllConstatns.MSGTOKEN.length(), msg
										.length());

								ServerObservable.getInstance().changedMsg(msg,
										AllConstatns.MSGCHANGED);
								return;
							}

							// ����ӽ���һ���ͻ���
							StringTokenizer msgBuffer = new StringTokenizer(
									(String) obj, AllConstatns.MSGTOKEN);
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
							//������Ҫ���ж����Ƿ��Ѿ������˸ÿͻ����ˣ������ѭ��ɨ��
							//if (ClientGroupManager.getInstance().contains(
							//		clientInfor)) {
							//	return;
							//}
							ClientGroupManager.getInstance().addClient(
									clientInfor);
							
							System.out.println(InetAddress.getLocalHost()
									.getHostAddress() + " --  " + clientInfor
									.getInfor(AllConstatns.CLIENTIP));
							
							// ����յ���ʹ�Լ����ģ��ͷ��أ���Ҫ�������ͣ����������ѭ��
							if (((String) (clientInfor
									.getInfor(AllConstatns.CLIENTIP)))
									.equals(InetAddress.getLocalHost()
											.getHostAddress())) {
								System.out.println("scan self!");
								return;
							}
							
							String replayMark =(String) clientInfor.getInfor(AllConstatns.SCANMARK);
							//����ǻظ��ģ���Ҫ�ٴη��ͣ�����������ѭ��
							if (AllConstatns.SCANREPLYMARK.equals(replayMark)) {
								return;
							}
							
							// �ط����Է��Լ�����Ϣ
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
	 * ��ʼ��������
	 */
	public void start() {
		// AbstractUdpTransfer.getInstatnce().setRemoteIpAddress("127.0.0.1");
		AbstractUdpTransfer.getInstatnce().sratReceive();
	}

}
