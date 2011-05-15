package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class LeftTopBtPanel extends JPanel implements ActionListener {

	

	//public static final String TEAMURL = "http://www.myjavaserver.com/~softromance/";

	//��¼���ڵ� Look & Feels
	private int lookFeelIndex = 0;

	JButton jbtTranportFiles = new JButton(createImageIcon("tranportFiles.jpg"));

	JButton jbtWindowsLayout = new JButton(createImageIcon("windowsLayout.jpg"));

	JButton jbtSylinx = new JButton(createImageIcon("sylinx.jpg"));

	public LeftTopBtPanel() {
        this.setPreferredSize(new Dimension(0, 30));
		//�������Ĵ�С�����еİ�ť��ע�ᣬ�Լ���ť����ʾ��Ϣ 
		jbtTranportFiles.setPreferredSize(new Dimension(35, 30));
		jbtTranportFiles.addActionListener(this);
		jbtTranportFiles.setToolTipText("�����ļ���");

		jbtWindowsLayout.setPreferredSize(new Dimension(35, 30));
		jbtWindowsLayout.addActionListener(this);
		jbtWindowsLayout.setToolTipText("����Look & Feels CTRL+W");
		//��������Look & Feels��ť�Ŀ�ݼ�
		jbtWindowsLayout.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtSylinx.setPreferredSize(new Dimension(141, 30));
		jbtSylinx.addActionListener(this);
		jbtSylinx.setToolTipText("���ڱ����� CTRL+Q");
		//���ù��ڱ��ŶӰ�ť�Ŀ�ݼ�
		jbtSylinx.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		//�����������
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		//���ö������ı�����ɫ
		setBackground(new Color(92, 197, 255));

		//����ť�ӵ��������
		//add(jbtTranportFiles);
		//add(jbtWindowsLayout);
		//add(jbtSylinx);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jbtWindowsLayout) { //���ڲ�������
			lookFeelIndex++;
			if (lookFeelIndex >= MskFrame.getInstance().LAFS.length) {
				lookFeelIndex = 0;
			}
			try {
				MskFrame.getInstance().setLookAndFeel(
						MskFrame.getInstance().LAFS[lookFeelIndex]);
			} catch (Exception evt) {
			}
			//���µ�����
			//SwingUtilities.updateComponentTreeUI(MessageSwitch.getMsk());		 
		} else if (e.getSource() == jbtSylinx) { //��������
			/*try {
			  Runtime.getRuntime().exec("cmd.exe /c start " + TEAMURL);
			} catch (Exception evt) {}*/
			//ShowHtml helpHtml = new ShowHtml("Help/Sylinx.htm");
		} else if (e.getSource() == jbtTranportFiles) {
			JOptionPane.showMessageDialog(null, "лл����ʹ��,�����ļ����ڸð汾�л���֧��!",
					"лл����ʹ��", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * ����ͼ��,ͼ���붼���浽/resources/images/��Ŀ¼��������Ŀ¼�£� 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/ui/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

}
