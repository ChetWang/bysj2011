package com.sylinxsoft.csframework.udp;

import java.util.HashMap;

/**
 * ���ļ��ָ��һ��һ�����ݰ�
 * 
 * @author Administrator
 * 
 */
public class DivFileToPackages {
	/** ���ָ���ļ��� */
	private String fileName;
	//�ָ��ļ����ܰ���
	private int packgeCounter;
	//�ļ�������
	private HashMap table = new HashMap();

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	/**
	 * ����ļ�������
	 * @param index
	 * @return
	 */
	public PacketUnit getPackge(int index) {
		if (index < 0 || index >= packgeCounter) {
			return null;
		}
		return (PacketUnit) (table.get(index));
	}

	/**
	 * ��ʼ�ļ���Ϣ�������ܰ���
	 */
	public void initFileInfor() {

	}

}
