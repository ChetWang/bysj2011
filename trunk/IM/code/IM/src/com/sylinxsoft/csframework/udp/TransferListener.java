package com.sylinxsoft.csframework.udp;

/**
 * ���崦���ļ����������һЩ�¼��Ĵ���
 * @author yqg
 *
 */
public interface TransferListener {
	public void receivePacket(PacketSendInterface packetSend,PacketUnit packet);
	
}
