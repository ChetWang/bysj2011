package com.sylinxsoft.util;

public class Ui {
	/**
	 * �����������Ļ�м�
	 * 
	 * @param c
	 */
	public static void centerFrame(java.awt.Component c) {
		java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
		c.setLocation(
				(int) ((tk.getScreenSize().getWidth() - c.getWidth()) / 2),
				(int) ((tk.getScreenSize().getHeight() - c.getHeight()) / 2));
	}
}
