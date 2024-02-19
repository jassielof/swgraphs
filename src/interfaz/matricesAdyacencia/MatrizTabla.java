package interfaz.matricesAdyacencia;

import grafos.Arista;
import grafos.Grafo;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class MatrizTabla extends JTable {
  private Object[][] cuerpoMatriz;
  
  private String[] listaNodos;
  
  public MatrizTabla(Grafo G) {
    this.cuerpoMatriz = (Object[][])new Double[G.getNodos().size()][G.getNodos().size()];
    this.listaNodos = new String[G.getNodos().size()];
    int i;
    for (i = 0; i < G.getNodos().size(); i++) {
      this.listaNodos[i] = G.getNodoByIndex(i).getNombre();
      for (int k = 0; k < G.getNodos().size(); k++) {
        Arista a = G.getArcoByNodosIndex(i, k);
        if (a == null) {
          this.cuerpoMatriz[i][k] = new Double(0.0D);
        } else {
          this.cuerpoMatriz[i][k] = new Double(a.getPeso());
        } 
      } 
    } 
    if (G.getTipo() == 0)
      for (i = 0; i < G.getNodos().size(); i++) {
        for (int k = 0; k < G.getNodos().size(); k++) {
          Double valor1 = (Double)this.cuerpoMatriz[i][k];
          Double valor2 = (Double)this.cuerpoMatriz[k][i];
          if (Double.compare(valor1.doubleValue(), 0.0D) != 0)
            this.cuerpoMatriz[k][i] = valor1; 
          if (Double.compare(valor2.doubleValue(), 0.0D) != 0)
            this.cuerpoMatriz[i][k] = valor2; 
        } 
      }  
    setModel(new MatrizTablaModel(this.listaNodos, this.cuerpoMatriz, G.getTipo()));
    setDefaultRenderer(String.class, new MatrizRenderer());
    getTableHeader().setVisible(true);
    getTableHeader().setReorderingAllowed(false);
    TableColumn column = null;
    for (int j = 0; j < getColumnCount(); j++) {
      column = getColumnModel().getColumn(j);
      column.setWidth(65);
      column.setMinWidth(65);
      column.setResizable(false);
    } 
    setAutoResizeMode(0);
  }
}
