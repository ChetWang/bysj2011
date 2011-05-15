package ui.configset;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ui.MessageBox;
import ui.MskFrame;

import com.sylinxsoft.csframework.ConfigSet;
import com.sylinxsoft.util.UniResources;

public class ConfigDlg extends JDialog {
	private JTabbedPane tabbedPane;
	private LanguageSetPanel languageSetPanel;
	private NetworkSetPanel networkSetPanel;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			ConfigDlg dialog = new ConfigDlg();
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

	/**
	 * Create the dialog
	 */
	public ConfigDlg() {
		super(MskFrame.getInstance());
		getContentPane().setLayout(new BorderLayout(5, 5));
		setTitle(UniResources.getString("msk.conf.confiset"));
		setBounds(100, 100, 318, 364);
		final JPanel bottomPanel = new JPanel();
		// bottomPanel.setBorder(new TitledBorder(null, "",
		// TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
		// null, null));
		bottomPanel.setLayout(null);
		bottomPanel.setPreferredSize(new Dimension(0, 40));
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		final JButton jbt_yes = new JButton();
		jbt_yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSettingConfig();
				MessageBox.show( UniResources.getString("msk.conf.saveok"));
				dispose();
			}
		});
		jbt_yes.setText(UniResources.getString("msk.conf.yes"));
		jbt_yes.setBounds(36, 5, 101, 25);
		bottomPanel.add(jbt_yes);

		final JButton jbt_cancel = new JButton();
		jbt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		jbt_cancel.setText(UniResources.getString("msk.conf.cancel"));
		jbt_cancel.setBounds(173, 5, 101, 25);
		bottomPanel.add(jbt_cancel);

		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		getContentPane().add(centerPanel, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP,
				JTabbedPane.SCROLL_TAB_LAYOUT);

		// tabbedPane.setUI(new PPTTabbedPaneUI());
		// tabbedPane.setBorder(new TitledBorder(null, "",
		// TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
		// null, null));
		tabbedPane.setBounds(0, 56, 750, 390);
		centerPanel.add(tabbedPane, BorderLayout.CENTER);

		String name = UniResources.getString("msk.conf.languageset");
		languageSetPanel = new LanguageSetPanel();
		tabbedPane.add(name, languageSetPanel);

		name = UniResources.getString("msk.conf.netset");
		networkSetPanel = new NetworkSetPanel();
		tabbedPane.add(name, networkSetPanel);

	}

	public void saveSettingConfig() {
		String oldLanguage = ConfigSet.getInstance().getProperty("language");
		if (null == oldLanguage) {
			oldLanguage = "default";
		}
		String oldSkin = ConfigSet.getInstance().getProperty("skin");
		if (null == oldSkin) {
			oldSkin = "default";
		}
		Properties prop = new Properties();
		languageSetPanel.saveSettingConfig(prop);
		networkSetPanel.saveSettingConfig(prop);
		ConfigSet.getInstance().saveSetting(prop);

		if (!oldLanguage.equals(ConfigSet.getInstance().getProperty("language"))) {
			MskFrame.getInstance().refreshLanguage();
		}
		if (!oldSkin.equals(ConfigSet.getInstance().getProperty("skin"))) {
			MskFrame.getInstance().refreshLookAndFeel();
		}
	}

}
