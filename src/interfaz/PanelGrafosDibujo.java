package interfaz;

import grafos.Arista;
import grafos.Grafo;
import grafos.Nodo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class PanelGrafosDibujo extends PanelGrafos implements MouseListener, ActionListener {
  private int operacion = 0;
  
  private Point punto;
  
  private int nodoSeleccionado = -1;
  
  private int aristaSeleccionada = -1;
  
  private final double umbralDistancia = 20.0D;
  
  public PanelGrafosDibujo() {}
  
  public PanelGrafosDibujo(Grafo arg0) {
    super(arg0);
  }
  
  public void setOperacion(int op) {
    this.operacion = op;
  }
  
  public int getOperacion() {
    return this.operacion;
  }
  
  public void mouseClicked(MouseEvent arg0) {
    if (this.operacion == 0) {
      this.nodoSeleccionado = obtenerNodoMasCercano(arg0.getPoint(), 20.0D);
      if (arg0.getButton() == 1)
        crearNuevoNodo(arg0.getPoint()); 
      if (arg0.getButton() == 3)
        if (this.nodoSeleccionado > -1)
          emergerMenuNodos(arg0.getPoint());  
    } else if (arg0.getButton() == 3) {
      this.aristaSeleccionada = obtenerAristaMasCercana(arg0.getPoint(), 20.0D);
      if (this.aristaSeleccionada > -1)
        if (this.operacion == 1) {
          emergerMenuAristas(arg0.getPoint());
        } else {
          emergerMenuArcos(arg0.getPoint());
        }  
    } 
  }
  
  public void mouseEntered(MouseEvent arg0) {}
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == 1) {
      this.nodoSeleccionado = obtenerNodoMasCercano(e.getPoint(), 20.0D);
      if (this.nodoSeleccionado != -1) {
        Nodo n = getGrafo().getNodoByIndex(this.nodoSeleccionado);
        this.punto = n.getPos();
      } 
    } else {
      this.nodoSeleccionado = -1;
    } 
  }
  
  public void mouseReleased(MouseEvent e) {
    switch (this.operacion) {
      case 0:
        if (this.nodoSeleccionado != -1 && e.getPoint().distance(this.punto) > 15.0D) {
          getGrafo().cambiarPosicionIndex(this.nodoSeleccionado, e.getPoint());
          repaint();
        } 
        break;
      case 1:
        if (this.nodoSeleccionado != -1 && obtenerNodoMasCercano(e.getPoint(), 20.0D) != -1 && 
          getGrafo().insertarArista(obtenerNodoMasCercano(e.getPoint(), 20.0D), this.nodoSeleccionado))
          repaint(); 
        break;
      case 2:
        if (this.nodoSeleccionado != -1 && obtenerNodoMasCercano(e.getPoint(), 20.0D) != -1 && 
          getGrafo().insertarArco(this.nodoSeleccionado, obtenerNodoMasCercano(e.getPoint(), 20.0D)))
          repaint(); 
        break;
    } 
  }
  
  public void actionPerformed(ActionEvent arg0) {
    JMenuItem source = (JMenuItem)arg0.getSource();
    switch (this.operacion) {
      case 0:
        if (this.nodoSeleccionado != -1 && source.getText() == "Borrar el nodo") {
          eliminarNodoSeleccionado();
          break;
        } 
        cambiarNombre();
        break;
      case 1:
        if (this.aristaSeleccionada != -1 && source.getText() == "Borrar la arista") {
          eliminarAristaSeleccionada();
          break;
        } 
        cambiarPeso();
        break;
      case 2:
        if (this.aristaSeleccionada != -1 && source.getText() == "Borrar el arco") {
          eliminarAristaSeleccionada();
          break;
        } 
        cambiarPeso();
        break;
    } 
  }
  
  private void eliminarAristaSeleccionada() {
    getGrafo().borrarAristaIndex(this.aristaSeleccionada);
    repaint();
  }
  
  private void eliminarNodoSeleccionado() {
    getGrafo().borrarNodoIndex(this.nodoSeleccionado);
    repaint();
  }
  
  private void cambiarNombre() {
    String texto = JOptionPane.showInputDialog(this, "Introduzca el nuevo nombre del nodo", "Cambiar Nombre", 3);
    try {
      if (texto.length() == 0) {
        JOptionPane.showMessageDialog(this, "No se permiten nombres vacios", "ERROR", 0);
      } else if (getGrafo().cambiarNombreIndex(this.nodoSeleccionado, texto)) {
        repaint();
      } else {
        JOptionPane.showMessageDialog(this, "El nombre ya existe", "ERROR", 0);
      } 
    } catch (NullPointerException nullPointerException) {}
  }
  
  private void cambiarPeso() {
    String texto = JOptionPane.showInputDialog(this, "Introduzca el nuevo peso de la arista", "Cambio peso", 3);
    try {
      double nuevoPeso = Double.parseDouble(texto);
      if (nuevoPeso == 0.0D) {
        JOptionPane.showMessageDialog(this, "El peso no es valido.\nDebe ser un número distinto a 0", "ERROR", 0);
      } else {
        Arista arista = getGrafo().getAristaByIndex(this.aristaSeleccionada);
        arista.setPeso(nuevoPeso);
        getGrafo().getAristas().set(this.aristaSeleccionada, arista);
        repaint();
      } 
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "El peso " + texto + " no es valido.\nDebe ser un número", "ERROR", 0);
    } catch (NullPointerException nullPointerException) {}
  }
  
  private void crearNuevoNodo(Point point) {
    if (this.nodoSeleccionado == -1) {
      getGrafo().insertarNodo(point);
      repaint();
    } 
  }
  
  private void emergerMenuAristas(Point p) {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem menuItem = new JMenuItem("Borrar la arista");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    popup.addSeparator();
    menuItem = new JMenuItem("Cambiar peso de la arista");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    popup.show(this, p.x, p.y);
  }
  
  private void emergerMenuArcos(Point p) {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem menuItem = new JMenuItem("Borrar el arco");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    popup.addSeparator();
    menuItem = new JMenuItem("Cambiar peso del arco");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    popup.show(this, p.x, p.y);
  }
  
  private void emergerMenuNodos(Point p) {
    JPopupMenu popup = new JPopupMenu();
    JMenuItem menuItem = new JMenuItem("Borrar el nodo");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    popup.addSeparator();
    menuItem = new JMenuItem("Cambiar el nombre");
    menuItem.addActionListener(this);
    popup.add(menuItem);
    popup.show(this, p.x, p.y);
  }
}
