package com.jgoodies.looks.plastic;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

public final class PlasticXPToolBarUI extends PlasticToolBarUI {
  public static ComponentUI createUI(JComponent b) {
    return new PlasticXPToolBarUI();
  }
  
  protected Border createRolloverBorder() {
    return PlasticXPBorders.getRolloverButtonBorder();
  }
}
