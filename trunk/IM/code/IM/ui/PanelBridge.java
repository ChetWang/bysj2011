package ui;

import java.util.Observable;
import java.util.Observer;

public class PanelBridge extends Observable {

	/** IP地址是左边面板和右边面板的一个桥梁 */
	private String ipAddress = new String();

	public String getIpAddress() {
		return ipAddress;
	}

	/** 改变IP地址 */
	public void changedIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		// 标记被观察者状态已经改变
		setChanged();
		// 通知观察者
		notifyObservers(this);
	}

}
