package ui;

import java.util.Timer;

import com.sylinxsoft.csframework.TransferFileList;

import javax.swing.JPanel;

/**
 * �ļ�����״̬���
 * 
 * @author Administrator
 * 
 */
public class TransferFilePanel extends JPanel {

	//��ʱ����������ʱ����״̬����
	private Timer timer = new Timer(false);
	//����
	private JPanel sendFile = new JPanel();
	//����
	private JPanel recvFile = new JPanel();

	public TransferFilePanel() {

	}

	//�����ļ��Ĵ���״̬
	public void updateTransferFile() {
		TransferFileList fileList = TransferFileList.getInstance();
		for (int i = 0; i < fileList.size(); ++i) {
			//������ڴ���
			if (fileList.get(i).getTransfering()
					&& fileList.get(i).getCompleteSize() != 0) {
				//���͵��ļ�
				if (fileList.get(i).getIsSend()) {
					//��ӵ����������
				} else {
					//��ӵ����������
				}
			}
		}
	}

}
