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
 * �����û��б�������ù۲���ģʽ���Ա������������û��б� ����ʾ����
 * 
 * 
 */
public class OnlineListPanel extends JPanel implements Observer {

	private JList list = new JList();

	private DefaultListModel listModel = new DefaultListModel();

	// �ͻ��˹�����
	private ClientGroupManager clientManager = null;

	// ��������ӣ��ɽ�����϶�
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
	 * object��ͨ��notifyObservers method��������
	 */
	public void update(Observable Observable, Object object) {

		if (Observable instanceof ClientGroupManager) {
			clientManager = (ClientGroupManager) Observable;
			// ������пͻ���
			Iterator iterator = clientManager.clients();
			// ɾ�����������û�
			listModel.removeAllElements();
			//list.updateUI();
			// �������
			while (iterator != null && iterator.hasNext()) {
				// ���һ���ͻ���
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
	 * ����ͼ��,ͼ���붼���浽/resources/images/��Ŀ¼��������Ŀ¼�£�
	 * 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/ui/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

	/** �������Ķ��� */
	class ListMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			JList list = (JList) e.getSource();
			int index = list.locationToIndex(e.getPoint());
			if (index == -1) {
				return;
			}
			IconListItem item = (IconListItem) list.getModel().getElementAt(
					index);

			// ȡ��IP��ַ�����������������У���֪ͨ���й۲��ߣ�IP�Ѿ��ı�
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
