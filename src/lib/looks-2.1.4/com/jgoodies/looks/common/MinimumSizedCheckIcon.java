package com.jgoodies.looks.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public final class MinimumSizedCheckIcon extends MinimumSizedIcon {
  private final JMenuItem menuItem;
  
  public MinimumSizedCheckIcon(Icon icon, JMenuItem menuItem) {
    super(icon);
    this.menuItem = menuItem;
  }
  
  public void paintIcon(Component c, Graphics g, int x, int y) {
    paintState(g, x, y);
    super.paintIcon(c, g, x, y);
  }
  
  private void paintState(Graphics g, int x, int y) {
    ButtonModel model = this.menuItem.getModel();
    int w = getIconWidth();
    int h = getIconHeight();
    g.translate(x, y);
    if (model.isSelected() || model.isArmed()) {
      Color background = model.isArmed() ? UIManager.getColor("MenuItem.background") : UIManager.getColor("ScrollBar.track");
      Color upColor = UIManager.getColor("controlLtHighlight");
      Color downColor = UIManager.getColor("controlDkShadow");
      g.setColor(background);
      g.fillRect(0, 0, w, h);
      g.setColor(model.isSelected() ? downColor : upColor);
      g.drawLine(0, 0, w - 2, 0);
      g.drawLine(0, 0, 0, h - 2);
      g.setColor(model.isSelected() ? upColor : downColor);
      g.drawLine(0, h - 1, w - 1, h - 1);
      g.drawLine(w - 1, 0, w - 1, h - 1);
    } 
    g.translate(-x, -y);
    g.setColor(UIManager.getColor("textText"));
  }
}
