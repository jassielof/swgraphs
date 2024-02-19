package com.jgoodies.looks.plastic;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.metal.MetalCheckBoxUI;

public final class PlasticXPCheckBoxUI extends MetalCheckBoxUI {
  private static final PlasticXPCheckBoxUI INSTANCE = new PlasticXPCheckBoxUI();
  
  public static ComponentUI createUI(JComponent b) {
    return INSTANCE;
  }
  
  protected BasicButtonListener createButtonListener(AbstractButton b) {
    return new ActiveBasicButtonListener(b);
  }
}
