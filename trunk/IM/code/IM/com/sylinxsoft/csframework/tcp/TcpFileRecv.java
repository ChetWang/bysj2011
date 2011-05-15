package com.sylinxsoft.csframework.tcp;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import ui.JProgressBarPanel;
import ui.LeftMiddleBtPanel;
import ui.MskFrame;

import com.sylinxsoft.csframework.FileInfor;
import com.sylinxsoft.csframework.server.ServerConfig;

/**
 * TCP�����ļ�
 * 
 * 
 */
public class TcpFileRecv implements Runnable {

	private int port;
	private String recvFileName;
	private FileInfor fileInfor;

	public TcpFileRecv(int port, final String recvFileName, FileInfor fileInfor) {
		this.port = port;
		this.recvFileName = recvFileName;
		this.fileInfor = fileInfor;
	}

	public void run() {
		try {
			// �����˶˿�
			ServerSocket socker = new ServerSocket(port);
			// �����ͻ��˵ȴ���������
			Socket socket = socker.accept();
			DataInputStream input = new DataInputStream(socket.getInputStream());
			byte[] buff = new byte[100];

			boolean isShowProgressBar = false;
			// ����1M���ļ�����ʾ������
			if (fileInfor.getSize() > 1048576) {
				JProgressBarPanel bar = new JProgressBarPanel();
				fileInfor.setProgressBar(bar);
				MskFrame.getInstance().getRightPanel().addRecvProgressBar(
						fileInfor.getFileName(), bar);
				isShowProgressBar = true;
				MskFrame.getInstance().getRightPanel().setTransStatusPane();
			}

			// ����Ҫ������ļ�
			FileOutputStream recvFile = new FileOutputStream(recvFileName);
			int len = 0, writeSize = 0;
			// ���ļ�����ѭ��д��
			while ((len = input.read(buff)) != -1) {
				recvFile.write(buff, 0, len);
				writeSize += len;
				if (isShowProgressBar) {
					fileInfor.setCompleteSize(writeSize);
				}
			}
			recvFile.close();
			input.close();
			socker.close();
			ServerConfig.getConfig().releasePort(port);
			MskFrame.getInstance().getRightPanel().removeRecvProgressBar(
					fileInfor.getFileName(), fileInfor.getProgressBar());
			LeftMiddleBtPanel.getRecvTextArea().append(
					recvFileName.substring(recvFileName.lastIndexOf("\\") + 1)
							+ "�������!");
			LeftMiddleBtPanel.getRecvTextArea().append("\n  ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
