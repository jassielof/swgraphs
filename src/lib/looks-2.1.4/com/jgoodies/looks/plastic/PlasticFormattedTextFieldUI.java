package com.jgoodies.looks.plastic;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
import javax.swing.text.Caret;

public final class PlasticFormattedTextFieldUI extends BasicFormattedTextFieldUI {
  public static ComponentUI createUI(JComponent c) {
    return new PlasticFormattedTextFieldUI();
  }
  
  protected Caret createCaret() {
    return new PlasticFieldCaret();
  }
}
