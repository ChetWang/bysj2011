package com.sylinxsoft.csframework;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * 文件列表管理
 * 
 * 
 */
public class TransferFilesManager extends Observable {

	private Map<String,FileInfor> transferingMap = new HashMap<String,FileInfor>();
	private static TransferFilesManager manager = new TransferFilesManager();
	private TransferFilesManager() {

	}
	
	public static TransferFilesManager getInstance() {
		return manager;
	}

	public boolean containsTransferingFile(String fileName) {
		return transferingMap.containsKey(fileName);
	}

	public FileInfor getTransferingFile(String fileName) {
		return (FileInfor) transferingMap.get(fileName);
	}



	public void addTransferingFile(String fileName,FileInfor fileInfor) {
		transferingMap.put(fileName, fileInfor);
	}



	public void removeTransferingFile(String fileName) {
		transferingMap.remove(fileName);
	}

	


}
