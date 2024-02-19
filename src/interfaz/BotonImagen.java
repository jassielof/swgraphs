package interfaz;

import javax.swing.Icon;
import javax.swing.JButton;

public class BotonImagen extends JButton {
  public BotonImagen(Icon arg0, String help, String text) {
    super(arg0);
    setToolTipText(help);
    setText(text);
    setVisible(true);
    setBorderPainted(false);
    setVerticalTextPosition(3);
    setHorizontalTextPosition(0);
  }
}
