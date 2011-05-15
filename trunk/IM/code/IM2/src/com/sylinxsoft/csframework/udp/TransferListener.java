package com.sylinxsoft.csframework.udp;

/**
 * 定义处理文件传输过程中一些事件的处理
 * @author yqg
 *
 */
public interface TransferListener {
	public void receivePacket(PacketSendInterface packetSend,PacketUnit packet);
	
}
