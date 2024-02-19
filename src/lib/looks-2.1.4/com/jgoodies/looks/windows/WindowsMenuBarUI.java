package com.jgoodies.looks.windows;

import com.jgoodies.looks.BorderStyle;
import com.jgoodies.looks.HeaderStyle;
import com.sun.java.swing.plaf.windows.WindowsMenuBarUI;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

public final class WindowsMenuBarUI extends WindowsMenuBarUI {
  private PropertyChangeListener listener;
  
  public static ComponentUI createUI(JComponent b) {
    return new WindowsMenuBarUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
    installSpecialBorder();
  }
  
  protected void installListeners() {
    super.installListeners();
    this.listener = createBorderStyleListener();
    this.menuBar.addPropertyChangeListener(this.listener);
  }
  
  protected void uninstallListeners() {
    this.menuBar.removePropertyChangeListener(this.listener);
    super.uninstallListeners();
  }
  
  private PropertyChangeListener createBorderStyleListener() {
    return new PropertyChangeListener() {
        private final WindowsMenuBarUI this$0;
        
        public void propertyChange(PropertyChangeEvent e) {
          String prop = e.getPropertyName();
          if (prop.equals("jgoodies.headerStyle") || prop.equals("jgoodies.windows.borderStyle"))
            WindowsMenuBarUI.this.installSpecialBorder(); 
        }
      };
  }
  
  private void installSpecialBorder() {
    String suffix;
    BorderStyle borderStyle = BorderStyle.from(this.menuBar, "jgoodies.windows.borderStyle");
    if (borderStyle == BorderStyle.EMPTY) {
      suffix = "emptyBorder";
    } else if (borderStyle == BorderStyle.ETCHED) {
      suffix = "etchedBorder";
    } else if (borderStyle == BorderStyle.SEPARATOR) {
      suffix = "separatorBorder";
    } else if (HeaderStyle.from(this.menuBar) == HeaderStyle.BOTH) {
      suffix = "headerBorder";
    } else {
      return;
    } 
    LookAndFeel.installBorder(this.menuBar, "MenuBar." + suffix);
  }
}
