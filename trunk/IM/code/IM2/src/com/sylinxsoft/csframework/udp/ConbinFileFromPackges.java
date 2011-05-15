package com.sylinxsoft.csframework.udp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * 将数据包合成原始文件
 * @author Administrator
 *
 */
public class ConbinFileFromPackges {
	
	/**文件名*/
	private String fileName;
	private int packgeCounter;
	private HashMap table = new HashMap();

	public void setFileName(String fileName) {
		this.fileName = fileName;

	}

	/**
	 * 设置文件总共的包数
	 * @param counter
	 */
	public void setPackgeCounter(int counter) {
		this.packgeCounter = counter;
	}

	
	/**
	 * 添加一个数据包
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
	 * 获得缺少的包
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
	 * 保存到文件中
	 */
	public void saveToFile() {
		ArrayList list = getOtherPackges();
		if (0 != list.size()) {
			return;
		}
		//写入文件
	}
	
}
