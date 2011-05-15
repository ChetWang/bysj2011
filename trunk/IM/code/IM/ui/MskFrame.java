package ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.sylinxsoft.constants.Constants;
import com.sylinxsoft.csframework.ConfigSet;
import com.sylinxsoft.csframework.LockOneApp;
import com.sylinxsoft.csframework.server.AbstarctServer;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ListenerServer;
import com.sylinxsoft.csframework.server.RecvMsgServer;
import com.sylinxsoft.csframework.server.ServerObservable;
import com.sylinxsoft.util.Ui;
import com.sylinxsoft.util.UniResources;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;


public class MskFrame extends JFrame implements LanguageChangeEnable {
	private boolean isTraySupported = false;
	private static MskFrame msk = null;
	public static LAF[] LAFS;
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	private MenuItem show, exit, about;
	
	/**����յ���Ϣ������*/
	private String lastMsgIpAddress;

	public MskFrame() {
		// ���趨��Դ�ļ�
		setUniResources();
		// ���ж��Ƿ��Ѿ�����������
		if (true == LockOneApp.isAppActive()) {
			JOptionPane.showMessageDialog(this, UniResources
					.getString("msk.staruperror"));
			System.exit(0);
		}

		setLookAndFel();
		refreshLookAndFeel();

		// ���������۲���
		ServerObservable serverObservable = ServerObservable.getInstance();
		// ������������ӣ�ͨ��������������˴�ͨ��
		PanelBridge panelBridge = new PanelBridge();

		// /////////////////////////////////////////////////////////////////
		// / UI��ʾ���ֹ��쿪ʼ
		// /////////////////////////////////////////////////////////////////

		// �������ô�С
		setPreferredSize(new Dimension(512, 480));
		if (Constants.ISSHUTDOWN) {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		} else {
			setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		}
		Image img = createImageIcon("title.jpg").getImage();
		setIconImage(img);
		// ֧�ֹ��ʻ�
		setTitle(UniResources.getString("msk.title"));
		Container container = getContentPane();

		container.setLayout(new BorderLayout(0, 0));
		container.setBackground(new Color(93, 183, 255));

		leftPanel = new LeftPanel(ClientGroupManager.getInstance());
		// clientGroupManager,panelBridge���Ǵ��ݸ�OnlineListPanelʹ��
		rightPanel = new RightPanel(ClientGroupManager.getInstance(),
				panelBridge);
		container.add(leftPanel, BorderLayout.CENTER);
		container.add(rightPanel, BorderLayout.EAST);
		pack();
		// ������ʾ
		Ui.centerFrame(this);
		setVisible(true);

		// /////////////////////////////////////////////////////////////////
		// / UI��ʾ���ֹ������
		// /////////////////////////////////////////////////////////////////

		// /////////////////////////////////////////////////////////////////
		// / UI��UI֮���Լ�UI�ͺ�̨��������ͨ�Ż��ƵĴ�����������ù۲���ģʽ��
		// /////////////////////////////////////////////////////////////////
		// ���PanelBridge�۲���
		panelBridge.addObserver(leftPanel);
		// ���PanelBridge�۲���
		panelBridge.addObserver(rightPanel);
		// ���ServerObservable�۲���
		serverObservable.addObserver(leftPanel);
		serverObservable.addObserver(rightPanel);

		// /////////////////////////////////////////////////////////////////
		// / ��̨�������̵߳Ĺ���,���ﹹ��ͻ����̺߳ͷ��������߳�ȫ���ǳ������
		// / ʹ����ֱ����Ե���һ�������.
		// /////////////////////////////////////////////////////////////////
		// ����TCPЭ�� �����������߳�, ��Ϊɨ���ٶ��������Է�������
		//AbstarctServer listenServer = new ListenerServer(serverObservable);
		//listenServer.start();

		// ����UDPЭ��
		com.sylinxsoft.csframework.udp.ListenerServer listenServer1 = new com.sylinxsoft.csframework.udp.ListenerServer();
		listenServer1.start();

		// ������Ϣ�������̼߳����������߳�
		AbstarctServer msgServer = new RecvMsgServer(serverObservable);
		msgServer.start();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}

		leftPanel.scanOnlinePc();

