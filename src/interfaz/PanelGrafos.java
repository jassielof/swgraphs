package interfaz;

import grafos.Arista;
import grafos.Grafo;
import grafos.Nodo;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class PanelGrafos extends JPanel {
  private Grafo G;
  
  public Image imagen = null;
  
  public PanelGrafos() {
    setVisible(true);
    this.G = new Grafo();
    repaint();
  }
  
  public PanelGrafos(Grafo arg0) {
    setGrafo(arg0);
  }
  
  public Grafo getGrafo() {
    return this.G;
  }
  
  public void setGrafo(Grafo grafo) {
    this.G = grafo;
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.G != null)
      this.G.pintarGrafo(g); 
  }
  
  protected int obtenerNodoMasCercano(Point p, double umbral) {
    int posicion = -1;
    double distanciaMinima = umbral;
    for (int i = 0; i < getGrafo().getNodos().size(); i++) {
      Nodo nodo1 = getGrafo().getNodoByIndex(i);
      double distancia = Math.sqrt(((p.x - (nodo1.getPos()).x) * (p.x - (nodo1.getPos()).x) + (p.y - (nodo1.getPos()).y) * (p.y - (nodo1.getPos()).y)));
      if (distancia < distanciaMinima) {
        distanciaMinima = distancia;
        posicion = i;
      } 
    } 
    return posicion;
  }
  
  protected int obtenerAristaMasCercana(Point p, double umbral) {
    int posicion = -1;
    double distanciaMinima = umbral;
    for (int i = 0; i < getGrafo().getAristas().size(); i++) {
      Arista arista = getGrafo().getAristaByIndex(i);
      double distancia = distanciaPuntoArista(arista, p);
      if (distancia < distanciaMinima) {
        distanciaMinima = distancia;
        posicion = i;
      } 
    } 
    return posicion;
  }
  
  protected double distanciaPuntoArista(Arista arista, Point pto) {
    Line2D.Double linea = null;
    Point ptoCabeza = null;
    Point ptoCola = null;
    Nodo nodoCabeza = getGrafo().getNodoByIndex(arista.getCabeza());
    Nodo nodoCola = getGrafo().getNodoByIndex(arista.getCola());
    ptoCabeza = nodoCabeza.getPos();
    ptoCola = nodoCola.getPos();
    if (ptoCabeza == ptoCola) {
      double dist = Math.sqrt(Math.pow((pto.x - ptoCabeza.x), 2.0D) + Math.pow((pto.y - ptoCabeza.y), 2.0D));
      return dist - 10.0D;
    } 
    linea = new Line2D.Double(ptoCabeza, ptoCola);
    return linea.ptSegDist(pto);
  }
  
  public void paint(Graphics g) {
    if (this.imagen != null) {
      g.drawImage(this.imagen, 0, 0, this.imagen.getWidth(null), this.imagen.getHeight(null), this);
      setOpaque(false);
    } else {
      setOpaque(true);
    } 
    super.paint(g);
    repaint();
  }
}
