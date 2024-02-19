package com.jgoodies.looks.plastic;

import java.awt.Component;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;

public final class PlasticTreeUI extends BasicTreeUI {
  private boolean linesEnabled = true;
  
  private PropertyChangeListener lineStyleHandler;
  
  public static ComponentUI createUI(JComponent b) {
    return new PlasticTreeUI();
  }
  
  public void installUI(JComponent c) {
    super.installUI(c);
    updateLineStyle(c.getClientProperty("JTree.lineStyle"));
    this.lineStyleHandler = new LineStyleHandler();
    c.addPropertyChangeListener(this.lineStyleHandler);
  }
  
  public void uninstallUI(JComponent c) {
    c.removePropertyChangeListener(this.lineStyleHandler);
    super.uninstallUI(c);
  }
  
  protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
    if (this.linesEnabled)
      drawDashedVerticalLine(g, x, top, bottom); 
  }
  
  protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
    if (this.linesEnabled)
      drawDashedHorizontalLine(g, y, left, right); 
  }
  
  protected void drawCentered(Component c, Graphics graphics, Icon icon, int x, int y) {
    icon.paintIcon(c, graphics, x - icon.getIconWidth() / 2 - 1, y - icon.getIconHeight() / 2);
  }
  
  private void updateLineStyle(Object lineStyle) {
    this.linesEnabled = !"None".equals(lineStyle);
  }
  
  private class LineStyleHandler implements PropertyChangeListener {
    private final PlasticTreeUI this$0;
    
    private LineStyleHandler() {}
    
    public void propertyChange(PropertyChangeEvent e) {
      String name = e.getPropertyName();
      Object value = e.getNewValue();
      if (name.equals("JTree.lineStyle"))
        PlasticTreeUI.this.updateLineStyle(value); 
    }
  }
}
