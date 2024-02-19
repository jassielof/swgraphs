package interfaz;

import Util.Util;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class GrafoIMGFileFilter extends FileFilter {
  public boolean accept(File arg0) {
    if (arg0.isDirectory())
      return true; 
    String extension = Util.getExtension(arg0);
    if (extension != null)
      if ((extension.equals("gif") | 
        extension.equals("bmp") | 
        extension.equals("jpg") | 
        extension.equals("png")) != 0)
        return true;  
    return false;
  }
  
  public String getDescription() {
    return "Imágenes: .jpg, .png, .gif, .bmp";
  }
}
