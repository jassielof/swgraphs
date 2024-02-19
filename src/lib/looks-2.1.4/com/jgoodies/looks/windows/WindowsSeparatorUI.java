package com.jgoodies.looks.windows;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

public final class WindowsSeparatorUI extends BasicSeparatorUI {
  private static ComponentUI separatorUI;
  
  public static ComponentUI createUI(JComponent c) {
    if (separatorUI == null)
      separatorUI = new WindowsSeparatorUI(); 
    return separatorUI;
  }
}
