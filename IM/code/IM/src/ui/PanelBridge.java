package ui;

import java.util.Observable;
import java.util.Observer;

public class PanelBridge extends Observable {

	/** IP��ַ����������ұ�����һ������ */
	private String ipAddress = new String();

	public String getIpAddress() {
		return ipAddress;
	}

	/** �ı�IP��ַ */
	public void changedIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		// ��Ǳ��۲���״̬�Ѿ��ı�
		setChanged();
		// ֪ͨ�۲���
		notifyObservers(this);
	}

}
