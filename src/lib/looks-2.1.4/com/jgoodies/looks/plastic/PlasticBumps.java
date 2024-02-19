package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.Icon;

final class PlasticBumps implements Icon {
  protected int xBumps;
  
  protected int yBumps;
  
  protected Color topColor;
  
  protected Color shadowColor;
  
  protected Color backColor;
  
  protected static Vector buffers = new Vector();
  
  protected BumpBuffer buffer;
  
  public PlasticBumps(Dimension bumpArea) {
    this(bumpArea.width, bumpArea.height);
  }
  
  public PlasticBumps(int width, int height) {
    this(width, height, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlDarkShadow(), PlasticLookAndFeel.getPrimaryControlShadow());
  }
  
  public PlasticBumps(int width, int height, Color newTopColor, Color newShadowColor, Color newBackColor) {
    setBumpArea(width, height);
    setBumpColors(newTopColor, newShadowColor, newBackColor);
  }
  
  private BumpBuffer getBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor) {
    if (this.buffer != null && this.buffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor))
      return this.buffer; 
    BumpBuffer result = null;
    Enumeration elements = buffers.elements();
    while (elements.hasMoreElements()) {
      BumpBuffer aBuffer = elements.nextElement();
      if (aBuffer.hasSameConfiguration(gc, aTopColor, aShadowColor, aBackColor)) {
        result = aBuffer;
        break;
      } 
    } 
    if (result == null) {
      result = new BumpBuffer(gc, this.topColor, this.shadowColor, this.backColor);
      buffers.addElement(result);
    } 
    return result;
  }
  
  public void setBumpArea(Dimension bumpArea) {
    setBumpArea(bumpArea.width, bumpArea.height);
  }
  
  public void setBumpArea(int width, int height) {
    this.xBumps = width / 2;
    this.yBumps = height / 2;
  }
  
  public void setBumpColors(Color newTopColor, Color newShadowColor, Color newBackColor) {
    this.topColor = newTopColor;
    this.shadowColor = newShadowColor;
    this.backColor = newBackColor;
  }
  
  public void paintIcon(Component c, Graphics g, int x, int y) {
    GraphicsConfiguration gc = (g instanceof Graphics2D) ? ((Graphics2D)g).getDeviceConfiguration() : null;
    this.buffer = getBuffer(gc, this.topColor, this.shadowColor, this.backColor);
    int bufferWidth = (this.buffer.getImageSize()).width;
    int bufferHeight = (this.buffer.getImageSize()).height;
    int iconWidth = getIconWidth();
    int iconHeight = getIconHeight();
    int x2 = x + iconWidth;
    int y2 = y + iconHeight;
    int savex = x;
    while (y < y2) {
      int h = Math.min(y2 - y, bufferHeight);
      for (x = savex; x < x2; x += bufferWidth) {
        int w = Math.min(x2 - x, bufferWidth);
        g.drawImage(this.buffer.getImage(), x, y, x + w, y + h, 0, 0, w, h, null);
      } 
      y += bufferHeight;
    } 
  }
  
  public int getIconWidth() {
    return this.xBumps * 2;
  }
  
  public int getIconHeight() {
    return this.yBumps * 2;
  }
}
