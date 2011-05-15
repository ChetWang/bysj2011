package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
  //进度条显示
public class JProgressBarPanel extends JPanel {
	private JProgressBar bar = new JProgressBar();
	private String title;

	public JProgressBarPanel() {
		this.setPreferredSize(new Dimension(
				AllUiConstatns.RIGHTPANELWIDTH - 20, 35));
		this.setLayout(new BorderLayout());
		bar.setPreferredSize(new Dimension(AllUiConstatns.RIGHTPANELWIDTH - 20,
				20));
		//不知道大小
		bar.setIndeterminate(true);
		bar.setStringPainted(true);
		this.add(bar, BorderLayout.CENTER);
		setBackground(new Color(255, 255, 255));
	}

	
	
	public void setBarString(String title) {
		this.title = title;
		if (null != title) {
			JLabel l = new JLabel(title);
			
			this.add( l, BorderLayout.SOUTH);
		}
	}

	public void setTotalValue(int totalValue) {
		bar.setMaximum(totalValue);
		bar.setValue(0);
		bar.setIndeterminate(false);

	}

	public void setValue(int value) {
		bar.setValue(value);
	}

}
