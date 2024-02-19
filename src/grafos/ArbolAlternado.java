package grafos;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class ArbolAlternado extends GrafoEdmonds {
  private int ultimoERevisado;
  
  private Vector vectorE = new Vector(1, 1);
  
  private Vector vectorI = new Vector(1, 1);
  
  public ArbolAlternado(SubGrafoIgualdad sgi) {
    this.nodos = sgi.nodos;
    this.ultimoERevisado = 0;
    setPrimeraFloracion(sgi.getPrimeraFloracion());
    addNodoE(sgi.getIndexNodoInsaturado());
  }
  
  public ArbolAlternado(SubGrafoIgualdad sgi, int index) {
    this.nodos = sgi.nodos;
    this.ultimoERevisado = 0;
    setPrimeraFloracion(sgi.getPrimeraFloracion());
    addNodoI(index);
  }
  
  public void addNodoE(int index) {
    if (index < getPrimeraFloracion()) {
      NodoEdmonds n = this.nodos.get(index);
      n.setEtiquetaE();
      this.nodos.set(index, n);
      if (n.getFloracion() == -1)
        this.vectorE.add(new Integer(index)); 
    } else {
      Floracion f = this.nodos.get(index);
      f.setEtiquetaE();
      this.nodos.set(index, f);
      for (int j = 0; j < f.getCicloContraido().size(); j++) {
        int indexNodoContraido = ((Integer)f.getCicloContraido().get(j)).intValue();
        addNodoE(indexNodoContraido);
        sacarDeE(indexNodoContraido);
      } 
      if (f.getFloracion() == -1)
        this.vectorE.add(new Integer(index)); 
    } 
  }
  
  public void addNodoE(int index, int posicion) {
    if (index < getPrimeraFloracion()) {
      NodoEdmonds n = this.nodos.get(index);
      n.setEtiquetaE();
      this.nodos.set(index, n);
      if (n.getFloracion() == -1)
        this.vectorE.add(posicion, new Integer(index)); 
    } else {
      Floracion f = this.nodos.get(index);
      f.setEtiquetaE();
      this.nodos.set(index, f);
      for (int j = 0; j < f.getCicloContraido().size(); j++) {
        int indexNodoContraido = ((Integer)f.getCicloContraido().get(j)).intValue();
        addNodoE(indexNodoContraido);
        sacarDeE(indexNodoContraido);
      } 
      if (f.getFloracion() == -1)
        this.vectorE.add(posicion, new Integer(index)); 
    } 
  }
  
  public Vector getE() {
    return this.vectorE;
  }
  
  private void sacarDeE(int indexNodoContraido) {
    for (int i = 0; i < this.vectorE.size(); i++) {
      int index = ((Integer)this.vectorE.get(i)).intValue();
      if (index == indexNodoContraido) {
        this.vectorE.remove(i);
        break;
      } 
    } 
  }
  
  public void addNodoI(int index) {
    if (index < getPrimeraFloracion()) {
      NodoEdmonds n = this.nodos.get(index);
      n.setEtiquetaI();
      this.nodos.set(index, n);
      if (n.getFloracion() == -1)
        this.vectorI.add(new Integer(index)); 
    } else {
      Floracion f = this.nodos.get(index);
      f.setEtiquetaI();
      this.nodos.set(index, f);
      for (int j = 0; j < f.getCicloContraido().size(); j++) {
        int indexNodoContraido = ((Integer)f.getCicloContraido().get(j)).intValue();
        addNodoI(indexNodoContraido);
      } 
      if (f.getFloracion() == -1)
        this.vectorI.add(new Integer(index)); 
    } 
  }
  
  public int getIndexUltimoERevisado() {
    return ((Integer)this.vectorE.get(this.ultimoERevisado)).intValue();
  }
  
  public void setUltimoERevisado(int i) {
    this.ultimoERevisado = i;
  }
  
  public int getUltimoERevisado() {
    return this.ultimoERevisado;
  }
  
  public int getNumNodosE() {
    return this.vectorE.size();
  }
  
  public int retrocesoDeEHastaI(int indexNodo) {
    for (int i = 0; i < getAcoplamiento().size(); i++) {
      Arista aristaAcop = getAcoplamiento().get(i);
      if (indexRealNodo(aristaAcop.getCabeza()) != indexRealNodo(aristaAcop.getCola())) {
        if (indexRealNodo(aristaAcop.getCabeza()) == indexNodo)
          return indexRealNodo(aristaAcop.getCola()); 
        if (indexRealNodo(aristaAcop.getCola()) == indexNodo)
          return indexRealNodo(aristaAcop.getCabeza()); 
      } 
    } 
    return -1;
  }
  
  public int retrocesoDeIHastaE(int indexNodo) {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista arista = this.aristas.get(i);
      if (indexRealNodo(arista.getCabeza()) != indexRealNodo(arista.getCola())) {
        if ((((indexRealNodo(arista.getCabeza()) == indexNodo) ? 1 : 0) & (getAcoplamiento().contains(arista) ? 0 : 1)) != 0)
          return indexRealNodo(arista.getCola()); 
        if ((((indexRealNodo(arista.getCola()) == indexNodo) ? 1 : 0) & (getAcoplamiento().contains(arista) ? 0 : 1)) != 0)
          return indexRealNodo(arista.getCabeza()); 
      } 
    } 
    return -1;
  }
  
  public boolean esNodoE(int indexNodo) {
    NodoEdmonds n = this.nodos.get(indexNodo);
    if (n.getEtiquetaArborea() == "E")
      return true; 
    return false;
  }
  
  public boolean esNodoI(int indexNodo) {
    NodoEdmonds n = this.nodos.get(indexNodo);
    if (n.getEtiquetaArborea() == "I")
      return true; 
    return false;
  }
  
  public boolean esNodoArbol(int indexNodo) {
    return esNodoI(indexNodo) | esNodoE(indexNodo);
  }
  
  public void pintarGrafo(Graphics g) {
    if (this != null) {
      for (int i = 0; i < getPrimeraFloracion(); i++) {
        NodoEdmonds nodo = this.nodos.get(i);
        if (nodo.getEtiquetaArborea() != null)
          nodo.pintarNodo(g); 
      } 
      for (int k = getPrimeraFloracion(); k < this.nodos.size(); k++) {
        Floracion f = this.nodos.get(k);
        if (f.getEtiquetaArborea() != null)
          f.pintarFloracion(g); 
      } 
      for (int j = 0; j < this.aristas.size(); j++) {
        Arista arista = this.aristas.get(j);
        if (arista != null)
          arista.pintarArista(g, localizarPosicion(arista.getCabeza()), localizarPosicion(arista.getCola()), arista.getTipo(), false, Color.BLACK); 
      } 
      for (int l = 0; l < getAcoplamiento().size(); l++) {
        Arista arista = getAcoplamiento().get(l);
        if (arista != null)
          arista.pintarArista(g, localizarPosicion(arista.getCabeza()), localizarPosicion(arista.getCola()), arista.getTipo(), true, Color.BLUE); 
      } 
      if (getUltimoERevisado() < this.vectorE.size()) {
        Vector<Integer> revisando = new Vector(1);
        revisando.add(new Integer(getIndexUltimoERevisado()));
        pintarNodos(g, revisando, Color.RED);
      } 
      int raiz = ((Integer)getE().get(0)).intValue();
      if (raiz < getPrimeraFloracion()) {
        NodoEdmonds nodoRaiz = getNodos().get(raiz);
        nodoRaiz.pintarNodo(g, new Color(150, 0, 0));
      } else {
        Floracion nodoRaiz = getNodos().get(raiz);
        nodoRaiz.pintarFloracion(g, new Color(150, 0, 0));
      } 
    } 
  }
  
  public void aumentarERevisado() {
    this.ultimoERevisado++;
  }
}
