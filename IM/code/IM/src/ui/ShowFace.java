package ui;

import java.net.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class ShowFace extends JPanel {

	// private JButton bt = new JButton("关闭");
	private JTextAreaEx sendTextArea = null;
	private boolean isSelect = false;
	private Popup parentPopup = null;
	private LeftMiddleBtPanel parentPanel;

	public ShowFace(LeftMiddleBtPanel parentPanel, JTextAreaEx sendTextArea) {
		this.setLayout(new BorderLayout());
		this.sendTextArea = sendTextArea;
		this.parentPanel = parentPanel;
		TableModel model = new QQfaceTableModel();
		final JTable table = new JTable(model);
		table.setSelectionBackground(new Color(90, 186, 246));
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					Point pt = evt.getPoint();
					int rowIndex = table.rowAtPoint(pt);
					int columnIndex = table.columnAtPoint(pt);
					if (rowIndex != -1 && columnIndex != -1) {
						//System.out.println(rowIndex + "   " + columnIndex);
						cancel(rowIndex, columnIndex);
					}
				}
			}
		});

		this.setPreferredSize(new Dimension(400, 120));
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	protected void processMouseEvent(MouseEvent e) {
		if (e.getID() == MouseEvent.MOUSE_ENTERED) {
			isSelect = true;
		} else if (e.getID() == MouseEvent.MOUSE_EXITED) {
			if (isSelect) {
				if (null != parentPanel) {
					parentPanel.hideFaceShowDlg();
				}
			}
		} else {
			super.processMouseEvent(e);
		}
	}

	public void cancel(int rowIndex, int couIndex) {
		sendTextArea.appendImage(rowIndex * 15 + couIndex);
		if (null != parentPanel) {
			parentPanel.hideFaceShowDlg();
		}
	}

	/**
	 * 创建图标,图标请都保存到/resources/images/的目录或其自子目录下，
	 * 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/Face/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

	class QQfaceTableModel extends AbstractTableModel {

		Object[][] cells = new Object[6][15];

		String[] columnNames = new String[15];

		public QQfaceTableModel() {
			int counter = 0;
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 15; j++) {
					String qqFile = new String(counter + ".JPG");
					cells[i][j] = createImageIcon(qqFile);
					counter++;
				}
			}
			for (int i = 0; i < 15; i++) {
				columnNames[i] = null;
			}
		}

		public String getColumnName(int c) {
			return columnNames[c];
		}

		public Class getColumnClass(int c) {
			return cells[0][c].getClass();
		}

		public int getColumnCount() {
			return cells[0].length;
		}

		public int getRowCount() {
			return cells.length;
		}

		public Object getValueAt(int r, int c) {
			return cells[r][c];
		}

		public void setValueAt(Object obj, int r, int c) {
			cells[r][c] = obj;
		}

		public boolean isCellEditable(int r, int c) {
			return false;
		}

	}
}
