package com.sylinxsoft.csframework.udp;

import java.io.Serializable;

/**
 * 规定数据报每次发送的单元数据，包括 头控制信息.因为各种网络的最大传输单元(MTU)不一样，最小的有小于1000字节的， <br>
 * 因此MTU一般设置不要超过1024
 * 
 * @author yqg
 * 
 */
public class PacketUnit implements Serializable {

	/** 头长度信息,命令%#标识%#序号%#文件开始位置5#数据大小%#以"0"填充到80字节,头信息里面不能包含中文 */
	public transient static final int HEARDERLENGTH = 80;

	/** 最大传输单元，这里要减去头信息长度 */
	public transient static final int DATAMTU = 930;
	
	public transient static final int MTU = HEARDERLENGTH + DATAMTU;
	
	/** 发送文件所包含的命令消息 */
	private transient final String[] commands = { "FsName", "FsFile", "FsStop", "FsCanc",
			"FsRepa", "FrReje", "FrRead", "FrEnd", "FrRepa", "FrErr", "FrCanc" };

	/** 文件标识 */
	private short fileMark;

	/** 各种命令 */
	private String command = null;

	/** 保留，可放文件偏移位置 */
	private long position;

	/** 数据内容大小 */
	private long dataSize;

	/** 存放数据内容 */
	private byte[] data = new byte[DATAMTU];

	/**
	 * 设置文件索引标记
	 * @param id
	 */
	public void setFileMark(short id) {
		fileMark = id;
	}

	public short getFileMark() {
		return fileMark;
	}

	/**
	 * 获得枚举类型中对应的命令
	 * 
	 * @param cm
	 * @return
	 */
	public String getCommand(FileCommand cm) {
		String comd = null;
		switch (cm) {
		case FsName:
			comd = commands[0];
			break;
		case FsFile:
			comd = commands[1];
			break;
		case FsStop:
			comd = commands[2];
			break;
		case FsCanc:
			comd = commands[3];
			break;
		case FsRepa:
			comd = commands[4];
			break;
		case FrReje:
			comd = commands[5];
			break;
		case FrRead:
			comd = commands[6];
			break;
		case FrEnd:
			comd = commands[7];
			break;
		case FrRepa:
			comd = commands[8];
			break;
		case FrErr:
			comd = commands[9];
			break;
		case FrCanc:
			comd = commands[10];
			break;
		}
		return comd;

	}

	/**
	 * 设置头命令
	 * 
	 * @param cm
	 */
	public void setCommand(FileCommand cm) {
		command = getCommand(cm);
	}

	/**
	 * 获得头命令
	 * 
	 * @return
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * 设置文件的偏移位置
	 * 
	 * @param position
	 */
	public void setPosition(long position) {
		this.position = position;
	}

	/**
	 * 获得文件的偏移位置
	 * 
	 * @return
	 */
	public long getPostion() {
		return position;
	}

	/**
	 * 设置数据的大小
	 * 
	 * @param size
	 */
	public void setDataSize(long size) {
		dataSize = size;
	}

	/**
	 * 获得数据的大小
	 * 
	 * @return
	 */
	public long getDataSize() {
		return dataSize;
	}

	/**
	 * 设置数据内容
	 * 
	 * @param data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * 获得数据内容
	 * 
	 * @return
	 */
	public byte[] getData() {
		return data;
	}


	
	
}
