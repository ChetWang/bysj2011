package ui.configset;

import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.sylinxsoft.csframework.ConfigSet;
import com.sylinxsoft.util.UniResources;

public class LanguageSetPanel extends JPanel {

	private ButtonGroup skinButtonGroup = new ButtonGroup();
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton jrb_lskin, jrb_noskin, jrb_ngskin, jjrb_defaultskin;
	private JRadioButton jrb_chinese, jrb_english, jjrb_chineseex,
			jrb_defaultlanguage;

	/**
	 * Create the panel
	 */
	public LanguageSetPanel() {
		super();
		setLayout(null);

		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, UniResources.getString("msk.conf.language"),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 10, 285, 106);
		add(panel_1);

		jrb_chinese = new JRadioButton();
		buttonGroup.add(jrb_chinese);
		jrb_chinese.setText(UniResources.getString("msk.conf.chinese"));
		jrb_chinese.setBounds(152, 25, 94, 23);
		panel_1.add(jrb_chinese);

		jjrb_chineseex = new JRadioButton();
		buttonGroup.add(jjrb_chineseex);
		jjrb_chineseex.setText(UniResources.getString("msk.conf.chineseTw"));
		jjrb_chineseex.setBounds(152, 64, 94, 23);
		panel_1.add(jjrb_chineseex);

		jrb_english = new JRadioButton();
		buttonGroup.add(jrb_english);
		jrb_english.setText(UniResources.getString("msk.conf.english"));
		jrb_english.setBounds(10, 64, 94, 23);
		panel_1.add(jrb_english);

		jrb_defaultlanguage = new JRadioButton();
		buttonGroup.add(jrb_defaultlanguage);
		jrb_defaultlanguage.setText(UniResources.getString("msk.conf.deflanguage"));
		jrb_defaultlanguage.setSelected(true);
		jrb_defaultlanguage.setBounds(10, 25, 94, 23);
		panel_1.add(jrb_defaultlanguage);

		final JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, UniResources.getString("msk.conf.skinset"),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel_2.setLayout(null);
		panel_2.setBounds(10, 122, 285, 119);
		add(panel_2);

		jjrb_defaultskin = new JRadioButton();
		skinButtonGroup.add(jjrb_defaultskin);
		jjrb_defaultskin.setText(UniResources.getString("msk.conf.defskin"));
		jjrb_defaultskin.setSelected(true);
		jjrb_defaultskin.setBounds(10, 24, 85, 23);
		panel_2.add(jjrb_defaultskin);

		jrb_lskin = new JRadioButton();
		skinButtonGroup.add(jrb_lskin);
		jrb_lskin.setText("Liquid");
		jrb_lskin.setBounds(154, 24, 85, 23);
		panel_2.add(jrb_lskin);

		jrb_noskin = new JRadioButton();
		skinButtonGroup.add(jrb_noskin);
		jrb_noskin.setText("Nimrod Ocean");
		jrb_noskin.setBounds(10, 53, 127, 23);
		panel_2.add(jrb_noskin);

		jrb_ngskin = new JRadioButton();
		skinButtonGroup.add(jrb_ngskin);
		jrb_ngskin.setText("Nimrod Gold");
		jrb_ngskin.setBounds(154, 53, 106, 23);
		panel_2.add(jrb_ngskin);
		//

		initConf();
	}

	/**
	 * 读取已经设置的参数
	 */
	public void initConf() {
		String skin = ConfigSet.getInstance().getProperty("skin");
		if (null != skin && !"".equals(skin)) {
			if ("Liquid".equals(skin)) {
				jrb_lskin.setSelected(true);
			} else if ("Nimrod Ocean".equals(skin)) {
				jrb_noskin.setSelected(true);
			} else if ("Nimrod Gold".equals(skin)) {
				jrb_ngskin.setSelected(true);
			} else {
				jjrb_defaultskin.setSelected(true);
			}
		}
		String language = ConfigSet.getInstance().getProperty("language");
		if (null != language && !"".equals(language)) {
			if ("chinese".equals(language)) {
				jrb_chinese.setSelected(true);
			} else if ("chinese_TW".equals(language)) {
				jjrb_chineseex.setSelected(true);
			} else if ("english".equals(language)) {
				jrb_english.setSelected(true);
			} else {
				jrb_defaultlanguage.setSelected(true);
			}
		}

	}

	public void saveSettingConfig(Properties prop) {
		String skin, language;
		if (jrb_chinese.isSelected()) {
			language = "chinese";
		} else if (jjrb_chineseex.isSelected()) {
			language = "chinese_TW";
		} else if (jrb_english.isSelected()) {
			language = "english";
		} else {
			language = "default";
		}

		if (jrb_lskin.isSelected()) {
			skin = "Liquid";
		} else if (jrb_noskin.isSelected()) {
			skin = "Nimrod Ocean";
		} else if (jrb_ngskin.isSelected()) {
			skin = "Nimrod Gold";
		} else {
			skin = "default";
		}
		prop.put("language", language);
		prop.put("skin", skin);
	}
}
