/*
 * CurtainPaneButton.java
 *
 * Created on June 9, 2007, 11:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.sylinxsoft.util.UniResources;

/**
 *
 * @author William Chen
 */
public class CurtainButton extends JComponent implements LanguageChangeEnable {

	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	private String text;
	private String restext;
	private Icon icon;
	private float alignment = LEFT_ALIGNMENT;

	/** Creates a new instance of CurtainPaneButton */
	public CurtainButton() {
		this(null, null);
	}

	public CurtainButton(String text, Icon icon) {
		this.restext = text;
		this.text = UniResources.getString(restext);
		this.icon = icon;
		setUI(new CurtainButtonUI());
	}

	public void addActionListener(ActionListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}

	public void removeActionListener(ActionListener listener) {
		if (listeners.contains(listener))
			listeners.remove(listener);
	}

	protected void fireActionPerformed(ActionEvent e) {
		for (ActionListener listener : listeners)
			listener.actionPerformed(e);
	}

	public void setText(String text) {
		this.text = text;
		repaint();
	}

	public String getText() {
		return text;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
		repaint();
	}

	public float getAlignment() {
		return alignment;
	}

	public void setAlignment(float alignment) {
		this.alignment = alignment;
		repaint();
	}

	public void updateForLanguage() {
		this.setText(UniResources.getString(restext));

	}
}
