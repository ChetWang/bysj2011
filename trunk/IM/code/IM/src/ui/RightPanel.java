package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ServerObservable;
import com.sylinxsoft.util.UniResources;

public class RightPanel extends JPanel implements Observer,
		LanguageChangeEnable {

	/** IP文本框边框大小 */
	private static final int HORZ_PAD = 12;

	private static final int VERT_PAD = 6;
	private JLabel t, l;
	/** 显示IP文本框 */
	private JTextField ipText = new JTextField();

	/** 客户端管理器 */
	private ClientGroupManager clientManager = null;

	private PanelBridge panelBridge = null;
	private ListPane aboutPanel;
	private ListPane recvProgressBars, sendProgressBars;
	private CurtainPane fp;
	JLabel authorLabel;
	JLabel titleLabel;

	public RightPanel(ClientGroupManager clientManager, PanelBridge panelBridge) {
		this.clientManager = clientManager;
		this.panelBridge = panelBridge;
		setPreferredSize(new Dimension(AllUiConstatns.RIGHTPANELWIDTH, 480));
		setBackground(new Color(93, 183, 255));
		setLayout(new BorderLayout());
		initComponent();
	}

	private void initComponent() {
		CurtainPane cp = getCurtainPane();
		cp.setAnimated(true);
		add(cp, BorderLayout.CENTER);
		ipText.setPreferredSize(new Dimension(30, 28));
		ipText.setBackground(new Color(215, 233, 251));

		Border b = BorderFactory.createEmptyBorder(VERT_PAD, HORZ_PAD,
				VERT_PAD, HORZ_PAD);
		ipText.setBorder(b);
		add(ipText, BorderLayout.SOUTH);
	}

	public void setSetSelectedPane(int index) {
		fp.setSelectedPane(index);
	}

	/** 切换到传输面板 */
	public void setTransStatusPane() {
		fp.setSelectedPane(1);
	}

	/** 窗帘式面板 */
	private CurtainPane getCurtainPane() {
		fp = new CurtainPane();
		fp.setAnimated(true);

		// 在线用户显示面板
		OnlineListPanel onlineListPanel = new OnlineListPanel(panelBridge);
		onlineListPanel.setSize(185, 86);
		// 添加一个观察者
		if (clientManager != null) {
			clientManager.addObserver(onlineListPanel);
		}
		fp.addPane("msk.onlineuser", createImageIcon("neighbor.png"),
				onlineListPanel);

		fp.addPane("msk.rightpanel.transtatus", createImageIcon("state.gif"),
				getOtherPlacePane());
		fp.addPane("msk.aboutthis", createImageIcon("copyright.gif"),
				getDetailsPane());

		fp.setSelectedPane(0);
		return fp;
	}

	/** 创建图标 */
	private Icon createImageIcon(String filename) {
		String path = "/ui/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

	private ListPane getDetailsPane() {
		aboutPanel = new ListPane();
		authorLabel = new JLabel(UniResources.getString("msk.author"));
		titleLabel = new JLabel(UniResources.getString("msk.breftitle"));
		aboutPanel.addItem(authorLabel);
		aboutPanel.addItem(new LinkLabel("www.sylinxsoft.com",
				"http://www.sylinxsoft.com"));
		aboutPanel.addItem(titleLabel);
		aboutPanel.setSize(185, 74);
		return aboutPanel;
	}

	private JPanel getOtherPlacePane() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setLayout(new BorderLayout());
		panel
				.setPreferredSize(new Dimension(AllUiConstatns.RIGHTPANELWIDTH,
						0));
		JPanel recvPanel = new JPanel();
		recvPanel.setLayout(new BorderLayout());
		recvPanel.setPreferredSize(new Dimension(
				AllUiConstatns.RIGHTPANELWIDTH, 0));
		t = new JLabel(UniResources.getString("msk.recvingfile"));
		t.setBackground(new Color(255, 255, 255));
		recvPanel.add(t, BorderLayout.NORTH);

		recvProgressBars = new ListPane();

		recvPanel.add(recvProgressBars, BorderLayout.CENTER);
		panel.add(recvPanel, BorderLayout.CENTER);

		JPanel sendPanel = new JPanel();
		sendPanel.setPreferredSize(new Dimension(
				AllUiConstatns.RIGHTPANELWIDTH, 150));
		sendPanel.setLayout(new BorderLayout(0, 5));
		l = new JLabel(UniResources.getString("msk.sengdingfile"));
		l.setBackground(new Color(255, 255, 255));
		sendPanel.add(l, BorderLayout.NORTH);

		sendProgressBars = new ListPane();
		sendProgressBars.setPreferredSize(new Dimension(
				AllUiConstatns.RIGHTPANELWIDTH, 150));
		sendPanel.add(sendProgressBars, BorderLayout.CENTER);
		panel.add(sendPanel, BorderLayout.SOUTH);
		return panel;
	}

	public void addSendProgressBar(String fileName, JProgressBarPanel bar) {
		sendProgressBars.add(bar);
		sendProgressBars.updateUI();
		// bar.setBarString(fileName);
	}

	public void removeSendProgressBar(String fileName, JProgressBarPanel bar) {
		sendProgressBars.remove(bar);
		sendProgressBars.updateUI();
	}

	public void removeRecvProgressBar(String fileName, JProgressBarPanel bar) {
		if (null != bar) {
			recvProgressBars.remove(bar);
		}
		recvProgressBars.updateUI();
	}

	public void addRecvProgressBar(String fileName, JProgressBarPanel bar) {
		recvProgressBars.add(bar);
		recvProgressBars.updateUI();
	}

	/** 通知观察者 */
	public void update(Observable observable, Object arg) {
		if (observable instanceof PanelBridge) {
			ipText.setText(((PanelBridge) observable).getIpAddress());
		} else if (observable instanceof ServerObservable) {

		}
	}

	public void updateForLanguage() {
		fp.updateForLanguage();
		t.setText(UniResources.getString("msk.recvingfile"));
		l.setText(UniResources.getString("msk.sengdingfile"));
		authorLabel.setText(UniResources.getString("msk.author"));
		titleLabel.setText(UniResources.getString("msk.breftitle"));
	}

}
