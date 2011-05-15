package com.sylinxsoft.csframework.udp;


/**
 * 规定发送报文的接口
 
 *
 */
public interface PacketSendInterface {
	/**发送一个包数据*/
     public void sendPacket(PacketUnit packet);
}
