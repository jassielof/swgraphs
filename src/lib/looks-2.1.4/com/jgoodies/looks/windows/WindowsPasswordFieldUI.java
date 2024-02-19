package com.jgoodies.looks.windows;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.common.ExtPasswordView;
import com.sun.java.swing.plaf.windows.WindowsPasswordFieldUI;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.Caret;
import javax.swing.text.Element;
import javax.swing.text.View;

public final class WindowsPasswordFieldUI extends WindowsPasswordFieldUI {
  public static ComponentUI createUI(JComponent c) {
    return new WindowsPasswordFieldUI();
  }
  
  public View create(Element elem) {
    return LookUtils.IS_JAVA_1_4_OR_5 ? (View)new ExtPasswordView(elem) : super.create(elem);
  }
  
  protected Caret createCaret() {
    return new WindowsFieldCaret();
  }
}
