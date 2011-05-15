package com.sylinxsoft.csframework.tcp;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;

import ui.JProgressBarPanel;
import ui.LeftMiddleBtPanel;
import ui.MessageBox;
import ui.MskFrame;

import com.sylinxsoft.csframework.FileInfor;
import com.sylinxsoft.csframework.TransferFilesManager;

/**
 * ����TCPЭ�鷢���ļ�
 * 
 * @author Administrator
 * 
 */
public class TcpFileSend implements Runnable {

	private String hostIp;
	private int port;
	private String sendFileName;
	private String fileName;

	public TcpFileSend(final String hostIp, int port, final String sendFileName) {
	
		this.port = port;
		this.hostIp = hostIp;
		
		FileInfor fileInfor = TransferFilesManager.getInstance()
		.getTransferingFile(sendFileName);
		if (null == fileInfor) {
			MessageBox.show("�Ҳ�����������ļ�!");
			return;
		}
		// ȡ�þ���·��
		this.sendFileName = fileInfor.getFileName();

		this.fileName = sendFileName;
	}

	public void run() {

		try {
			
			Socket socker = new Socket(hostIp, port);
			DataOutputStream output = new DataOutputStream(socker
					.getOutputStream());
			FileInputStream sendFile = new FileInputStream(sendFileName);
			FileInfor fileInfor = TransferFilesManager.getInstance()
					.getTransferingFile(fileName);

			boolean isShowProgressBar = false;
			// ����1M���ļ�����ʾ������
			if (fileInfor.getSize() > 1048576) {
				JProgressBarPanel bar = new JProgressBarPanel();
				fileInfor.setProgressBar(bar);
				MskFrame.getInstance().getRightPanel().addSendProgressBar(
						fileName, bar);
				isShowProgressBar = true;
				MskFrame.getInstance().getRightPanel().setTransStatusPane();
			}
			long totalLenght = 0;
			byte[] buff = new byte[100];
			int len = 0;
			// ��������
			while ((len = sendFile.read(buff)) != -1) {
				output.write(buff, 0, len);
				totalLenght += len;
				if (isShowProgressBar) {
					fileInfor.setCompleteSize(totalLenght);
				}
			}
			sendFile.close();
			output.close();

			// �����ļ����ļ����������Ƴ�
			TransferFilesManager.getInstance().removeTransferingFile(fileName);
			if (isShowProgressBar) {
				MskFrame.getInstance().getRightPanel().removeSendProgressBar(
						fileName, fileInfor.getProgressBar());
			}

			LeftMiddleBtPanel.getRecvTextArea().append(fileName + "�������!");
			LeftMiddleBtPanel.getRecvTextArea().append("\n  ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
