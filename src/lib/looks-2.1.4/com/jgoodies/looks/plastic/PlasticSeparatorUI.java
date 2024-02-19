package com.jgoodies.looks.plastic;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalSeparatorUI;

public final class PlasticSeparatorUI extends MetalSeparatorUI {
  private static ComponentUI separatorUI;
  
  public static ComponentUI createUI(JComponent c) {
    if (separatorUI == null)
      separatorUI = new PlasticSeparatorUI(); 
    return separatorUI;
  }
}
