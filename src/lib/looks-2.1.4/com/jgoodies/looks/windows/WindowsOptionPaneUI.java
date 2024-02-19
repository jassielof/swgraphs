package com.jgoodies.looks.windows;

import com.jgoodies.looks.common.ExtButtonAreaLayout;
import java.awt.Container;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;

public final class WindowsOptionPaneUI extends BasicOptionPaneUI {
  public static ComponentUI createUI(JComponent b) {
    return new WindowsOptionPaneUI();
  }
  
  protected Container createButtonArea() {
    JPanel bottom = new JPanel((LayoutManager)new ExtButtonAreaLayout(true, 6));
    bottom.setBorder(UIManager.getBorder("OptionPane.buttonAreaBorder"));
    addButtonComponents(bottom, getButtons(), getInitialValueIndex());
    return bottom;
  }
}
