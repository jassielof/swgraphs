package grafos;

import Util.Matriz;
import interfaz.MenuPrincipal;
import interfaz.matricesAdyacencia.MatrizTabla;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Stack;
import java.util.Vector;

public class Grafo {
  protected Vector nodos;
  
  protected Vector aristas;
  
  private int tipoGrafo;
  
  private GrafoMultiColor grafoFlujoMax;
  
  public Grafo() {
    this.nodos = new Vector(5, 5);
    this.aristas = new Vector(10, 5);
    this.tipoGrafo = 0;
  }
  
  public Grafo(int K, int peso) {
    int holgura;
    this.tipoGrafo = 0;
    int diametro = K * 20;
    if (diametro > 300)
      diametro = 300; 
    this.nodos = new Vector(10, 1);
    if (K > 34) {
      holgura = K * 8;
    } else if (K > 26) {
      holgura = K * 12;
    } else if (K > 20) {
      holgura = K * 16;
    } else if (K > 11) {
      holgura = K * 20;
    } else if (K > 8) {
      holgura = 250;
    } else {
      holgura = 175;
    } 
    int i;
    for (i = 0; i < K; i++) {
      int x = (int)(Math.sin(6.283185307179586D * i / K) * diametro) + holgura;
      int y = (int)(Math.cos(6.283185307179586D * i / K) * diametro) + holgura;
      insertarNodo(new Point(x, y));
    } 
    this.aristas = new Vector(15, 1);
    for (i = 0; i < K; i++) {
      for (int j = i + 1; j < K; j++) {
        Arista arista1 = new Arista(i, j, 0);
        arista1.setPeso(peso);
        this.aristas.add(arista1);
      } 
    } 
  }
  
  public void setGrafoFlujoMax(GrafoMultiColor grafoFlujoMax) {
    this.grafoFlujoMax = grafoFlujoMax;
  }
  
  public GrafoMultiColor getGrafoFlujoMax() {
    return this.grafoFlujoMax;
  }
  
  public int getTipo() {
    return this.tipoGrafo;
  }
  
  public void setTipo(int t) {
    this.tipoGrafo = t;
  }
  
  public Vector getAristas() {
    return this.aristas;
  }
  
  public void setAristas(Vector aristasP) {
    this.aristas = aristasP;
  }
  
  public void resetAristas() {
    this.aristas = new Vector(10, 5);
  }
  
  public Vector getNodos() {
    return this.nodos;
  }
  
