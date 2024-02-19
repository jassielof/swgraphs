package com.jgoodies.looks.windows;

import com.sun.java.swing.plaf.windows.WindowsButtonUI;
import java.awt.Dimension;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public final class WindowsButtonUI extends WindowsButtonUI {
  public static ComponentUI createUI(JComponent b) {
    return new WindowsButtonUI();
  }
  
  public Dimension getPreferredSize(JComponent c) {
    AbstractButton b = (AbstractButton)c;
    Dimension d = BasicGraphicsUtils.getPreferredButtonSize(b, b.getIconTextGap());
    if (d != null && b.isFocusPainted() && 
      d.width % 2 == 0)
      d.width++; 
    return d;
  }
}
