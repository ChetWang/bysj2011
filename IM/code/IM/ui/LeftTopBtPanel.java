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

	//记录现在的 Look & Feels
	private int lookFeelIndex = 0;

	JButton jbtTranportFiles = new JButton(createImageIcon("tranportFiles.jpg"));

	JButton jbtWindowsLayout = new JButton(createImageIcon("windowsLayout.jpg"));

	JButton jbtSylinx = new JButton(createImageIcon("sylinx.jpg"));

	public LeftTopBtPanel() {
        this.setPreferredSize(new Dimension(0, 30));
		//顶部面板的大小，其中的按钮的注册，以及按钮的提示信息 
		jbtTranportFiles.setPreferredSize(new Dimension(35, 30));
		jbtTranportFiles.addActionListener(this);
		jbtTranportFiles.setToolTipText("传输文件夹");

		jbtWindowsLayout.setPreferredSize(new Dimension(35, 30));
		jbtWindowsLayout.addActionListener(this);
		jbtWindowsLayout.setToolTipText("设置Look & Feels CTRL+W");
		//设置设置Look & Feels按钮的快捷键
		jbtWindowsLayout.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		jbtSylinx.setPreferredSize(new Dimension(141, 30));
		jbtSylinx.addActionListener(this);
		jbtSylinx.setToolTipText("关于本程序 CTRL+Q");
		//设置关于本团队按钮的快捷键
		jbtSylinx.registerKeyboardAction(this, KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK),
				JComponent.WHEN_IN_FOCUSED_WINDOW);

		//创建顶部面板
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		//设置顶部面板的背景颜色
		setBackground(new Color(92, 197, 255));

		//将按钮加到顶部面板
		//add(jbtTranportFiles);
		//add(jbtWindowsLayout);
		//add(jbtSylinx);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == jbtWindowsLayout) { //窗口布局设置
			lookFeelIndex++;
			if (lookFeelIndex >= MskFrame.getInstance().LAFS.length) {
				lookFeelIndex = 0;
			}
			try {
				MskFrame.getInstance().setLookAndFeel(
						MskFrame.getInstance().LAFS[lookFeelIndex]);
			} catch (Exception evt) {
			}
			//更新到所有
			//SwingUtilities.updateComponentTreeUI(MessageSwitch.getMsk());		 
		} else if (e.getSource() == jbtSylinx) { //网络连接
			/*try {
			  Runtime.getRuntime().exec("cmd.exe /c start " + TEAMURL);
			} catch (Exception evt) {}*/
			//ShowHtml helpHtml = new ShowHtml("Help/Sylinx.htm");
		} else if (e.getSource() == jbtTranportFiles) {
			JOptionPane.showMessageDialog(null, "谢谢您的使用,传输文件夹在该版本中还不支持!",
					"谢谢您的使用", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 创建图标,图标请都保存到/resources/images/的目录或其自子目录下， 
	 * @param filename
	 * @return
	 */
	public ImageIcon createImageIcon(String filename) {
		String path = "/ui/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
	}

}
