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
 * JTextAreaEx��չ��JTextPane��������ʾ���������ͼƬ���ؼ�
 * 
 * @author �����
 * 
 */
public class JTextAreaEx extends JTextPane {

	/** ������ɫ */
	private Color color = Color.BLACK;

	private int fontSize = 12;

	/** ������ */
	private String fontFamily = "����";

	/** �Ƿ���� */
	private boolean bold = false;

	private Document doc = null;

	/** �����ļ�ͷ��Ϣ */

	private List<IntegerEx> postionList = new ArrayList<IntegerEx>();

	private List<IntegerEx> imageList = new ArrayList<IntegerEx>();

	public void setDocumentListener() {
		doc = getDocument();
		// �������������ĵ�
		doc.addDocumentListener(new FilterEdit());
	}

	/**
	 * �����������ɫ
	 * 
	 * @param c
	 */
	public void setFontColor(Color c) {
		color = c;

	}

	/**
	 * @return �������ɫ
	 */
	public int getFontColor() {
		return color.getRGB();
	}

	/**
	 * @return �������ɫ
	 */
	public Color getFontFromColor() {
		return color;
	}

	/**
	 * ��������Ĵ�С
	 */
	public void setFontSize(int size) {
		fontSize = size;
	}

	/**
	 * 
	 * @return ����Ĵ�С
	 */
	public int getFontSize() {
		return fontSize;
	}

	/**
	 * ��������ķ��
	 * 
	 * @param fontStyle
	 */
	public void setFontFamily(String fontStyle) {
		fontFamily = fontStyle;
	}

	/**
	 * 
	 * @return ������
	 */
	public String getFontFamily() {
		return fontFamily;
	}

	/**
	 * ���ô���
	 * 
	 * @param bordtag
	 */
	public void setBold(boolean bordtag) {
		bold = bordtag;
	}

	/**
	 * 
	 * @return �Ƿ����
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * ���һ��Ĭ�����Ե��ı�
	 * 
	 */
	public void append(String str) {
		append(str, color, bold, fontSize, fontFamily);
	}

	/**
	 * 
	 * @return �����ı�����Ϣ
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
	 * @return �����ı�����Ϣ
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
	 * ���һ���������Ե��ı�
	 * 
	 */
	public void append(String str, Color col, boolean bold, int size,
			String fontFamily) {

		// �������������ĵ�
		Document doc = getDocument();
		// doc.addDocumentListener(new FilterEdit());
		// ����һ�����������,�����������
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		// �����������ɫ
		StyleConstants.setForeground(attrSet, col);
		// �����Ƿ����
		if (bold == true) {
			StyleConstants.setBold(attrSet, true);
		}
		// ��������Ĵ�С
		StyleConstants.setFontSize(attrSet, size);
		// ��������ķ��
		StyleConstants.setFontFamily(attrSet, fontFamily);
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			System.out.println(e);
		}
	}

	/**
	 * ����һ��ͼƬ,���ұ���һЩ��Ϣ
	 * 
	 * @param imageIndex
	 */
	public void appendImage(int imageIndex) {

		postionList.add(new IntegerEx(getSelectionEnd()));
		imageList.add(new IntegerEx(imageIndex));

		Icon image = createImageIcon(String.valueOf(imageIndex) + ".gif");
		// ����һ��ͼ��
		select(getText().length(), getText().length());
		insertIcon(image);
	}

	public void insertComponent(Component c) {
		select(getText().length(), getText().length());
		super.insertComponent(c);
	}

	/**
	 * ����ͼ��,ͼ���붼���浽/resources/images/��Ŀ¼��������Ŀ¼�£�
	 * 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/Face/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

	/**
	 * ����һ��ͼƬ
	 * 
	 * @param imageIndex
	 */
	public void appendImage(int position, int imageIndex) {
		Icon image = createImageIcon(String.valueOf(imageIndex) + ".gif");
		// ѡ��Ҫ�����λ��
		select(position - 1, position);
		// ����һ��ͼ��
		insertIcon(image);
	}

	class FilterEdit implements DocumentListener {

		public void changedUpdate(DocumentEvent event) {
		}

		/** �����ʱ�������� */
		public void insertUpdate(DocumentEvent event) {
			int position = event.getOffset();
			int length = event.getLength();
			int i = 0;
			while (i < imageList.size()) {
				// ������ڵ�һ�������⴦��
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
				// λ�ô�����Ҫ���ϲ�����ַ�����,������������λ�òŸպú���
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

		/** ɾ����ʱ�� */
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
	 * Integer���͵���չ����Ϊ����������Ҫ�ı�intֵ�� ����Ҫ������з�װ
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
	 * ���Բ���
	 * 
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout(0, 0));
		JTextAreaEx textPanel = new JTextAreaEx();

		textPanel.append("<html><b>java_source</b><br>Folder</html>");
		textPanel.append("�ڶ��е�����", Color.BLACK, true, 25, "���Ĳ���");
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
