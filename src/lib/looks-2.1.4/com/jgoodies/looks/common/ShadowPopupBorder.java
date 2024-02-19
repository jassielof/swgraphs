package com.jgoodies.looks.common;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.border.AbstractBorder;

final class ShadowPopupBorder extends AbstractBorder {
  private static final int SHADOW_SIZE = 5;
  
  private static ShadowPopupBorder instance = new ShadowPopupBorder();
  
  private static Image shadow = (new ImageIcon(ShadowPopupBorder.class.getResource("shadow.png"))).getImage();
  
  public static ShadowPopupBorder getInstance() {
    return instance;
  }
  
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    JComponent popup = (JComponent)c;
    Image hShadowBg = (Image)popup.getClientProperty("jgoodies.hShadowBg");
    if (hShadowBg != null)
      g.drawImage(hShadowBg, x, y + height - 5, c); 
    Image vShadowBg = (Image)popup.getClientProperty("jgoodies.vShadowBg");
    if (vShadowBg != null)
      g.drawImage(vShadowBg, x + width - 5, y, c); 
    g.drawImage(shadow, x + 5, y + height - 5, x + 10, y + height, 0, 6, 5, 11, null, c);
    g.drawImage(shadow, x + 10, y + height - 5, x + width - 5, y + height, 5, 6, 6, 11, null, c);
    g.drawImage(shadow, x + width - 5, y + 5, x + width, y + 10, 6, 0, 11, 5, null, c);
    g.drawImage(shadow, x + width - 5, y + 10, x + width, y + height - 5, 6, 5, 11, 6, null, c);
    g.drawImage(shadow, x + width - 5, y + height - 5, x + width, y + height, 6, 6, 11, 11, null, c);
  }
  
  public Insets getBorderInsets(Component c) {
    return new Insets(0, 0, 5, 5);
  }
  
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.left = insets.top = 0;
    insets.right = insets.bottom = 5;
    return insets;
  }
}
