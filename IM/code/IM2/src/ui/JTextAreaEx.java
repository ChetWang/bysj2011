package ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;

/**
 * JTextAreaEx扩展了JTextPane，便于显示各种字体和图片及控件
 * 
 * @author 颜清国
 * 
 */
public class JTextAreaEx extends JTextPane {

	/** 字体颜色 */
	private Color color = Color.BLACK;

	private int fontSize = 12;

	/** 字体风格 */
	private String fontFamily = "宋体";

	/** 是否粗体 */
	private boolean bold = false;

	private Document doc = null;

	/** 保存文件头信息 */

	private List<IntegerEx> postionList = new ArrayList<IntegerEx>();

	private List<IntegerEx> imageList = new ArrayList<IntegerEx>();

	public void setDocumentListener() {
		doc = getDocument();
		// 获得与此相联的文档
		doc.addDocumentListener(new FilterEdit());
	}

	/**
	 * 设置字体的颜色
	 * 
	 * @param c
	 */
	public void setFontColor(Color c) {
		color = c;

	}

	/**
	 * @return 字体的颜色
	 */
	public int getFontColor() {
		return color.getRGB();
	}

	/**
	 * @return 字体的颜色
	 */
	public Color getFontFromColor() {
		return color;
	}

	/**
	 * 设置字体的大小
	 */
	public void setFontSize(int size) {
		fontSize = size;
	}

	/**
	 * 
	 * @return 字体的大小
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * 设置字体的风格
	 * 
	 * @param fontStyle
	 */
	public void setFontFamily(String fontStyle) {
		fontFamily = fontStyle;
	}

	/**
	 * 
	 * @return 字体族
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * 设置粗体
	 * 
	 * @param bordtag
	 */
	public void setBold(boolean bordtag) {
		bold = bordtag;
	}

	/**
	 * 
	 * @return 是否粗体
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * 添加一段默认属性的文本
	 * 
	 */
	public void append(String str) {
		append(str, color, bold, fontSize, fontFamily);
	}

	/**
	 * 
	 * @return 包含的表情信息
	 */
	public String getHeadImage() {
		int i = 0;
		StringBuffer headImag = new StringBuffer();
		while (i < imageList.size()) {
			headImag.append(postionList.get(i).intValue() + "#"
					+ imageList.get(i).intValue() + "$");
			i++;
		}
		String temp = headImag.toString();
		imageList.clear();
		postionList.clear();
		return temp;
	}

	/**
	 * 
	 * @return 包含的表情信息
	 */
	public String getHeadImageUnClear() {
		int i = 0;
		StringBuffer headImag = new StringBuffer();
		while (i < imageList.size()) {
			headImag.append(postionList.get(i).intValue() + "#"
					+ imageList.get(i).intValue() + "$");
			i++;
		}
		String temp = headImag.toString();
		return temp;
	}

	/**
	 * 添加一段特有属性的文本
	 * 
	 */
	public void append(String str, Color col, boolean bold, int size,
			String fontFamily) {

		// 获得与此相联的文档
		Document doc = getDocument();
		// doc.addDocumentListener(new FilterEdit());
		// 创建一个字体空属性,以下依次填充
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		// 设置字体的颜色
		StyleConstants.setForeground(attrSet, col);
		// 设置是否粗体
		if (bold == true) {
			StyleConstants.setBold(attrSet, true);
		}
		// 设置字体的大小
		StyleConstants.setFontSize(attrSet, size);
		// 设置字体的风格
		StyleConstants.setFontFamily(attrSet, fontFamily);
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			System.out.println(e);
		}
	}

	/**
	 * 插入一幅图片,并且保存一些信息
	 * 
	 * @param imageIndex
	 */
	public void appendImage(int imageIndex) {

		postionList.add(new IntegerEx(getSelectionEnd()));
		imageList.add(new IntegerEx(imageIndex));

		Icon image = createImageIcon(String.valueOf(imageIndex) + ".gif");
		// 插入一个图标
		select(getText().length(), getText().length());
		insertIcon(image);
	}

	public void insertComponent(Component c) {
		select(getText().length(), getText().length());
		super.insertComponent(c);
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

	/**
	 * 插入一幅图片
	 * 
	 * @param imageIndex
	 */
	public void appendImage(int position, int imageIndex) {
		Icon image = createImageIcon(String.valueOf(imageIndex) + ".gif");
		// 选定要插入的位置
		select(position - 1, position);
		// 插入一个图标
		insertIcon(image);
	}

	class FilterEdit implements DocumentListener {

		public void changedUpdate(DocumentEvent event) {
		}

		/** 插入的时候做处理 */
		public void insertUpdate(DocumentEvent event) {
			int position = event.getOffset();
			int length = event.getLength();
			int i = 0;
			while (i < imageList.size()) {
				// 表情插在第一个，特殊处理
				if (postionList.get(i).intValue() == position) {
					if (position == 0) {
						postionList.get(i).setValue(
								postionList.get(i).intValue() + length);
					}
					// System.out.println("ppp:" +
					// postionList.get(i).intValue());
					// System.out.println("ppp:" + doc.getLength());
					// else if (postionList.get(i).intValue() doc.getLength())

				} else
				// 位置大于则要加上插入的字符长度,这样表情插入的位置才刚好合适
				if (postionList.get(i).intValue() > position
						&& postionList.get(i).intValue() < doc.getLength()) {
					// System.out.println("fff:" + event.getOffset());
					postionList.get(i).setValue(
							postionList.get(i).intValue() + length);
					// System.out.println("fff:" + event.getOffset());
				}
				i++;
			}
		}

		/** 删除的时候 */
		public void removeUpdate(DocumentEvent event) {
			int position = event.getOffset();
			int length = event.getLength();
			int i = 0;

			while (i < imageList.size()) {
				if (postionList.get(i).intValue() == position) {
					postionList.remove(i);
					imageList.remove(i);
				} else if (postionList.get(i).intValue() > doc.getLength()
						&& doc.getLength() != 0) {
					postionList.remove(i);
					imageList.remove(i);
				} else if (postionList.get(i).intValue() > position
						&& length < doc.getLength()
						&& (postionList.get(i).intValue() - length) >= 0) {
					postionList.get(i).setValue(
							postionList.get(i).intValue() - length);
				}
				i++;
			}

		}

	}

	/**
	 * Integer类型的扩展，因为在容器中需要改变int值， 故需要对其进行封装
	 * <p>
	 * 
	 * @author yqg
	 * 
	 */
	class IntegerEx {

		private int value = 0;

		public IntegerEx(int value) {
			this.value = value;
		}

		public int intValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

	/*
	 * 测试部分
	 * 
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout(0, 0));
		JTextAreaEx textPanel = new JTextAreaEx();

		textPanel.append("<html><b>java_source</b><br>Folder</html>");
		textPanel.append("第二行的文字", Color.BLACK, true, 25, "华文彩云");
		textPanel.select(textPanel.getText().length(), textPanel.getText()
				.length());
		HyperLink hyperLink1 = new HyperLink();
		hyperLink1.setText("Let's Swing Java");

		hyperLink1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.print("fdasdfasdf");
			}
		});

		textPanel.insertComponent(hyperLink1);

		// textPanel.appendImage(2);
		container.add(textPanel, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

}
