package algoritmos;

import grafos.Arista;
import grafos.Grafo;
import grafos.Nodo;
import interfaz.PanelGrafos;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JTextArea;

public class Hierholzer extends Algoritmo {
  private PanelGrafos resultado;
  
  public Hierholzer(PanelGrafos p, PanelGrafos r, JTextArea area) {
    this.panel = p;
    this.resultado = r;
    this.textoInformacion = area;
    setGrafoOriginal(this.panel.getGrafo());
  }
  
  public void setGrafoOriginal(Grafo grafoOriginal) {
    this.grafoOriginal = grafoOriginal;
  }
  
  public Grafo getGrafoOriginal() {
    return this.grafoOriginal;
  }
  
  public void run() {
    this.textoInformacion.setText("El algoritmo de Hierholzer ha finalizado.\n\nRESULTADO:\n");
    this.textoInformacion.append("\nEn el panel inferior se muestra el ciclo euleriano.");
    Vector<String> NodosCiclo = new Vector(10, 5);
    Grafo aux = this.grafoOriginal.clonar();
    Grafo auxMantenido = this.grafoOriginal.clonar();
    int indexNodo = 0;
    int indexSiguiente = -1;
    int donde = -1;
    Nodo n = aux.getNodoByIndex(indexNodo);
    NodosCiclo.add(n.getNombre());
    while (auxMantenido.getAristas().size() > 0) {
      Vector<Integer> verticesAdyacentes = auxMantenido.indexNodosAdyacentes(indexNodo);
      if (verticesAdyacentes.size() == 0)
        if (auxMantenido.getAristas().size() > 0) {
          Arista otra = auxMantenido.getAristas().get(0);
          indexNodo = otra.getCola();
          for (int j = 0; j < NodosCiclo.size(); j++) {
            Nodo n1 = auxMantenido.getNodoByIndex(indexNodo);
            if (NodosCiclo.get(j).toString() == n1.getNombre())
              donde = j; 
          } 
        } else {
          break;
        }  
      while (verticesAdyacentes.size() > 0) {
        indexSiguiente = ((Integer)verticesAdyacentes.get(0)).intValue();
        if (aux.getTipo() == 0) {
          aux.borrarAristabyNodosIndex(indexNodo, indexSiguiente);
        } else {
          aux.borrarArcobyNodosIndex(indexNodo, indexSiguiente);
        } 
        if (verticesAdyacentes.size() == 1) {
          indexNodo = indexSiguiente;
          n = aux.getNodoByIndex(indexSiguiente);
          if (donde == -1) {
            NodosCiclo.add(n.getNombre());
          } else {
            donde++;
            NodosCiclo.add(donde, n.getNombre());
          } 
          auxMantenido = aux.clonar();
          break;
        } 
        aux = auxMantenido.clonar();
        verticesAdyacentes.remove(0);
      } 
    } 
    Grafo cicloEuleriano = new Grafo();
    n = new Nodo(NodosCiclo.get(0).toString(), new Point(10, 20));
    cicloEuleriano.insertarNodo(n);
    for (int i = 1; i < NodosCiclo.size(); i++) {
      n = new Nodo(NodosCiclo.get(i).toString(), new Point(10 + 60 * cicloEuleriano.getNodos().size(), 20));
      cicloEuleriano.insertarNodo(n);
      int tipo = -1;
      if (auxMantenido.getTipo() == 0) {
        tipo = 0;
      } else {
        tipo = 1;
      } 
      Arista a = new Arista(i - 1, i, tipo);
      a.setPeso(1.0D);
      cicloEuleriano.insertarArista(a);
    } 
    this.resultado.setGrafo(cicloEuleriano);
    if (10 + 60 * (cicloEuleriano.getNodos().size() - 1) > 450) {
      this.resultado.setPreferredSize(new Dimension(10 + 60 * (cicloEuleriano.getNodos().size() - 1) + 20, 50));
      this.resultado.revalidate();
    } 
  }
}
