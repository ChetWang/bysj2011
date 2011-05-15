package ui.configset;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.sylinxsoft.constants.Constants;
import com.sylinxsoft.csframework.ConfigSet;
import com.sylinxsoft.util.UniResources;

public class NetworkSetPanel extends JPanel {
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField jtf_port;
	private JTextField jtf_ip;
	private JTextField jtf_cpName;
	private JRadioButton jrb_tcp, jrb_udp;

	/**
	 * Create the panel
	 */
	public NetworkSetPanel() {
		super();
		setLayout(null);

		final JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(null, UniResources.getString("msk.conf.cpset"),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel1.setLayout(null);
		panel1.setBounds(10, 10, 285, 65);
		add(panel1);

		final JLabel label = new JLabel();
		label.setText(UniResources.getString("msk.conf.cpid"));
		label.setBounds(10, 30, 60, 15);
		panel1.add(label);

		jtf_cpName = new JTextField();
		// jtf_cpName.setText(Constants.DEFAULTMULIP);
		jtf_cpName.setBounds(76, 27, 199, 21);
		panel1.add(jtf_cpName);

		final JPanel panel2 = new JPanel();
		panel2.setBorder(new TitledBorder(null, UniResources.getString("msk.conf.netset"),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel2.setLayout(null);
		panel2.setBounds(10, 82, 285, 102);
		add(panel2);

		final JLabel label2 = new JLabel();
		label2.setText(UniResources.getString("msk.conf.muludp"));
		label2.setBounds(10, 33, 60, 15);
		panel2.add(label2);

		jtf_ip = new JTextField();
		jtf_ip.setText(Constants.DEFAULTMULIP);
		jtf_ip.setBounds(76, 30, 199, 21);
		jtf_ip.setEditable(false);
		panel2.add(jtf_ip);

		final JLabel label3 = new JLabel();
		label3.setText(UniResources.getString("msk.conf.muludpport"));
		label3.setBounds(10, 70, 60, 15);
		panel2.add(label3);

		jtf_port = new JTextField();
		jtf_port.setText(Constants.DEFAULTMULPORT);
		jtf_port.setBounds(76, 65, 199, 21);
		jtf_port.setEditable(false);
		panel2.add(jtf_port);

		final JPanel panel3 = new JPanel();
		panel3.setBorder(new TitledBorder(null, UniResources.getString("msk.conf.filetransset"),
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel3.setLayout(null);
		panel3.setBounds(10, 190, 285, 59);
		add(panel3);

		jrb_tcp = new JRadioButton();
		buttonGroup.add(jrb_tcp);
		jrb_tcp.setText("TCP");
		jrb_tcp.setBounds(43, 24, 78, 23);
		jrb_tcp.setSelected(true);
		panel3.add(jrb_tcp);

		jrb_udp = new JRadioButton();
		buttonGroup.add(jrb_udp);
		jrb_udp.setText("UDP");
		jrb_udp.setEnabled(false);
		jrb_udp.setBounds(164, 24, 78, 23);
		panel3.add(jrb_udp);
		initConf();

	}

	/**
	 * 读取已经设置的参数
	 */
	public void initConf() {
		String cpName = ConfigSet.getInstance().getProperty("client-name");
		if (null != cpName && !"".equals(cpName)) {
			jtf_cpName.setText(cpName);
		} else {
			try {
				InetAddress localIp = InetAddress.getLocalHost();
				jtf_cpName.setText(localIp.getHostName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String filetran = ConfigSet.getInstance().getProperty("filetran");

		if (null != filetran && !"".equals(filetran)) {
			if ("udp".equals(filetran)) {
				jrb_udp.setSelected(true);
			} else {
				jrb_tcp.setSelected(true);
			}
		}
	}

	public void saveSettingConfig(Properties prop) {
		String filetran, cpName = "pc";
		if (!"".equals(jtf_cpName.getText())) {
			cpName = jtf_cpName.getText();
		} else {
			InetAddress localIp;
			try {
				localIp = InetAddress.getLocalHost();
				cpName = localIp.getHostName();
				// 防止没有主机名的,就用其IP
				if (null == cpName || "".equals(cpName.trim())) {
					cpName = localIp.getHostAddress();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (jrb_udp.isSelected()) {
			filetran = "udp";
		} else {
			filetran = "tcp";
		}
		prop.put("filetran", filetran);
		prop.put("client-name", cpName);
		prop.put("client-group", jtf_ip.getText());
	}

}
