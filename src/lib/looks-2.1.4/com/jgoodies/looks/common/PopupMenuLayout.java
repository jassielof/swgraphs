package com.jgoodies.looks.common;

import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JPopupMenu;
import javax.swing.plaf.UIResource;

public final class PopupMenuLayout extends BoxLayout implements UIResource {
  public PopupMenuLayout(Container target, int axis) {
    super(target, axis);
  }
  
  public synchronized void invalidateLayout(Container target) {
    if (target instanceof JPopupMenu) {
      JPopupMenu menu = (JPopupMenu)target;
      menu.putClientProperty("maxTextWidth", null);
      menu.putClientProperty("maxAccWidth", null);
    } 
    super.invalidateLayout(target);
  }
}
