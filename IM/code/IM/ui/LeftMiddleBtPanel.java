package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.InetAddress;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;

import ui.configset.ConfigDlg;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.FileInfor;
import com.sylinxsoft.csframework.TransferFilesManager;
import com.sylinxsoft.csframework.client.AbstractClient;
import com.sylinxsoft.csframework.client.ClientConfig;
import com.sylinxsoft.csframework.client.SendMsgClient;
import com.sylinxsoft.csframework.server.AbstarctServer;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ClientInfor;
import com.sylinxsoft.csframework.server.ClientInforInterface;
import com.sylinxsoft.csframework.server.RecvMsgServer;
import com.sylinxsoft.csframework.server.ServerConfig;
import com.sylinxsoft.csframework.udp.AbstractUdpTransfer;
import com.sylinxsoft.util.Ui;
import com.sylinxsoft.util.UniResources;

public class LeftMiddleBtPanel extends JPanel implements ActionListener,
		LanguageChangeEnable {

	public static final Color BKCOLOR = new Color(215, 233, 251);

	private static final int btSize = 18;

	private JTextAreaEx sendPanel = null;

	private static JTextAreaEx recvTextArea = null;

	private String ipAddress = new String();

	// �м���岿�ָ���ť
	private JButton jbtFile = new JButton(createImageIcon("tupian.jpg"));

	private JButton jbtRefresh = new JButton(createImageIcon("refresh.jpg"));

	private JButton jbtSet = new JButton(createImageIcon("set.jpg"));

	//private JButton jbtAbout = new JButton(createImageIcon("about.jpg"));

	private JButton jbtCharacterType = new JButton(
			createImageIcon("CharType.jpg"));

	private JButton jbtEmotion = new JButton(createImageIcon("emotion.jpg"));


	private JButton jbtMsg = new JButton(createImageIcon("msg.jpg"));

	// ѡ�����������Ƿ���ʾ
	private boolean fontChooseShowOrNot = false;

	/** ����ѡ����� */
	private FontChoosePanel fontChoosePanel = null;

	/** ��ʾ���鵯���� */
	private Popup faceShowDlg;

	/** ����Ի��� */
	private ShowFace faceDlg = null;

	public LeftMiddleBtPanel(JTextAreaEx sendPanel, JTextAreaEx recvTextArea) {
		this.sendPanel = sendPanel;
		this.recvTextArea = recvTextArea;
		// ����ѡ���
		fontChoosePanel = new FontChoosePanel(sendPanel);
		// ����Ի���
		faceDlg = new ShowFace(this, sendPanel);
		// �м����Ĵ�С�����еİ�ť��ע�ᣬ�Լ���ť����ʾ��Ϣ
		jbtCharacterType.setPreferredSize(new Dimension(btSize, btSize));
		jbtCharacterType.addActionListener(this);
		jbtCharacterType.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.CharacterTypetip"));
		// ���øı����尴ť�Ŀ�ݼ�
		jbtCharacterType.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtEmotion.setPreferredSize(new Dimension(btSize, btSize));
		jbtEmotion.addActionListener(this);
		jbtEmotion.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.Emotiontip"));
		// ����ѡ����鰴ť�Ŀ�ݼ�
		jbtEmotion.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtFile.setPreferredSize(new Dimension(btSize, btSize));
		ActionSendFile actionFile = new ActionSendFile();
		jbtFile.addActionListener(actionFile);
		jbtFile.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.Sendfiletip"));
		// ���÷����ļ���ť�Ŀ�ݼ�
		jbtFile.registerKeyboardAction(actionFile, KeyStroke.getKeyStroke(
				KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtSet.setPreferredSize(new Dimension(btSize, btSize));
		jbtSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfigDlg dlg = new ConfigDlg();
				dlg.setModal(true);
				Ui.centerFrame(dlg);
				dlg.setVisible(true);
			}
		});

		jbtSet.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.optionsettip"));
		// �����������ΰ�ť�Ŀ�ݼ�
		// jbtSet.registerKeyboardAction(this, KeyStroke.getKeyStroke(
		// KeyEvent.VK_S,java.awt.event.InputEvent.CTRL_MASK),
		// JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtRefresh.setPreferredSize(new Dimension(btSize, btSize));
		jbtRefresh.addActionListener(this);
		jbtRefresh.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.refreshtip"));
		// ����ˢ�°�ť�Ŀ�ݼ�
		jbtRefresh.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

//		jbtAbout.setPreferredSize(new Dimension(btSize, btSize));
//		jbtAbout.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				AboutDialog dlg = new AboutDialog();
//				dlg.setModal(true);
//				Ui.centerFrame(dlg);
//				dlg.setVisible(true);
//			}
//		});
//
//		jbtAbout.setToolTipText(UniResources.getString("msk.aboutthis"));
//		
		jbtMsg.setPreferredSize(new Dimension(btSize, btSize));
		jbtMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MsgView dlg = new MsgView();
				dlg.setModal(true);
				Ui.centerFrame(dlg);
				dlg.setVisible(true);
			}
		});

		jbtMsg.setToolTipText(UniResources.getString("msk.aboutthis"));
		
		// ���ù��ڰ�ť�Ŀ�ݼ�
		// jbtAbout.registerKeyboardAction(this, KeyStroke.getKeyStroke(
		// KeyEvent.VK_L,java.awt.event.InputEvent.CTRL_MASK),
		// JComponent.WHEN_IN_FOCUSED_WINDOW);

		// �����м����İ�ť���з�ʽ��������ɫ����С

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5));
		panel.setBackground(BKCOLOR);
		panel
				.setPreferredSize(new Dimension(AllUiConstatns.LEFTPANELWIDTH,
						25));
		// ��һ�°�ť�ӵ��м����
		panel.add(jbtCharacterType);
		panel.add(jbtEmotion);
		panel.add(jbtFile);
		panel.add(jbtSet);
		panel.add(jbtRefresh);
