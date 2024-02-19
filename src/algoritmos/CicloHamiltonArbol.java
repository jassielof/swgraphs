package algoritmos;

import grafos.Arista;
import grafos.Grafo;
import interfaz.PanelGrafos;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JTextArea;

public class CicloHamiltonArbol extends CicloHamiltoniano {
  public CicloHamiltonArbol(PanelGrafos p, JTextArea area, Grafo g) {
    super(p, area, g);
    this.grafoResultado = this.panel.getGrafo();
  }
  
  public void run() {
    mostrarInformacionInicial(10, 0);
    ArrayList<String> NodosConectadosRepe = new ArrayList<String>();
    ArrayList<String> NodosConectados = new ArrayList<String>();
    ArrayList<Integer> NodosOrden = new ArrayList<Integer>();
    for (int i = 0; i < this.grafoResultado.getNodos().size(); i++)
      NodosConectadosRepe.add(this.grafoResultado.getNodoByIndex(i).getNombre()); 
    HashSet<String> hashSet = new HashSet<String>(NodosConectadosRepe);
    NodosConectados.clear();
    NodosConectados.addAll(hashSet);
    for (int j = 0; j < NodosConectados.size(); j++) {
      NodosOrden.add(Integer.valueOf(this.grafoOriginal.getPosicionNodoByNombre(NodosConectados.get(j))));
      this.textoInformacion.setText(String.valueOf(this.textoInformacion.getText()) + "\n" + (String)NodosConectados.get(j));
    } 
    NodosOrden.add(Integer.valueOf(this.grafoOriginal.getPosicionNodoByNombre(NodosConectados.get(0))));
    this.textoInformacion.setText(String.valueOf(this.textoInformacion.getText()) + "\n" + (String)NodosConectados.get(0) + "\n");
    this.grafoResultado.resetAristas();
    for (int k = 0; k < NodosOrden.size() - 1; k++) {
      Arista a = new Arista(((Integer)NodosOrden.get(k)).intValue(), ((Integer)NodosOrden.get(k + 1)).intValue(), 0);
      this.grafoResultado.insertarArista(a);
    } 
    crearGrafoResultadoYmostrarInformacionResultado(10, true);
  }
}
