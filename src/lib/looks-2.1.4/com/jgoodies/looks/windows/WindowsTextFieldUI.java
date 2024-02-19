package com.jgoodies.looks.windows;

import com.sun.java.swing.plaf.windows.WindowsTextFieldUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.Caret;

public final class WindowsTextFieldUI extends WindowsTextFieldUI {
  public static ComponentUI createUI(JComponent c) {
    return new WindowsTextFieldUI();
  }
  
  protected Caret createCaret() {
    return new WindowsFieldCaret();
  }
}
