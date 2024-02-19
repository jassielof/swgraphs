package interfaz.matricesAdyacencia;

import javax.swing.table.AbstractTableModel;

public class MatrizTablaModel extends AbstractTableModel {
  private Object[][] datos;
  
  private String[] columnas;
  
  private int tipoGrafo;
  
  public MatrizTablaModel(String[] arg0, Object[][] arg1, int arg2) {
    this.columnas = arg0;
    this.datos = arg1;
    this.tipoGrafo = arg2;
  }
  
  public int getColumnCount() {
    return this.columnas.length;
  }
  
  public int getRowCount() {
    return this.datos.length;
  }
  
  public String getColumnName(int col) {
    return this.columnas[col];
  }
  
  public Object getValueAt(int row, int col) {
    return this.datos[row][col];
  }
  
  public Class getColumnClass(int c) {
    return getValueAt(0, c).getClass();
  }
  
  public boolean isCellEditable(int row, int col) {
    return true;
  }
  
  public void setValueAt(Object value, int row, int col) {
    if (this.tipoGrafo == 0) {
      this.datos[row][col] = value;
      this.datos[col][row] = value;
      fireTableCellUpdated(row, col);
      fireTableCellUpdated(col, row);
    } else {
      this.datos[row][col] = value;
      fireTableCellUpdated(row, col);
    } 
  }
}
