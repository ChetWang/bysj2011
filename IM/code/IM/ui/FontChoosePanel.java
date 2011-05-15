package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.sylinxsoft.util.UniResources;

/**
 * ��������ɫ�ȵ�ѡ��
 * 
 * @author yqg
 * 
 */
public class FontChoosePanel extends JPanel implements ItemListener,
		ActionListener {

	private JTextAreaEx textFontPanel = null;

	// ������ѡ����Ͽ�
	private JComboBox fontFamily = new JComboBox();

	// �����Сѡ����Ͽ�
	private JComboBox fontSize = new JComboBox();

	// ��ť��С
	private static final int BTWIDTH = 23;

	private static final int BTHEIGHT = 21;

	// ������
	private static final String[] FONTFAMILY = { "Batang", "Dotum", "DotumChe",
			"Fixedsys", "Gulim", "MingLiU", "MS Gothic", "MS PGthic",
			"MS UI Gothic", "PMingLiU", "Terminal", "��������", "����Ҧ��",
			"����_GB2312", "����", "���Ĳ���", "����ϸ��", "������κ", "��������", "�����п�",
			"����_GB2312", "����", "����", "����_����������", "������", "��Բ", "Microsoft Sans",
			"Arial Black", "Roman", "Webdings", "Verdana", "WST_Czec" };

	private JButton jbtBold = new JButton(createImageIcon("fontBold.jpg"));

	private JButton fontColor = new JButton(createImageIcon("fontColor.jpg"));

	/**
	 * Ҫ���������JTextAreaEx
	 * 
	 * @param textFontPanel
	 */
	public FontChoosePanel(JTextAreaEx textFontPanel) {

		this.textFontPanel = textFontPanel;
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		// ���ñ���ɫ
		setBackground(LeftMiddlePanel.BKCOLOR);
		// Ԥ�����ô�С
		setPreferredSize(new Dimension(340, 30));

		jbtBold.setPreferredSize(new Dimension(BTWIDTH, BTHEIGHT));
		jbtBold.addActionListener(this);

		fontColor.setPreferredSize(new Dimension(BTWIDTH, BTHEIGHT));
		fontColor.addActionListener(this);

		fontFamily.setPreferredSize(new Dimension(103, 21));
		int i = FONTFAMILY.length - 1;
		while (i >= 0) {
			fontFamily.addItem(FONTFAMILY[i--]);
		}
		fontFamily.setSelectedItem(
				UniResources.getString("msk.defaultfont"));
		fontFamily.addItemListener(this);

		fontSize.setPreferredSize(new Dimension(55, 21));
		fontSize.addItem(8);
		fontSize.addItem(9);
		fontSize.addItem(10);
		fontSize.addItem(11);
		fontSize.addItem(12);
		fontSize.addItem(14);
		fontSize.addItem(16);
		fontSize.addItem(18);
		fontSize.addItem(20);
		fontSize.addItem(22);
		fontSize.setSelectedItem(12);
		fontSize.addItemListener(this);

		add(fontFamily);
		add(fontSize);
		add(jbtBold);
		add(fontColor);
	}

	public void itemStateChanged(ItemEvent e) {
		// �����񱻸ı�
		if (e.getSource() == fontFamily) {
			textFontPanel.setFontFamily((String) e.getItem());
		} else if (e.getSource() == fontSize) {// �����С���ı�
			textFontPanel.setFontSize(((Integer) e.getItem()).intValue());
		}
		// �����ı�
		refresh();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jbtBold) { // ����
			if (textFontPanel.isBold()) {
				textFontPanel.setBold(false);
			} else {
				textFontPanel.setBold(true);
			}
		} else if (e.getSource() == fontColor) { // ��ɫ

			Color selectedColor = JColorChooser.showDialog(this,
					"Choose a color", Color.BLACK);
			if (selectedColor != null) {
				textFontPanel.setFontColor(selectedColor);
			}
		}
		// �����ı�
		refresh();

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

	/**
	 * �����ı�,�������ı�������ʾ
	 * 
	 */
	protected void refresh() {
		try {
			String str = textFontPanel.getText();
			// ��ȡ�����ı���
			StringTokenizer headFace = new StringTokenizer(textFontPanel
					.getHeadImageUnClear(), "#$");
			// �����������λ�ú��ĸ�����
			int[] faceArgs = new int[headFace.countTokens()];
			// ������
			int tempCount = 0;
			// ��ȡ��������λ�ú��ĸ�����
			while (headFace.hasMoreTokens()) {
				faceArgs[tempCount++] = Integer.parseInt(headFace.nextToken());
			}
			textFontPanel.setText(null);

			if (tempCount == 0) {
				textFontPanel.append(str);
				return;
			}

			tempCount = 0;
			while (tempCount < faceArgs.length) {
				if (tempCount >= 2) {
					if (faceArgs[tempCount] < str.length()) {
						textFontPanel.append(str.substring(
								faceArgs[tempCount - 2] + 1,
								faceArgs[tempCount]));
					} else {
						textFontPanel.append(str
								.substring(faceArgs[tempCount - 2] + 1));
					}
				} else {
					if (faceArgs[tempCount] < str.length()) {
						textFontPanel.append(str.substring(0,
								faceArgs[tempCount]));
					} else {
						textFontPanel.append(str.substring(0));
					}
				}
				textFontPanel.appendImage(textFontPanel.getText().length() + 1,
						faceArgs[tempCount + 1]);
				tempCount += 2;
			}

			// ��������ַ���
			if (faceArgs[tempCount - 2] + 1 < str.length()) {
				textFontPanel
						.append(str.substring(faceArgs[tempCount - 2] + 1));
			}
		} catch (Exception e) {

		}
	}

}
