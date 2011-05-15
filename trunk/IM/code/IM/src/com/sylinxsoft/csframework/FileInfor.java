package com.sylinxsoft.csframework;

import ui.JProgressBarPanel;

/**
 * 表示正在传输文件的信息
 * 
 * @author Administrator
 * 
 */
public class FileInfor {

	// 文件发送方得IP
	private String sourceIp;
	// 文件发送名称
	private String sourceFileName;
	// 文件发送路径
	private String sourcePath;
	private String targetPath;
	// 文件保存得地址
	private String targetFileName;
	// 文件的总大小
	private long size;
	// 已经传输完成的大小
	private long completeSize = 0;
	// 是否已经开始传输
	private boolean transfering = false;
	// 是否是发送
	private boolean isSend;

	private String fileName;

	/** 与文件关联的进度条 */
	private JProgressBarPanel progressBar;

	public FileInfor() {

	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public void setTargetFileName(String targetFileName) {
		this.targetFileName = targetFileName;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setCompleteSize(long completeSize) {
		this.completeSize = completeSize;
		if (null != progressBar) {
			progressBar.setValue((int) (completeSize / 1024));
		}
	}

	public void setTransfering(boolean transfering) {
		this.transfering = transfering;
	}

	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public long getSize() {
		return size;
	}

	public long getCompleteSize() {
		return completeSize;
	}

	public boolean getTransfering() {
		return transfering;
	}

	public boolean getIsSend() {
		return isSend;
	}

	public JProgressBarPanel getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBarPanel progressBar) {
		this.progressBar = progressBar;
		if (null != progressBar) {
			progressBar.setTotalValue((int) (size / 1024));
			progressBar.setBarString(fileName);
		}
	}

}
