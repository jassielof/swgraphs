package interfaz;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class DialogoAyuda extends JDialog {
  private JTextPane textoInformacion;
  
  private JScrollPane areaScrollPane;
  
  public DialogoAyuda(int referencia) throws HeadlessException {
    setTitle("AYUDA");
    setModal(true);
    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension ventana = getSize();
    setLocation((
        pantalla.width - ventana.width) / 2 - 275, (
        pantalla.height - ventana.height) / 2 - 315);
    construirIGU(referencia);
  }
  
  private void construirIGU(int referencia) {
    this.textoInformacion = new JTextPane();
    this.textoInformacion.setEditable(false);
    this.textoInformacion.setContentType("text/html");
    this.areaScrollPane = new JScrollPane(this.textoInformacion);
    this.areaScrollPane.setPreferredSize(new Dimension(500, 500));
    this.areaScrollPane.setVisible(true);
    this.areaScrollPane.setBorder(
        BorderFactory.createCompoundBorder(
          BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)), 
          this.areaScrollPane.getBorder()));
    mensajeAyuda(referencia);
    BotonImagen botonsalir = new BotonImagen(new ImageIcon(getClass().getResource("/interfaz/img/home.png")), "Cierra el dialogo y vuelve a la ventana anterior", "");
    botonsalir.setPreferredSize(new Dimension(125, 80));
    botonsalir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            DialogoAyuda.this.botonsalirActionPerformed(evt);
          }
        });
    setContentPane(new JPanel());
    getContentPane().add(this.areaScrollPane);
    getContentPane().add(botonsalir);
    setIconImage((new ImageIcon(getClass().getResource("/interfaz/img/k.png"))).getImage());
    setSize(550, 630);
    setResizable(false);
    setVisible(true);
    pack();
  }
  
  private void mensajeAyuda(int referencia) {
    switch (referencia) {
      case 11:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/manual.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 16:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/generalidades.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 12:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/matriz.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 1:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/edmonds2.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 2:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/cartero.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 3:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/dijkstra.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 4:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/bfs.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 5:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/dfs.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 6:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/hierholzer.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 7:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/etiquetaje.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 8:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/kruskal.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 10:
      case 14:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/hamiltoniano.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 9:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/conectividad.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 15:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/orientabilidad.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
      case 13:
        try {
          this.textoInformacion.setPage(getClass().getClassLoader().getResource("ayuda/acercade.html"));
        } catch (IOException e) {
          e.printStackTrace();
        } 
        break;
    } 
    this.textoInformacion.setCaretPosition(0);
  }
  
  private void botonsalirActionPerformed(ActionEvent evt) {
    processWindowEvent(new WindowEvent(this, 201));
  }
}