		TrayIcon trayIcon = null;
		if (SystemTray.isSupported()) {
			isTraySupported = true;
			SystemTray tray = SystemTray.getSystemTray();
			MouseListener listener = new MouseListener() {
				public void mouseClicked(MouseEvent e) {
					// �����Ҽ���
					if (e.getButton() != e.BUTTON3) {
						setVisible(true);
						setState(0);
					}
				}

				public void mouseExited(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

			};

			PopupMenu popup = new PopupMenu();
			about = new MenuItem();
			about.setLabel(UniResources.getString("msk.about"));
			about.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AboutDialog dlg = new AboutDialog();
					dlg.setModal(true);
					Ui.centerFrame(dlg);
					dlg.setVisible(true);
				}
			});
			show = new MenuItem();
			show.setLabel(UniResources.getString("msk.openmsk"));
			exit = new MenuItem();
			exit.setLabel(UniResources.getString("msk.quitmsk"));
			ActionListener al;
			// �˳���ť
			al = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					System.exit(0);
				}
			};
			exit.addActionListener(al);
			// ��ʾ��ť
			al = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(true);
					setState(0);
				}
			};
			show.addActionListener(al);

			popup.add(about);
			popup.add(show);
			popup.addSeparator();
			popup.add(exit);

			trayIcon = new TrayIcon(img, UniResources
					.getString("msk.traytitle"), popup);
			trayIcon.addMouseListener(listener);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
				isTraySupported = false;
			}
			trayIcon.setImageAutoSize(true);

		}

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				//dispose();
				//System.exit(0);
				formWindowIconified(evt);
			}

			public void windowDeiconified(java.awt.event.WindowEvent evt) {

			}

			public void windowIconified(java.awt.event.WindowEvent evt) {
				formWindowIconified(evt);
			}
		});
        //�����Ϸ��¼����� ~2009.3.4
        new DropTarget(this, DnDConstants.ACTION_COPY , new FileDropTargetListener());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (Exception e) {
		// }

		if (null == msk) {
			msk = new MskFrame();
		}
	}

	public static MskFrame getInstance() {
		return msk;
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

	private void formWindowIconified(java.awt.event.WindowEvent evt) {
		this.setState(javax.swing.JFrame.ICONIFIED);
		if (isTraySupported)
			this.setVisible(false);

	}

	public void setLookAndFel() {
		LAFS = new LAF[4];
		LAFS[0] = new LAF("Windows",
				"com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
				LAF.THEME_DEAULT);
		LAFS[3] = new LAF("Liquid", "com.birosoft.liquid.LiquidLookAndFeel",
				LAF.THEME_DEAULT);

		LAFS[2] = new LAF("Nimrod Ocean",
				"com.nilo.plaf.nimrod.NimRODLookAndFeel", LAF.THEME_OCEAN);
		LAFS[1] = new LAF("Nimrod Gold",
				"com.nilo.plaf.nimrod.NimRODLookAndFeel", LAF.THEME_GOLD);

		// Set the first enabled look and feel.
		// ���õ�1���������
		for (int index = 0; index < LAFS.length; index++) {
			try {
				if (LAFS[index].isSupported()) {
					LAFS[index].setSelected(true);
					UIManager.setLookAndFeel(LAFS[index].getClassName());
					break;
				}
			} catch (Exception e) {
			}
		}
	}

	public LAF[] getLookAndFel() {
		return LAFS;
	}

	public void setLookAndFeel(LAF laf) {
		try {
			UIManager.setLookAndFeel(laf.getClassName());
			LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
			LAF.setTheme(lookAndFeel, laf.getTheme());
			UIManager.setLookAndFeel(lookAndFeel);
			SwingUtilities.updateComponentTreeUI(this);
			SwingUtilities.updateComponentTreeUI(leftPanel);
			SwingUtilities.updateComponentTreeUI(rightPanel);
		} catch (Exception e) {
		}
	}

	public void refreshLookAndFeel() {
		String skin = ConfigSet.getInstance().getProperty("skin");
		
		if (null == skin || "default".equals(skin)) {
			setLookAndFeel(LAFS[0]);
		} else {
			for (int i = 0; i < LAFS.length; ++i) {
				if (skin.equals(LAFS[i].getTitle())) {
					setLookAndFeel(LAFS[i]);
					return;
				}
			}
		}
	}

	public void refreshLanguage() {
		setUniResources();
		updateForLanguage();
		leftPanel.updateForLanguage();
		rightPanel.updateForLanguage();
	}

	public void updateForLanguage() {
		setTitle(UniResources.getString("msk.title"));
		about.setLabel(UniResources.getString("msk.about"));
		show.setLabel(UniResources.getString("msk.openmsk"));
		exit.setLabel(UniResources.getString("msk.quitmsk"));
	}

	public void setUniResources() {
		String language = ConfigSet.getInstance().getProperty("language");
		if ("english".equals(language)) {
			UniResources.setUniResources(UniResources.ENGLISHBOUNDLE);
		} else if ("chinese_TW".equals(language)) {
			UniResources.setUniResources(UniResources.TAIWANBOUNDLE);
		} else if ("chinese".equals(language)) {
			UniResources.setUniResources(UniResources.CHINABOUNDLE);
		}
	}

	public RightPanel getRightPanel() {
		return rightPanel;
	}

    /**
	 * �����ļ�����
	 */
	public void sendFileRequest(String filePath) {
         leftPanel.sendFileRequest(filePath);
	}

	/**
	 * �����ļ�����
	 */
	public void sendFileFoldRequest(String filePath) {
         //MessageBox.show(ConfigSet.getInstance().getProperty(""));
		 MessageBox.show("�ð汾�в�֧���ļ��д���!");
	}
	
	public String getLastMsgIpAddress() {
		return lastMsgIpAddress;
	}

	public void setLastMsgIpAddress(String lastMsgIpAddress) {
		this.lastMsgIpAddress = lastMsgIpAddress;
	}


}
