package com.sylinxsoft.csframework;

import java.util.ArrayList;

/**
 * 正在传输的文件列表
 * 
 * @author Administrator
 * 
 */
public class TransferFileList {

	private ArrayList<FileInfor> list = new ArrayList<FileInfor>();
	private static TransferFileList instance = new TransferFileList();

	private TransferFileList() {

	}

	
	public static TransferFileList getInstance() {
		return instance;
	}

	public void addTransferFile(FileInfor fileInfor) {
		for (int i = 0; i < list.size(); ++i) {
			if (fileInfor.getFileName().equals(list.get(i).getFileName())) {
				return;
			}
		}
		list.add(fileInfor);
	}

	public int size() {
		return list.size();
	}

	public FileInfor get(int index) {
		if (index >= 0 && index < size()) {
			return list.get(index);
		}
		return null;
	}

	public void removeTransferFile(String fileName) {
		for (int i = 0; i < list.size(); ++i) {
			if (fileName.equals(list.get(i).getFileName())) {
				list.remove(i);
			}
		}
	}

}
