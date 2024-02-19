package interfaz;

import Util.Util;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class GrafoXMLFileFilter extends FileFilter {
  public boolean accept(File arg0) {
    if (arg0.isDirectory())
      return true; 
    String extension = Util.getExtension(arg0);
    if (extension != null && 
      extension.equals("xml"))
      return true; 
    return false;
  }
  
  public String getDescription() {
    return "Grafos XML";
  }
}
