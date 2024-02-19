package com.jgoodies.looks.common;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.Popup;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public final class ShadowPopup extends Popup {
  private static final int MAX_CACHE_SIZE = 5;
  
  private static List cache;
  
  private static final Border SHADOW_BORDER = ShadowPopupBorder.getInstance();
  
  private static final int SHADOW_SIZE = 5;
  
  private static boolean canSnapshot = true;
  
  private Component owner;
  
  private Component contents;
  
  private int x;
  
  private int y;
  
  private Popup popup;
  
  private Border oldBorder;
  
  private boolean oldOpaque;
  
  private Container heavyWeightContainer;
  
  static Popup getInstance(Component owner, Component contents, int x, int y, Popup delegate) {
    ShadowPopup result;
    synchronized (ShadowPopup.class) {
      if (cache == null)
        cache = new ArrayList(5); 
      if (cache.size() > 0) {
        result = cache.remove(0);
      } else {
        result = new ShadowPopup();
      } 
    } 
    result.reset(owner, contents, x, y, delegate);
    return result;
  }
  
  private static void recycle(ShadowPopup popup) {
    synchronized (ShadowPopup.class) {
      if (cache.size() < 5)
        cache.add(popup); 
    } 
  }
  
  public static boolean canSnapshot() {
    return canSnapshot;
  }
  
  public void hide() {
    if (this.contents == null)
      return; 
    JComponent parent = (JComponent)this.contents.getParent();
    this.popup.hide();
    if (parent != null && parent.getBorder() == SHADOW_BORDER) {
      parent.setBorder(this.oldBorder);
      parent.setOpaque(this.oldOpaque);
      this.oldBorder = null;
      if (this.heavyWeightContainer != null) {
        parent.putClientProperty("jgoodies.hShadowBg", (Object)null);
        parent.putClientProperty("jgoodies.vShadowBg", (Object)null);
        this.heavyWeightContainer = null;
      } 
    } 
    this.owner = null;
    this.contents = null;
    this.popup = null;
    recycle(this);
  }
  
  public void show() {
    if (this.heavyWeightContainer != null)
      snapshot(); 
    this.popup.show();
  }
  
  private void reset(Component owner, Component contents, int x, int y, Popup popup) {
    this.owner = owner;
    this.contents = contents;
    this.popup = popup;
    this.x = x;
    this.y = y;
    if (owner instanceof javax.swing.JComboBox)
      return; 
    Dimension contentsPrefSize = contents.getPreferredSize();
    if (contentsPrefSize.width <= 0 || contentsPrefSize.height <= 0)
      return; 
    for (Container p = contents.getParent(); p != null; p = p.getParent()) {
      if (p instanceof javax.swing.JWindow || p instanceof java.awt.Panel) {
        p.setBackground(contents.getBackground());
        this.heavyWeightContainer = p;
        break;
      } 
    } 
    JComponent parent = (JComponent)contents.getParent();
    this.oldOpaque = parent.isOpaque();
    this.oldBorder = parent.getBorder();
    parent.setOpaque(false);
    parent.setBorder(SHADOW_BORDER);
    if (this.heavyWeightContainer != null) {
      this.heavyWeightContainer.setSize(this.heavyWeightContainer.getPreferredSize());
    } else {
      parent.setSize(parent.getPreferredSize());
    } 
  }
  
  private static final Point POINT = new Point();
  
  private static final Rectangle RECT = new Rectangle();
  
  private void snapshot() {
    try {
      Dimension size = this.heavyWeightContainer.getPreferredSize();
      int width = size.width;
      int height = size.height;
      if (width <= 0 || height <= 5)
        return; 
      Robot robot = new Robot();
      RECT.setBounds(this.x, this.y + height - 5, width, 5);
      BufferedImage hShadowBg = robot.createScreenCapture(RECT);
      RECT.setBounds(this.x + width - 5, this.y, 5, height - 5);
      BufferedImage vShadowBg = robot.createScreenCapture(RECT);
      JComponent parent = (JComponent)this.contents.getParent();
      parent.putClientProperty("jgoodies.hShadowBg", hShadowBg);
      parent.putClientProperty("jgoodies.vShadowBg", vShadowBg);
      Container layeredPane = getLayeredPane();
      if (layeredPane == null)
        return; 
      int layeredPaneWidth = layeredPane.getWidth();
      int layeredPaneHeight = layeredPane.getHeight();
      POINT.x = this.x;
      POINT.y = this.y;
      SwingUtilities.convertPointFromScreen(POINT, layeredPane);
      RECT.x = POINT.x;
      RECT.y = POINT.y + height - 5;
      RECT.width = width;
      RECT.height = 5;
      if (RECT.x + RECT.width > layeredPaneWidth)
        RECT.width = layeredPaneWidth - RECT.x; 
      if (RECT.y + RECT.height > layeredPaneHeight)
        RECT.height = layeredPaneHeight - RECT.y; 
      if (!RECT.isEmpty()) {
        Graphics g = hShadowBg.createGraphics();
        g.translate(-RECT.x, -RECT.y);
        g.setClip(RECT);
        if (layeredPane instanceof JComponent) {
          JComponent c = (JComponent)layeredPane;
          boolean doubleBuffered = c.isDoubleBuffered();
          c.setDoubleBuffered(false);
          c.paintAll(g);
          c.setDoubleBuffered(doubleBuffered);
        } else {
          layeredPane.paintAll(g);
        } 
        g.dispose();
      } 
      RECT.x = POINT.x + width - 5;
      RECT.y = POINT.y;
      RECT.width = 5;
      RECT.height = height - 5;
      if (RECT.x + RECT.width > layeredPaneWidth)
        RECT.width = layeredPaneWidth - RECT.x; 
      if (RECT.y + RECT.height > layeredPaneHeight)
        RECT.height = layeredPaneHeight - RECT.y; 
      if (!RECT.isEmpty()) {
        Graphics g = vShadowBg.createGraphics();
        g.translate(-RECT.x, -RECT.y);
        g.setClip(RECT);
        if (layeredPane instanceof JComponent) {
          JComponent c = (JComponent)layeredPane;
          boolean doubleBuffered = c.isDoubleBuffered();
          c.setDoubleBuffered(false);
          c.paintAll(g);
          c.setDoubleBuffered(doubleBuffered);
        } else {
          layeredPane.paintAll(g);
        } 
        g.dispose();
      } 
    } catch (AWTException e) {
      canSnapshot = false;
    } catch (SecurityException e) {
      canSnapshot = false;
    } 
  }
  
  private Container getLayeredPane() {
    Container parent = null;
    if (this.owner != null)
      parent = (this.owner instanceof Container) ? (Container)this.owner : this.owner.getParent(); 
    for (Container p = parent; p != null; p = p.getParent()) {
      if (p instanceof JRootPane) {
        if (!(p.getParent() instanceof javax.swing.JInternalFrame))
          parent = ((JRootPane)p).getLayeredPane(); 
      } else {
        if (p instanceof java.awt.Window) {
          if (parent == null)
            parent = p; 
          break;
        } 
        if (p instanceof javax.swing.JApplet)
          break; 
      } 
    } 
    return parent;
  }
}
