package com.jgoodies.looks.plastic;

import com.jgoodies.looks.common.PopupMenuLayout;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

public final class PlasticPopupMenuUI extends BasicPopupMenuUI {
  private PropertyChangeListener borderListener;
  
  public static ComponentUI createUI(JComponent b) {
    return new PlasticPopupMenuUI();
  }
  
  public void installDefaults() {
    super.installDefaults();
    installBorder();
    if (this.popupMenu.getLayout() == null || this.popupMenu.getLayout() instanceof javax.swing.plaf.UIResource)
      this.popupMenu.setLayout((LayoutManager)new PopupMenuLayout(this.popupMenu, 1)); 
  }
  
  protected void installListeners() {
    super.installListeners();
    this.borderListener = new BorderStyleChangeHandler();
    this.popupMenu.addPropertyChangeListener("JPopupMenu.noMargin", this.borderListener);
  }
  
  protected void uninstallListeners() {
    this.popupMenu.removePropertyChangeListener("JPopupMenu.noMargin", this.borderListener);
    super.uninstallListeners();
  }
  
  private final class BorderStyleChangeHandler implements PropertyChangeListener {
    private final PlasticPopupMenuUI this$0;
    
    private BorderStyleChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent e) {
      PlasticPopupMenuUI.this.installBorder();
    }
  }
  
  private void installBorder() {
    boolean useNarrowBorder = Boolean.TRUE.equals(this.popupMenu.getClientProperty("JPopupMenu.noMargin"));
    String suffix = useNarrowBorder ? "noMarginBorder" : "border";
    LookAndFeel.installBorder(this.popupMenu, "PopupMenu." + suffix);
  }
}
