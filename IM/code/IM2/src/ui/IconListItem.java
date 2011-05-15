package ui;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * 列表框中的每个元素
 * @author yqg
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
