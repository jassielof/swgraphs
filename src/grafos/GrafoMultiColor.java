package grafos;

import interfaz.matricesAdyacencia.MatrizTabla;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class GrafoMultiColor extends Grafo {
  public Vector deAzul = new Vector(5, 1);
  
  public void pintarGrafo(Graphics g) {
    if (this != null) {
      int i;
      for (i = 0; i < this.nodos.size(); i++) {
        Nodo n = this.nodos.get(i);
        n.pintarNodo(g, Color.BLACK);
      } 
      for (i = 0; i < this.aristas.size(); i++) {
        Arista arista = this.aristas.get(i);
        if (arista != null) {
          Nodo n1 = getNodoByIndex(arista.getCabeza());
          Nodo n2 = getNodoByIndex(arista.getCola());
          arista.pintarArista(g, n1.getPos(), n2.getPos(), arista.getTipo(), false, Color.BLACK);
        } 
      } 
      for (int j = 0; j < this.deAzul.size(); j++) {
        Arista arista = this.deAzul.get(j);
        if (arista != null) {
          Nodo n1 = getNodoByIndex(arista.getCabeza());
          Nodo n2 = getNodoByIndex(arista.getCola());
          arista.pintarArista(g, n1.getPos(), n2.getPos(), arista.getTipo(), true, Color.BLUE);
        } 
      } 
    } 
  }
  
  public GrafoMultiColor convertirAGrafoMultiColor(Grafo g) {
    MatrizTabla matrizGrafoPintar = new MatrizTabla(g);
    GrafoMultiColor grafoPintar = new GrafoMultiColor();
    grafoPintar.setTipo(g.getTipo());
    int i;
    for (i = 0; i < g.getNodos().size(); i++) {
      Nodo n = g.getNodoByIndex(i);
      grafoPintar.insertarNodo(n);
    } 
    for (i = 0; i < g.getNodos().size(); i++) {
      int fin = 0;
      if (grafoPintar.getTipo() == 0)
        fin = i; 
      for (int j = fin; j < g.getNodos().size(); j++) {
        Double peso = (Double)matrizGrafoPintar.getValueAt(i, j);
        if (peso.doubleValue() != 0.0D)
          if (grafoPintar.getTipo() == 0) {
            Arista a = new Arista(i, j, 0);
            a.setPeso(peso.doubleValue());
            grafoPintar.insertarArista(a);
          } else {
            Arista a = new Arista(i, j, 1);
            a.setPeso(peso.doubleValue());
            grafoPintar.insertarArco(a);
          }  
      } 
    } 
    return grafoPintar;
  }
  
  public boolean existeAristaAzul(Arista a) {
    boolean existeArista = false;
    for (int k = 0; k < this.deAzul.size(); k++) {
      Arista arista = this.deAzul.get(k);
      if ((arista.getCabeza() == a.getCabeza() && arista.getCola() == a.getCola()) || (arista.getCabeza() == a.getCola() && arista.getCola() == a.getCabeza()))
        existeArista = true; 
    } 
    return existeArista;
  }
  
  public boolean existeArcoAzul(Arista a) {
    boolean existeArco = false;
    for (int k = 0; k < this.deAzul.size(); k++) {
      Arista arista = this.deAzul.get(k);
      if (arista.getCabeza() == a.getCabeza() && arista.getCola() == a.getCola())
        existeArco = true; 
    } 
    return existeArco;
  }
  
  public GrafoMultiColor clonar() {
    GrafoMultiColor grf_clon = new GrafoMultiColor();
    grf_clon.setTipo(getTipo());
    grf_clon.nodos = (Vector)this.nodos.clone();
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      Arista a_clonada = new Arista(a.getCabeza(), a.getCola(), a.getTipo());
      a_clonada.setPeso(a.getPeso());
      a_clonada.setMultiplicidad(a.getMultiplicidad());
      grf_clon.aristas.add(a_clonada);
    } 
    for (int j = 0; j < this.deAzul.size(); j++) {
      Arista arista = this.deAzul.get(j);
      grf_clon.deAzul.add(arista);
    } 
    return grf_clon;
  }
}
