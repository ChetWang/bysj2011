package ui;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sylinxsoft.csframework.AllConstatns;
import com.sylinxsoft.csframework.server.ClientGroupManager;
import com.sylinxsoft.csframework.server.ClientInforInterface;

/**
 * 在线用户列表，这里采用观察者模式，以便获得最新在线用户列表， 并显示出来
 * 
 * 
 */
public class OnlineListPanel extends JPanel implements Observer {

	private JList list = new JList();

	private DefaultListModel listModel = new DefaultListModel();

	// 客户端管理器
	private ClientGroupManager clientManager = null;

	// 面板桥连接，可降低耦合度
	private PanelBridge panelBridge = null;

	public OnlineListPanel(PanelBridge panelBridge) {
		this.panelBridge = panelBridge;
		list.setCellRenderer(new IconCellRenderer());
		list.setModel(listModel);
		list.addMouseListener(new ListMouseListener());
		setLayout(new BorderLayout(0, 0));
		add(list, BorderLayout.CENTER);
	}

	/**
	 * object是通过notifyObservers method传过来的
	 */
	public void update(Observable Observable, Object object) {

		if (Observable instanceof ClientGroupManager) {
			clientManager = (ClientGroupManager) Observable;
			// 获得所有客户端
			Iterator iterator = clientManager.clients();
			// 删除所有在线用户
			listModel.removeAllElements();
			//list.updateUI();
			// 依次添加
			while (iterator != null && iterator.hasNext()) {
				// 获得一个客户端
				ClientInforInterface client = (ClientInforInterface) iterator
						.next();
				IconListItem item = new IconListItem(
						createImageIcon("user.gif"), (String) (client
								.getInfor(AllConstatns.CLIENTNAME)));
				listModel.addElement(item);
			}
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

	/** 监听鼠标的动作 */
	class ListMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JList list = (JList) e.getSource();
			int index = list.locationToIndex(e.getPoint());
			if (index == -1) {
				return;
			}
			IconListItem item = (IconListItem) list.getModel().getElementAt(
					index);

			// 取得IP地址，保存在桥连接器中，并通知所有观察者，IP已经改变
			if (panelBridge != null && clientManager != null) {
				panelBridge.changedIpAddress((String) clientManager
						.getClientByName(item.getText()).getInfor(
								AllConstatns.CLIENTIP));
			}

			Rectangle rect = list.getCellBounds(index, index);
			list.repaint(rect);
		}
	}

}
