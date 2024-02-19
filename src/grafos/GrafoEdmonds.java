package grafos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

public class GrafoEdmonds extends Grafo {
  protected Vector acoplamiento = new Vector(5, 1);
  
  private int numNodosReales;
  
  private int numAristasReales;
  
  private int primeraFloracion;
  
  public GrafoEdmonds() {}
  
  public GrafoEdmonds(int k) {
    super(k, 1);
  }
  
  public void cambiarEtiquetaIndex(int index, double etiq) {
    Nodo n = this.nodos.get(index);
    n.setEtiqueta(etiq);
    this.nodos.set(index, n);
  }
  
  public Vector indexNodosAdyacentes(int index) {
    int indexReal = indexRealNodo(index);
    Vector<Integer> listaNodosAdyacentes = new Vector(1, 1);
    if (indexReal < getPrimeraFloracion()) {
      listaNodosAdyacentes = super.indexNodosAdyacentes(index);
      for (int i = 0; i < listaNodosAdyacentes.size(); i++) {
        int indexNodoAdy = ((Integer)listaNodosAdyacentes.get(i)).intValue();
        int indexRealNodoAdy = indexRealNodo(indexNodoAdy);
        if (indexRealNodoAdy != indexNodoAdy)
          listaNodosAdyacentes.set(i, new Integer(indexRealNodoAdy)); 
      } 
    } else {
      for (int j = 0; j < getPrimeraFloracion(); j++) {
        if (indexRealNodo(j) == indexReal)
          listaNodosAdyacentes.addAll(super.indexNodosAdyacentes(j)); 
      } 
      for (int k = 0; k < listaNodosAdyacentes.size(); k++) {
        int indexNodoAdyFloracion = ((Integer)listaNodosAdyacentes.get(k)).intValue();
        int indexRealNodoAdyFloracion = indexRealNodo(indexNodoAdyFloracion);
        if (indexRealNodoAdyFloracion == indexReal) {
          listaNodosAdyacentes.remove(k);
          k--;
        } else if (indexRealNodoAdyFloracion != indexNodoAdyFloracion) {
          listaNodosAdyacentes.set(k, new Integer(indexRealNodoAdyFloracion));
        } 
      } 
    } 
    return listaNodosAdyacentes;
  }
  
  public int indexRealNodo(int index) {
    NodoEdmonds n = this.nodos.get(index);
    int indexReal = index;
    while (n.getFloracion() != -1) {
      if (n.getFloracion() >= getNodos().size()) {
        n.setFloracion(-1);
        return indexReal;
      } 
      indexReal = n.getFloracion();
      n = this.nodos.get(n.getFloracion());
    } 
    return indexReal;
  }
  
  public void pintarGrafo(Graphics g) {
    if (this != null) {
      for (int i = 0; i < this.primeraFloracion; i++) {
        NodoEdmonds nodo = this.nodos.get(i);
        nodo.pintarNodo(g);
      } 
      for (int k = this.primeraFloracion; k < this.nodos.size(); k++) {
        Floracion f = this.nodos.get(k);
        f.pintarFloracion(g);
      } 
      for (int j = 0; j < this.aristas.size(); j++) {
        Arista arista = this.aristas.get(j);
        if (arista != null) {
          NodoEdmonds nodoCabeza = (NodoEdmonds)getNodoByIndex(arista.getCabeza());
          NodoEdmonds nodoCola = (NodoEdmonds)getNodoByIndex(arista.getCola());
          if (nodoCabeza != null && nodoCola != null)
            arista.pintarArista(g, localizarPosicion(arista.getCabeza()), localizarPosicion(arista.getCola()), arista.getTipo(), false, Color.BLACK); 
        } 
      } 
      for (int l = 0; l < this.acoplamiento.size(); l++) {
        Arista arista = this.acoplamiento.get(l);
        if (arista != null)
          arista.pintarArista(g, localizarPosicion(arista.getCabeza()), localizarPosicion(arista.getCola()), arista.getTipo(), true, Color.BLUE); 
      } 
    } 
  }
  
  protected Point localizarPosicion(int index) {
    Nodo n = getNodoByIndex(indexRealNodo(index));
    return n.getPos();
  }
  
