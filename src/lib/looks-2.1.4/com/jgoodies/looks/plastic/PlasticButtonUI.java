package com.jgoodies.looks.plastic;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class PlasticButtonUI extends MetalButtonUI {
  private static final PlasticButtonUI INSTANCE = new PlasticButtonUI();
  
  private boolean borderPaintsFocus;
  
  public static ComponentUI createUI(JComponent b) {
    return INSTANCE;
  }
  
  public void installDefaults(AbstractButton b) {
    super.installDefaults(b);
    this.borderPaintsFocus = Boolean.TRUE.equals(UIManager.get("Button.borderPaintsFocus"));
  }
  
  public void update(Graphics g, JComponent c) {
    if (c.isOpaque()) {
      AbstractButton b = (AbstractButton)c;
      if (isToolBarButton(b)) {
        c.setOpaque(false);
      } else if (b.isContentAreaFilled()) {
        g.setColor(c.getBackground());
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
        if (is3D(b)) {
          Rectangle r = new Rectangle(1, 1, c.getWidth() - 2, c.getHeight() - 1);
          PlasticUtils.add3DEffekt(g, r);
        } 
      } 
    } 
    paint(g, c);
  }
  
  protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    if (this.borderPaintsFocus)
      return; 
    boolean isDefault = (b instanceof JButton && ((JButton)b).isDefaultButton());
    int topLeftInset = isDefault ? 3 : 2;
    int width = b.getWidth() - 1 - topLeftInset * 2;
    int height = b.getHeight() - 1 - topLeftInset * 2;
    g.setColor(getFocusColor());
    g.drawRect(topLeftInset, topLeftInset, width - 1, height - 1);
  }
  
  protected boolean isToolBarButton(AbstractButton b) {
    Container parent = b.getParent();
    return (parent != null && (parent instanceof javax.swing.JToolBar || parent.getParent() instanceof javax.swing.JToolBar));
  }
  
  protected boolean is3D(AbstractButton b) {
    if (PlasticUtils.force3D(b))
      return true; 
    if (PlasticUtils.forceFlat(b))
      return false; 
    ButtonModel model = b.getModel();
    return (PlasticUtils.is3D("Button.") && b.isBorderPainted() && model.isEnabled() && (!model.isPressed() || !model.isArmed()));
  }
}
