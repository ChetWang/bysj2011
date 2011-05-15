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

	// 中间面板部分各按钮
	private JButton jbtFile = new JButton(createImageIcon("tupian.jpg"));

	private JButton jbtRefresh = new JButton(createImageIcon("refresh.jpg"));

	private JButton jbtSet = new JButton(createImageIcon("set.jpg"));

	//private JButton jbtAbout = new JButton(createImageIcon("about.jpg"));

	private JButton jbtCharacterType = new JButton(
			createImageIcon("CharType.jpg"));

	private JButton jbtEmotion = new JButton(createImageIcon("emotion.jpg"));


	private JButton jbtMsg = new JButton(createImageIcon("msg.jpg"));

	// 选择字体的面板是否显示
	private boolean fontChooseShowOrNot = false;

	/** 字体选择面板 */
	private FontChoosePanel fontChoosePanel = null;

	/** 显示表情弹出框 */
	private Popup faceShowDlg;

	/** 表情对话框 */
	private ShowFace faceDlg = null;

	public LeftMiddleBtPanel(JTextAreaEx sendPanel, JTextAreaEx recvTextArea) {
		this.sendPanel = sendPanel;
		this.recvTextArea = recvTextArea;
		// 字体选择框
		fontChoosePanel = new FontChoosePanel(sendPanel);
		// 表情对话框
		faceDlg = new ShowFace(this, sendPanel);
		// 中间面板的大小，其中的按钮的注册，以及按钮的提示信息
		jbtCharacterType.setPreferredSize(new Dimension(btSize, btSize));
		jbtCharacterType.addActionListener(this);
		jbtCharacterType.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.CharacterTypetip"));
		// 设置改变字体按钮的快捷键
		jbtCharacterType.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtEmotion.setPreferredSize(new Dimension(btSize, btSize));
		jbtEmotion.addActionListener(this);
		jbtEmotion.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.Emotiontip"));
		// 设置选择表情按钮的快捷键
		jbtEmotion.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtFile.setPreferredSize(new Dimension(btSize, btSize));
		ActionSendFile actionFile = new ActionSendFile();
		jbtFile.addActionListener(actionFile);
		jbtFile.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.Sendfiletip"));
		// 设置发送文件按钮的快捷键
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
		// 设置连接网段按钮的快捷键
		// jbtSet.registerKeyboardAction(this, KeyStroke.getKeyStroke(
		// KeyEvent.VK_S,java.awt.event.InputEvent.CTRL_MASK),
		// JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtRefresh.setPreferredSize(new Dimension(btSize, btSize));
		jbtRefresh.addActionListener(this);
		jbtRefresh.setToolTipText(UniResources
				.getString("msk.LeftMiddleBtPanel.refreshtip"));
		// 设置刷新按钮的快捷键
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
		
		// 设置关于按钮的快捷键
		// jbtAbout.registerKeyboardAction(this, KeyStroke.getKeyStroke(
		// KeyEvent.VK_L,java.awt.event.InputEvent.CTRL_MASK),
		// JComponent.WHEN_IN_FOCUSED_WINDOW);

		// 设置中间面板的按钮排列方式，背景颜色，大小

		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 5));
		panel.setBackground(BKCOLOR);
		panel
				.setPreferredSize(new Dimension(AllUiConstatns.LEFTPANELWIDTH,
						25));
		// 将一下按钮加到中间面板
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
	 * 获得消息接收框
	 * 
	 * @return
	 */
	public static JTextAreaEx getRecvTextArea() {
		return recvTextArea;
	}

	/**
	 * 扫描在线用户
	 */
	public void scanOnlinePc() {
		jbtRefresh.setEnabled(false);
		// 清除所有在线用户
		ClientGroupManager.getInstance().clearAllClient();
		ClientInforInterface clientInfor = new ClientInfor();
		clientInfor.setInfor(AllConstatns.CLIENTNAME, UniResources
				.getString("msk.all"));
		clientInfor.setInfor(AllConstatns.CLIENTIP, "255.255.255.255");
		ClientGroupManager.getInstance().addClient(clientInfor);

		try {

			// 回发给对方自己的信息
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
			 * // 获得客户端配置信息 ClientConfig clientConfig =
			 * ClientConfig.getConfig(); // 扫描起始IP String start = (String)
			 * clientConfig.get(AllConstatns.SCANSTARTIP); // 扫描起始结束 String end
			 * = (String) clientConfig.get(AllConstatns.SCANENDTIP); String
			 * preIP = start.substring(0, start.lastIndexOf(".") + 1); int
			 * startIp = Integer.parseInt(start.substring(start
			 * .lastIndexOf(".") + 1)); int endIp = Integer.parseInt(end
			 * .substring(end.lastIndexOf(".") + 1)); for (int i = startIp; i <=
			 * endIp; i++) { String ip = preIP + i; System.out.println(ip);
			 * AbstractClient client = new InitOnlinePc(); // 如果地址可达 if
			 * (client.setIpAddress(ip, 5074)) { client.start(); } // 每个线程间隔启动
			 * Thread.sleep(300); } // 停止4秒后才使刷新按钮有效 Thread.sleep(4000);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		jbtRefresh.setEnabled(true);
	}

	/**
	 * 设置选中的IP
	 * 
	 * @param ipAddress
	 */
	public void setSelectIp(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * 响应发送文件的动作
	 * 
	 * 
	 */
	class ActionSendFile implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// 验证IP是否为空
			if (ipAddress.equals("")) {
				JOptionPane.showMessageDialog(null, UniResources
						.getString("msk.leftBottomPanel.iperror"), UniResources
						.getString("msk.error"), JOptionPane.WARNING_MESSAGE);
				return;
			}
			// 创建并弹出选择文件对话框，选择要发送的文件
			JFileChooser chooser = new JFileChooser();
			// /chooser.setMultiSelectionEnabled(true);
			int returnVal = chooser.showOpenDialog(MskFrame.getInstance());

			// 如果已经选择了
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// 获得文件全名
				final String sendFile = chooser.getSelectedFile().toString();
				// 发送文件请求
				sendFileRequest(sendFile, chooser.getSelectedFile().getName());
			}
		}
	}

	/**
	 * 启动发送文件请求
	 */
	public void sendFileRequest(String sendFilePath) {
		// 验证IP是否为空
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
	 * 启动发送文件请求
	 */
	private void sendFileRequest(String sendFile, String sendFilename) {
		// 发送消息客户端线程
		AbstractClient msgClient = new SendMsgClient();
		msgClient.setIpAddress(ipAddress, AllConstatns.RECVMSGIPPORT);
		String localIp = null;
		try {
			localIp = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 取得文件大小
		File file = new File(sendFile);
		ServerConfig serverConfig = ServerConfig.getConfig();
		int port = (Integer) serverConfig.get(AllConstatns.RECVFILEPORT);
		String header = AllConstatns.PROTOCOLSTARTMARK
				+ AllConstatns.SENDFILEPROTOCOL + AllConstatns.PROTOCOLENDMARK
				+ AllConstatns.MSGTOKEN + localIp + AllConstatns.IPHEADERMARK
				+ sendFilename + AllConstatns.SENDFILENAME + file.length()
				+ AllConstatns.FILESIZE + port + AllConstatns.FILESERVERPORT;
		// 判断该文件是否在传输队列中
		if (TransferFilesManager.getInstance().containsTransferingFile(
				sendFilename)) {
			JOptionPane.showMessageDialog(null, UniResources
					.getString("msk.filesending"), UniResources
					.getString("msk.error"), JOptionPane.WARNING_MESSAGE);
			return;
		}

		// 计算文件大小
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
		// 添加到传输队列中
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

		// 添加等待文件
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

		// 发送接收文件请求
		msgClient.setSendMsg(header);
		msgClient.start();

		// 启动确认接收文件服务器线程
		AbstarctServer msgServer = new RecvMsgServer(null);
		// 设置服务器所在的端口
		msgServer.setPort(port);
		// 设置最大服务器，这里只服务1次
		msgServer.setMaxServerTimes(1);
		msgServer.start();
	}

	/**
	 * 取消按钮
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
			// 发送接收文件请求
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
		if (e.getSource() == jbtCharacterType) { // 选择字体
			fontChooseShowOrNot = fontChooseShowOrNot ? false : true;
			// 开关按钮，每次开关显示字体设置面板
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
	 * 将显示对话框置空
	 */
	public void hideFaceShowDlg() {
		if (null != faceShowDlg) {
			faceShowDlg.hide();
			faceShowDlg = null;
		}
	}

	/**
	 * 创建图标,图标请都保存到/resources/images/的目录或其自子目录下，
	 * 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/ui/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

	/**
	 * 支持多种语言
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
