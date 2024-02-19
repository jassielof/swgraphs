package com.jgoodies.looks.common;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

public final class ExtBasicCheckBoxMenuItemUI extends ExtBasicRadioButtonMenuItemUI {
  protected String getPropertyPrefix() {
    return "CheckBoxMenuItem";
  }
  
  public static ComponentUI createUI(JComponent b) {
    return new ExtBasicCheckBoxMenuItemUI();
  }
}
