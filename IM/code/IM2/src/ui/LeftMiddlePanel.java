package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.FileInfor;
import com.sylinxsoft.csframework.client.AbstractClient;
import com.sylinxsoft.csframework.client.SendMsgClient;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ServerConfig;
import com.sylinxsoft.csframework.server.ServerObservable;
import com.sylinxsoft.csframework.tcp.TcpFileRecv;
import com.sylinxsoft.util.MsgUtil;
import com.sylinxsoft.util.UniResources;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;

/**
 * 中间面板集合消息显示窗口和中间操作按钮
 * 
 * @author yqg
 * 
 */
public class LeftMiddlePanel extends JPanel implements LanguageChangeEnable {

	// 接收到消息的文本面板，可以显示图片和各种字体
	private JTextAreaEx revTextArea = new JTextAreaEx();

	// 按钮的大小
	private static final int btSize = 18;

	// 面板背景色
	public static final Color BKCOLOR = new Color(215, 233, 251);

	public LeftMiddlePanel() {
		setLayout(new BorderLayout(0, 0));
		// 创建接收面板
		JScrollPane recvPanel = new JScrollPane(revTextArea);
		recvPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		recvPanel
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(recvPanel, BorderLayout.CENTER);
		setBackground(BKCOLOR);
		revTextArea.setEditable(false);
		revTextArea.setDragEnabled(true);
		//增加拖放事件处理 ~2009.3.4
        new DropTarget(revTextArea, DnDConstants.ACTION_COPY , new FileDropTargetListener());
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

	public JTextAreaEx getRecvPanel() {
		return revTextArea;
	}

	public void cannelFileSend(Observable observable) {
		ServerObservable obj = (ServerObservable) observable;
		String msg = obj.getMsg();
		String ip = msg.substring(0, msg.indexOf(AllConstatns.IPHEADERMARK));
		String name = null;
		ClientGroupManager clientGroupManager = ClientGroupManager
				.getInstance();
		if (clientGroupManager != null) {
			name = (String) clientGroupManager.getClientByIp(ip).getInfor(
					AllConstatns.CLIENTNAME);
		}
		// 找不到用户名，则显示IP，否则显示用户名
		name = (name == null ? ip : name);

		String message = msg.substring(msg.indexOf(AllConstatns.IPHEADERMARK)
				+ AllConstatns.IPHEADERMARK.length());

		String fileName = message.substring(0, message
				.indexOf(AllConstatns.SENDFILENAME));

		revTextArea.append("\n  ");
		revTextArea.appendImage(1000);
		revTextArea.insertComponent(new JLabel(" " + name
				+ UniResources.getString("msk.cancelsendfile") + "\""
				+ fileName + "\""));
		revTextArea.append("\n        ");
		// 取消文件接收超链接
		HyperLinkManager.getInstance().removeHyperLinks(fileName);
	}

	public void agreeFileSend(Observable observable) {
		if (observable instanceof ServerObservable) {
			ServerObservable obj = (ServerObservable) observable;
			String msg = obj.getMsg();
			revTextArea.append("\n  ");
			revTextArea.appendImage(1000);
			revTextArea.insertComponent(new JLabel(msg));
			revTextArea.append("\n        ");
		}
	}

	/**
	 * 由于客户端发送文件过来而得到通知
	 * 
	 * @param observable
	 */
	public void updateForSendMsg(Observable observable) {
		if (observable instanceof ServerObservable) {
			ServerObservable obj = (ServerObservable) observable;
			String msg = obj.getMsg();
			String ip = msg
					.substring(0, msg.indexOf(AllConstatns.IPHEADERMARK));
			String name = null;
			ClientGroupManager clientGroupManager = ClientGroupManager
					.getInstance();
			if (clientGroupManager != null) {
				name = (String) clientGroupManager.getClientByIp(ip).getInfor(
						AllConstatns.CLIENTNAME);
			}
			// 找不到用户名，则显示IP，否则显示用户名
			name = (name == null ? ip : name);

			String message = msg.substring(msg
					.indexOf(AllConstatns.IPHEADERMARK)
					+ AllConstatns.IPHEADERMARK.length());

			String fileName = message.substring(0, message
					.indexOf(AllConstatns.SENDFILENAME));
			String fileSize = message.substring(message
					.indexOf(AllConstatns.SENDFILENAME)
					+ AllConstatns.SENDFILENAME.length(), message
					.indexOf(AllConstatns.FILESIZE));

			long size = Long.parseLong(fileSize);
			String sizeStr = "";
			if (size > 1024) {
				sizeStr = size / 1024 + "KB";
			} else {
				sizeStr = size + "B";
			}

			String serverPort = message.substring(message
					.indexOf(AllConstatns.FILESIZE)
					+ AllConstatns.FILESIZE.length(), message
					.indexOf(AllConstatns.FILESERVERPORT));

			FileInfor fileInfor = new FileInfor();
			fileInfor.setFileName(fileName);
			fileInfor.setSize(size);

			revTextArea.append("\n  ");
			revTextArea.appendImage(1000);
			revTextArea.insertComponent(new JLabel(" " + name
					+ UniResources.getString("msk.sendfiletoyou") + "\""
					+ fileName + "\"(" + sizeStr + ")," + "  "
					+ UniResources.getString("msk.youdecision") + " "));
			revTextArea.append("\n        ");
			HyperLink agree = new HyperLink();
			agree.setText(UniResources.getString("msk.recvfile"));

			// 添加
			agree.addActionListener(new AnswerSendFile(
					AllConstatns.FSAGREECOMMAND, fileInfor, ip, Integer
							.parseInt(serverPort)));
			agree.setImageTip(createImageIcon("tt.gif"));
			agree.setPreferredSize(new Dimension(20, 15));
			HyperLinkManager.getInstance().addHyperLink(fileName, agree);
			revTextArea.insertComponent(agree);

			HyperLink agreeto = new HyperLink();
			agreeto.setPreferredSize(new Dimension(30, 15));
			agreeto.setText(UniResources.getString("msk.saveas"));
			agreeto.addActionListener(new AnswerSendFile(
					AllConstatns.FSAGREETOCOMMAND, fileInfor, ip, Integer
							.parseInt(serverPort)));
			HyperLinkManager.getInstance().addHyperLink(fileName, agreeto);
			revTextArea.insertComponent(agreeto);
			HyperLink refuse = new HyperLink();
			refuse.setPreferredSize(new Dimension(20, 15));
			refuse.setText(UniResources.getString("msk.refusefile"));
			refuse.addActionListener(new AnswerSendFile(
					AllConstatns.FSREFUSECOMMAND, fileInfor, ip, Integer
							.parseInt(serverPort)));
			revTextArea.insertComponent(refuse);
			HyperLinkManager.getInstance().addHyperLink(fileName, refuse);
			revTextArea.append("\n\n");
		}
	}

	class AnswerSendFile implements ActionListener {

		private String command = null;

		private String fileName = null;

		private String ipAddress = null;

		private int port = 0;
		// 文件信息对象
		private FileInfor fileInfor;

		public AnswerSendFile(String command, FileInfor fileInfor,
				String ipAddress, int port) {
			this.command = command;
			this.fileInfor = fileInfor;
			this.fileName = fileInfor.getFileName();
			this.ipAddress = ipAddress;
			this.port = port;

		}

		private void sendAnswerMsg(int recvport) {
			String localIp = null;
			try {
				localIp = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
			}
			ServerConfig serverConfig = ServerConfig.getConfig();
			String fsMsg = AllConstatns.PROTOCOLSTARTMARK
					+ AllConstatns.ANSWERSENDFILEPROTOCOL
					+ AllConstatns.PROTOCOLENDMARK + AllConstatns.MSGTOKEN
					+ localIp + AllConstatns.IPHEADERMARK + fileName
					+ AllConstatns.SENDFILENAME + command
					+ AllConstatns.FILECOMMAND + recvport
					+ AllConstatns.FILESERVERPORT;

			// 发送消息客户端线程
			AbstractClient msgClient = new SendMsgClient();
			msgClient.setIpAddress(ipAddress, port);
			// 发送确认接收文件请求
			msgClient.setSendMsg(fsMsg);
			msgClient.start();
		}

		public void actionPerformed(ActionEvent arg0) {
			// 获得文件要保存的路径
			String recvFileName = null;
			// 新文件名称
			String newFileName = null;
			if ((AllConstatns.FSREFUSECOMMAND).equals(command)) {
				// 发出拒绝接收消息，端口没用
				sendAnswerMsg(100);
				// 添加到传输队列中
				HyperLinkManager.getInstance().removeHyperLinks(fileName);
				return;
			}

			// 同意接收
			if ((AllConstatns.FSAGREETOCOMMAND).equals(command)) {
				// 创建一个保存文件对话框
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogType(JFileChooser.SAVE_DIALOG);
				chooser.setDialogTitle(UniResources.getString("msk.tofile")
						+ fileName + UniResources.getString("msk.saveas"));

				chooser.setSelectedFile(new File(fileName));
				chooser.setCurrentDirectory(new File(System
						.getProperty("user.home")));
				int returnVal = chooser.showSaveDialog(null);
				// 用户选择接收文件
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				} else {
					// 获得文件要保存的路径
					recvFileName = chooser.getSelectedFile().toString();
					newFileName = recvFileName.substring(recvFileName
							.lastIndexOf("\\") + 1);
				}
			} else if ((AllConstatns.FSAGREECOMMAND).equals(command)) {
				// 获得文件要保存的路径
				recvFileName = System.getProperty("user.home") + File.separator
						+ fileName;
				newFileName = fileName;
			}

			int recvport = (Integer) (ServerConfig.getConfig()
					.get(AllConstatns.RECVFILEPORT));
			if (-1 == recvport) {
				MessageBox.show(UniResources.getString("msk.notports"));
				return;
			}
			System.out.println("recvport" + recvport);
			sendAnswerMsg(recvport);
			revTextArea.append("\n  ");
			revTextArea.appendImage(1000);
			revTextArea.insertComponent(new JLabel(UniResources
					.getString("msk.agreetrantcpfile")));
			revTextArea.append("\n");
			// 启动数据报端口

			// 启动TCP接受文件线程
			TcpFileRecv recvFile = new TcpFileRecv(recvport, recvFileName,
					fileInfor);
			(new Thread(recvFile)).start();
			HyperLinkManager.getInstance().removeHyperLinks(fileName);
		}
	}