  public void etiquetarAdmisible() {
    for (int i = 0; i < this.nodos.size(); i++) {
      Nodo n = this.nodos.get(i);
      double etiqueta = -10000.0D;
      for (int j = 0; j < this.aristas.size(); j++) {
        Arista a = this.aristas.get(j);
        Nodo n1 = this.nodos.get(a.getCabeza());
        Nodo n2 = this.nodos.get(a.getCola());
        if (n1 == n)
          etiqueta = Math.max(a.getPeso() - n2.getEtiqueta(), etiqueta); 
        if (n2 == n)
          etiqueta = Math.max(a.getPeso() - n1.getEtiqueta(), etiqueta); 
      } 
      n.setEtiqueta(etiqueta);
      this.nodos.remove(i);
      this.nodos.add(i, n);
    } 
  }
  
  public boolean insertaAristaAcoplamiento(int nodo1, int nodo2, Grafo SGI) {
    if (nodo1 < this.primeraFloracion && nodo2 < this.primeraFloracion) {
      Arista a = getAristaByNodosIndex(nodo1, nodo2);
      if (!this.acoplamiento.contains(a))
        this.acoplamiento.add(a); 
      return true;
    } 
    if (nodo1 < this.primeraFloracion && nodo2 >= this.primeraFloracion)
      for (int i = 0; i < SGI.aristas.size(); i++) {
        Arista a = SGI.getAristaByIndex(i);
        if ((((a.getCabeza() == nodo1) ? 1 : 0) & nodoContenidoEnFloracion(nodo2, a.getCola())) != 0 || ((
          (a.getCola() == nodo1) ? 1 : 0) & nodoContenidoEnFloracion(nodo2, a.getCabeza())) != 0) {
          if (!this.acoplamiento.contains(a))
            this.acoplamiento.add(a); 
          return true;
        } 
      }  
    if (nodo2 < this.primeraFloracion && nodo1 >= this.primeraFloracion)
      for (int i = 0; i < SGI.aristas.size(); i++) {
        Arista a = SGI.getAristaByIndex(i);
        if ((((a.getCabeza() == nodo2) ? 1 : 0) & nodoContenidoEnFloracion(nodo1, a.getCola())) != 0 || ((
          (a.getCola() == nodo2) ? 1 : 0) & nodoContenidoEnFloracion(nodo1, a.getCabeza())) != 0) {
          if (!this.acoplamiento.contains(a))
            this.acoplamiento.add(a); 
          return true;
        } 
      }  
    if (nodo1 >= this.primeraFloracion && nodo2 >= this.primeraFloracion)
      for (int i = 0; i < SGI.aristas.size(); i++) {
        Arista a = SGI.getAristaByIndex(i);
        if ((nodoContenidoEnFloracion(nodo1, a.getCabeza()) & nodoContenidoEnFloracion(nodo2, a.getCola())) != 0 || (
          nodoContenidoEnFloracion(nodo1, a.getCola()) & nodoContenidoEnFloracion(nodo2, a.getCabeza())) != 0) {
          if (!this.acoplamiento.contains(a))
            this.acoplamiento.add(a); 
          return true;
        } 
      }  
    return false;
  }
  
