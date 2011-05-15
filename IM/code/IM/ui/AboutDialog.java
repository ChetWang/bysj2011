package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.sylinxsoft.constants.Constants;
import com.sylinxsoft.util.UniResources;

/**
 * 关于对话框
 */
public class AboutDialog extends JDialog {

	private JTextArea jhgjhgjTextArea;

	
	public static void main(String args[]) {
		try {
			AboutDialog dialog = new AboutDialog();
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public AboutDialog() {
		super(MskFrame.getInstance());
		getContentPane().setLayout(null);
		setTitle(UniResources.getString("msk.aboutthis"));
		setBounds(100, 100, 362, 333);

		final JPanel panel = new JPanel();
		panel.setBounds(10, 10, 334, 30);
		getContentPane().add(panel);

		final JLabel title_lb = new JLabel();
		title_lb.setForeground(new Color(0, 102, 255));
		title_lb.setFont(new Font(UniResources.getString("msk.defaultfont"), Font.BOLD, 14));
		panel.add(title_lb);
		title_lb.setText(UniResources.getString("msk.breftitle"));

		final JLabel label = new JLabel();
		label.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		label.setText(UniResources.getString("msk.version"));
		label.setBounds(10, 57, 60, 15);
		getContentPane().add(label);

		final JLabel ver_lb = new JLabel();
		ver_lb.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		ver_lb.setText(Constants.VERSION);
		ver_lb.setBounds(76, 57, 78, 15);
		getContentPane().add(ver_lb);

		final JLabel date_lb = new JLabel();
		date_lb.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		date_lb.setText(Constants.POSTTIME);
		date_lb.setBounds(221, 55, 78, 15);
		getContentPane().add(date_lb);

		final JLabel label_1 = new JLabel();
		label_1.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		label_1.setText(UniResources.getString("msk.posttime"));
		label_1.setBounds(165, 55, 60, 15);
		getContentPane().add(label_1);

		final JLabel label_2 = new JLabel();
		label_2.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		label_2.setText(UniResources.getString("msk.support"));
		label_2.setBounds(10, 82, 78, 15);
		getContentPane().add(label_2);

		final JLabel ver_lb_1 = new LinkLabel("281163883",
				"tencent://message/?uin=281163883&Site=思宁软件&Menu=yes");
		ver_lb_1.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		ver_lb_1.setBounds(77, 78, 67, 20);
		getContentPane().add(ver_lb_1);

		final JLabel label_2_2 = new JLabel();
		label_2_2.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		label_2_2.setText(UniResources.getString("msk.tel"));
		label_2_2.setBounds(10, 107, 78, 15);
		getContentPane().add(label_2_2);

		final JLabel ver_lb_1_1 = new JLabel();
		ver_lb_1_1.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		ver_lb_1_1.setText("****-********");
		ver_lb_1_1.setBounds(77, 107, 77, 15);
		getContentPane().add(ver_lb_1_1);

		final JLabel label_1_1 = new JLabel();
		label_1_1.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		label_1_1.setText(UniResources.getString("msk.email"));
		label_1_1.setBounds(165, 105, 60, 15);
		getContentPane().add(label_1_1);

		final JLabel date_lb_1 = new JLabel();
		date_lb_1.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		date_lb_1.setText("sylinxsoft@gmail.com");
		date_lb_1.setBounds(221, 105, 120, 15);
		getContentPane().add(date_lb_1);

		final JLabel date_lb_2 = new LinkLabel("www.sylinxsoft.com",
				"http://www.sylinxsoft.com");
		date_lb_2.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		date_lb_2.setBounds(221, 75, 118, 20);
		getContentPane().add(date_lb_2);

		final JLabel label_1_2 = new JLabel();
		label_1_2.setFont(new Font(UniResources.getString("msk.defaultfont"),
				Font.PLAIN, 12));
		label_1_2.setText(UniResources.getString("msk.site"));
		label_1_2.setBounds(165, 80, 60, 15);
		getContentPane().add(label_1_2);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 133, 334, 119);
		getContentPane().add(scrollPane);

		jhgjhgjTextArea = new JTextArea();
		scrollPane.setViewportView(jhgjhgjTextArea);
		jhgjhgjTextArea.setEditable(false);
		jhgjhgjTextArea.setText(UniResources.getString("msk.abouttext"));
		jhgjhgjTextArea.setFont(new Font(UniResources
				.getString("msk.defaultfont"), Font.PLAIN, 12));
		jhgjhgjTextArea.setLineWrap(true);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setText(UniResources.getString("msk.shutdown"));
		button.setBounds(255, 258, 89, 30);
		getContentPane().add(button);
		setResizable(false);
		//
	}


   
}