//		panel.add(jbtAbout);
		panel.add(jbtMsg);
		
		add(panel, BorderLayout.CENTER);

	}

	/**
	 * �����Ϣ���տ�
	 * 
	 * @return
	 */
	public static JTextAreaEx getRecvTextArea() {
		return recvTextArea;
	}

	/**
	 * ɨ�������û�
	 */
	public void scanOnlinePc() {
		jbtRefresh.setEnabled(false);
		// ������������û�
		ClientGroupManager.getInstance().clearAllClient();
		ClientInforInterface clientInfor = new ClientInfor();
		clientInfor.setInfor(AllConstatns.CLIENTNAME, UniResources
				.getString("msk.all"));
		clientInfor.setInfor(AllConstatns.CLIENTIP, "255.255.255.255");
		ClientGroupManager.getInstance().addClient(clientInfor);

		try {

			// �ط����Է��Լ�����Ϣ
			ServerConfig config = ServerConfig.getConfig();
			ClientConfig clientConfig = ClientConfig.getConfig();
			String body = AllConstatns.MSGTOKEN + AllConstatns.RECVMSGPORT
					+ AllConstatns.MSGTOKEN
					+ config.get(AllConstatns.RECVMSGPORT)
					+ AllConstatns.MSGTOKEN + AllConstatns.CLIENTNAME
					+ AllConstatns.MSGTOKEN
					+ clientConfig.get(AllConstatns.CLIENTNAME)
					+ AllConstatns.MSGTOKEN + AllConstatns.CLIENTIP
					+ AllConstatns.MSGTOKEN
					+ InetAddress.getLocalHost().getHostAddress()
					+ AllConstatns.MSGTOKEN + AllConstatns.SCANMARK
					+ AllConstatns.MSGTOKEN
					+ AllConstatns.SCANREFRESHMARK;
			
			String hearder = AllConstatns.PROTOCOLSTARTMARK
					+ AllConstatns.INITONLINEPCPROTOCOL
					+ AllConstatns.PROTOCOLENDMARK;
			String content = hearder + body;
			// BroadcastAddr info = new BroadcastAddr();
			// info.setNetmask("255.255.255.0");
			// info.setNetaddr("192.168.0.106");
			// info.execCalc();
			// ia = InetAddress.getByName(info.getNetbroadcastaddr());

			AbstractUdpTransfer.getInstatnce().setRemoteIpAddress(
					"255.255.255.255");
			int remoteport = Integer.parseInt((String) ClientConfig.getConfig()
					.get("multicast-localport"));
			AbstractUdpTransfer.getInstatnce().setRemoteIpPort(remoteport);
			AbstractUdpTransfer.getInstatnce().setSendData(content);
			AbstractUdpTransfer.getInstatnce().startSend();

			// AbstractUdpTransfer.getInstatnce(10008)
			// .setSendData("fdsfds");

			// AbstractUdpTransfer.getInstatnce(10008).startSend();

			/*
			 * // ��ÿͻ���������Ϣ ClientConfig clientConfig =
			 * ClientConfig.getConfig(); // ɨ����ʼIP String start = (String)
			 * clientConfig.get(AllConstatns.SCANSTARTIP); // ɨ����ʼ���� String end
			 * = (String) clientConfig.get(AllConstatns.SCANENDTIP); String
			 * preIP = start.substring(0, start.lastIndexOf(".") + 1); int
			 * startIp = Integer.parseInt(start.substring(start
			 * .lastIndexOf(".") + 1)); int endIp = Integer.parseInt(end
			 * .substring(end.lastIndexOf(".") + 1)); for (int i = startIp; i <=
			 * endIp; i++) { String ip = preIP + i; System.out.println(ip);
			 * AbstractClient client = new InitOnlinePc(); // �����ַ�ɴ� if
			 * (client.setIpAddress(ip, 5074)) { client.start(); } // ÿ���̼߳������
			 * Thread.sleep(300); } // ֹͣ4����ʹˢ�°�ť��Ч Thread.sleep(4000);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		jbtRefresh.setEnabled(true);
	}

	/**
	 * ����ѡ�е�IP
	 * 
	 * @param ipAddress
	 */
	public void setSelectIp(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * ��Ӧ�����ļ��Ķ���
	 * 
	 * 
	 */
	class ActionSendFile implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// ��֤IP�Ƿ�Ϊ��
			if (ipAddress.equals("")) {
				JOptionPane.showMessageDialog(null, UniResources
						.getString("msk.leftBottomPanel.iperror"), UniResources
						.getString("msk.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			// ����������ѡ���ļ��Ի���ѡ��Ҫ���͵��ļ�
			JFileChooser chooser = new JFileChooser();
			// /chooser.setMultiSelectionEnabled(true);
			int returnVal = chooser.showOpenDialog(MskFrame.getInstance());

			// ����Ѿ�ѡ����
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// ����ļ�ȫ��
				final String sendFile = chooser.getSelectedFile().toString();
				// �����ļ�����
				sendFileRequest(sendFile, chooser.getSelectedFile().getName());
			}
		}
	}

	/**
	 * ���������ļ�����
	 */
	public void sendFileRequest(String sendFilePath) {
		// ��֤IP�Ƿ�Ϊ��
		if (ipAddress.equals("")) {
			JOptionPane.showMessageDialog(null, UniResources
					.getString("msk.leftBottomPanel.iperror"), UniResources
					.getString("msk.error"), JOptionPane.WARNING_MESSAGE);
			return;
		}
		String sendFilename = sendFilePath.substring(sendFilePath
				.lastIndexOf(File.separatorChar) + 1);
		sendFileRequest(sendFilePath, sendFilename);
	}

	/**
	 * ���������ļ�����
	 */
	private void sendFileRequest(String sendFile, String sendFilename) {
		// ������Ϣ�ͻ����߳�
		AbstractClient msgClient = new SendMsgClient();
		msgClient.setIpAddress(ipAddress, AllConstatns.RECVMSGIPPORT);
		String localIp = null;
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ȡ���ļ���С
		File file = new File(sendFile);
		ServerConfig serverConfig = ServerConfig.getConfig();
		int port = (Integer) serverConfig.get(AllConstatns.RECVFILEPORT);
		String header = AllConstatns.PROTOCOLSTARTMARK
				+ AllConstatns.SENDFILEPROTOCOL + AllConstatns.PROTOCOLENDMARK
				+ AllConstatns.MSGTOKEN + localIp + AllConstatns.IPHEADERMARK
				+ sendFilename + AllConstatns.SENDFILENAME + file.length()
				+ AllConstatns.FILESIZE + port + AllConstatns.FILESERVERPORT;
		// �жϸ��ļ��Ƿ��ڴ��������
		if (TransferFilesManager.getInstance().containsTransferingFile(
				sendFilename)) {
			JOptionPane.showMessageDialog(null, UniResources
					.getString("msk.filesending"), UniResources
					.getString("msk.error"), JOptionPane.WARNING_MESSAGE);
			return;
		}

		// �����ļ���С
		String fileSize;
		if (0 == file.length() / 1024) {
			fileSize = file.length() + "B";
		} else {
			fileSize = (file.length() / 1024) + "KB";
		}

		FileInfor fileInfor = new FileInfor();
		fileInfor.setIsSend(true);
		fileInfor.setFileName(sendFile);
		fileInfor.setSize((file.length()));
		// ��ӵ����������
		TransferFilesManager.getInstance().addTransferingFile(sendFilename,
				fileInfor);

		String name = null;
		ClientGroupManager clientGroupManager = ClientGroupManager
				.getInstance();
		if (null != clientGroupManager) {
			name = (String) clientGroupManager.getClientByIp(ipAddress)
					.getInfor(AllConstatns.CLIENTNAME);
		}

		recvTextArea.append("\n  ");
		recvTextArea.appendImage(1000);

		// ��ӵȴ��ļ�
		recvTextArea.insertComponent(new JLabel(" "
				+ UniResources.getString("msk.wait") + name
				+ UniResources.getString("msk.recvfileto") + "\""
				+ sendFilename + "\"(" + fileSize + ")!"));
		recvTextArea.append("\n        ");

		HyperLink cancel = new HyperLink();
		cancel.setText(UniResources.getString("msk.conf.cancel"));
		cancel.setImageTip(createImageIcon("tt.gif"));
		cancel.setPreferredSize(new Dimension(20, 15));

		cancel
				.addActionListener(new CancelAction(sendFilename, ipAddress,
						port));
		HyperLinkManager.getInstance().addHyperLink(
				AllConstatns.FSINTERRUPTCOMMAND + sendFilename, cancel);
		recvTextArea.insertComponent(cancel);
		recvTextArea.append("\n\n");

		// ���ͽ����ļ�����
		msgClient.setSendMsg(header);
		msgClient.start();

		// ����ȷ�Ͻ����ļ��������߳�
		AbstarctServer msgServer = new RecvMsgServer(null);
		// ���÷��������ڵĶ˿�
		msgServer.setPort(port);
		// ������������������ֻ����1��
		msgServer.setMaxServerTimes(1);
		msgServer.start();
	}

	/**
	 * ȡ����ť
	 * 
	 * 
	 */
	class CancelAction implements ActionListener {
		private String key = null;

		private String ipAddress = null;

		private int port = -1;

		public CancelAction(String key, String ipAddress, int port) {
			this.key = key;
			this.ipAddress = ipAddress;
			this.port = port;

		}

		public void actionPerformed(ActionEvent e) {

			AbstractClient msgClient = new SendMsgClient();
			msgClient.setIpAddress(ipAddress, AllConstatns.RECVMSGIPPORT);
			String localIp = null;
			try {
				localIp = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception ee) {
			}
			// ���ͽ����ļ�����
			String header = AllConstatns.PROTOCOLSTARTMARK
					+ AllConstatns.CANNELSENDFILEPROTOCOL
					+ AllConstatns.PROTOCOLENDMARK + AllConstatns.MSGTOKEN
					+ localIp + AllConstatns.IPHEADERMARK + key
					+ AllConstatns.SENDFILENAME + 1 + AllConstatns.FILESIZE
					+ port + AllConstatns.FILESERVERPORT;
			msgClient.setSendMsg(header);
			msgClient.start();
			TransferFilesManager.getInstance().removeTransferingFile(key);
			HyperLinkManager.getInstance().removeHyperLinks(
					AllConstatns.FSINTERRUPTCOMMAND + key);
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtCharacterType) { // ѡ������
			fontChooseShowOrNot = fontChooseShowOrNot ? false : true;
			// ���ذ�ť��ÿ�ο�����ʾ�����������
			if (fontChooseShowOrNot) {
				add(fontChoosePanel, BorderLayout.NORTH);
				updateUI();

			} else {
				remove(fontChoosePanel);
				updateUI();
			}
		} else if (e.getSource() == jbtEmotion) {
			// Point point = jbtEmotion.getLocationOnScreen();
			// ShowFace face = new ShowFace(sendPanel, point.y, point.x);
			showPanel(jbtEmotion);

		} else if (e.getSource() == jbtRefresh) {
			scanOnlinePc();
		}
	}

	private void showPanel(Component owner) {

		if (null != faceShowDlg) {
			faceShowDlg.hide();
			faceShowDlg = null;
		} else {
			Point show = new Point(0, this.getHeight());
			SwingUtilities.convertPointToScreen(show, owner);
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			int x = show.x;
			int y = show.y;
			if (x < 0) {
				x = 0;
			}
			if (x > size.width - 400) {
				x = size.width - 400;
			}
			if (y < size.height - 120) {
			} else {
				y -= 120;
			}
			faceShowDlg = PopupFactory.getSharedInstance().getPopup(owner,
					faceDlg, x, y);

			faceShowDlg.show();
			// isFaceDlgShow = true;
		}
	}

	/**
	 * ����ʾ�Ի����ÿ�
	 */
	public void hideFaceShowDlg() {
		if (null != faceShowDlg) {
			faceShowDlg.hide();
			faceShowDlg = null;
		}
	}

	/**
	 * ����ͼ��,ͼ���붼���浽/resources/images/��Ŀ¼��������Ŀ¼�£�
	 * 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/ui/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

	/**
	 * ֧�ֶ�������
	 */
	public void updateForLanguage() {
		jbtFile.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.Sendfiletip"));

		jbtEmotion.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.Emotiontip"));

		jbtCharacterType.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.CharacterTypetip"));
//		jbtAbout.setToolTipText(UniResources.getString("msk.aboutthis"));
		jbtRefresh.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.refreshtip"));
		jbtSet.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.optionsettip"));
	}

}
