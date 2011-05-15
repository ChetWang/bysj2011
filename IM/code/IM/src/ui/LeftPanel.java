package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ServerObservable;

public class LeftPanel extends JPanel implements Observer,LanguageChangeEnable {

	private LeftBottomPanel bottomPanel = null;

	private LeftMiddlePanel middlePanel = null;

	private ClientGroupManager clientGroupManager = null;

	public LeftPanel(ClientGroupManager clientGroupManager) {

		this.clientGroupManager = clientGroupManager;
		// 预先设置该面板大小，否则会变形
		setPreferredSize(new Dimension(AllUiConstatns.LEFTPANELWIDTH, 480));
		setLayout(new BorderLayout(0, 0));
		setBackground(new Color(93, 183, 255));

		LeftTopBtPanel topPanel = new LeftTopBtPanel();
		middlePanel = new LeftMiddlePanel();
		bottomPanel = new LeftBottomPanel(middlePanel.getRecvPanel());
		add(topPanel, BorderLayout.NORTH);
		add(middlePanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		Border b = BorderFactory.createLineBorder(new Color(139, 168, 198));
		setBorder(b);
	}

	/**扫描在线的机器*/
	public void scanOnlinePc() {
		 bottomPanel.scanOnlinePc();
	}
	
	
	/** 通知观察者 */
	public void update(Observable observable, Object arg) {
		if (observable instanceof PanelBridge) {
			bottomPanel.update(observable, arg);
		} else if (observable instanceof ServerObservable) {
			if (arg instanceof String ) {
				if (AllConstatns.MSGCHANGED.equals((String)arg)) {
			        middlePanel.update(observable, clientGroupManager);
			     } else if (AllConstatns.MSGSENDFILE.equals((String)arg)){
			    	middlePanel.updateForSendMsg(observable);
			     } else if (AllConstatns.CANNELSENDFILEPROTOCOL.equals((String)arg)){
			    	middlePanel.cannelFileSend(observable);
			     } else if (AllConstatns.FSAGREECOMMAND.equals((String)arg)) {
			    	 middlePanel.agreeFileSend(observable);
			     }
				
			}
		}
	}

	public void updateForLanguage() {
		bottomPanel.updateForLanguage();
		middlePanel.updateForLanguage();	
	}

    /**
	 * 发送文件请求
	 */
	public void sendFileRequest(String filePath) {
         bottomPanel.sendFileRequest(filePath);
	}
}
