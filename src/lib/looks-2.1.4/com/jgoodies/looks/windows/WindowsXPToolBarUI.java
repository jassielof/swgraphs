package com.jgoodies.looks.windows;

import com.jgoodies.looks.BorderStyle;
import com.jgoodies.looks.HeaderStyle;
import com.sun.java.swing.plaf.windows.WindowsToolBarUI;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

public final class WindowsXPToolBarUI extends WindowsToolBarUI {
  private PropertyChangeListener listener;
  
  public static ComponentUI createUI(JComponent b) {
    return new WindowsXPToolBarUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
    installSpecialBorder();
  }
  
  protected void installListeners() {
    super.installListeners();
    this.listener = createBorderStyleListener();
    this.toolBar.addPropertyChangeListener(this.listener);
  }
  
  protected void uninstallListeners() {
    this.toolBar.removePropertyChangeListener(this.listener);
    super.uninstallListeners();
  }
  
  private PropertyChangeListener createBorderStyleListener() {
    return new PropertyChangeListener() {
        private final WindowsXPToolBarUI this$0;
        
        public void propertyChange(PropertyChangeEvent e) {
          String prop = e.getPropertyName();
          if (prop.equals("jgoodies.headerStyle") || prop.equals("jgoodies.windows.borderStyle"))
            WindowsXPToolBarUI.this.installSpecialBorder(); 
        }
      };
  }
  
  private void installSpecialBorder() {
    String suffix;
    BorderStyle borderStyle = BorderStyle.from(this.toolBar, "jgoodies.windows.borderStyle");
    if (borderStyle == BorderStyle.EMPTY) {
      suffix = "emptyBorder";
    } else if (borderStyle == BorderStyle.SEPARATOR) {
      suffix = "separatorBorder";
    } else if (borderStyle == BorderStyle.ETCHED) {
      suffix = "etchedBorder";
    } else if (HeaderStyle.from(this.toolBar) == HeaderStyle.BOTH) {
      suffix = "headerBorder";
    } else {
      suffix = "border";
    } 
    LookAndFeel.installBorder(this.toolBar, "ToolBar." + suffix);
  }
  
  protected void setBorderToRollover(Component c) {
    if (c instanceof javax.swing.AbstractButton) {
      super.setBorderToRollover(c);
    } else if (c instanceof Container) {
      Container cont = (Container)c;
      for (int i = 0; i < cont.getComponentCount(); i++)
        super.setBorderToRollover(cont.getComponent(i)); 
    } 
  }
}
