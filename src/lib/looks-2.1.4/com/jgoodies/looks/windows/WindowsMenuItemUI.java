package com.jgoodies.looks.windows;

import com.jgoodies.looks.common.ExtBasicMenuItemUI;
import com.jgoodies.looks.common.MenuItemRenderer;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;

public final class WindowsMenuItemUI extends ExtBasicMenuItemUI {
  public static ComponentUI createUI(JComponent b) {
    return (ComponentUI)new WindowsMenuItemUI();
  }
  
  protected MenuItemRenderer createRenderer(JMenuItem menuItem, boolean iconBorderEnabled, Font acceleratorFont, Color selectionForeground, Color disabledForeground, Color acceleratorForeground, Color acceleratorSelectionForeground) {
    return new WindowsMenuItemRenderer(menuItem, iconBorderEnabled(), acceleratorFont, selectionForeground, disabledForeground, acceleratorForeground, acceleratorSelectionForeground);
  }
}
