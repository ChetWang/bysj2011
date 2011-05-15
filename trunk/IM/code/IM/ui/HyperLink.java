/*
 * HyperLink.java
 *
 * Created on 2007年3月26日, 下午11:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ui;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.event.MouseInputListener;

/**
 * 
 */
public class HyperLink extends JLabel implements MouseInputListener {
	private String hyper_text;

	private Icon webShot;

	private boolean enable = true;

	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();

	public HyperLink() {
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public HyperLink(String label) {
		super(label);
		this.hyper_text = label;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setText(String l) {
		hyper_text = l;
		super.setText("<html><body><div align=\"center\" color=\"#0000ff\">"
				+ l + "</div></body></html>");
	}

	public void addActionListener(ActionListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void removeActionListener(ActionListener l) {
		if (listeners.contains(l))
			listeners.remove(l);

	}

	public void clearHyperLink() {
		super.setText("<html><body><div color=\"balck\"><u>" + hyper_text
				+ "</u></div></body></html>");
		enable = false;
		listeners.clear();
	}

	protected void fireActionPerformed(ActionEvent e) {
		for (ActionListener listener : listeners)
			listener.actionPerformed(e);
	}

	public void mouseClicked(MouseEvent e) {
		if (enable) {
			fireActionPerformed(new ActionEvent(this, 0, "hyper linkage action"));
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		if (enable) {
			super.setText("<html><body><div color=\"green\"><u>" + hyper_text
					+ "</u></div></body></html>");
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseExited(MouseEvent e) {
		if (enable) {
			super
					.setText("<html><body><div align=\"center\" color=\"#0000ff\">"
							+ hyper_text + "</div></body></html>");
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {

	}

	public void setImageTip(Icon webShot) {
		putClientProperty(TOOL_TIP_TEXT_KEY, "");
		ToolTipManager toolTipManager = ToolTipManager.sharedInstance();
		if (webShot != null && this.webShot == null)
			toolTipManager.registerComponent(this);
		else if (webShot == null)
			toolTipManager.unregisterComponent(this);
		this.webShot = webShot;
	}

	public Icon getWebShot() {
		return webShot;
	}

	public JToolTip createToolTip() {
		ImageToolTip tip = new ImageToolTip(webShot);
		tip.setComponent(this);
		return tip;
	}
}
