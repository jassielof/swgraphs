package com.jgoodies.looks.windows;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;
import javax.swing.text.Caret;

public final class WindowsFormattedTextFieldUI extends BasicFormattedTextFieldUI {
  public static ComponentUI createUI(JComponent c) {
    return new WindowsFormattedTextFieldUI();
  }
  
  protected Caret createCaret() {
    return new WindowsFieldCaret();
  }
}
