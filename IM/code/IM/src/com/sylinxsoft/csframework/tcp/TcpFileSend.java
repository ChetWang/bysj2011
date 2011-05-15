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
 * 采用TCP协议发送文件
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
			MessageBox.show("找不到待传输的文件!");
			return;
		}
		// 取得绝对路径
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
			// 大于1M的文件才显示进度条
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
			// 发送内容
			while ((len = sendFile.read(buff)) != -1) {
				output.write(buff, 0, len);
				totalLenght += len;
				if (isShowProgressBar) {
					fileInfor.setCompleteSize(totalLenght);
				}
			}
			sendFile.close();
			output.close();

			// 将该文件从文件管理器中移除
			TransferFilesManager.getInstance().removeTransferingFile(fileName);
			if (isShowProgressBar) {
				MskFrame.getInstance().getRightPanel().removeSendProgressBar(
						fileName, fileInfor.getProgressBar());
			}

			LeftMiddleBtPanel.getRecvTextArea().append(fileName + "发送完成!");
			LeftMiddleBtPanel.getRecvTextArea().append("\n  ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
