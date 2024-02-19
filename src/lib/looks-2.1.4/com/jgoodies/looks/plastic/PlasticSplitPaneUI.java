package com.jgoodies.looks.plastic;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

public final class PlasticSplitPaneUI extends BasicSplitPaneUI {
  public static ComponentUI createUI(JComponent x) {
    return new PlasticSplitPaneUI();
  }
  
  public BasicSplitPaneDivider createDefaultDivider() {
    return new PlasticSplitPaneDivider(this);
  }
}
