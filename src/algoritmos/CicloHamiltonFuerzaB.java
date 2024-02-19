package algoritmos;

import grafos.Arista;
import grafos.Grafo;
import interfaz.PanelGrafos;
import interfaz.matricesAdyacencia.MatrizTabla;
import java.util.ArrayList;
import javax.swing.JTextArea;

public class CicloHamiltonFuerzaB extends CicloHamiltoniano {
  private Grafo aux = null;
  
  private ArrayList<String> ciclos = new ArrayList<String>();
  
  public CicloHamiltonFuerzaB(PanelGrafos p, JTextArea area) {
    super(p, area, (Grafo)null);
    this.grafoOriginal = this.panel.getGrafo();
    this.grafoResultado = this.grafoOriginal.clonar();
  }
  
  public void run() {
    boolean encuentraCiclo = false;
    mostrarInformacionInicial(14, 2);
    encuentraCiclo = ejecutaAlgoritmoFuerzaBruta();
    crearGrafoResultadoYmostrarInformacionResultado(14, encuentraCiclo);
  }
  
  private boolean ejecutaAlgoritmoFuerzaBruta() {
    MatrizTabla matrizGrafo = new MatrizTabla(this.grafoOriginal);
    this.aux = this.grafoOriginal.clonar();
    ArrayList<Integer> lista = null;
    Arista a = null;
    String ciclo = "";
    String cicloOK = "";
    int ant = 0;
    double menorPeso = 99999.0D;
    Double peso = Double.valueOf(0.0D);
    double pesoCiclo = 0.0D;
    boolean eliminado = false;
    boolean existeCiclo = false;
    ArrayList<Integer> Nodos = new ArrayList<Integer>();
    for (int i = 0; i < this.grafoResultado.getNodos().size(); i++)
      Nodos.add(Integer.valueOf(i)); 
    int n = Nodos.size();
    int r = Nodos.size();
    Permutaciones(Nodos, "", n, r);
    int j;
    for (j = 0; j < this.ciclos.size(); j++) {
      ciclo = this.ciclos.get(j);
      lista = new ArrayList<Integer>();
      byte b;
      int m;
      String[] arrayOfString;
      for (m = (arrayOfString = ciclo.split(",")).length, b = 0; b < m; ) {
        String numero = arrayOfString[b];
        lista.add(Integer.valueOf(numero));
        b++;
      } 
      lista.add(lista.get(0));
      ant = ((Integer)lista.get(0)).intValue();
      pesoCiclo = 0.0D;
      eliminado = false;
      this.aux.resetAristas();
      for (int k = 1; k < lista.size(); k++) {
        peso = (Double)matrizGrafo.getValueAt(ant, ((Integer)lista.get(k)).intValue());
        if (peso.doubleValue() != 0.0D) {
          pesoCiclo += peso.doubleValue();
          a = new Arista(ant, ((Integer)lista.get(k)).intValue(), 0);
          a.setPeso(peso.doubleValue());
          this.aux.insertarArista(a);
        } else {
          eliminado = true;
          break;
        } 
        ant = ((Integer)lista.get(k)).intValue();
      } 
      if (!eliminado) {
        if (pesoCiclo < menorPeso) {
          menorPeso = pesoCiclo;
          cicloOK = ciclo;
          this.grafoResultado = this.aux.clonar();
        } 
        existeCiclo = true;
      } 
    } 
    if (existeCiclo) {
      ciclo = cicloOK;
      lista = new ArrayList<Integer>();
      byte b;
      int k;
      String[] arrayOfString;
      for (k = (arrayOfString = ciclo.split(",")).length, b = 0; b < k; ) {
        String numero = arrayOfString[b];
        lista.add(Integer.valueOf(numero));
        b++;
      } 
      lista.add(lista.get(0));
      for (j = 0; j < lista.size(); j++)
        this.textoInformacion.append("\n" + this.grafoResultado.getNodoByIndex(((Integer)lista.get(j)).intValue()).getNombre()); 
      this.textoInformacion.append("\n");
    } 
    return existeCiclo;
  }
  
  private void Permutaciones(ArrayList<Integer> elem, String act, int n, int r) {
    if (n == 0) {
      this.ciclos.add(act);
    } else {
      for (int i = 0; i < r; i++) {
        if (!act.contains(((Integer)elem.get(i)).toString()))
          Permutaciones(elem, String.valueOf(act) + elem.get(i) + ",", n - 1, r); 
      } 
    } 
  }
}
