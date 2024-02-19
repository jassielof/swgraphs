package interfaz.matricesAdyacencia;

import grafos.Grafo;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class ColumnaPrincipal extends JTable {
  public ColumnaPrincipal(Grafo grafo) {
    final String[][] nombres = new String[grafo.getNodos().size()][1];
    for (int i = 0; i < grafo.getNodos().size(); i++)
      nombres[i][0] = grafo.getNodoByIndex(i).getNombre(); 
    AbstractTableModel modelo = new AbstractTableModel() {
        public int getColumnCount() {
          return 1;
        }
        
        public int getRowCount() {
          return nombres.length;
        }
        
        public Object getValueAt(int row, int col) {
          return nombres[row][col];
        }
        
        public boolean isCellEditable(int row, int col) {
          return false;
        }
      };
    setModel(modelo);
    setDefaultRenderer(String.class, new MatrizRenderer());
    TableColumn column = null;
    for (int j = 0; j < getColumnCount(); j++) {
      column = getColumnModel().getColumn(j);
      column.setPreferredWidth(80);
      column.setMinWidth(80);
    } 
    JTableHeader columnHeader = getTableHeader();
    setForeground(columnHeader.getForeground());
    setBackground(columnHeader.getBackground());
    setFont(columnHeader.getFont());
    setAutoResizeMode(0);
    setPreferredScrollableViewportSize(new Dimension(80, 200));
  }
}
