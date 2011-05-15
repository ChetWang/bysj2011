package com.sylinxsoft.csframework.udp;

import java.util.HashMap;

/**
 * 将文件分割成一个一个数据包
 * 
 * @author Administrator
 * 
 */
public class DivFileToPackages {
	/** 待分割的文件名 */
	private String fileName;
	//分割文件的总包数
	private int packgeCounter;
	//文件包索引
	private HashMap table = new HashMap();

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

	/**
	 * 获得文件索引包
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
	 * 初始文件信息，包括总包数
	 */
	public void initFileInfor() {

	}

}
