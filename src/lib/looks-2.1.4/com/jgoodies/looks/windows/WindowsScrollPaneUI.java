package com.jgoodies.looks.windows;

import com.sun.java.swing.plaf.windows.WindowsScrollPaneUI;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

public final class WindowsScrollPaneUI extends WindowsScrollPaneUI {
  private PropertyChangeListener borderStyleChangeHandler;
  
  public static ComponentUI createUI(JComponent b) {
    return new WindowsScrollPaneUI();
  }
  
  protected void installDefaults(JScrollPane scrollPane) {
    super.installDefaults(scrollPane);
    installEtchedBorder(scrollPane);
  }
  
  protected void installEtchedBorder(JScrollPane scrollPane) {
    Object value = scrollPane.getClientProperty("jgoodies.isEtched");
    boolean hasEtchedBorder = Boolean.TRUE.equals(value);
    LookAndFeel.installBorder(scrollPane, hasEtchedBorder ? "ScrollPane.etchedBorder" : "ScrollPane.border");
  }
  
  public void installListeners(JScrollPane scrollPane) {
    super.installListeners(scrollPane);
    this.borderStyleChangeHandler = new BorderStyleChangeHandler();
    scrollPane.addPropertyChangeListener("jgoodies.isEtched", this.borderStyleChangeHandler);
  }
  
  protected void uninstallListeners(JComponent c) {
    ((JScrollPane)c).removePropertyChangeListener("jgoodies.isEtched", this.borderStyleChangeHandler);
    super.uninstallListeners(c);
  }
  
  private class BorderStyleChangeHandler implements PropertyChangeListener {
    private final WindowsScrollPaneUI this$0;
    
    private BorderStyleChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent evt) {
      JScrollPane scrollPane = (JScrollPane)evt.getSource();
      WindowsScrollPaneUI.this.installEtchedBorder(scrollPane);
    }
  }
}