  public Grafo clonar() {
    Grafo grf_clon = new Grafo();
    grf_clon.tipoGrafo = this.tipoGrafo;
    grf_clon.nodos = (Vector)this.nodos.clone();
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      Arista a_clonada = new Arista(a.getCabeza(), a.getCola(), a.getTipo());
      a_clonada.setPeso(a.getPeso());
      a_clonada.setMultiplicidad(a.getMultiplicidad());
      grf_clon.aristas.add(a_clonada);
    } 
    return grf_clon;
  }
  
  public int getPosicionNodoByNombre(String name) {
    for (int i = 0; i < this.nodos.size(); i++) {
      Nodo n = this.nodos.get(i);
      if (n.getNombre().matches(name))
        return i; 
    } 
    return -1;
  }
  
  public Nodo getNodoByNombre(String name) {
    for (int i = 0; i < this.nodos.size(); i++) {
      Nodo n = this.nodos.get(i);
      if (n.getNombre().matches(name))
        return n; 
    } 
    return null;
  }
  
  public Nodo getNodoByIndex(int index) {
    if (index >= 0 && index < this.nodos.size()) {
      Nodo n = this.nodos.get(index);
      return n;
    } 
    return null;
  }
  
  public Arista getAristaByIndex(int index) {
    if (index >= 0 && index < this.aristas.size()) {
      Arista n = this.aristas.get(index);
      return n;
    } 
    return null;
  }
  
  public Arista getAristaByNodosIndex(int indexN1, int indexN2) {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if ((a.getCabeza() == indexN1 && a.getCola() == indexN2) || (a.getCabeza() == indexN2 && a.getCola() == indexN1))
        return a; 
    } 
    return null;
  }
  
  public Arista getArcoByNodosIndex(int indexN1, int indexN2) {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if (a.getCabeza() == indexN1 && a.getCola() == indexN2)
        return a; 
    } 
    return null;
  }
  
  public boolean cambiarPesoArista(int cabeza, int cola, double peso) {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if ((((a.getCabeza() == cabeza) ? 1 : 0) & ((a.getCola() == cola) ? 1 : 0) | ((a.getCabeza() == cola) ? 1 : 0) & ((a.getCola() == cabeza) ? 1 : 0)) != 0) {
        a.setPeso(peso);
        this.aristas.set(i, a);
        return true;
      } 
    } 
    return false;
  }
  
  public void pintarGrafo(Graphics g) {
    Arista arista = null;
    if (this != null) {
      for (int i = 0; i < this.nodos.size(); i++) {
        Nodo nodo = this.nodos.get(i);
        nodo.pintarNodo(g, Color.BLACK);
      } 
      for (int j = 0; j < this.aristas.size(); j++) {
        arista = this.aristas.get(j);
        if (arista != null) {
          Nodo nodoCabeza = this.nodos.get(arista.getCabeza());
          Nodo nodoCola = this.nodos.get(arista.getCola());
          arista.pintarArista(g, nodoCabeza.getPos(), nodoCola.getPos(), arista.getTipo(), false, Color.BLACK);
        } 
      } 
    } 
  }
  
  public void pintarNodos(Graphics g, Vector<Integer> indices, Color color) {
    for (int i = 0; i < indices.size(); i++) {
      int indice = ((Integer)indices.get(i)).intValue();
      Nodo nodo = this.nodos.get(indice);
      nodo.pintarNodoRelleno(g, color);
    } 
  }
  
  public void insertarNodo(Point v_punto) {
    int tamanyo = this.nodos.size() + 1;
    String nombre = "v" + tamanyo;
    Nodo n = getNodoByNombre(nombre);
    int i = this.nodos.size() - 1;
    while (n != null) {
      nombre = "v" + i;
      n = getNodoByNombre(nombre);
      i--;
    } 
    this.nodos.add(new Nodo(nombre, v_punto));
  }
  
  public void insertarNodo(Nodo n) {
    this.nodos.add(n);
  }
  
  public void insertarNodo(Nodo n, int indice) {
    this.nodos.add(indice, n);
  }
  
  public void borrarNodoNombre(String nom) {
    for (int i = 0; i < this.nodos.size(); i++) {
      if (getNodoByIndex(i).getNombre().equalsIgnoreCase(nom)) {
        borrarNodoIndex(i);
        break;
      } 
    } 
  }
  
  public void borrarNodoIndex(int index) {
    if (index > -1 && index < this.nodos.size()) {
      this.nodos.remove(index);
      for (int j = 0; j < this.aristas.size(); j++) {
        Arista arista1 = this.aristas.get(j);
        if (arista1.getCabeza() == index || arista1.getCola() == index) {
          this.aristas.remove(j);
          j--;
        } else {
          if (arista1.getCabeza() > index)
            arista1.setCabeza(arista1.getCabeza() - 1); 
          if (arista1.getCola() > index)
            arista1.setCola(arista1.getCola() - 1); 
          this.aristas.set(j, arista1);
        } 
      } 
    } 
  }
  
  public boolean insertarArista(int nodo1, int nodo2) {
    if (!existeArista(nodo1, nodo2)) {
      this.aristas.add(new Arista(nodo1, nodo2, 0));
      return true;
    } 
    return false;
  }
  
  public void insertarArista(Arista a) {
    if (!existeArista(a))
      this.aristas.add(a); 
  }
  
  public boolean insertarArco(int nodo1, int nodo2) {
    if (!existeArco(nodo1, nodo2)) {
      this.aristas.add(new Arista(nodo1, nodo2, 1));
      return true;
    } 
    return false;
  }
  
  public void insertarArco(Arista a) {
    if (!existeArco(a))
      this.aristas.add(a); 
  }
  
  public boolean existeArista(int cabeza, int cola) {
    boolean existeArista = false;
    for (int k = 0; k < this.aristas.size(); k++) {
      Arista arista = this.aristas.get(k);
      if ((arista.getCabeza() == cabeza && arista.getCola() == cola) || (arista.getCabeza() == cola && arista.getCola() == cabeza))
        existeArista = true; 
    } 
    return existeArista;
  }
  
  public boolean existeArista(Arista a) {
    boolean existeArista = false;
    for (int k = 0; k < this.aristas.size(); k++) {
      Arista arista = this.aristas.get(k);
      if ((arista.getCabeza() == a.getCabeza() && arista.getCola() == a.getCola()) || (arista.getCabeza() == a.getCola() && arista.getCola() == a.getCabeza()))
        existeArista = true; 
    } 
    return existeArista;
  }
  
  public boolean existeArco(int cabeza, int cola) {
    boolean existeArco = false;
    for (int k = 0; k < this.aristas.size(); k++) {
      Arista arista = this.aristas.get(k);
      if (arista.getCabeza() == cabeza && arista.getCola() == cola)
        existeArco = true; 
    } 
    return existeArco;
  }
  
  public boolean existeArco(Arista a) {
    boolean existeArco = false;
    for (int k = 0; k < this.aristas.size(); k++) {
      Arista arista = this.aristas.get(k);
      if (arista.getCabeza() == a.getCabeza() && arista.getCola() == a.getCola())
        existeArco = true; 
    } 
    return existeArco;
  }
  
  public boolean aumentarArista(int indexN1, int indexN2) {
    Arista a = null;
    int index;
    for (index = 0; index < this.aristas.size(); index++) {
      a = this.aristas.get(index);
      if ((((a.getCabeza() == indexN1) ? 1 : 0) & ((a.getCola() == indexN2) ? 1 : 0) | ((a.getCabeza() == indexN2) ? 1 : 0) & ((a.getCola() == indexN1) ? 1 : 0)) != 0)
        break; 
    } 
    if (index < this.aristas.size()) {
      a.incrementarMultiplicidad();
      this.aristas.set(index, a);
      return true;
    } 
    return false;
  }
  
  public void borrarArcobyNodosIndex(int indexN1, int indexN2) {
    Arista a = null;
    int index;
    for (index = 0; index < this.aristas.size(); index++) {
      a = this.aristas.get(index);
      if ((((a.getCabeza() == indexN1) ? 1 : 0) & ((a.getCola() == indexN2) ? 1 : 0)) != 0)
        break; 
    } 
    if (index < this.aristas.size())
      if (a.getMultiplicidad() == 1) {
        this.aristas.remove(index);
      } else {
        a.decrementarMultiplicidad();
        this.aristas.set(index, a);
      }  
  }
  
  public void borrarAristabyNodosIndex(int indexN1, int indexN2) {
    Arista a = null;
    int index;
    for (index = 0; index < this.aristas.size(); index++) {
      a = this.aristas.get(index);
      if ((((a.getCabeza() == indexN1) ? 1 : 0) & ((a.getCola() == indexN2) ? 1 : 0) | ((a.getCabeza() == indexN2) ? 1 : 0) & ((a.getCola() == indexN1) ? 1 : 0)) != 0)
        break; 
    } 
    if (index < this.aristas.size())
      if (a.getMultiplicidad() == 1) {
        this.aristas.remove(index);
      } else {
        a.decrementarMultiplicidad();
        this.aristas.set(index, a);
      }  
  }
  
  public void borrarAristaIndex(int index) {
    if (index != -1 && index < this.aristas.size()) {
      Arista arista = this.aristas.get(index);
      if (arista.getMultiplicidad() == 1) {
        this.aristas.remove(index);
      } else {
        arista.decrementarMultiplicidad();
        this.aristas.set(index, arista);
      } 
    } 
  }
  
  private Vector nodosAdyacentes(Nodo n) {
    Vector<Nodo> ListaNodosAdyacentes = new Vector(5, 2);
    Arista a = null;
    Nodo n1 = null, n2 = null;
    for (int i = 0; i < this.aristas.size(); i++) {
      a = this.aristas.get(i);
      n1 = this.nodos.get(a.getCabeza());
      n2 = this.nodos.get(a.getCola());
      if (a.getTipo() == 0) {
        if (n1 == n) {
          ListaNodosAdyacentes.add(n2);
        } else if (n2 == n) {
          ListaNodosAdyacentes.add(n1);
        } 
      } else if (n1 == n) {
        ListaNodosAdyacentes.add(n2);
      } 
    } 
    return ListaNodosAdyacentes;
  }
  
  public Vector BFS(MenuPrincipal menuppal, Nodo n, boolean flujoMaximo) {
    Vector<Nodo> nodos_alcanzados = new Vector(5, 5);
    Vector<Nodo> adyacentes = null;
    Nodo nodo = null;
    GrafoMultiColor grafoPintar = new GrafoMultiColor();
    grafoPintar = grafoPintar.convertirAGrafoMultiColor(this);
    nodos_alcanzados.add(n);
    for (int i = 0; i < nodos_alcanzados.size(); i++) {
      adyacentes = nodosAdyacentes(nodos_alcanzados.get(i));
      for (int j = 0; j < adyacentes.size(); j++) {
        nodo = adyacentes.get(j);
        if (!nodos_alcanzados.contains(nodo)) {
          nodos_alcanzados.add(nodo);
          if (menuppal != null || flujoMaximo) {
            Nodo n1 = nodos_alcanzados.get(i);
            Arista a0 = null;
            Arista a1 = null;
            if (grafoPintar.getTipo() == 0) {
              a0 = getAristaByNodosIndex(getPosicionNodoByNombre(n1.getNombre()), getPosicionNodoByNombre(nodo.getNombre()));
              a1 = new Arista(getPosicionNodoByNombre(n1.getNombre()), getPosicionNodoByNombre(nodo.getNombre()), 0);
              if (a0 != null)
                a1.setPeso(a0.getPeso()); 
              if (grafoPintar.existeArista(a1))
                grafoPintar.deAzul.add(a1); 
            } else {
              a0 = getArcoByNodosIndex(getPosicionNodoByNombre(n1.getNombre()), getPosicionNodoByNombre(nodo.getNombre()));
              a1 = new Arista(getPosicionNodoByNombre(n1.getNombre()), getPosicionNodoByNombre(nodo.getNombre()), 1);
              if (a0 != null)
                a1.setPeso(a0.getPeso()); 
              if (grafoPintar.existeArco(a1))
                grafoPintar.deAzul.add(a1); 
            } 
          } 
        } 
      } 
    } 
    if (menuppal != null) {
      menuppal.panel.setGrafo(grafoPintar);
      menuppal.redibujarGrafo();
    } 
    if (flujoMaximo)
      this.grafoFlujoMax = grafoPintar.clonar(); 
    return nodos_alcanzados;
  }
  
  public Grafo DFS(MenuPrincipal menuppal, Nodo n, boolean orientar, Vector<Nodo> ListaNodosAdyacentes) {
    int cima = 0;
    boolean existeW2 = false;
    GrafoMultiColor grafoPintar = new GrafoMultiColor();
    grafoPintar = grafoPintar.convertirAGrafoMultiColor(this);
    Vector<Nodo> L = new Vector<Nodo>();
    Vector<Arista> A = new Vector<Arista>();
    Stack<Nodo> P = new Stack<Nodo>();
    L.add(n);
    P.push(n);
    while (!P.isEmpty()) {
      Nodo w1 = P.peek();
      for (int i = 0; i < getNodos().size(); i++) {
        if (w1 == getNodoByIndex(i))
          cima = i; 
      } 
      existeW2 = false;
      for (int j = 0; j < getAristas().size(); j++) {
        int cola = -1;
        Nodo w2 = null;
        boolean existeCima = false;
        boolean contenidoL = false;
        if (getTipo() == 0) {
          existeCima = !(cima != getAristaByIndex(j).getCabeza() && cima != getAristaByIndex(j).getCola());
          cola = getAristaByIndex(j).getCola();
          w2 = getNodoByIndex(cola);
          contenidoL = !L.contains(w2);
          if (!contenidoL) {
            cola = getAristaByIndex(j).getCabeza();
            w2 = getNodoByIndex(cola);
            contenidoL = !L.contains(w2);
          } 
        } else {
          existeCima = (cima == getAristaByIndex(j).getCabeza());
          cola = getAristaByIndex(j).getCola();
          w2 = getNodoByIndex(cola);
          contenidoL = !L.contains(w2);
        } 
        if (!existeW2 && existeCima && contenidoL) {
          P.push(w2);
          L.add(w2);
          Arista a = new Arista(cima, cola, getAristaByIndex(j).getTipo());
          A.add(a);
          if (getTipo() == 0) {
            if (grafoPintar.existeArista(getAristaByIndex(j)))
              grafoPintar.deAzul.add(getAristaByIndex(j)); 
          } else if (grafoPintar.existeArco(getAristaByIndex(j))) {
            grafoPintar.deAzul.add(getAristaByIndex(j));
          } 
          existeW2 = true;
        } 
      } 
      if (!existeW2 && !P.isEmpty())
        P.pop(); 
    } 
    if (menuppal != null) {
      menuppal.panel.setGrafo(grafoPintar);
      menuppal.redibujarGrafo();
    } 
    Grafo G = new Grafo();
    G.nodos = L;
    if (orientar) {
      for (int i = 0; i < G.getNodos().size(); i++) {
        Nodo n13 = G.getNodoByIndex(i);
        ListaNodosAdyacentes.add(n13);
      } 
      return grafoPintar;
    } 
    return G;
  }
  
  public boolean esConexo() {
    Nodo n = null;
    if (this.nodos.size() > 0) {
      n = this.nodos.get(0);
      if (BFS(null, n, false).size() == this.nodos.size())
        return true; 
    } 
    return false;
  }
  
  public boolean esFuertementeConexo() {
    for (int i = 0; i < this.nodos.size(); i++) {
      if (BFS(null, this.nodos.get(i), false).size() != this.nodos.size())
        return false; 
    } 
    return true;
  }
  
  public boolean esDebilmenteConexo() {
    Grafo g = obtenerGrafoSubyacente(this);
    return g.esConexo();
  }
  
  public Grafo obtenerGrafoSubyacente(Grafo g) {
    Grafo resultado = clonar();
    resultado.resetAristas();
    resultado.setTipo(0);
    MatrizTabla matriz = new MatrizTabla(this);
    for (int i = 0; i < getNodos().size(); i++) {
      for (int j = 0; j < getNodos().size(); j++) {
        Double peso = (Double)matriz.getValueAt(i, j);
        if (peso.doubleValue() != 0.0D) {
          Arista a = new Arista(i, j, 0);
          a.setPeso(peso.doubleValue());
          resultado.insertarArista(a);
        } 
      } 
    } 
    resultado.convertirAGrafoNOdirigido();
    return resultado;
  }
  
  public int cardComponentesConexas() {
    int componentes = 0;
    Grafo aux = clonar();
    while (getNodos().size() != 0) {
      componentes++;
      Vector<Nodo> nodos_componente = BFS(null, this.nodos.get(0), false);
      for (int i = 0; i < nodos_componente.size(); i++) {
        Nodo n = nodos_componente.get(i);
        borrarNodoNombre(n.getNombre());
      } 
    } 
    this.aristas = aux.aristas;
    this.nodos = aux.nodos;
    return componentes;
  }
  
  public int cardComponentesFuerteConexas() {
    int n = getNodos().size();
    Matriz m = new Matriz(n, n);
    Vector<Nodo> v = null;
    Nodo n1 = null;
    Nodo n2 = null;
    int indexNodo = -1;
    int i;
    for (i = 0; i < n; i++) {
      for (int k = 0; k < n; k++) {
        if (i == k) {
          m.A[i][k] = 1.0D;
        } else {
          m.A[i][k] = 0.0D;
        } 
      } 
    } 
    for (i = 0; i < n; i++) {
      n1 = this.nodos.get(i);
      v = BFS(null, n1, false);
      for (int k = 0; k < v.size(); k++) {
        n2 = v.get(k);
        indexNodo = getPosicionNodoByNombre(n2.getNombre());
        m.A[i][indexNodo] = 1.0D;
      } 
    } 
    Matriz t = m.traspuesta();
    Matriz p = m.productoElementoAElemento(t);
    Vector<Double> aux1 = new Vector(n);
    Vector<Double> aux2 = new Vector(n);
    int componentes = 0;
    for (int j = 0; j < n - 1; j++) {
      aux1.clear();
      for (int k = 0; k < getNodos().size(); k++)
        aux1.add(Double.valueOf(p.A[j][k])); 
      for (int i1 = j + 1; i1 < n; i1++) {
        aux2.clear();
        for (int i2 = 0; i2 < getNodos().size(); i2++)
          aux2.add(Double.valueOf(p.A[i1][i2])); 
        if (aux2.equals(aux1)) {
          p = p.eliminarFila(i1);
          n--;
          j = -1;
        } 
      } 
    } 
    componentes = n;
    return componentes;
  }
  
  public Vector indexNodosAdyacentes(int index) {
    Vector<Integer> listaNodosAdyacentes = new Vector(5, 2);
    Arista a = null;
    for (int i = 0; i < this.aristas.size(); i++) {
      a = this.aristas.get(i);
      if (getTipo() == 0) {
        if (a.getCabeza() == index) {
          listaNodosAdyacentes.add(Integer.valueOf(a.getCola()));
        } else if (a.getCola() == index) {
          listaNodosAdyacentes.add(Integer.valueOf(a.getCabeza()));
        } 
      } else if (a.getCabeza() == index) {
        listaNodosAdyacentes.add(Integer.valueOf(a.getCola()));
      } 
    } 
    return listaNodosAdyacentes;
  }
  
  public DijkstraObject dijkstra(int raiz) {
    DijkstraObject djkObjeto = new DijkstraObject(raiz, getNodos().size());
    Vector<Integer> nodosPendientes = new Vector(this.nodos.size());
    for (int i = 0; i < this.nodos.size(); i++)
      nodosPendientes.add(new Integer(i)); 
    while (!nodosPendientes.isEmpty()) {
      int indiceATratar = djkObjeto.siguienteNodo(nodosPendientes);
      if (indiceATratar < 0)
        break; 
      nodosPendientes.remove(new Integer(indiceATratar));
      Vector<Integer> adyacentes = indexNodosAdyacentes(indiceATratar);
      for (int j = 0; j < adyacentes.size(); j++) {
        if (nodosPendientes.contains(adyacentes.get(j))) {
          int adyacente = ((Integer)adyacentes.get(j)).intValue();
          Arista a = null;
          if (getTipo() == 0) {
            a = getAristaByNodosIndex(indiceATratar, adyacente);
          } else {
            a = getArcoByNodosIndex(indiceATratar, adyacente);
          } 
          djkObjeto.intentarMejora(a.getPeso(), indiceATratar, adyacente);
        } 
      } 
    } 
    return djkObjeto;
  }
  
  public DijkstraObject dijkstra(int raiz, int destino) {
    DijkstraObject djkObjeto = new DijkstraObject(raiz, getNodos().size());
    Vector<Integer> nodosPendientes = new Vector(this.nodos.size());
    for (int i = 0; i < this.nodos.size(); i++)
      nodosPendientes.add(new Integer(i)); 
    while (nodosPendientes.contains(new Integer(destino))) {
      int indiceATratar = djkObjeto.siguienteNodo(nodosPendientes);
      if (indiceATratar < 0)
        break; 
      nodosPendientes.remove(new Integer(indiceATratar));
      Vector<Integer> adyacentes = indexNodosAdyacentes(indiceATratar);
      for (int j = 0; j < adyacentes.size(); j++) {
        if (nodosPendientes.contains(adyacentes.get(j))) {
          int adyacente = ((Integer)adyacentes.get(j)).intValue();
          Arista a = getAristaByNodosIndex(indiceATratar, adyacente);
          djkObjeto.intentarMejora(a.getPeso(), indiceATratar, adyacente);
        } 
      } 
    } 
    return djkObjeto;
  }
  
  public boolean cambiarNombreIndex(int index, String NewName) {
    if (index >= this.nodos.size())
      return false; 
    for (int i = 0; i < this.nodos.size(); i++) {
      if (i != index) {
        Nodo n = this.nodos.get(i);
        if (n.getNombre().matches(NewName))
          return false; 
      } 
    } 
    Nodo n1 = this.nodos.get(index);
    n1.setNombre(NewName);
    this.nodos.set(index, n1);
    return true;
  }
  
  public boolean cambiarPosicionIndex(int index, Point NewPos) {
    if (index >= this.nodos.size())
      return false; 
    for (int i = 0; i < this.nodos.size(); i++) {
      if (i != index) {
        Nodo n = getNodoByIndex(i);
        if (n.getPos().distance(NewPos) < 15.0D)
          return false; 
      } 
    } 
    Nodo n1 = this.nodos.get(index);
    n1.setPos(NewPos);
    this.nodos.set(index, n1);
    return true;
  }
  
  public GrafoEdmonds convertirAEdmonds() {
    GrafoEdmonds G = new GrafoEdmonds();
    G.aristas = this.aristas;
    for (int i = 0; i < this.nodos.size(); i++) {
      Nodo n = getNodoByIndex(i);
      NodoEdmonds nEdmonds = new NodoEdmonds(n.getNombre(), n.getPos());
      G.nodos.add(nEdmonds);
    } 
    G.setPrimeraFloracion(G.nodos.size());
    return G;
  }
  
  public int getGradoIndex(int index) {
    int grado = 0;
    for (int j = 0; j < this.aristas.size(); j++) {
      Arista arista = this.aristas.get(j);
      if ((((arista.getCabeza() == index) ? 1 : 0) | ((arista.getCola() == index) ? 1 : 0)) != 0) {
        grado += arista.getMultiplicidad();
        if (arista.getCabeza() == index && arista.getCola() == index)
          grado += arista.getMultiplicidad(); 
      } 
    } 
    return grado;
  }
  
  public int getGradoEntradaIndex(int index) {
    int grado = 0;
    for (int j = 0; j < this.aristas.size(); j++) {
      Arista arista = this.aristas.get(j);
      if (arista.getCola() == index)
        grado += arista.getMultiplicidad(); 
    } 
    return grado;
  }
  
  public int getGradoSalidaIndex(int index) {
    int grado = 0;
    for (int j = 0; j < this.aristas.size(); j++) {
      Arista arista = this.aristas.get(j);
      if (arista.getCabeza() == index)
        grado += arista.getMultiplicidad(); 
    } 
    return grado;
  }
  
  public void invertirPesosAristas() {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = getAristaByIndex(i);
      a.setPeso(-a.getPeso());
      this.aristas.set(i, a);
    } 
  }
  
  public String toMathematica() {
    if (this.nodos.size() == 0)
      return ""; 
    String str = "Grafo[{";
    for (int i = 0; i < this.nodos.size() - 1; i++) {
      str = String.valueOf(str) + "{";
      for (int k = 0; k < this.nodos.size() - 1; k++) {
        Arista arista1 = getAristaByNodosIndex(i, k);
        if (arista1 == null) {
          str = String.valueOf(str) + "0, ";
        } else {
          str = String.valueOf(str) + arista1.getPeso() + ", ";
        } 
      } 
      Arista arista = getAristaByNodosIndex(i, this.nodos.size() - 1);
      if (arista == null) {
        str = String.valueOf(str) + "0";
      } else {
        str = String.valueOf(str) + arista.getPeso();
      } 
      str = String.valueOf(str) + "}, ";
    } 
    str = String.valueOf(str) + "{";
    for (int j = 0; j < this.nodos.size() - 1; j++) {
      Arista arista = getAristaByNodosIndex(this.nodos.size() - 1, j);
      if (arista == null) {
        str = String.valueOf(str) + "0, ";
      } else {
        str = String.valueOf(str) + arista.getPeso() + ", ";
      } 
    } 
    Arista a = getAristaByNodosIndex(this.nodos.size() - 1, this.nodos.size() - 1);
    if (a == null) {
      str = String.valueOf(str) + "0";
    } else {
      str = String.valueOf(str) + a.getPeso();
    } 
    str = String.valueOf(str) + "} ";
    str = String.valueOf(str) + "}, VerticesDefecto[" + this.nodos.size() + "]]";
    return str;
  }
  
  public String toXMLString() {
    if (this.tipoGrafo == 0)
      convertirAGrafoNOdirigido(); 
    String str = "<grafo>\n<tipoGrafo> \"" + getTipo() + "\" </tipoGrafo>\n<nodos>";
    int i;
    for (i = 0; i < getNodos().size(); i++)
      str = String.valueOf(str) + "<nodo>\n<nombre> \"" + 
        getNodoByIndex(i).getNombre() + "\" </nombre>\n<posicion> (" + 
        (getNodoByIndex(i).getPos()).x + "," + (getNodoByIndex(i).getPos()).y + ") </posicion>" + 
        "\n</nodo>\n"; 
    str = String.valueOf(str) + "</nodos>\n<aristas>";
    for (i = 0; i < getAristas().size(); i++)
      str = String.valueOf(str) + "<arista>\n<nodo1> \"" + getAristaByIndex(i).getCabeza() + "\" </nodo1>\n<nodo2> \"" + 
        getAristaByIndex(i).getCola() + "\" </nodo2>\n<peso> \"" + 
        
        getAristaByIndex(i).getPeso() + "\" </peso>\n<tipo> \"" + 
        getAristaByIndex(i).getTipo() + "\" </tipo>\n</arista>\n"; 
    str = String.valueOf(str) + "</aristas> \n </grafo>";
    return str;
  }
  
  public boolean hayBucles() {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if (a.getCabeza() == a.getCola())
        return true; 
    } 
    return false;
  }
  
  public boolean hayArcos() {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if (a.getTipo() == 1)
        return true; 
    } 
    return false;
  }
  
  public boolean hayAristas() {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if (a.getTipo() == 0)
        return true; 
    } 
    return false;
  }
  
  public boolean hayAristasDeCorte() {
    Grafo aux = clonar();
    int comp = cardComponentesConexas();
    for (int i = 0; i < this.aristas.size(); i++) {
      aux.borrarAristaIndex(i);
      if (comp < aux.cardComponentesConexas())
        return true; 
      aux = clonar();
    } 
    return false;
  }
  
  public boolean hayVerticesDeCorte() {
    Grafo aux = clonar();
    int comp = cardComponentesConexas();
    for (int i = 0; i < this.nodos.size(); i++) {
      aux.borrarNodoIndex(i);
      if (comp < aux.cardComponentesConexas())
        return true; 
      aux = clonar();
    } 
    return false;
  }
  
  public boolean existenPesosNegativos() {
    for (int i = 0; i < this.aristas.size(); i++) {
      Arista a = this.aristas.get(i);
      if (a.getPeso() < 0.0D)
        return true; 
    } 
    return false;
  }
  
  private boolean todosLosNodosGradoPar() {
    Vector<Integer> nGradoPar = new Vector(2, 2);
    for (int i = 0; i < getNodos().size(); i++) {
      int grado = getGradoIndex(i);
      if (grado / 2.0F == Math.round((grado / 2)))
        nGradoPar.add(new Integer(i)); 
    } 
    return (nGradoPar.size() == getNodos().size());
  }
  
  private boolean todosLosNodosMismoGradoEntradaSalida() {
    for (int j = 0; j < this.nodos.size(); j++) {
      if (getGradoEntradaIndex(j) != getGradoSalidaIndex(j))
        return false; 
    } 
    return true;
  }
  
  public boolean esEuleriano() {
    if (getTipo() == 0 && esConexo() && todosLosNodosGradoPar())
      return true; 
    if (getTipo() == 1 && esDebilmenteConexo() && todosLosNodosMismoGradoEntradaSalida())
      return true; 
    return false;
  }
  
  public void pasarDePesosAMultiplicidades() {
    for (int i = 0; i < getAristas().size(); i++) {
      Arista a = getAristaByIndex(i);
      int pesoArista = (int)a.getPeso();
      if (pesoArista > 1)
        a.setMultiplicidad(pesoArista); 
    } 
  }
  
  public void pasarDeMultiplicidadesAPesos() {
    for (int i = 0; i < getAristas().size(); i++) {
      Arista a = getAristaByIndex(i);
      int multiplicidadArista = a.getMultiplicidad();
      if (multiplicidadArista > 1) {
        a.setPeso(multiplicidadArista);
        a.setMultiplicidad(1);
      } 
    } 
  }
  
  public boolean existenPesosConDecimales() {
    double peso = 0.0D;
    for (int i = 0; i < getAristas().size(); i++) {
      Arista a = getAristaByIndex(i);
      peso = a.getPeso();
      if (peso - (int)peso != 0.0D)
        return true; 
    } 
    return false;
  }
  
  public boolean verificaDesigualdadTriangular() {
    double pesoA1 = 0.0D;
    double pesoA2 = 0.0D;
    double pesoA3 = 0.0D;
    Arista a1 = null;
    Arista a2 = null;
    Arista a3 = null;
    for (int i = 0; i < this.aristas.size(); i++) {
      a1 = getAristaByIndex(i);
      pesoA1 = a1.getPeso();
      for (int j = 0; j < this.nodos.size(); j++) {
        a2 = getAristaByNodosIndex(a1.getCabeza(), j);
        a3 = getAristaByNodosIndex(a1.getCola(), j);
        if (a2 != null && a3 != null) {
          pesoA2 = a2.getPeso();
          pesoA3 = a3.getPeso();
          if (pesoA1 > pesoA2 + pesoA3)
            return false; 
        } 
      } 
    } 
    return true;
  }
  
  public boolean existeVerticeGradoUno() {
    for (int i = 0; i < this.nodos.size(); i++) {
      if (getGradoIndex(i) == 1)
        return true; 
    } 
    return false;
  }
  
  public void convertirAGrafoNOdirigido() {
    setTipo(0);
    MatrizTabla matriz = new MatrizTabla(this);
    resetAristas();
    for (int i = 0; i < getNodos().size(); i++) {
      for (int j = i; j < getNodos().size(); j++) {
        Double peso = (Double)matriz.getValueAt(i, j);
        if (peso.doubleValue() != 0.0D) {
          Arista a = new Arista(i, j, 0);
          a.setPeso(peso.doubleValue());
          insertarArista(a);
        } 
      } 
    } 
  }
  
  public boolean esMatrizSimetrica() {
    MatrizTabla matriz = new MatrizTabla(this);
    for (int i = 0; i < getNodos().size(); i++) {
      for (int j = i; j < getNodos().size(); j++) {
        Double peso1 = (Double)matriz.getValueAt(i, j);
        Double peso2 = (Double)matriz.getValueAt(j, i);
        if (peso1.doubleValue() != peso2.doubleValue())
          return false; 
      } 
    } 
    return true;
  }
  
  public void convertirAGrafoDirigido() {
    setTipo(1);
    for (int i = 0; i < getAristas().size(); i++) {
      Arista a = getAristaByIndex(i);
      a.setTipo(1);
      Arista a2 = new Arista(a.getCola(), a.getCabeza(), 1);
      a2.setPeso(a.getPeso());
      insertarArco(a2);
    } 
  }
  
  public void marcarTodosLosPesosAUno() {
    for (int i = 0; i < getAristas().size(); i++) {
      Arista a = getAristaByIndex(i);
      a.setPeso(1.0D);
    } 
  }
  
  public boolean esGrafoCompleto() {
    int numVertices = getNodos().size();
    int numAristasReales = getAristas().size();
    int numAristasTeoricas = numVertices * (numVertices - 1) / 2;
    return (numAristasTeoricas == numAristasReales);
  }
  
  public double obtenerPesoGrafo() {
    double pesoTotal = 0.0D;
    Arista a = null;
    for (int i = 0; i < this.aristas.size(); i++) {
      a = getAristaByIndex(i);
      pesoTotal += a.getPeso();
    } 
    return pesoTotal;
  }
}
