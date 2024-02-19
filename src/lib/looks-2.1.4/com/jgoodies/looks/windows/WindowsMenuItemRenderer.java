package com.jgoodies.looks.windows;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.common.MenuItemRenderer;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

final class WindowsMenuItemRenderer extends MenuItemRenderer {
  public WindowsMenuItemRenderer(JMenuItem menuItem, boolean iconBorderEnabled, Font acceleratorFont, Color selectionForeground, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground) {
    super(menuItem, iconBorderEnabled, acceleratorFont, selectionForeground, disabledForeground, acceleratorForeground, acceleratorSelectionForeground);
  }
  
  protected boolean isMnemonicHidden() {
    return WindowsLookAndFeel.isMnemonicHidden();
  }
  
  protected boolean disabledTextHasShadow() {
    return (!LookUtils.IS_LAF_WINDOWS_XP_ENABLED || UIManager.getColor("MenuItem.disabledForeground") == null);
  }
}
