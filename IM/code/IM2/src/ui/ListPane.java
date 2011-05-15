/*
 * ListPane.java
 *
 * Created on June 8, 2007, 9:17 PM
 */

package ui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ListPane extends JPanel {
    private static final int HORZ_PAD=8;
    private static final int VERT_PAD=2;
    
    /**
     * Creates new form ListPane
     */
    public ListPane() {
        initComponents();
        Border b = BorderFactory.createEmptyBorder(VERT_PAD,HORZ_PAD,VERT_PAD,HORZ_PAD);
        setBorder(b);
    }
    private void initComponents() {
        FlowLayout layout=new FlowLayout(FlowLayout.LEADING, 0, 10);
        setLayout(layout);
        setBackground(new Color(255, 255, 255));
    }
    public void addItem(String text, String iconURL){
        JLabel lblItem=new JLabel();
        if(iconURL!=null){
            lblItem.setIcon(new ImageIcon(getClass().getResource(iconURL)));
            lblItem.setForeground(new Color(33, 93, 198));
        }else
            lblItem.setForeground(Color.BLACK);
        lblItem.setText(text);
        add(lblItem);
    }
    
    public void addItem(JComponent com){
        add(com);
    }
    
    
    
}
