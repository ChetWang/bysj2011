package ui;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * �б���е�ÿ��Ԫ��
 * �б��������û�ͼ����ʾ�Լ�������ʾ
 *
 */
public class IconListItem  {
         Icon icon;
         String text;
         public IconListItem(Icon icon, String text)
         {
             this.icon = icon;
             this.text = text;
         }
         public Icon getIcon() { return icon;}
         public String getText() { return text;}
         public void setIcon(Icon icon){ this.icon = icon;}
         public void setText(String text){ this.text = text; }
}