  public boolean quitarAristaDeAcoplamiento(int nodo1, int nodo2) {
    if (nodo1 < this.primeraFloracion && nodo2 < this.primeraFloracion)
      for (int i = 0; i < this.acoplamiento.size(); i++) {
        Arista a = this.acoplamiento.get(i);
        if ((((a.getCabeza() == nodo1) ? 1 : 0) & ((a.getCola() == nodo2) ? 1 : 0) | (
          (a.getCabeza() == nodo2) ? 1 : 0) & ((a.getCola() == nodo1) ? 1 : 0)) != 0) {
          this.acoplamiento.remove(i);
          return true;
        } 
      }  
    if (nodo1 < this.primeraFloracion && nodo2 >= this.primeraFloracion)
      for (int i = 0; i < this.acoplamiento.size(); i++) {
        Arista a = this.acoplamiento.get(i);
        if ((((a.getCabeza() == nodo1) ? 1 : 0) & nodoContenidoEnFloracion(nodo2, a.getCola())) != 0 || ((
          (a.getCola() == nodo1) ? 1 : 0) & nodoContenidoEnFloracion(nodo2, a.getCabeza())) != 0) {
          this.acoplamiento.remove(i);
          return true;
        } 
      }  
    if (nodo1 >= this.primeraFloracion && nodo2 < this.primeraFloracion)
      for (int i = 0; i < this.acoplamiento.size(); i++) {
        Arista a = this.acoplamiento.get(i);
        if ((((a.getCabeza() == nodo2) ? 1 : 0) & nodoContenidoEnFloracion(nodo1, a.getCola())) != 0 || ((
          (a.getCola() == nodo2) ? 1 : 0) & nodoContenidoEnFloracion(nodo1, a.getCabeza())) != 0) {
          this.acoplamiento.remove(i);
          return true;
        } 
      }  
    if (nodo1 >= this.primeraFloracion && nodo2 >= this.primeraFloracion)
      for (int i = 0; i < this.acoplamiento.size(); i++) {
        Arista a = this.acoplamiento.get(i);
        if ((nodoContenidoEnFloracion(nodo1, a.getCabeza()) & nodoContenidoEnFloracion(nodo2, a.getCola())) != 0 || (
          nodoContenidoEnFloracion(nodo1, a.getCola()) & nodoContenidoEnFloracion(nodo2, a.getCabeza())) != 0) {
          this.acoplamiento.remove(i);
          return true;
        } 
      }  
    return false;
  }
  
  public Grafo convertirAGrafo() {
    Grafo G = new Grafo();
    for (int i = 0; i < this.nodos.size(); i++) {
      NodoEdmonds nE = this.nodos.get(i);
      G.nodos.add(i, nE.convertirANodo());
    } 
    G.aristas = this.aristas;
    return G;
  }
  
  public void addNodoFicticio() {
    int numeroFicticio = this.nodos.size() - getNumNodosReales() + 1;
    NodoEdmonds nuevoNodoFicticio = new NodoEdmonds("Ficticio " + numeroFicticio, new Point(720 * numeroFicticio, 720 * numeroFicticio));
    for (int i = 0; i < getNumNodosReales(); i++) {
      Arista nuevaAristaFicticia = new Arista(i, this.nodos.size(), 0);
      nuevaAristaFicticia.setPeso(-10000.0D);
      this.aristas.add(nuevaAristaFicticia);
    } 
    this.nodos.add(nuevoNodoFicticio);
    setPrimeraFloracion(this.primeraFloracion + 1);
  }
  
  public boolean esSaturadoIndex(int index) {
    if (index < this.primeraFloracion) {
      for (int i = 0; i < this.acoplamiento.size(); i++) {
        Arista a = this.acoplamiento.get(i);
        if ((((a.getCabeza() == index) ? 1 : 0) | ((a.getCola() == index) ? 1 : 0)) != 0)
          return true; 
      } 
    } else {
      for (int i = 0; i < this.acoplamiento.size(); i++) {
        Arista a = this.acoplamiento.get(i);
        if ((((indexRealNodo(a.getCabeza()) == index) ? 1 : 0) & ((indexRealNodo(a.getCabeza()) != indexRealNodo(a.getCola())) ? 1 : 0)) != 0)
          return true; 
        if ((((indexRealNodo(a.getCola()) == index) ? 1 : 0) & ((indexRealNodo(a.getCabeza()) != indexRealNodo(a.getCola())) ? 1 : 0)) != 0)
          return true; 
      } 
    } 
    return false;
  }
  
  public int getIndexNodoInsaturado() {
    for (int i = this.primeraFloracion - 1; i >= 0; i--) {
      NodoEdmonds n = this.nodos.get(i);
      if ((((n.getFloracion() == -1) ? 1 : 0) & (esSaturadoIndex(i) ? 0 : 1)) != 0)
        return i; 
    } 
    for (int j = this.primeraFloracion; j < this.nodos.size(); j++) {
      NodoEdmonds n = this.nodos.get(j);
      if ((((n.getFloracion() == -1) ? 1 : 0) & (esSaturadoIndex(j) ? 0 : 1)) != 0)
        return j; 
    } 
    return -1;
  }
  
