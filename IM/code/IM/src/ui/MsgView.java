package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.util.MsgUtil;
import com.sylinxsoft.util.UniResources;

public class MsgView extends JDialog {
	private JTextAreaEx revTextArea = new JTextAreaEx();

	public MsgView() {
		super(MskFrame.getInstance());
		getContentPane().setLayout(new BorderLayout());
		// 创建接收面板
		JScrollPane recvPanel = new JScrollPane(revTextArea);
		recvPanel
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		recvPanel
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(recvPanel, BorderLayout.CENTER);
		setBackground(AllConstatns.BKCOLOR);
		revTextArea.setEditable(false);
		revTextArea.setDragEnabled(true);
		this.add(recvPanel, BorderLayout.CENTER);
		this.setSize(new Dimension(400, 400));
		this.setTitle("msg view");
		initMsg();
	}

	public void initMsg() {
		Iterator<String> ite = MsgUtil.reader(null);
		ClientGroupManager clientGroupManager = ClientGroupManager
				.getInstance();
		while (ite.hasNext()) {
			String msg = ite.next();
			if (!"".equals(msg)) {
				String date = msg.substring(0, msg
						.indexOf(AllConstatns.IPHEADERMARK));
				msg = msg.substring(msg.indexOf(AllConstatns.IPHEADERMARK)
						+ AllConstatns.IPHEADERMARK.length());
				String ip = msg.substring(0, msg
						.indexOf(AllConstatns.IPHEADERMARK));
				String time = new SimpleDateFormat("HH:mm:ss",
						Locale.SIMPLIFIED_CHINESE).format(new java.util.Date());
				String name = null;

				if (clientGroupManager != null
						&& null != clientGroupManager.getClientByIp(ip)) {
					name = (String) clientGroupManager.getClientByIp(ip)
							.getInfor(AllConstatns.CLIENTNAME);
				}
				// 找不到用户名，则显示IP，否则显示用户名
				name = (name == null ? ip : name);
				if (name.endsWith(AllConstatns.SELFMSGFILEMARK)) {
					name = name.substring(0,name
							.indexOf(AllConstatns.SELFMSGFILEMARK));
					revTextArea.append(name + "  " + date + "\n" + "  ",
							Color.BLUE, false, 12, UniResources
									.getString("msk.defaultfont"));
				} else {
					revTextArea.append(name + "  " + date + "\n" + "  ",
							new Color(-16756696), false, 12, UniResources
									.getString("msk.defaultfont"));
				}

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
				message = message.substring(message
						.indexOf(AllConstatns.HEADEND)
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
			}

		}
	}

	public static void main(String args[]) {
		try {
			MsgView dialog = new MsgView();
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
