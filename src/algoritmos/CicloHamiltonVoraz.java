package algoritmos;

import grafos.Arista;
import grafos.Grafo;
import interfaz.PanelGrafos;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class CicloHamiltonVoraz extends CicloHamiltoniano {
  private ArrayList<Integer> NodosConectados;
  
  private ArrayList<Integer> NodosNOConectados;
  
  public CicloHamiltonVoraz(PanelGrafos p, JTextArea area) {
    super(p, area, (Grafo)null);
    this.grafoOriginal = this.panel.getGrafo();
  }
  
  public void run() {
    mostrarInformacionInicial(10, 1);
    ejecutaAlgoritmoVoraz();
    crearGrafoResultadoYmostrarInformacionResultado(10, true);
  }
  
  private boolean ejecutaAlgoritmoVoraz() {
    this.NodosConectados = new ArrayList<Integer>();
    this.NodosNOConectados = new ArrayList<Integer>();
    double pesoMin = 9999.0D;
    Arista a = null;
    this.grafoResultado = this.grafoOriginal.clonar();
    this.grafoResultado.resetAristas();
    for (int j = 1; j < this.grafoOriginal.getNodos().size(); j++)
      this.NodosNOConectados.add(Integer.valueOf(j)); 
    int nodoActual = 0;
    int nodoNuevo = -1;
    int posNodoNuevo = -1;
    int posNodoAnt = 1;
    this.NodosConectados.add(Integer.valueOf(nodoActual));
    this.NodosConectados.add(Integer.valueOf(nodoActual));
    this.textoInformacion.append("\n" + this.grafoResultado.getNodoByIndex(nodoActual).getNombre());
    int k = this.NodosConectados.size();
    int p = this.grafoOriginal.getNodos().size();
    while (k <= p) {
      for (int i = 0; i < this.NodosNOConectados.size(); i++) {
        for (int m = 1; m < this.NodosConectados.size(); m++) {
          a = this.grafoOriginal.getAristaByNodosIndex(((Integer)this.NodosNOConectados.get(i)).intValue(), ((Integer)this.NodosConectados.get(m)).intValue());
          if (a != null && pesoMin >= a.getPeso()) {
            pesoMin = a.getPeso();
            nodoNuevo = ((Integer)this.NodosNOConectados.get(i)).intValue();
            posNodoNuevo = i;
            posNodoAnt = m;
          } 
          a = null;
        } 
      } 
      this.NodosConectados.add(posNodoAnt, Integer.valueOf(nodoNuevo));
      this.NodosNOConectados.remove(posNodoNuevo);
      this.textoInformacion.append("\n" + this.grafoResultado.getNodoByIndex(nodoNuevo).getNombre());
      k++;
      nodoActual = nodoNuevo;
      pesoMin = 9999.0D;
    } 
    insertarAristasAlGrafoResultado();
    this.textoInformacion.append("\n" + this.grafoResultado.getNodoByIndex(0).getNombre() + "\n");
    return true;
  }
  
  private void insertarAristasAlGrafoResultado() {
    int nodo1 = ((Integer)this.NodosConectados.get(0)).intValue();
    int nodo2 = -1;
    for (int i = 1; i < this.NodosConectados.size(); i++) {
      nodo2 = ((Integer)this.NodosConectados.get(i)).intValue();
      this.grafoResultado.insertarArista(nodo1, nodo2);
      nodo1 = nodo2;
    } 
  }
}
