package com.sylinxsoft.csframework.udp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * �����ݰ��ϳ�ԭʼ�ļ�
 * @author Administrator
 *
 */
public class ConbinFileFromPackges {
	
	/**�ļ���*/
	private String fileName;
	private int packgeCounter;
	private HashMap table = new HashMap();

	public void setFileName(String fileName) {
		this.fileName = fileName;

	}

	/**
	 * �����ļ��ܹ��İ���
	 * @param counter
	 */
	public void setPackgeCounter(int counter) {
		this.packgeCounter = counter;
	}

	
	/**
	 * ���һ�����ݰ�
	 * @param packge
	 */
	public void addPackge(PacketUnit packge) {
		int index = packge.getFileMark();
		if (index < 0 || index >= packgeCounter || table.containsKey(index)) {
			return;
		}
		table.put(index, packge);
	}

	
	/**
	 * ���ȱ�ٵİ�
	 * @return
	 */
	public ArrayList getOtherPackges() {
		ArrayList list = new ArrayList();
		for (int i = 0; i < packgeCounter; ++ i) {
			if (!table.containsKey(i)) {
				list.add(new Integer(i));
			}
		}
		return list;
	}
	
	
	/**
	 * ���浽�ļ���
	 */
	public void saveToFile() {
		ArrayList list = getOtherPackges();
		if (0 != list.size()) {
			return;
		}
		//д���ļ�
	}
	
}
