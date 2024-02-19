package com.jgoodies.looks.windows;

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public final class WindowsScrollBarUI extends WindowsScrollBarUI {
  public static ComponentUI createUI(JComponent b) {
    return new WindowsScrollBarUI();
  }
  
  protected JButton createDecreaseButton(int orientation) {
    return new WindowsArrowButton(orientation);
  }
  
  protected JButton createIncreaseButton(int orientation) {
    return createDecreaseButton(orientation);
  }
}
