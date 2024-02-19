package algoritmos;

import grafos.ArbolAlternado;
import grafos.Arista;
import grafos.Floracion;
import grafos.Grafo;
import grafos.GrafoEdmonds;
import grafos.Nodo;
import grafos.NodoEdmonds;
import grafos.SubGrafoIgualdad;
import interfaz.PanelGrafos;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JTextArea;

public class Edmonds2 extends Algoritmo {
  private GrafoEdmonds grafoTrabajo;
  
  private SubGrafoIgualdad sGI;
  
  private ArbolAlternado arbol;
  
  private boolean minimizar;
  
  private boolean ajustarEtiquetado;
  
  private Vector indexAdyacentes = new Vector(3, 2);
  
  private int indexNodo = -1;
  
  private int contadorFloracion = 0;
  
  private double delta = 0.0D, delta3 = 0.0D;
  
  public Edmonds2(PanelGrafos p, JTextArea area) {
    this.panel = p;
    this.textoInformacion = area;
    this.minimizar = false;
    this.ajustarEtiquetado = false;
    this.grafoTrabajo = this.panel.getGrafo().convertirAEdmonds();
    this.grafoTrabajo.setNumNodosReales();
    this.grafoTrabajo.setNumAristasReales();
  }
  
  public void run() {
    while (!terminado() && !enSuspension()) {
      double delta1, delta2;
      int floracionMinimaI;
      NodoEdmonds n;
      switch (this.estado) {
        case 0:
          iniciar();
          if (this.minimizar) {
            this.grafoTrabajo.invertirPesosAristas();
            if (this.modo == Algoritmo.porPasos) {
              this.panel.setGrafo((Grafo)this.grafoTrabajo);
              this.panel.repaint();
              this.textoInformacion.setText("Dado que se va a minimizar se cambia el peso de todas las aristas del grafo, adjudicando como nuevo peso el opuesto del peso inicial");
              suspender();
            } 
          } 
          this.estado = 1;
          continue;
        case 1:
          if (this.grafoTrabajo.getNumNodosReales() / 2.0F != Math.round((this.grafoTrabajo.getNumNodosReales() / 2))) {
            this.grafoTrabajo.addNodoFicticio();
            if (this.modo == Algoritmo.porPasos) {
              this.panel.setGrafo((Grafo)this.grafoTrabajo);
              this.panel.repaint();
              this.textoInformacion.setText("El grafo tenía un número impar de nodos, por este motivo y para conseguir un acoplamiento perfecto se ha añadido un nodo ficticio. Este nodo se ha enlazado al resto de nodos con aristas de peso muy negativo");
              suspender();
            } 
          } else if (this.modo == Algoritmo.porPasos) {
            this.textoInformacion.setText("El grafo tiene un número par de nodos y no requiere de nodos ficticios para alcanzar un acoplamiento perfecto, se va a aplicar un etiquetado admisible al grafo");
            suspender();
          } 
          this.estado = 2;
          continue;
        case 2:
          this.grafoTrabajo.etiquetarAdmisible();
          if (this.modo == Algoritmo.porPasos) {
            if (this.ajustarEtiquetado)
              this.grafoTrabajo.etiquetarAdmisible(); 
            this.panel.setGrafo((Grafo)this.grafoTrabajo);
            this.panel.repaint();
            this.textoInformacion.setText("Se ha generado un etiquetado admisible para el grafo. A partir de este etiquetado se calculará el subgrafo igualdad asociado, formado por todos los vértices del grafo  y todas aquellas aristas cuyo peso sea igual a la suma de las etiquetas de los nodos sobre los que incide. En base a esto, ¿podrías hallar el subgrafo que se va a generar?");
            suspender();
          } else {
            this.grafoTrabajo.etiquetarAdmisible();
          } 
          this.estado = 3;
          continue;
        case 3:
          this.sGI = new SubGrafoIgualdad(this.grafoTrabajo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.sGI);
            this.textoInformacion.setText("Este es el subgrafo igualdad con el que empezamos a trabajar");
            this.panel.repaint();
            suspender();
          } 
          this.estado = 4;
          continue;
        case 4:
          if (this.sGI.getIndexNodoInsaturado() == -1) {
            this.estado = 23;
            continue;
          } 
          this.arbol = new ArbolAlternado(this.sGI);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.arbol);
            this.panel.repaint();
            this.textoInformacion.setText("Se ha tomado el nodo " + this.grafoTrabajo.getNodoByIndex(this.sGI.getIndexNodoInsaturado()).getNombre() + " del grafo." + 
                " Dicho nodo está insaturado y será utilizado como raíz del árbol alternado que se va a construir");
            suspender();
          } 
          this.estado = 5;
          continue;
        case 5:
          if (this.arbol.getUltimoERevisado() < this.arbol.getNumNodosE()) {
            this.estado = 6;
            if (this.modo == Algoritmo.porPasos) {
              this.panel.setGrafo((Grafo)this.arbol);
              this.panel.repaint();
              this.textoInformacion.setText("Se va a revisar la posibilidad de encontrar un camino M-Aumentable desde el nodo " + this.grafoTrabajo.getNodoByIndex(this.arbol.getIndexUltimoERevisado()).getNombre() + 
                  " del grafo, que está etiquetado E dentro del árbol");
              suspender();
            } 
            continue;
          } 
          this.estado = 14;
          if (this.modo == Algoritmo.porPasos) {
            this.textoInformacion.setText("Ya no queda ningun nodo etiquetado E por examinar, hemos llegado a una situación de árbol húngaro y se va a realizar un cambio de etiquetado");
            suspender();
          } 
          continue;
        case 6:
          this.indexAdyacentes = this.sGI.indexNodosAdyacentes(this.arbol.getIndexUltimoERevisado());
          this.estado = 7;
          continue;
        case 7:
          if (this.indexAdyacentes.size() > 0) {
            this.indexNodo = ((Integer)this.indexAdyacentes.get(0)).intValue();
            this.indexAdyacentes.remove(0);
            this.indexNodo = this.sGI.indexRealNodo(this.indexNodo);
            this.estado = 8;
            continue;
          } 
          if (this.modo == Algoritmo.porPasos) {
            this.textoInformacion.setText("Desde el nodo " + this.grafoTrabajo.getNodoByIndex(this.arbol.getIndexUltimoERevisado()).getNombre() + " ya no es posible hacer crecer más el árbol." + 
                " Tampoco hay adyacentes para perfeccionar el acoplamiento." + 
                " Se intentará realizar alguna de estas operaciones desde otro nodo etiquetado E que no haya sido revisado aún");
            suspender();
          } 
          this.arbol.aumentarERevisado();
          this.estado = 5;
          continue;
        case 8:
          if (this.sGI.esSaturadoIndex(this.indexNodo)) {
            this.estado = 11;
            continue;
          } 
          this.estado = 9;
          continue;
        case 9:
          this.arbol.insertarArista(this.sGI.getAristaByNodosIndex(this.arbol.getIndexUltimoERevisado(), this.indexNodo));
          this.arbol.addNodoI(this.indexNodo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.arbol);
            this.panel.repaint();
            this.textoInformacion.setText("Se ha encontrado un camino aumentable con inicio en la raíz del arbol y final en " + this.grafoTrabajo.getNodoByIndex(this.indexNodo).getNombre() + ". Se va a perfeccionar el acoplamiento");
            suspender();
          } 
          this.estado = 10;
          continue;
        case 10:
          perfeccionarAcoplamiento(this.indexNodo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.sGI);
            this.panel.repaint();
            this.textoInformacion.setText("Se ha perfeccionado el acoplamiento. Se muestra en el panel el subgrafo igualdad (que no ha variado). El árbol alternado actual se elimina. En caso de quedar nodos insaturados se comenzará a construir un nuevo árbol.");
            suspender();
          } 
          eliminarArbol();
          this.estado = 4;
          continue;
        case 11:
          if (!this.arbol.esNodoArbol(this.indexNodo)) {
            this.estado = 12;
            continue;
          } 
          if (this.arbol.esNodoE(this.indexNodo)) {
            this.estado = 13;
            continue;
          } 
          this.estado = 7;
          continue;
        case 12:
          extenderArbol(this.indexNodo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.arbol);
            this.panel.repaint();
            suspender();
          } 
          this.estado = 7;
          continue;
        case 13:
          contraerFloracion(this.indexNodo, this.arbol.getIndexUltimoERevisado());
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.sGI);
            this.panel.repaint();
            suspender();
            this.textoInformacion.setText("Se ha contraído un ciclo de longitud impar dando lugar a una floración");
          } 
          this.estado = 5;
          continue;
        case 14:
          delta1 = calcularDelta1();
          delta2 = calcularDelta2();
          this.delta3 = calcularDelta3();
          this.delta = Math.min(Math.min(delta1, delta2), this.delta3);
          this.estado = 15;
          continue;
        case 15:
          if (Double.isInfinite(this.delta)) {
            this.estado = 16;
            continue;
          } 
          this.estado = 18;
          continue;
        case 16:
          if (this.sGI.getIndexNodoInsaturado() == -1) {
            this.estado = 25;
            continue;
          } 
          this.estado = 17;
          continue;
        case 17:
          this.grafoTrabajo.completarConAristasFicticias();
          this.sGI = new SubGrafoIgualdad(this.grafoTrabajo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.grafoTrabajo);
            this.panel.repaint();
            suspender();
            this.textoInformacion.setText("El parámetro delta ha tomado valor infinito, es obligado por tanto introducir aristas ficticias para conseguir un acoplamiento perfecto. Se va a recalcular el valor de delta una vez introducidas las aristas ficticias");
          } 
          this.estado = 14;
          continue;
        case 18:
          if (this.delta == this.delta3) {
            this.estado = 19;
            continue;
          } 
          this.estado = 21;
          continue;
        case 19:
          n = null;
          for (floracionMinimaI = this.grafoTrabajo.getPrimeraFloracion(); floracionMinimaI < this.grafoTrabajo.getNodos().size(); floracionMinimaI++) {
            n = (NodoEdmonds)this.grafoTrabajo.getNodoByIndex(floracionMinimaI);
            if ((((n.getEtiquetaArborea() == "I") ? 1 : 0) & ((n.getFloracion() == -1) ? 1 : 0) & ((n.getEtiqueta() == this.delta * 2.0D) ? 1 : 0)) != 0) {
              cambiarEtiquetado(this.delta);
              expandirFloracion(floracionMinimaI);
              this.grafoTrabajo.borrarFloracionIndex(floracionMinimaI);
              break;
            } 
          } 
          if (this.modo == Algoritmo.porPasos) {
            this.textoInformacion.setText("El árbol era húngaro, se ha cambiado el etiquetado con delta = " + this.delta + "." + 
                " A continuación se ha expandido la floración " + n.getNombre() + " que estaba etiquetada como nodo I y dió lugar a este valor");
            this.panel.setGrafo((Grafo)this.sGI);
            this.panel.repaint();
            suspender();
          } 
          eliminarArbol();
          this.estado = 20;
          continue;
        case 20:
          this.sGI = new SubGrafoIgualdad(this.grafoTrabajo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.sGI);
            this.panel.repaint();
            this.textoInformacion.setText("Se ha obtenido este subgrafo igualdad después del cambio de etiquetado");
            suspender();
          } 
          this.estado = 4;
          continue;
        case 21:
          cambiarEtiquetado(this.delta);
          if (this.modo == Algoritmo.porPasos) {
            if (this.grafoTrabajo.getAristas().size() < 300) {
              this.panel.setGrafo((Grafo)this.grafoTrabajo);
              this.panel.repaint();
            } 
            this.textoInformacion.setText("Se realiza un cambio de etiquetado con un valor de delta = " + this.delta + " y se conserva el árbol. El siguiente paso es derivar un nuevo subgrafo igualdad");
            suspender();
          } 
          this.estado = 22;
          continue;
        case 22:
          this.sGI = new SubGrafoIgualdad(this.grafoTrabajo);
          if (this.modo == Algoritmo.porPasos) {
            this.panel.setGrafo((Grafo)this.sGI);
            this.panel.repaint();
            this.textoInformacion.setText("Se ha obtenido este subgrafo igualdad después del cambio de etiquetado");
            suspender();
          } 
          this.arbol.setUltimoERevisado(0);
          this.estado = 5;
          continue;
        case 23:
          if (this.grafoTrabajo.getNodos().size() - 1 >= this.grafoTrabajo.getPrimeraFloracion()) {
            expandirFloracion(this.grafoTrabajo.getNodos().size() - 1);
            String nombre = this.grafoTrabajo.getNodoByIndex(this.grafoTrabajo.getNodos().size() - 1).getNombre();
            this.grafoTrabajo.borrarNodoIndex(this.grafoTrabajo.getNodos().size() - 1);
            if (this.modo == Algoritmo.porPasos) {
              this.textoInformacion.setText("Se están expandiendo las floraciones en orden inverso a como fueron creadas. Se muestra el estado del grafo tras expandir la floración " + nombre);
              this.panel.setGrafo((Grafo)this.sGI);
              this.panel.repaint();
              suspender();
            } 
            this.estado = 23;
            this.sGI = new SubGrafoIgualdad(this.grafoTrabajo);
            continue;
          } 
          this.estado = 24;
          continue;
        case 24:
          if (this.grafoTrabajo.getAristas().size() != this.grafoTrabajo.getNumAristasReales()) {
            this.grafoTrabajo.eliminarFiccion();
            if (this.modo == Algoritmo.porPasos) {
              this.textoInformacion.setText("Se han eliminado las aristas y el nodo ficticio que se introdujeron anteriormente");
              this.panel.setGrafo((Grafo)this.grafoTrabajo);
              this.panel.repaint();
              suspender();
            } 
          } 
          this.estado = 25;
          continue;
        case 25:
          if (this.minimizar)
            this.grafoTrabajo.invertirPesosAristas(); 
          this.panel.setGrafo((Grafo)this.grafoTrabajo);
          if (this.modo != Algoritmo.oculto) {
            this.panel.repaint();
            escribirResultadoFinal();
          } 
          terminar();
          continue;
      } 
      terminar();
    } 
    this.t = null;
  }
  
  public void setMinimizar(boolean min) {
    this.minimizar = min;
  }
  
  private void extenderArbol(int indexNodo) {
    Arista aristaAcopl = null;
    this.arbol.addNodoI(this.grafoTrabajo.indexRealNodo(indexNodo));
    Arista aristaInsaturada = this.sGI.getAristaByNodosIndex(this.arbol.getIndexUltimoERevisado(), indexNodo);
    for (int i = 0; i < this.grafoTrabajo.getAcoplamiento().size(); i++) {
      aristaAcopl = this.grafoTrabajo.getAcoplamiento().get(i);
      if ((((this.grafoTrabajo.indexRealNodo(aristaAcopl.getCabeza()) == indexNodo) ? 1 : 0) & ((this.grafoTrabajo.indexRealNodo(aristaAcopl.getCabeza()) != this.grafoTrabajo.indexRealNodo(aristaAcopl.getCola())) ? 1 : 0)) != 0) {
        this.arbol.addNodoE(this.grafoTrabajo.indexRealNodo(aristaAcopl.getCola()));
        this.arbol.getAcoplamiento().add(aristaAcopl);
        this.arbol.getAcoplamiento().add(aristaAcopl);
        break;
      } 
      if ((((this.grafoTrabajo.indexRealNodo(aristaAcopl.getCola()) == indexNodo) ? 1 : 0) & ((this.grafoTrabajo.indexRealNodo(aristaAcopl.getCabeza()) != this.grafoTrabajo.indexRealNodo(aristaAcopl.getCola())) ? 1 : 0)) != 0) {
        this.arbol.addNodoE(this.grafoTrabajo.indexRealNodo(aristaAcopl.getCabeza()));
        this.arbol.getAcoplamiento().add(aristaAcopl);
        this.arbol.getAristas().add(aristaAcopl);
        break;
      } 
    } 
    if (this.modo == Algoritmo.porPasos)
      this.textoInformacion.setText("Se ha extendido el arbol, añadiendo los nodos " + this.grafoTrabajo.getNodoByIndex(aristaAcopl.getCabeza()).getNombre() + 
          " y " + this.grafoTrabajo.getNodoByIndex(aristaAcopl.getCola()).getNombre() + ". Se muestra el estado actual del arbol"); 
    this.arbol.getAristas().add(aristaInsaturada);
  }
  
  private void perfeccionarAcoplamiento(int indexNodo) {
    Vector<Integer> caminoMAumentable = new Vector(2, 2);
    int indexNodoE = this.arbol.retrocesoDeIHastaE(indexNodo);
    caminoMAumentable.add(new Integer(indexNodo));
    caminoMAumentable.add(new Integer(indexNodoE));
    int indexNodoI = this.arbol.retrocesoDeEHastaI(indexNodoE);
    while (indexNodoI > -1) {
      indexNodoE = this.arbol.retrocesoDeIHastaE(indexNodoI);
      caminoMAumentable.add(new Integer(indexNodoI));
      caminoMAumentable.add(new Integer(indexNodoE));
      indexNodoI = this.arbol.retrocesoDeEHastaI(indexNodoE);
    } 
    for (int i = 1; i < caminoMAumentable.size(); i += 2) {
      int indexNodo1 = ((Integer)caminoMAumentable.get(i - 1)).intValue();
      int indexNodo2 = ((Integer)caminoMAumentable.get(i)).intValue();
      Arista a = this.sGI.getAristaByNodosIndex(indexNodo1, indexNodo2);
      this.sGI.getAcoplamiento().add(a);
    } 
    for (int j = 2; j < caminoMAumentable.size(); j += 2) {
      int indexNodo1 = ((Integer)caminoMAumentable.get(j - 1)).intValue();
      int indexNodo2 = ((Integer)caminoMAumentable.get(j)).intValue();
      this.sGI.quitarAristaDeAcoplamiento(indexNodo1, indexNodo2);
    } 
  }
  
  private void eliminarArbol() {
    for (int i = 0; i < this.sGI.getPrimeraFloracion(); i++) {
      NodoEdmonds n = this.sGI.getNodos().get(i);
      n.borrarEtiquetaArborea();
      this.sGI.getNodos().set(i, n);
    } 
    for (int j = this.sGI.getPrimeraFloracion(); j < this.sGI.getNodos().size(); j++) {
      Floracion f = this.sGI.getNodos().get(j);
      f.borrarEtiquetaArborea();
      this.sGI.getNodos().set(j, f);
    } 
    this.arbol = null;
  }
  
  private void contraerFloracion(int indexNodo, int indexNodo2) {
    Vector<Integer> cicloFloracion = new Vector(3, 2);
    cicloFloracion.add(new Integer(indexNodo));
    int indexNodoI = this.arbol.retrocesoDeEHastaI(indexNodo);
    while (indexNodoI > -1) {
      int m = this.arbol.retrocesoDeIHastaE(indexNodoI);
      cicloFloracion.add(new Integer(indexNodoI));
      cicloFloracion.add(new Integer(m));
      indexNodoI = this.arbol.retrocesoDeEHastaI(m);
    } 
    int indexNodoE = indexNodo2;
    while (!cicloFloracion.contains(new Integer(indexNodoE))) {
      indexNodoI = this.arbol.retrocesoDeEHastaI(indexNodoE);
      cicloFloracion.add(0, new Integer(indexNodoE));
      cicloFloracion.add(0, new Integer(indexNodoI));
      indexNodoE = this.arbol.retrocesoDeIHastaE(indexNodoI);
    } 
    int i = ((Integer)cicloFloracion.get(cicloFloracion.size() - 1)).intValue();
    while (i != indexNodoE) {
      cicloFloracion.remove(cicloFloracion.size() - 1);
      i = ((Integer)cicloFloracion.get(cicloFloracion.size() - 1)).intValue();
    } 
    for (int j = 0; j < cicloFloracion.size(); j++) {
      int indexNodoContraido = ((Integer)cicloFloracion.get(j)).intValue();
      if (indexNodoContraido < this.grafoTrabajo.getPrimeraFloracion()) {
        NodoEdmonds nodoContraido = (NodoEdmonds)this.grafoTrabajo.getNodoByIndex(indexNodoContraido);
        nodoContraido.setFloracion(this.grafoTrabajo.getNodos().size());
        this.grafoTrabajo.getNodos().set(indexNodoContraido, nodoContraido);
      } else {
        Floracion floracionContraida = (Floracion)this.grafoTrabajo.getNodoByIndex(indexNodoContraido);
        floracionContraida.setFloracion(this.grafoTrabajo.getNodos().size());
        this.grafoTrabajo.getNodos().set(indexNodoContraido, floracionContraida);
      } 
    } 
    Point posFloracion = ((NodoEdmonds)this.sGI.getNodoByIndex(indexNodoE)).getPos();
    this.contadorFloracion++;
    Floracion f = new Floracion("F" + this.contadorFloracion, posFloracion, cicloFloracion);
    this.grafoTrabajo.getNodos().add(f);
    Vector<Integer> nodosE = this.arbol.getE();
    for (int k = 0; k < nodosE.size(); k++) {
      Integer numNodo = nodosE.get(k);
      if (indexNodoE == numNodo.intValue()) {
        this.arbol.addNodoE(this.arbol.getNodos().size() - 1, k);
        this.arbol.setUltimoERevisado(k);
        break;
      } 
    } 
  }
  
  private void expandirFloracion(int indexFloracion) {
    Floracion f = (Floracion)this.grafoTrabajo.getNodoByIndex(indexFloracion);
    eliminarArbol();
    this.arbol = new ArbolAlternado(this.sGI, indexFloracion);
    cambiarEtiquetado(f.getEtiqueta() / 2.0D);
    eliminarArbol();
    int nodoEnlazadoAlExterior = -1;
    for (int i = 0; i < this.grafoTrabajo.getAcoplamiento().size(); i++) {
      Arista a = this.grafoTrabajo.getAcoplamiento().get(i);
      if ((((this.grafoTrabajo.indexRealNodo(a.getCabeza()) == indexFloracion) ? 1 : 0) & ((this.grafoTrabajo.indexRealNodo(a.getCabeza()) != this.grafoTrabajo.indexRealNodo(a.getCola())) ? 1 : 0)) != 0) {
        nodoEnlazadoAlExterior = a.getCabeza();
        break;
      } 
      if ((((this.grafoTrabajo.indexRealNodo(a.getCola()) == indexFloracion) ? 1 : 0) & ((this.grafoTrabajo.indexRealNodo(a.getCabeza()) != this.grafoTrabajo.indexRealNodo(a.getCola())) ? 1 : 0)) != 0) {
        nodoEnlazadoAlExterior = a.getCola();
        break;
      } 
    } 
    int posEnCiclo;
    for (posEnCiclo = 0; posEnCiclo < f.getCicloContraido().size(); posEnCiclo++) {
      int indexNodoContraido = ((Integer)f.getCicloContraido().get(posEnCiclo)).intValue();
      if ((indexNodoContraido < this.grafoTrabajo.getPrimeraFloracion()) ? (
        indexNodoContraido == nodoEnlazadoAlExterior) : 
        
        this.grafoTrabajo.nodoContenidoEnFloracion(indexNodoContraido, nodoEnlazadoAlExterior))
        break; 
    } 
    if (posEnCiclo > 0) {
      Vector<Integer> aux = new Vector(1, 1);
      for (int j = 0; j < posEnCiclo; j++) {
        aux.add(f.getCicloContraido().get(0));
        f.getCicloContraido().remove(0);
      } 
      f.getCicloContraido().addAll(aux);
    } 
    for (int k = 0; k < f.getCicloContraido().size(); k++) {
      int index = ((Integer)f.getCicloContraido().get(k)).intValue();
      if (index < this.grafoTrabajo.getPrimeraFloracion()) {
        NodoEdmonds n = (NodoEdmonds)this.grafoTrabajo.getNodoByIndex(index);
        if (n.getFloracion() == indexFloracion)
          n.setFloracion(-1); 
        this.grafoTrabajo.getNodos().set(index, n);
      } else {
        Floracion fInterna = (Floracion)this.grafoTrabajo.getNodoByIndex(index);
        if (fInterna.getFloracion() == indexFloracion)
          fInterna.setFloracion(-1); 
        this.grafoTrabajo.getNodos().set(index, fInterna);
      } 
    } 
    this.sGI = new SubGrafoIgualdad(this.grafoTrabajo);
    int nodo1 = -1, nodo2 = -1, nodo3 = -1;
    for (int l = 0; l < f.getCicloContraido().size() - 1; l += 2) {
      nodo1 = ((Integer)f.getCicloContraido().get(l)).intValue();
      nodo2 = ((Integer)f.getCicloContraido().get(l + 1)).intValue();
      nodo3 = ((Integer)f.getCicloContraido().get(l + 2)).intValue();
      this.grafoTrabajo.quitarAristaDeAcoplamiento(nodo1, nodo2);
      this.grafoTrabajo.insertaAristaAcoplamiento(nodo2, nodo3, (Grafo)this.sGI);
    } 
    nodo1 = ((Integer)f.getCicloContraido().get(0)).intValue();
    this.grafoTrabajo.quitarAristaDeAcoplamiento(nodo1, nodo3);
  }
  
  private void cambiarEtiquetado(double delta) {
    for (int i = 0; i < this.grafoTrabajo.getPrimeraFloracion(); i++) {
      if (this.arbol.esNodoE(i)) {
        NodoEdmonds nE = this.grafoTrabajo.getNodos().get(i);
        nE.setEtiqueta(nE.getEtiqueta() - delta);
        this.grafoTrabajo.getNodos().set(i, nE);
      } else if (this.arbol.esNodoI(i)) {
        NodoEdmonds nI = this.grafoTrabajo.getNodos().get(i);
        nI.setEtiqueta(nI.getEtiqueta() + delta);
        this.grafoTrabajo.getNodos().set(i, nI);
      } 
    } 
    for (int j = this.grafoTrabajo.getPrimeraFloracion(); j < this.grafoTrabajo.getNodos().size(); j++) {
      if (this.arbol.esNodoE(j)) {
        Floracion fE = this.grafoTrabajo.getNodos().get(j);
        fE.setEtiqueta(fE.getEtiqueta() + 2.0D * delta);
        this.grafoTrabajo.getNodos().set(j, fE);
      } 
      if (this.arbol.esNodoI(j)) {
        Floracion fI = this.grafoTrabajo.getNodos().get(j);
        fI.setEtiqueta(fI.getEtiqueta() - 2.0D * delta);
        this.grafoTrabajo.getNodos().set(j, fI);
      } 
    } 
  }
  
  private double calcularDelta1() {
    double delta1 = Double.POSITIVE_INFINITY;
    Vector<Arista> C1 = new Vector(3, 1);
    for (int i = 0; i < this.sGI.getC().size(); i++) {
      Arista a = this.sGI.getC().get(i);
      if ((this.arbol.esNodoE(a.getCabeza()) & (this.arbol.esNodoArbol(a.getCola()) ? 0 : 1) | this.arbol.esNodoE(a.getCola()) & (this.arbol.esNodoArbol(a.getCabeza()) ? 0 : 1)) != 0)
        C1.add(a); 
    } 
    for (int j = 0; j < C1.size(); j++) {
      Arista aC1 = C1.get(j);
      Nodo n1 = this.sGI.getNodos().get(aC1.getCabeza());
      Nodo n2 = this.sGI.getNodos().get(aC1.getCola());
      delta1 = Math.min(delta1, n1.getEtiqueta() + n2.getEtiqueta() - aC1.getPeso());
    } 
    return delta1;
  }
  
  private double calcularDelta2() {
    double delta2 = Double.POSITIVE_INFINITY;
    Vector<Arista> C2 = new Vector(3, 1);
    for (int i = 0; i < this.sGI.getC().size(); i++) {
      Arista a = this.sGI.getC().get(i);
      if ((this.arbol.esNodoE(a.getCabeza()) & this.arbol.esNodoE(a.getCola()) & ((this.grafoTrabajo.indexRealNodo(a.getCabeza()) != this.grafoTrabajo.indexRealNodo(a.getCola())) ? 1 : 0)) != 0)
        C2.add(a); 
    } 
    for (int j = 0; j < C2.size(); j++) {
      Arista aC2 = C2.get(j);
      Nodo n1 = this.sGI.getNodos().get(aC2.getCabeza());
      Nodo n2 = this.sGI.getNodos().get(aC2.getCola());
      delta2 = Math.min(delta2, (n1.getEtiqueta() + n2.getEtiqueta() - aC2.getPeso()) / 2.0D);
    } 
    return delta2;
  }
  
  private double calcularDelta3() {
    double delta3 = Double.POSITIVE_INFINITY;
    for (int i = this.grafoTrabajo.getPrimeraFloracion(); i < this.grafoTrabajo.getNodos().size(); i++) {
      NodoEdmonds n = (NodoEdmonds)this.grafoTrabajo.getNodoByIndex(i);
      if ((((n.getEtiquetaArborea() == "I") ? 1 : 0) & ((n.getFloracion() == -1) ? 1 : 0)) != 0)
        delta3 = Math.min(delta3, n.getEtiqueta() / 2.0D); 
    } 
    return delta3;
  }
  
  private void escribirResultadoFinal() {
    this.textoInformacion.setText("El algoritmo de Edmonds 2 ha finalizado. \n\nRESULTADO:\n\n");
    this.textoInformacion.append("Cardinalidad del acoplamiento = " + this.grafoTrabajo.getAcoplamiento().size() + "\n\nAristas que forman parte del acoplamiento :\n");
    Arista a = null;
    double pesototal = 0.0D;
    for (int i = 0; i < this.grafoTrabajo.getAcoplamiento().size(); i++) {
      a = this.grafoTrabajo.getAcoplamiento().get(i);
      this.textoInformacion.append(" · (" + this.grafoTrabajo.getNodoByIndex(a.getCabeza()).getNombre() + ", " + this.grafoTrabajo.getNodoByIndex(a.getCola()).getNombre() + ") con peso = " + a.getPeso() + "\n");
      pesototal += a.getPeso();
    } 
    this.textoInformacion.append("\nPeso del acoplamiento= " + pesototal + " uds.");
  }
  
  public void setEtiquetadoAjustado() {
    this.ajustarEtiquetado = true;
  }
}
