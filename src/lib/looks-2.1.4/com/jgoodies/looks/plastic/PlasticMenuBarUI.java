package com.jgoodies.looks.plastic;

import com.jgoodies.looks.BorderStyle;
import com.jgoodies.looks.HeaderStyle;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

public final class PlasticMenuBarUI extends BasicMenuBarUI {
  private PropertyChangeListener listener;
  
  public static ComponentUI createUI(JComponent b) {
    return new PlasticMenuBarUI();
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
        private final PlasticMenuBarUI this$0;
        
        public void propertyChange(PropertyChangeEvent e) {
          String prop = e.getPropertyName();
          if (prop.equals("jgoodies.headerStyle") || prop.equals("Plastic.borderStyle"))
            PlasticMenuBarUI.this.installSpecialBorder(); 
        }
      };
  }
  
  public void installSpecialBorder() {
    String suffix;
    BorderStyle borderStyle = BorderStyle.from(this.menuBar, "Plastic.borderStyle");
    if (borderStyle == BorderStyle.EMPTY) {
      suffix = "emptyBorder";
    } else if (borderStyle == BorderStyle.ETCHED) {
      suffix = "etchedBorder";
    } else if (borderStyle == BorderStyle.SEPARATOR) {
      suffix = "separatorBorder";
    } else {
      HeaderStyle headerStyle = HeaderStyle.from(this.menuBar);
      if (headerStyle == HeaderStyle.BOTH) {
        suffix = "headerBorder";
      } else if (headerStyle == HeaderStyle.SINGLE && is3D()) {
        suffix = "etchedBorder";
      } else {
        return;
      } 
    } 
    LookAndFeel.installBorder(this.menuBar, "MenuBar." + suffix);
  }
  
  public void update(Graphics g, JComponent c) {
    if (c.isOpaque()) {
      g.setColor(c.getBackground());
      g.fillRect(0, 0, c.getWidth(), c.getHeight());
      if (is3D()) {
        Rectangle bounds = new Rectangle(0, 0, c.getWidth(), c.getHeight());
        PlasticUtils.addLight3DEffekt(g, bounds, true);
      } 
    } 
    paint(g, c);
  }
  
  private boolean is3D() {
    if (PlasticUtils.force3D(this.menuBar))
      return true; 
    if (PlasticUtils.forceFlat(this.menuBar))
      return false; 
    return (PlasticUtils.is3D("MenuBar.") && HeaderStyle.from(this.menuBar) != null && BorderStyle.from(this.menuBar, "Plastic.borderStyle") != BorderStyle.EMPTY);
  }
}
