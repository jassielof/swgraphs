package grafos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

public class Floracion extends NodoEdmonds {
  private Vector cicloContraido = null;
  
  public Floracion(String arg0, Point arg1, Vector arg2) {
    super(arg0, arg1);
    this.cicloContraido = arg2;
    setEtiqueta(0.0D);
  }
  
  public Vector getCicloContraido() {
    return this.cicloContraido;
  }
  
  public void setCicloContraido(Vector ciclo) {
    this.cicloContraido = ciclo;
  }
  
  public void pintarFloracion(Graphics g) {
    if (getFloracion() == -1) {
      Point p = getPos();
      g.setColor(Color.BLACK);
      if (getEtiquetaArborea() == "E")
        g.setColor(Color.RED); 
      if (getEtiquetaArborea() == "I")
        g.setColor(Color.BLUE); 
      g.drawRect(p.x, p.y, 25, 15);
      g.drawString(getNombre(), p.x + 10, p.y + 10);
      g.drawString(String.valueOf(getEtiqueta()), p.x - 5, p.y - 5);
    } 
  }
  
  public void pintarFloracion(Graphics g, Color c) {
    if (getFloracion() == -1) {
      Point p = getPos();
      g.setColor(c);
      g.drawRect(p.x, p.y, 25, 15);
      g.drawString(getNombre(), p.x + 10, p.y + 10);
      g.drawString(String.valueOf(getEtiqueta()), p.x - 5, p.y - 5);
    } 
  }
}
