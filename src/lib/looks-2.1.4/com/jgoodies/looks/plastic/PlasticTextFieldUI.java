package com.jgoodies.looks.plastic;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalTextFieldUI;
import javax.swing.text.Caret;

public final class PlasticTextFieldUI extends MetalTextFieldUI {
  public static ComponentUI createUI(JComponent c) {
    return new PlasticTextFieldUI();
  }
  
  protected Caret createCaret() {
    return new PlasticFieldCaret();
  }
}
