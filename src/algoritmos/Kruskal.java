package algoritmos;

import grafos.Arista;
import grafos.Grafo;
import grafos.GrafoMultiColor;
import grafos.Nodo;
import interfaz.PanelGrafos;
import interfaz.matricesAdyacencia.MatrizTabla;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class Kruskal extends Algoritmo {
  private Arista a = null;
  
  private Arista a2 = null;
  
  private ArrayList<Integer> nodosConectados = new ArrayList<Integer>();
  
  public Kruskal(PanelGrafos p, JTextArea area) {
    this.panel = p;
    this.textoInformacion = area;
    this.grafoOriginal = this.panel.getGrafo();
  }
  
  public void run() {
    MatrizTabla matrizGrafoPintar = new MatrizTabla(this.grafoOriginal);
    this.grafoResultado = this.grafoOriginal.clonar();
    this.grafoResultado.resetAristas();
    double pesoTotal = 0.0D;
    double pesoMin = 9999.0D;
    this.nodosConectados.add(Integer.valueOf(0));
    while (this.nodosConectados.size() != this.grafoOriginal.getNodos().size()) {
      int indexa2 = -1;
      for (int j = 0; j < this.grafoOriginal.getAristas().size(); j++) {
        this.a = this.grafoOriginal.getAristas().get(j);
        if ((this.nodosConectados.contains(Integer.valueOf(this.a.getCabeza())) | this.nodosConectados.contains(Integer.valueOf(this.a.getCola()))) != 0)
          if (pesoMin >= this.a.getPeso() && !this.grafoResultado.existeArista(this.a.getCabeza(), this.a.getCola())) {
            pesoMin = this.a.getPeso();
            this.a2 = new Arista(this.a.getCabeza(), this.a.getCola(), this.a.getTipo());
            this.a2.setPeso(this.a.getPeso());
            this.a2.setMultiplicidad(this.a.getMultiplicidad());
            indexa2 = j;
          }  
      } 
      int NodoaInsertar = this.a2.getCola();
      if (this.nodosConectados.contains(Integer.valueOf(NodoaInsertar)))
        NodoaInsertar = this.a2.getCabeza(); 
      if (!cierraCicloLaAristaAInsertar(NodoaInsertar)) {
        this.grafoResultado.getAristas().add(this.a2);
        if (!this.nodosConectados.contains(Integer.valueOf(NodoaInsertar)))
          this.nodosConectados.add(Integer.valueOf(NodoaInsertar)); 
      } 
      if (indexa2 != -1) {
        this.grafoOriginal.getAristas().remove(indexa2);
      } else {
        boolean salir = false;
        for (int k = 0; k < this.grafoOriginal.getNodos().size(); k++) {
          if (!this.nodosConectados.contains(Integer.valueOf(k)) && !salir) {
            this.nodosConectados.add(Integer.valueOf(k));
            salir = true;
          } 
        } 
      } 
      pesoMin = 9999.0D;
    } 
    this.textoInformacion.setText("El algoritmo de Kruskal ha finalizado.\n\nRESULTADO:\n");
    this.textoInformacion.append("\n\nEl árbol de expansión mínima (bosque de expansión mínima en el caso de grafos no conexos) se muestra en el panel de grafos de color azul sobre el grafo original.\n");
    GrafoMultiColor grafoPintar = new GrafoMultiColor();
    int i;
    for (i = 0; i < this.grafoOriginal.getNodos().size(); i++) {
      Nodo n = this.grafoOriginal.getNodoByIndex(i);
      grafoPintar.insertarNodo(n);
    } 
    for (i = 0; i < this.grafoResultado.getNodos().size(); i++) {
      for (int j = i; j < this.grafoResultado.getNodos().size(); j++) {
        Double peso = (Double)matrizGrafoPintar.getValueAt(i, j);
        if (peso.doubleValue() != 0.0D) {
          Arista a = new Arista(i, j, 0);
          a.setPeso(peso.doubleValue());
          grafoPintar.insertarArista(a);
          if (this.grafoResultado.existeArista(a)) {
            grafoPintar.deAzul.add(a);
            pesoTotal += a.getPeso();
          } 
        } 
      } 
    } 
    this.textoInformacion.append("\nEl peso del árbol generador es: " + pesoTotal);
    this.panel.setGrafo((Grafo)grafoPintar);
  }
  
  private boolean cierraCicloLaAristaAInsertar(int NodoaInsertar) {
    if (this.nodosConectados.contains(Integer.valueOf(NodoaInsertar)) && this.nodosConectados.size() > 2)
      return true; 
    return false;
  }
}
