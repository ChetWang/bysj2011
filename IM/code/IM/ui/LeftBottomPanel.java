package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.IErrorReport;
import com.sylinxsoft.csframework.client.AbstractClient;
import com.sylinxsoft.csframework.client.ClientConfig;
import com.sylinxsoft.csframework.client.SendMsgClient;
import com.sylinxsoft.csframework.udp.AbstractUdpTransfer;
import com.sylinxsoft.util.MsgUtil;
import com.sylinxsoft.util.UniResources;


public class LeftBottomPanel extends JPanel implements LanguageChangeEnable {

	// 发送消息的文本面板，可以显示图片和各种字体
	private JTextAreaEx sendTextArea = new JTextAreaEx();

	private JTextAreaEx recvTextArea = null;

	private LeftMiddleBtPanel middleBtPanel = null;

	// 按钮区域背景色
	public static final Color BTBKCOLOR = new Color(90, 186, 246);

	public static final Color BKCOLOR = new Color(215, 233, 251);

	/** 保存选种的IP地址 */
	private String ipAddress = new String();

	// 底下四个按钮
	private JButton jbtSend = new JButton(UniResources
			.getString("msk.leftBottomPanel.jbt_sent"));

	private JButton jbtClose = new JButton(UniResources
			.getString("msk.leftBottomPanel.jbt_clear"));

	private JButton jbtChatingHistory = new JButton(UniResources
			.getString("msk.leftBottomPanel.jbt_record"));

	private JButton jbtReplay = new JButton(UniResources
			.getString("msk.leftBottomPanel.jbt_msg"));

