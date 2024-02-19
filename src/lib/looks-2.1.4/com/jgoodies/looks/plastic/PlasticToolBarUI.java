package com.jgoodies.looks.plastic;

import com.jgoodies.looks.BorderStyle;
import com.jgoodies.looks.HeaderStyle;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.LookAndFeel;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolBarUI;

public class PlasticToolBarUI extends MetalToolBarUI {
  private static final String PROPERTY_PREFIX = "ToolBar.";
  
  private PropertyChangeListener listener;
  
  public static ComponentUI createUI(JComponent b) {
    return new PlasticToolBarUI();
  }
  
  protected Border createRolloverBorder() {
    return PlasticBorders.getRolloverButtonBorder();
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
        private final PlasticToolBarUI this$0;
        
        public void propertyChange(PropertyChangeEvent e) {
          String prop = e.getPropertyName();
          if (prop.equals("jgoodies.headerStyle") || prop.equals("Plastic.borderStyle"))
            PlasticToolBarUI.this.installSpecialBorder(); 
        }
      };
  }
  
  private void installSpecialBorder() {
    String suffix;
    BorderStyle borderStyle = BorderStyle.from(this.toolBar, "Plastic.borderStyle");
    if (borderStyle == BorderStyle.EMPTY) {
      suffix = "emptyBorder";
    } else if (borderStyle == BorderStyle.ETCHED) {
      suffix = "etchedBorder";
    } else if (borderStyle == BorderStyle.SEPARATOR) {
      suffix = "separatorBorder";
    } else {
      HeaderStyle headerStyle = HeaderStyle.from(this.toolBar);
      if (headerStyle == HeaderStyle.BOTH) {
        suffix = "headerBorder";
      } else if (headerStyle == HeaderStyle.SINGLE && is3D()) {
        suffix = "etchedBorder";
      } else {
        suffix = "border";
      } 
    } 
    LookAndFeel.installBorder(this.toolBar, "ToolBar." + suffix);
  }
  
  public void update(Graphics g, JComponent c) {
    if (c.isOpaque()) {
      g.setColor(c.getBackground());
      g.fillRect(0, 0, c.getWidth(), c.getHeight());
      if (is3D()) {
        Rectangle bounds = new Rectangle(0, 0, c.getWidth(), c.getHeight());
        boolean isHorizontal = (((JToolBar)c).getOrientation() == 0);
        PlasticUtils.addLight3DEffekt(g, bounds, isHorizontal);
      } 
    } 
    paint(g, c);
  }
  
  private boolean is3D() {
    if (PlasticUtils.force3D(this.toolBar))
      return true; 
    if (PlasticUtils.forceFlat(this.toolBar))
      return false; 
    return (PlasticUtils.is3D("ToolBar.") && HeaderStyle.from(this.toolBar) != null && BorderStyle.from(this.toolBar, "Plastic.borderStyle") != BorderStyle.EMPTY);
  }
}
