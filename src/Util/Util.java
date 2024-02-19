package Util;

import grafos.Arista;
import grafos.Grafo;
import grafos.Nodo;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class Util {
  public static String getExtension(File f) {
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if (i > 0 && i < s.length() - 1) {
      ext = s.substring(i + 1).toLowerCase();
      return ext;
    } 
    return "no extension";
  }
  
  public static Point getPunto(String textPos) {
    int x = 0, y = 0;
    int i = textPos.lastIndexOf(',');
    int j = textPos.lastIndexOf('(');
    if (i > 0 && i < textPos.length() - 1) {
      x = Integer.parseInt(textPos.substring(j + 1, i));
      y = Integer.parseInt(textPos.substring(i + 1));
    } 
    Point p = new Point(x, y);
    return p;
  }
  
  public static boolean escribirGrafo(File file, Grafo grafo) {
    try {
      File filexml;
      if (!getExtension(file).equals("xml")) {
        filexml = new File(String.valueOf(file.getAbsolutePath()) + ".xml");
      } else {
        filexml = file;
      } 
      FileWriter fw = new FileWriter(filexml);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter out = new PrintWriter(bw);
      out.println("<?xml version=\"1.0\" standalone=\"yes\" ?>");
      out.println(grafo.toXMLString());
      out.close();
    } catch (IOException e1) {
      return false;
    } 
    return true;
  }
  
  public static Grafo leerGrafo(File fichero, String grafo) {
    Grafo G = new Grafo();
    try {
      Document doc;
      if (fichero == null) {
        InputStream input = ClassLoader.getSystemResourceAsStream(grafo);
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
      } else {
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fichero);
      } 
      Element e = doc.getDocumentElement();
      NodeList n0 = e.getElementsByTagName("tipoGrafo");
      String tipoGrafo = n0.item(0).getFirstChild().toString();
      tipoGrafo = tipoGrafo.substring(7, tipoGrafo.length() - 2);
      tipoGrafo = tipoGrafo.replace('"', ' ');
      tipoGrafo = tipoGrafo.trim();
      G.setTipo(Integer.parseInt(tipoGrafo));
      NodeList nl = e.getElementsByTagName("nodo");
      for (int i = 0; i < nl.getLength(); i++) {
        Point p = null;
        String nombreNodo = null;
        Node nodInt = nl.item(i);
        NodeList nl2 = nodInt.getChildNodes();
        int k;
        for (k = 0; k < nl2.getLength(); k++) {
          if (nl2.item(k).getNodeName().equalsIgnoreCase("nombre")) {
            nombreNodo = nl2.item(k).getFirstChild().toString();
            nombreNodo = nombreNodo.substring(8, nombreNodo.length() - 2);
            nombreNodo = nombreNodo.replace('"', ' ');
            nombreNodo = nombreNodo.trim();
            break;
          } 
        } 
        for (k = 0; k < nl2.getLength(); k++) {
          if (nl2.item(k).getNodeName().equalsIgnoreCase("posicion")) {
            String posicion = nl2.item(k).getFirstChild().toString();
            posicion = posicion.substring(3, posicion.length() - 3);
            p = getPunto(posicion);
            break;
          } 
        } 
        if (nombreNodo != null && p != null) {
          Nodo n = new Nodo(nombreNodo, p);
          G.insertarNodo(n);
        } 
      } 
      NodeList na = e.getElementsByTagName("arista");
      for (int j = 0; j < na.getLength(); j++) {
        int cabeza = -1;
        int cola = -1;
        double peso = 0.0D;
        int tipo = -1;
        String aux = null;
        Node nodInt = na.item(j);
        NodeList nl2 = nodInt.getChildNodes();
        int k;
        for (k = 0; k < nl2.getLength(); k++) {
          if (nl2.item(k).getNodeName().equalsIgnoreCase("nodo1")) {
            aux = nl2.item(k).getFirstChild().toString();
            aux = aux.substring(7, aux.length() - 2);
            aux = aux.replace('"', ' ');
            aux = aux.trim();
            cabeza = Integer.parseInt(aux);
            break;
          } 
        } 
        for (k = 0; k < nl2.getLength(); k++) {
          if (nl2.item(k).getNodeName().equalsIgnoreCase("nodo2")) {
            aux = nl2.item(k).getFirstChild().toString();
            aux = aux.substring(7, aux.length() - 2);
            aux = aux.replace('"', ' ');
            aux = aux.trim();
            cola = Integer.parseInt(aux);
            break;
          } 
        } 
        for (k = 0; k < nl2.getLength(); k++) {
          if (nl2.item(k).getNodeName().equalsIgnoreCase("peso")) {
            aux = nl2.item(k).getFirstChild().toString();
            aux = aux.substring(7, aux.length() - 2);
            aux = aux.replace('"', ' ');
            aux = aux.trim();
            peso = Double.parseDouble(aux);
            break;
          } 
        } 
        for (k = 0; k < nl2.getLength(); k++) {
          if (nl2.item(k).getNodeName().equalsIgnoreCase("tipo")) {
            aux = nl2.item(k).getFirstChild().toString();
            aux = aux.substring(7, aux.length() - 2);
            aux = aux.replace('"', ' ');
            aux = aux.trim();
            tipo = Integer.parseInt(aux);
            break;
          } 
        } 
        if (cabeza < G.getNodos().size() && cabeza > -1 && cola < G.getNodos().size() && cola > -1 && tipo > -1) {
          Arista a = new Arista(cabeza, cola, tipo);
          a.setPeso(peso);
          if (tipo == 0) {
            G.insertarArista(a);
          } else {
            G.insertarArco(a);
          } 
        } 
      } 
    } catch (SAXException e) {
      JOptionPane.showMessageDialog(null, "Error al abrir el archivo.\nLa sintaxis no se corresponde con lo esperado. Revise el código XML.", "ERROR", 0);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (FactoryConfigurationError e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return G;
  }
}
