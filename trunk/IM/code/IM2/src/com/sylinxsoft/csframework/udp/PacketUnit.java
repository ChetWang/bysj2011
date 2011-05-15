package com.sylinxsoft.csframework.udp;

import java.io.Serializable;

/**
 * �涨���ݱ�ÿ�η��͵ĵ�Ԫ���ݣ����� ͷ������Ϣ.��Ϊ�������������䵥Ԫ(MTU)��һ������С����С��1000�ֽڵģ� <br>
 * ���MTUһ�����ò�Ҫ����1024
 * 
 * @author yqg
 * 
 */
public class PacketUnit implements Serializable {

	/** ͷ������Ϣ,����%#��ʶ%#���%#�ļ���ʼλ��5#���ݴ�С%#��"0"��䵽80�ֽ�,ͷ��Ϣ���治�ܰ������� */
	public transient static final int HEARDERLENGTH = 80;

	/** ����䵥Ԫ������Ҫ��ȥͷ��Ϣ���� */
	public transient static final int DATAMTU = 930;
	
	public transient static final int MTU = HEARDERLENGTH + DATAMTU;
	
	/** �����ļ���������������Ϣ */
	private transient final String[] commands = { "FsName", "FsFile", "FsStop", "FsCanc",
			"FsRepa", "FrReje", "FrRead", "FrEnd", "FrRepa", "FrErr", "FrCanc" };

	/** �ļ���ʶ */
	private short fileMark;

	/** �������� */
	private String command = null;

	/** �������ɷ��ļ�ƫ��λ�� */
	private long position;

	/** �������ݴ�С */
	private long dataSize;

	/** ����������� */
	private byte[] data = new byte[DATAMTU];

	/**
	 * �����ļ��������
	 * @param id
	 */
	public void setFileMark(short id) {
		fileMark = id;
	}

	public short getFileMark() {
		return fileMark;
	}

	/**
	 * ���ö�������ж�Ӧ������
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
	 * ����ͷ����
	 * 
	 * @param cm
	 */
	public void setCommand(FileCommand cm) {
		command = getCommand(cm);
	}

	/**
	 * ���ͷ����
	 * 
	 * @return
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * �����ļ���ƫ��λ��
	 * 
	 * @param position
	 */
	public void setPosition(long position) {
		this.position = position;
	}

	/**
	 * ����ļ���ƫ��λ��
	 * 
	 * @return
	 */
	public long getPostion() {
		return position;
	}

	/**
	 * �������ݵĴ�С
	 * 
	 * @param size
	 */
	public void setDataSize(long size) {
		dataSize = size;
	}

	/**
	 * ������ݵĴ�С
	 * 
	 * @return
	 */
	public long getDataSize() {
		return dataSize;
	}

	/**
	 * ������������
	 * 
	 * @param data
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	public byte[] getData() {
		return data;
	}


	
	
}
