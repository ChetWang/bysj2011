package com.sylinxsoft.csframework;

import ui.JProgressBarPanel;

/**
 * ��ʾ���ڴ����ļ�����Ϣ
 * 
 * @author Administrator
 * 
 */
public class FileInfor {

	// �ļ����ͷ���IP
	private String sourceIp;
	// �ļ���������
	private String sourceFileName;
	// �ļ�����·��
	private String sourcePath;
	private String targetPath;
	// �ļ�����õ�ַ
	private String targetFileName;
	// �ļ����ܴ�С
	private long size;
	// �Ѿ�������ɵĴ�С
	private long completeSize = 0;
	// �Ƿ��Ѿ���ʼ����
	private boolean transfering = false;
	// �Ƿ��Ƿ���
	private boolean isSend;

	private String fileName;

	/** ���ļ������Ľ����� */
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
