package com.jgoodies.looks.windows;

import com.sun.java.swing.plaf.windows.WindowsSplitPaneUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

public final class WindowsSplitPaneUI extends WindowsSplitPaneUI {
  public static ComponentUI createUI(JComponent x) {
    return new WindowsSplitPaneUI();
  }
  
  public BasicSplitPaneDivider createDefaultDivider() {
    return new WindowsSplitPaneDivider(this);
  }
}