	/** 通知观察者 */
	public void update(Observable observable,
			ClientGroupManager clientGroupManager) {

		if (observable instanceof ServerObservable) {
			ServerObservable obj = (ServerObservable) observable;
			//获取消息
			String msg = obj.getMsg();
			//将消息内容保存在历史记录中
			MsgUtil.save(msg);
			String ip = msg
					.substring(0, msg.indexOf(AllConstatns.IPHEADERMARK));
			String time = new SimpleDateFormat("HH:mm:ss",
					Locale.SIMPLIFIED_CHINESE).format(new java.util.Date());
			String name = null;
			if (clientGroupManager != null
					&& null != clientGroupManager.getClientByIp(ip)) {
				name = (String) clientGroupManager.getClientByIp(ip).getInfor(
						AllConstatns.CLIENTNAME);
			}
			// 找不到用户名，则显示IP，否则显示用户名
			name = (name == null ? ip : name);
			revTextArea.append(name + "  " + time + "\n" + "  ", new Color(
					-16756696), false, 12, UniResources
					.getString("msk.defaultfont"));

			String message = msg.substring(msg
					.indexOf(AllConstatns.IPHEADERMARK)
					+ AllConstatns.IPHEADERMARK.length());

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
				revTextArea.append(message.substring(0, message
						.indexOf(AllConstatns.FACEMARKSTART)), color, bold,
						fontSize, fontFamily);
				int imageIndex = Integer.parseInt(message.substring(message
						.indexOf(AllConstatns.FACEMARKSTART)
						+ AllConstatns.FACEMARKSTART.length(), message
						.indexOf(AllConstatns.FACEMARKEND)));
				revTextArea.appendImage(revTextArea.getText().length(),
						imageIndex);
				message = message.substring(message
						.indexOf(AllConstatns.FACEMARKEND)
						+ AllConstatns.FACEMARKEND.length());
			}
			revTextArea.append(message + "\n", color, bold, fontSize,
					fontFamily);
			// 将滚动条滚到底部
			revTextArea
					.setCaretPosition(revTextArea.getDocument().getLength() - 1);

		}
	}

	public void updateForLanguage() {

	}

}
