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

	// ������Ϣ���ı���壬������ʾͼƬ�͸�������
	private JTextAreaEx sendTextArea = new JTextAreaEx();

	private JTextAreaEx recvTextArea = null;

	private LeftMiddleBtPanel middleBtPanel = null;

	// ��ť���򱳾�ɫ
	public static final Color BTBKCOLOR = new Color(90, 186, 246);

	public static final Color BKCOLOR = new Color(215, 233, 251);

	/** ����ѡ�ֵ�IP��ַ */
	private String ipAddress = new String();

	// �����ĸ���ť
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
		// Ԥ�����ø�����С����������
		// setPreferredSize(new Dimension(AllUiConstatns.LEFTPANELWIDTH, 145));
		setLayout(new BorderLayout(0, 0));
		// ������岼��
		JPanel buttomPanel = new JPanel(new BorderLayout(0, 0));
		// ��������JPanel���ֱ�����Ҽ��밴ť
		JPanel buttomPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel buttomPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttomPanel.add(buttomPanelLeft, BorderLayout.CENTER);
		buttomPanel.add(buttomPanelRight, BorderLayout.EAST);

		// ���õײ����ı�����ɫ
		buttomPanelLeft.setBackground(BTBKCOLOR);
		buttomPanelRight.setBackground(BTBKCOLOR);
		// ����ť�ӵ��ײ����
		//buttomPanelLeft.add(jbtMessageStyle);
		// buttomPanelLeft.add(jbtChatingHistory);
		buttomPanelRight.add(jbtClose);
		buttomPanelRight.add(jbtReplay);
		buttomPanelRight.add(jbtSend);
		// ��Ϣģʽ
		
		jbtReplay.setBackground(BTBKCOLOR);
		// ������ʷ��¼
		// jbtChatingHistory.addActionListener(this);
		// jbtChatingHistory.setBackground(BTBKCOLOR);
		// �رհ�ť
		ClearAction clearAction = new ClearAction();
		jbtClose.addActionListener(clearAction);
		jbtClose.setBackground(BTBKCOLOR);
		// ���ùرհ�ť�Ŀ�ݼ�
		jbtClose.registerKeyboardAction(clearAction, KeyStroke.getKeyStroke(
				KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		// ������Ϣ��ť
		jbtSend.setBackground(BTBKCOLOR);
		SendMsgAction sendAction = new SendMsgAction();
		jbtSend.addActionListener(sendAction);
		jbtReplay.addActionListener(sendAction);
		
		// ���÷�����Ϣ�Ŀ�ݼ�
		jbtSend.registerKeyboardAction(sendAction, KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		// ������Ϣ������ʾ��Ϣ
		jbtSend.setToolTipText(UniResources
				.getString("msk.leftBottomPanel.jbt_senttip"));

		// ʹ������Ϣ������ɹ���
		JScrollPane sendScrollPane = new JScrollPane(sendTextArea);
		// ������ʽ,��ֱ���Թ���
		sendScrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		// ������ʽ,ˮƽ���ܹ���
		sendScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		sendScrollPane.setPreferredSize(new Dimension(
				AllUiConstatns.LEFTPANELWIDTH, 100));

		// Ϊ����崴�����ַ�ʽ
		JPanel panel = new JPanel(new BorderLayout(0, 0));
		// Ԥ�����ø�����С����������

		// ���������ӵ������
		panel.add(buttomPanel, BorderLayout.SOUTH);
		panel.add(sendScrollPane, BorderLayout.CENTER);
		middleBtPanel = getMiddleBtPanel(sendTextArea, recvTextArea);
		panel.add(middleBtPanel, BorderLayout.NORTH);
		setBackground(BKCOLOR);
		add(panel, BorderLayout.CENTER);

		
        //�����Ϸ��¼����� 
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
	 * ����ͼ��,ͼ���붼���浽/resources/images/��Ŀ¼��������Ŀ¼�£�
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
	 * ������Ϣ��ť����
	 * 
	 * @author yqg
	 * 
	 */
	class SendMsgAction implements ActionListener,IErrorReport {
		private void sendMsg(String ipAds) {
			// ��÷��͵���Ϣ
			String message = sendTextArea.getText();
			// ����Ƿ�Ϊ�մ�
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
				// ��֤IP�Ƿ�Ϊ��
				if (ipAddress.equals("")) {
					JOptionPane.showMessageDialog(null, UniResources
							.getString("msk.leftBottomPanel.iperror"),
							UniResources.getString("msk.breftitle"),
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				remoteIpAds = ipAddress;
			}

			// ������Ϣ�ͻ����߳�
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

			// ��֤IP�Ƿ�Ϊ���ҹ㲥�õ�ַ
			if (remoteIpAds.equals("255.255.255.255")) {
				message = codeMessage(" ("
						+ UniResources.getString("msk.talktoall") + ")  "
						+ sendTextArea.getText());
				// ��ʼ���ҹ㲥��Ϣ
				AbstractUdpTransfer.getInstatnce().setRemoteIpAddress(
						"255.255.255.255");
				int remoteport = Integer.parseInt((String) ClientConfig
						.getConfig().get("multicast-localport"));
				AbstractUdpTransfer.getInstatnce().setRemoteIpPort(remoteport);
				AbstractUdpTransfer.getInstatnce()
						.setSendData(header + message);
				AbstractUdpTransfer.getInstatnce().startSend();
				sendTextArea.setText("");
				// ��ʼ�㲥
				return;
			}
			
			// ��֤IP��ַ�Ƿ�ɴ�
			/* ���ﲻ���ж�
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
			recvTextArea.append("\"" + orimsg + "\"\n" + errorMsg + "\n", Color.gray, false, 12, "����");
		}
	}

	/**
	 * ��ʾ����˵����Ϣ
	 * 
	 */
	public void showSelfMsg(String message) {
		String time = new SimpleDateFormat("HH:mm:ss",
				Locale.SIMPLIFIED_CHINESE).format(new java.util.Date());
		String name = (String) ClientConfig.getConfig().get(
				AllConstatns.CLIENTNAME);
		//����Ϣ���浽��Ϣ��¼�ļ���
		MsgUtil.save(name +  AllConstatns.SELFMSGFILEMARK + AllConstatns.IPHEADERMARK + message);
		recvTextArea.append(name + "  " + time + "\n" + "  ", Color.BLUE,
				false, 12, UniResources.getString("msk.defaultfont"));

		// ��ȡ������ɫֵ
		int colorValue = Integer.parseInt(message.substring(message
				.indexOf(AllConstatns.HEADFONTCOLOR)
				+ AllConstatns.HEADFONTCOLOR.length(), message
				.indexOf(AllConstatns.HEADFONTSIZE)));
		Color color = new Color(colorValue);
		// ��ȡ�����С
		int fontSize = Integer.parseInt(message.substring(message
				.indexOf(AllConstatns.HEADFONTSIZE)
				+ AllConstatns.HEADFONTSIZE.length(), message
				.indexOf(AllConstatns.HEADFONTFAMILY)));
		// ��ȡ������
		String fontFamily = message.substring(message
				.indexOf(AllConstatns.HEADFONTFAMILY)
				+ AllConstatns.HEADFONTFAMILY.length(), message
				.indexOf(AllConstatns.HEADBOLD));

		// ��ȡ�����Ƿ�Ϊ��
		boolean bold = Boolean.parseBoolean(message.substring(message
				.indexOf(AllConstatns.HEADBOLD)
				+ AllConstatns.HEADBOLD.length(), message
				.indexOf(AllConstatns.HEADEND)));
		message = message.substring(message.indexOf(AllConstatns.HEADEND)
				+ AllConstatns.HEADEND.length());
		while (message.indexOf(AllConstatns.FACEMARKSTART) != -1) {
			// �����͵���Ϣ���ض�����ʽ�ҵ������
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
		// �������������ײ�
		recvTextArea
				.setCaretPosition(recvTextArea.getDocument().getLength() - 1);
	}

	/**
	 * Ϊ��Ϣ����
	 * 
	 * @param message
	 * @return ��������Ϣ
	 */
	public String codeMessage(String message) {
		// Ϊ��Ϣ���ϱ���,�Է��������ͼƬ
		String headMark = AllConstatns.HEADFONTCOLOR
				+ sendTextArea.getFontColor() + AllConstatns.HEADFONTSIZE
				+ sendTextArea.getFontSize() + AllConstatns.HEADFONTFAMILY
				+ sendTextArea.getFontFamily() + AllConstatns.HEADBOLD
				+ sendTextArea.isBold() + AllConstatns.HEADEND;
		// ��ȡ�����ı���
		StringTokenizer headFace = new StringTokenizer(sendTextArea
				.getHeadImage(), "#$");
		// �����������λ�ú��ĸ�����
		int[] faceArgs = new int[headFace.countTokens()];
		// ������
		int tempCount = 0;
		// ��ȡ��������λ�ú��ĸ�����
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
		// Ϊ��Ϣ���ϱ���
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
	 * �����ļ�����
	 */
	public void sendFileRequest(String filePath) {
         middleBtPanel.sendFileRequest(filePath);
	}

}
