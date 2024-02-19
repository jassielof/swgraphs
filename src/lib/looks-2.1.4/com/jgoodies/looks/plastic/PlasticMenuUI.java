package com.jgoodies.looks.plastic;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.common.ExtBasicMenuUI;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;

public final class PlasticMenuUI extends ExtBasicMenuUI {
  private boolean oldOpaque;
  
  public static ComponentUI createUI(JComponent b) {
    return (ComponentUI)new PlasticMenuUI();
  }
  
  protected void installDefaults() {
    super.installDefaults();
    this.oldOpaque = this.menuItem.isOpaque();
  }
  
  protected void uninstallDefaults() {
    super.uninstallDefaults();
    if (!LookUtils.IS_OS_WINDOWS_VISTA && !LookUtils.IS_JAVA_6_OR_LATER)
      this.menuItem.setOpaque(this.oldOpaque); 
  }
  
  protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon, Icon anArrowIcon, Color background, Color foreground, int textIconGap) {
    JMenuItem b = (JMenuItem)c;
    if (((JMenu)this.menuItem).isTopLevelMenu()) {
      b.setOpaque(false);
      if (b.getModel().isSelected()) {
        int menuWidth = this.menuItem.getWidth();
        int menuHeight = this.menuItem.getHeight();
        Color oldColor = g.getColor();
        g.setColor(background);
        g.fillRect(0, 0, menuWidth, menuHeight);
        g.setColor(oldColor);
      } 
    } 
    super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
  }
}
