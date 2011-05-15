package ui;

import java.util.Timer;

import com.sylinxsoft.csframework.TransferFileList;

import javax.swing.JPanel;

/**
 * 文件传输状态面版
 * 
 * @author Administrator
 * 
 */
public class TransferFilePanel extends JPanel {

	//定时器，用来定时更新状态条的
	private Timer timer = new Timer(false);
	//发送
	private JPanel sendFile = new JPanel();
	//接收
	private JPanel recvFile = new JPanel();

	public TransferFilePanel() {

	}

	//更新文件的传输状态
	public void updateTransferFile() {
		TransferFileList fileList = TransferFileList.getInstance();
		for (int i = 0; i < fileList.size(); ++i) {
			//如果是在传输
			if (fileList.get(i).getTransfering()
					&& fileList.get(i).getCompleteSize() != 0) {
				//发送的文件
				if (fileList.get(i).getIsSend()) {
					//添加到发送面板中
				} else {
					//添加到接收面板中
				}
			}
		}
	}

}
