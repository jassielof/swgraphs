package com.jgoodies.looks.plastic;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.common.ExtPasswordView;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.text.Caret;
import javax.swing.text.Element;
import javax.swing.text.View;

public final class PlasticPasswordFieldUI extends BasicPasswordFieldUI {
  public static ComponentUI createUI(JComponent c) {
    return new PlasticPasswordFieldUI();
  }
  
  public View create(Element elem) {
    return LookUtils.IS_JAVA_1_4_OR_5 ? (View)new ExtPasswordView(elem) : super.create(elem);
  }
  
  protected Caret createCaret() {
    return PlasticLookAndFeel.isSelectTextOnKeyboardFocusGained() ? new PlasticFieldCaret() : super.createCaret();
  }
}