	public LeftBottomPanel(JTextAreaEx recvTextArea) {
		this.recvTextArea = recvTextArea;
		sendTextArea.setDocumentListener();
		// 预先设置该面板大小，否则会变形
		// setPreferredSize(new Dimension(AllUiConstatns.LEFTPANELWIDTH, 145));
		setLayout(new BorderLayout(0, 0));
		// 设置面板布局
		JPanel buttomPanel = new JPanel(new BorderLayout(0, 0));
		// 创建两个JPanel，分别从左，右加入按钮
		JPanel buttomPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel buttomPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttomPanel.add(buttomPanelLeft, BorderLayout.CENTER);
		buttomPanel.add(buttomPanelRight, BorderLayout.EAST);

		// 设置底部面板的背景颜色
		buttomPanelLeft.setBackground(BTBKCOLOR);
		buttomPanelRight.setBackground(BTBKCOLOR);
		// 将按钮加到底部面板
		//buttomPanelLeft.add(jbtMessageStyle);
		// buttomPanelLeft.add(jbtChatingHistory);
		buttomPanelRight.add(jbtClose);
		buttomPanelRight.add(jbtReplay);
		buttomPanelRight.add(jbtSend);
		// 消息模式
		
		jbtReplay.setBackground(BTBKCOLOR);
		// 聊天历史记录
		// jbtChatingHistory.addActionListener(this);
		// jbtChatingHistory.setBackground(BTBKCOLOR);
		// 关闭按钮
		ClearAction clearAction = new ClearAction();
		jbtClose.addActionListener(clearAction);
		jbtClose.setBackground(BTBKCOLOR);
		// 设置关闭按钮的快捷键
		jbtClose.registerKeyboardAction(clearAction, KeyStroke.getKeyStroke(
				KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		// 发送消息按钮
		jbtSend.setBackground(BTBKCOLOR);
		SendMsgAction sendAction = new SendMsgAction();
		jbtSend.addActionListener(sendAction);
		jbtReplay.addActionListener(sendAction);
		
		// 设置发送消息的快捷键
		jbtSend.registerKeyboardAction(sendAction, KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		// 发送消息键的提示信息
		jbtSend.setToolTipText(UniResources
				.getString("msk.leftBottomPanel.jbt_senttip"));

		// 使发送消息的区域可滚动
		JScrollPane sendScrollPane = new JScrollPane(sendTextArea);
		// 滚动方式,垂直可以滚动
		sendScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// 滚动方式,水平不能滚动
		sendScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		sendScrollPane.setPreferredSize(new Dimension(
				AllUiConstatns.LEFTPANELWIDTH, 100));

		// 为各面板创建布局方式
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		// 预先设置该面板大小，否则会变形

		// 将三块面板加到框架中
		panel.add(buttomPanel, BorderLayout.SOUTH);
		panel.add(sendScrollPane, BorderLayout.CENTER);
		middleBtPanel = getMiddleBtPanel(sendTextArea, recvTextArea);
		panel.add(middleBtPanel, BorderLayout.NORTH);
		setBackground(BKCOLOR);
		add(panel, BorderLayout.CENTER);

		
        //增加拖放事件处理 
        new DropTarget(sendTextArea, DnDConstants.ACTION_COPY , new FileDropTargetListener());
	}

	private LeftMiddleBtPanel getMiddleBtPanel(JTextAreaEx textFontPanel,
			JTextAreaEx recvTextArea) {
		LeftMiddleBtPanel panel = new LeftMiddleBtPanel(textFontPanel,
				recvTextArea);
		return panel;
	}

	public void scanOnlinePc() {
		middleBtPanel.scanOnlinePc();
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

	public void update(Observable observable, Object arg) {
		if (observable instanceof PanelBridge) {
			ipAddress = ((PanelBridge) observable).getIpAddress();
			middleBtPanel.setSelectIp(ipAddress);
		}
	}

	class ClearAction implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			sendTextArea.setText("");
			sendTextArea.getHeadImage();
		}
	}

	/**
	 * 发送消息按钮动作
	 * 
	 * @author yqg
	 * 
	 */
	class SendMsgAction implements ActionListener,IErrorReport {
		private void sendMsg(String ipAds) {
			// 获得发送的消息
			String message = sendTextArea.getText();
			// 检查是否为空串
			if (message.equals("")
					&& sendTextArea.getHeadImageUnClear().length() == 0) {
				MessageBox.show(null,UniResources.getString("msk.sendmsgnotnull"),UniResources.getString("msk.breftitle"),
						MessageBox.ICONERROR);
				return;
			}
			String remoteIpAds  = "";
			if ( null != ipAds ) {
				remoteIpAds = ipAds;
			} else {
				// 验证IP是否为空
				if (ipAddress.equals("")) {
					JOptionPane.showMessageDialog(null, UniResources
							.getString("msk.leftBottomPanel.iperror"),
							UniResources.getString("msk.breftitle"),
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				remoteIpAds = ipAddress;
			}

			// 发送消息客户端线程
			AbstractClient msgClient = new SendMsgClient();
			msgClient.setIpAddress(remoteIpAds, AllConstatns.RECVMSGIPPORT);

			String localIp = null;
			try {
				localIp = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
			}

			String header = AllConstatns.PROTOCOLSTARTMARK
					+ AllConstatns.GENERALMSGPCPROTOCOL
					+ AllConstatns.PROTOCOLENDMARK + AllConstatns.MSGTOKEN
					+ localIp + AllConstatns.IPHEADERMARK;

			// 验证IP是否为向大家广播得地址
			if (remoteIpAds.equals("255.255.255.255")) {
				message = codeMessage(" ("
						+ UniResources.getString("msk.talktoall") + ")  "
						+ sendTextArea.getText());
				// 开始向大家广播消息
				AbstractUdpTransfer.getInstatnce().setRemoteIpAddress(
						"255.255.255.255");
				int remoteport = Integer.parseInt((String) ClientConfig
						.getConfig().get("multicast-localport"));
				AbstractUdpTransfer.getInstatnce().setRemoteIpPort(remoteport);
				AbstractUdpTransfer.getInstatnce()
						.setSendData(header + message);
				AbstractUdpTransfer.getInstatnce().startSend();
				sendTextArea.setText("");
				// 开始广播
				return;
			}
			
			// 验证IP地址是否可达
			/* 这里不做判断
			try {
				InetAddress address = InetAddress.getByName(remoteIpAds);
				if (!address.isReachable(2000)) {
					JOptionPane.showMessageDialog(null, UniResources
							.getString("msk.sendtimeout"), UniResources
							.getString("msk.error"),
							JOptionPane.WARNING_MESSAGE);
					return;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, UniResources
						.getString("msk.sendtimeout")
						+ e, UniResources.getString("msk.error"),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			*/
			message = codeMessage(sendTextArea.getText());
			showSelfMsg(message);
			msgClient.setSendMsg(header + message);
			msgClient.start();
			sendTextArea.setText("");
		}
		
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtReplay) {
				final String lastIpAds = MskFrame.getInstance().getLastMsgIpAddress();
				sendMsg(lastIpAds);
			} else {
				sendMsg(null);
			}
		}
		
		public void reportError(String orimsg,String errorMsg) {
			if (-1 != orimsg.indexOf(AllConstatns.FACEMARKEND)) {
				orimsg = orimsg.substring(orimsg
						.indexOf(AllConstatns.FACEMARKEND)
						+ AllConstatns.FACEMARKEND.length());
			} 
			recvTextArea.append("\"" + orimsg + "\"\n" + errorMsg + "\n", Color.gray, false, 12, "宋体");
		}
	}

	/**
	 * 显示自身说的消息
	 * 
	 */
	public void showSelfMsg(String message) {
		String time = new SimpleDateFormat("HH:mm:ss",
				Locale.SIMPLIFIED_CHINESE).format(new java.util.Date());
		String name = (String) ClientConfig.getConfig().get(
				AllConstatns.CLIENTNAME);
		//将消息保存到消息记录文件中
		MsgUtil.save(name +  AllConstatns.SELFMSGFILEMARK + AllConstatns.IPHEADERMARK + message);
		recvTextArea.append(name + "  " + time + "\n" + "  ", Color.BLUE,
				false, 12, UniResources.getString("msk.defaultfont"));

		// 提取字体颜色值
		int colorValue = Integer.parseInt(message.substring(message
				.indexOf(AllConstatns.HEADFONTCOLOR)
				+ AllConstatns.HEADFONTCOLOR.length(), message
				.indexOf(AllConstatns.HEADFONTSIZE)));
		Color color = new Color(colorValue);
		// 提取字体大小
		int fontSize = Integer.parseInt(message.substring(message
				.indexOf(AllConstatns.HEADFONTSIZE)
				+ AllConstatns.HEADFONTSIZE.length(), message
				.indexOf(AllConstatns.HEADFONTFAMILY)));
		// 提取字体风格
		String fontFamily = message.substring(message
				.indexOf(AllConstatns.HEADFONTFAMILY)
				+ AllConstatns.HEADFONTFAMILY.length(), message
				.indexOf(AllConstatns.HEADBOLD));

		// 提取字体是否为粗
		boolean bold = Boolean.parseBoolean(message.substring(message
				.indexOf(AllConstatns.HEADBOLD)
				+ AllConstatns.HEADBOLD.length(), message
				.indexOf(AllConstatns.HEADEND)));
		message = message.substring(message.indexOf(AllConstatns.HEADEND)
				+ AllConstatns.HEADEND.length());
		while (message.indexOf(AllConstatns.FACEMARKSTART) != -1) {
			// 将发送的消息以特定的样式家到面板中
			recvTextArea.append(message.substring(0, message
					.indexOf(AllConstatns.FACEMARKSTART)), color, bold,
					fontSize, fontFamily);
			int imageIndex = Integer.parseInt(message.substring(message
					.indexOf(AllConstatns.FACEMARKSTART)
					+ AllConstatns.FACEMARKSTART.length(), message
					.indexOf(AllConstatns.FACEMARKEND)));
			recvTextArea.appendImage(recvTextArea.getText().length(),
					imageIndex);
			message = message.substring(message
					.indexOf(AllConstatns.FACEMARKEND)
					+ AllConstatns.FACEMARKEND.length());
		}
		recvTextArea.append(message + "\n", color, bold, fontSize, fontFamily);
		// 将滚动条滚到底部
		recvTextArea
				.setCaretPosition(recvTextArea.getDocument().getLength() - 1);
	}

	/**
	 * 为消息编码
	 * 
	 * @param message
	 * @return 编码后的消息
	 */
	public String codeMessage(String message) {
		// 为消息加上编码,以发送字体和图片
		String headMark = AllConstatns.HEADFONTCOLOR
				+ sendTextArea.getFontColor() + AllConstatns.HEADFONTSIZE
				+ sendTextArea.getFontSize() + AllConstatns.HEADFONTFAMILY
				+ sendTextArea.getFontFamily() + AllConstatns.HEADBOLD
				+ sendTextArea.isBold() + AllConstatns.HEADEND;
		// 提取包含的表情
		StringTokenizer headFace = new StringTokenizer(sendTextArea
				.getHeadImage(), "#$");
		// 保存表情插入的位置和哪个表情
		int[] faceArgs = new int[headFace.countTokens()];
		// 计数器
		int tempCount = 0;
		// 提取表情插入的位置和哪个表情
		while (headFace.hasMoreTokens()) {
			faceArgs[tempCount++] = Integer.parseInt(headFace.nextToken());
			// System.out.println(faceArgs[tempCount - 1]);
		}

		StringBuffer strMsg = new StringBuffer(headMark);
		StringBuffer msg = new StringBuffer(message);
		tempCount = 0;
		int acuCount = 0;
		while (tempCount < faceArgs.length) {
			msg.insert(faceArgs[tempCount] + acuCount,
					AllConstatns.FACEMARKSTART
							+ String.valueOf(faceArgs[tempCount + 1]
									+ AllConstatns.FACEMARKEND));
			acuCount += 10 + String.valueOf(faceArgs[tempCount + 1]).length();
			tempCount = tempCount + 2;

		}
		// 为消息加上编码
		message = headMark + msg.toString();
		return message;
	}

	public void updateForLanguage() {
		jbtSend.setText(UniResources.getString("msk.leftBottomPanel.jbt_sent"));
		jbtSend.setToolTipText(UniResources
				.getString("msk.leftBottomPanel.jbt_senttip"));
		jbtClose.setText(UniResources
				.getString("msk.leftBottomPanel.jbt_clear"));
		jbtChatingHistory.setText(UniResources
				.getString("msk.leftBottomPanel.jbt_record"));
		jbtReplay.setText(UniResources
				.getString("msk.leftBottomPanel.jbt_msg"));
		middleBtPanel.updateForLanguage();
	}

	 /**
	 * 发送文件请求
	 */
	public void sendFileRequest(String filePath) {
         middleBtPanel.sendFileRequest(filePath);
	}

}
