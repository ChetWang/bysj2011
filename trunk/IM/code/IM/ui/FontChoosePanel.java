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
 * 字体风格颜色等的选择
 * 
 * @author yqg
 * 
 */
public class FontChoosePanel extends JPanel implements ItemListener,
		ActionListener {

	private JTextAreaEx textFontPanel = null;

	// 字体族选择组合框
	private JComboBox fontFamily = new JComboBox();

	// 字体大小选择组合框
	private JComboBox fontSize = new JComboBox();

	// 按钮大小
	private static final int BTWIDTH = 23;

	private static final int BTHEIGHT = 21;

	// 字体族
	private static final String[] FONTFAMILY = { "Batang", "Dotum", "DotumChe",
			"Fixedsys", "Gulim", "MingLiU", "MS Gothic", "MS PGthic",
			"MS UI Gothic", "PMingLiU", "Terminal", "方正舒体", "方正姚体",
			"仿宋_GB2312", "黑体", "华文彩云", "华文细黑", "华文新魏", "华文中宋", "华文行楷",
			"楷体_GB2312", "隶书", "宋体", "宋体_方正超大字", "新宋体", "幼圆", "Microsoft Sans",
			"Arial Black", "Roman", "Webdings", "Verdana", "WST_Czec" };

	private JButton jbtBold = new JButton(createImageIcon("fontBold.jpg"));

	private JButton fontColor = new JButton(createImageIcon("fontColor.jpg"));

	/**
	 * 要设置字体的JTextAreaEx
	 * 
	 * @param textFontPanel
	 */
	public FontChoosePanel(JTextAreaEx textFontPanel) {

		this.textFontPanel = textFontPanel;
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 2));
		// 设置背景色
		setBackground(LeftMiddlePanel.BKCOLOR);
		// 预先设置大小
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
		// 字体风格被改变
		if (e.getSource() == fontFamily) {
			textFontPanel.setFontFamily((String) e.getItem());
		} else if (e.getSource() == fontSize) {// 字体大小被改变
			textFontPanel.setFontSize(((Integer) e.getItem()).intValue());
		}
		// 更新文本
		refresh();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jbtBold) { // 粗体
			if (textFontPanel.isBold()) {
				textFontPanel.setBold(false);
			} else {
				textFontPanel.setBold(true);
			}
		} else if (e.getSource() == fontColor) { // 颜色

			Color selectedColor = JColorChooser.showDialog(this,
					"Choose a color", Color.BLACK);
			if (selectedColor != null) {
				textFontPanel.setFontColor(selectedColor);
			}
		}
		// 更新文本
		refresh();

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

	/**
	 * 更新文本,将表情文本重新显示
	 * 
	 */
	protected void refresh() {
		try {
			String str = textFontPanel.getText();
			// 提取包含的表情
			StringTokenizer headFace = new StringTokenizer(textFontPanel
					.getHeadImageUnClear(), "#$");
			// 保存表情插入的位置和哪个表情
			int[] faceArgs = new int[headFace.countTokens()];
			// 计数器
			int tempCount = 0;
			// 提取表情插入的位置和哪个表情
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

			// 加上最后字符串
			if (faceArgs[tempCount - 2] + 1 < str.length()) {
				textFontPanel
						.append(str.substring(faceArgs[tempCount - 2] + 1));
			}
		} catch (Exception e) {

		}
	}

}
