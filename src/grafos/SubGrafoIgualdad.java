package grafos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class SubGrafoIgualdad extends GrafoEdmonds {
  private Vector vectorC = new Vector(5, 5);
  
  public SubGrafoIgualdad(GrafoEdmonds G) {
    this.nodos = G.nodos;
    setPrimeraFloracion(G.getPrimeraFloracion());
    for (int i = 0; i < G.getAristas().size(); i++) {
      double diferencia;
      Arista a = G.getAristaByIndex(i);
      if (indexRealNodo(a.getCabeza()) == indexRealNodo(a.getCola())) {
        double etiquetaFloracion = floracionMasInternaEtiqueta(a.getCabeza(), a.getCola());
        NodoEdmonds n1 = (NodoEdmonds)G.getNodoByIndex(a.getCabeza());
        NodoEdmonds n2 = (NodoEdmonds)G.getNodoByIndex(a.getCola());
        diferencia = a.getPeso() - n1.getEtiqueta() - n2.getEtiqueta() - etiquetaFloracion;
      } else {
        NodoEdmonds n1 = (NodoEdmonds)G.getNodoByIndex(a.getCabeza());
        NodoEdmonds n2 = (NodoEdmonds)G.getNodoByIndex(a.getCola());
        diferencia = a.getPeso() - n1.getEtiqueta() - n2.getEtiqueta();
      } 
      if ((((diferencia < 1.0E-7D) ? 1 : 0) & ((diferencia > -1.0E-7D) ? 1 : 0)) != 0) {
        this.aristas.add(a);
      } else {
        this.vectorC.add(a);
      } 
    } 
    this.acoplamiento = G.acoplamiento;
  }
  
  private double floracionMasInternaEtiqueta(int n1, int n2) {
    Floracion f1 = null;
    for (int i = getPrimeraFloracion(); i < getNodos().size(); i++) {
      if (nodoContenidoEnFloracion(i, n1) && nodoContenidoEnFloracion(i, n1)) {
        f1 = this.nodos.get(i);
        return f1.getEtiqueta();
      } 
    } 
    return 0.0D;
  }
  
  public Vector getC() {
    return this.vectorC;
  }
  
  public void pintarGrafo(Graphics g) {
    if (this != null) {
      for (int i = 0; i < getPrimeraFloracion(); i++) {
        NodoEdmonds nodo = this.nodos.get(i);
        nodo.pintarNodo(g);
      } 
      for (int k = getPrimeraFloracion(); k < this.nodos.size(); k++) {
        Floracion f = this.nodos.get(k);
        f.pintarFloracion(g);
      } 
      for (int j = 0; j < this.aristas.size(); j++) {
        Arista arista = this.aristas.get(j);
        if (arista != null)
          arista.pintarArista(g, localizarPosicion(arista.getCabeza()), localizarPosicion(arista.getCola()), arista.getTipo(), false, Color.BLACK); 
      } 
      for (int l = 0; l < this.acoplamiento.size(); l++) {
        Arista arista = this.acoplamiento.get(l);
        if (arista != null)
          arista.pintarArista(g, localizarPosicion(arista.getCabeza()), localizarPosicion(arista.getCola()), arista.getTipo(), true, Color.BLUE); 
      } 
    } 
  }
}