  public Arista getAristaByNodosIndex(int indexN1, int indexN2) {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if ((((indexRealNodo(a.getCabeza()) == indexRealNodo(indexN1)) ? 1 : 0) & ((indexRealNodo(a.getCola()) == indexRealNodo(indexN2)) ? 1 : 0) | (
        (indexRealNodo(a.getCabeza()) == indexRealNodo(indexN2)) ? 1 : 0) & ((indexRealNodo(a.getCola()) == indexRealNodo(indexN1)) ? 1 : 0)) != 0)
        return a; 
    } 
    return null;
  }
  
  public void borrarFloracionIndex(int indexFloracion) {
    int i;
    for (i = 0; i < this.primeraFloracion; i++) {
      NodoEdmonds n = (NodoEdmonds)getNodoByIndex(i);
      if (n.getFloracion() > indexFloracion) {
        n.setFloracion(n.getFloracion() - 1);
        this.nodos.set(i, n);
      } 
    } 
    for (i = this.primeraFloracion; i < indexFloracion; i++) {
      Floracion f = (Floracion)getNodoByIndex(i);
      if (f.getFloracion() > indexFloracion) {
        f.setFloracion(f.getFloracion() - 1);
        this.nodos.set(i, f);
      } 
    } 
    for (i = indexFloracion; i < getNodos().size(); i++) {
      Floracion f = (Floracion)getNodoByIndex(i);
      if (f.getFloracion() > indexFloracion)
        f.setFloracion(f.getFloracion() - 1); 
      Vector<Integer> cicloContraido = f.getCicloContraido();
      for (int j = 0; j < cicloContraido.size(); j++) {
        int nodoContraido = ((Integer)cicloContraido.get(j)).intValue();
        if (nodoContraido > indexFloracion) {
          cicloContraido.set(j, new Integer(nodoContraido - 1));
          f.setCicloContraido(cicloContraido);
        } 
      } 
      this.nodos.set(i, f);
    } 
    getNodos().remove(indexFloracion);
  }
  
  public void completarConAristasFicticias() {
    for (int i = 0; i < this.primeraFloracion; i++) {
      for (int j = i + 1; j < this.primeraFloracion; j++) {
        if (super.getAristaByNodosIndex(i, j) == null) {
          Arista a = new Arista(i, j, 0);
          a.setPeso(-10000.0D);
          insertarArista(a);
        } 
      } 
    } 
  }
  
  public void eliminarFiccion() {
    while (this.aristas.size() > this.numAristasReales)
      this.aristas.remove(this.aristas.size() - 1); 
    for (int i = this.acoplamiento.size() - 1; i >= 0; i--) {
      Arista aristaAcopl = this.acoplamiento.get(i);
      if (getAristaByNodosIndex(aristaAcopl.getCabeza(), aristaAcopl.getCola()) == null)
        this.acoplamiento.remove(i); 
    } 
    if (this.nodos.size() == this.numNodosReales + 1) {
      this.nodos.remove(this.numNodosReales);
      this.primeraFloracion--;
    } 
  }
  
  public boolean nodoContenidoEnFloracion(int indexFloracion, int indexNodo) {
    NodoEdmonds n = this.nodos.get(indexNodo);
    if (n.getFloracion() == -1)
      return false; 
    if (n.getFloracion() == indexFloracion)
      return true; 
    return nodoContenidoEnFloracion(indexFloracion, n.getFloracion());
  }
  
  public Vector listaNodosContenidos(Floracion f) {
    Vector<Integer> nodosContenidos = new Vector(3, 2);
    for (int i = 0; i < f.getCicloContraido().size(); i++) {
      int index = ((Integer)f.getCicloContraido().get(i)).intValue();
      if (index < this.primeraFloracion) {
        nodosContenidos.add(new Integer(index));
      } else {
        Floracion floracion = (Floracion)getNodoByIndex(index);
        nodosContenidos.addAll(listaNodosContenidos(floracion));
      } 
    } 
    return nodosContenidos;
  }
  
  public Vector getAcoplamiento() {
    return this.acoplamiento;
  }
  
  public int getNumNodosReales() {
    return this.numNodosReales;
  }
  
  public void setNumAristasReales() {
    this.numAristasReales = this.aristas.size();
  }
  
  public int getNumAristasReales() {
    return this.numAristasReales;
  }
  
  public void setNumNodosReales() {
    this.numNodosReales = this.nodos.size();
  }
  
  public int getPrimeraFloracion() {
    return this.primeraFloracion;
  }
  
  public void setPrimeraFloracion(int i) {
    this.primeraFloracion = i;
  }
}
