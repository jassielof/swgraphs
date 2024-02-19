package interfaz.matricesAdyacencia;

import java.awt.Component;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class MatrizRenderer extends JTextField implements TableCellRenderer {
  public MatrizRenderer() {
    setOpaque(true);
  }
  
  public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
    setText((String)arg1);
    JTableHeader columnHeader = arg0.getTableHeader();
    setForeground(columnHeader.getForeground());
    setBackground(columnHeader.getBackground());
    setFont(columnHeader.getFont());
    setAlignmentX(0.5F);
    setBorder(BorderFactory.createRaisedBevelBorder());
    setMargin(new Insets(10, 30, 0, 30));
    return this;
  }
}
