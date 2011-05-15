package ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*������ʾ�ұ������ͼ���Լ���صı��⣬���б�ѡ��֮��Ͳ�ѡ��ʱ����ɫ*/

public class IconCellRenderer extends JLabel implements ListCellRenderer {

	private Color bkColor = new Color(206,226,254);
    private Border selectedBorder = new LineBorder(new Color(139,168,198),1);
    //emptyBorder = BorderFactory.createEmptyBorder(1,1,1,1);
	public IconCellRenderer() {
	         setOpaque(true);
	}

	public Component getListCellRendererComponent(JList list, Object object,
			int arg2, boolean isSelected, boolean cellHasFocus) {
		
		 IconListItem item = (IconListItem)object;
         setIcon(item.getIcon());
         setText(item.getText());
       
         setBackground(isSelected ? bkColor : Color.white);
         if(isSelected) {
        	 setBorder(selectedBorder);
         } else {
        	 setBorder(null);
         }
         //setForeground(isSelected ? Color.white : Color.black);
         return this;
	}
	

}
