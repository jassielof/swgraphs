package com.jgoodies.looks.plastic;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import javax.swing.text.View;

public class PlasticToggleButtonUI extends MetalToggleButtonUI {
  private static final PlasticToggleButtonUI INSTANCE = new PlasticToggleButtonUI();
  
  protected static final String HTML_KEY = "html";
  
  private boolean borderPaintsFocus;
  
  public static ComponentUI createUI(JComponent b) {
    return INSTANCE;
  }
  
  public void installDefaults(AbstractButton b) {
    super.installDefaults(b);
    this.borderPaintsFocus = Boolean.TRUE.equals(UIManager.get("ToggleButton.borderPaintsFocus"));
  }
  
  public void update(Graphics g, JComponent c) {
    AbstractButton b = (AbstractButton)c;
    if (c.isOpaque())
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
    paint(g, c);
  }
  
  protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    if (this.borderPaintsFocus)
      return; 
    boolean isDefault = false;
    int topLeftInset = isDefault ? 3 : 2;
    int width = b.getWidth() - 1 - topLeftInset * 2;
    int height = b.getHeight() - 1 - topLeftInset * 2;
    g.setColor(getFocusColor());
    g.drawRect(topLeftInset, topLeftInset, width - 1, height - 1);
  }
  
  public void paint(Graphics g, JComponent c) {
    AbstractButton b = (AbstractButton)c;
    ButtonModel model = b.getModel();
    Dimension size = b.getSize();
    FontMetrics fm = g.getFontMetrics();
    Insets i = c.getInsets();
    Rectangle viewRect = new Rectangle(size);
    viewRect.x += i.left;
    viewRect.y += i.top;
    viewRect.width -= i.right + viewRect.x;
    viewRect.height -= i.bottom + viewRect.y;
    Rectangle iconRect = new Rectangle();
    Rectangle textRect = new Rectangle();
    Font f = c.getFont();
    g.setFont(f);
    String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, (b.getText() == null) ? 0 : b.getIconTextGap());
    g.setColor(b.getBackground());
    if ((model.isArmed() && model.isPressed()) || model.isSelected())
      paintButtonPressed(g, b); 
    if (b.getIcon() != null)
      paintIcon(g, b, iconRect); 
    if (text != null && !text.equals("")) {
      View v = (View)c.getClientProperty("html");
      if (v != null) {
        v.paint(g, textRect);
      } else {
        paintText(g, c, textRect, text);
      } 
    } 
    if (b.isFocusPainted() && b.hasFocus())
      paintFocus(g, b, viewRect, textRect, iconRect); 
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
    return (PlasticUtils.is3D("ToggleButton.") && b.isBorderPainted() && model.isEnabled() && !model.isPressed());
  }
}
