package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

final class PlasticSplitPaneDivider extends BasicSplitPaneDivider {
  PlasticSplitPaneDivider(BasicSplitPaneUI ui) {
    super(ui);
  }
  
  protected JButton createLeftOneTouchButton() {
    JButton btn = super.createLeftOneTouchButton();
    btn.setOpaque(false);
    return btn;
  }
  
  protected JButton createRightOneTouchButton() {
    JButton btn = super.createRightOneTouchButton();
    btn.setOpaque(false);
    return btn;
  }
  
  public void paint(Graphics g) {
    if (this.splitPane.isOpaque()) {
      Color bgColor = getBackground();
      if (bgColor != null) {
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
      } 
    } 
    super.paint(g);
  }
}
