package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

final class BumpBuffer {
  static final int IMAGE_SIZE = 64;
  
  static Dimension imageSize = new Dimension(64, 64);
  
  transient Image image;
  
  Color topColor;
  
  Color shadowColor;
  
  Color backColor;
  
  private GraphicsConfiguration gc;
  
  public BumpBuffer(GraphicsConfiguration gc, Color aTopColor, Color aShadowColor, Color aBackColor) {
    this.gc = gc;
    this.topColor = aTopColor;
    this.shadowColor = aShadowColor;
    this.backColor = aBackColor;
    createImage();
    fillBumpBuffer();
  }
  
  public boolean hasSameConfiguration(GraphicsConfiguration aGC, Color aTopColor, Color aShadowColor, Color aBackColor) {
    if (this.gc != null) {
      if (!this.gc.equals(aGC))
        return false; 
    } else if (aGC != null) {
      return false;
    } 
    return (this.topColor.equals(aTopColor) && this.shadowColor.equals(aShadowColor) && this.backColor.equals(aBackColor));
  }
  
  public Image getImage() {
    return this.image;
  }
  
  public Dimension getImageSize() {
    return imageSize;
  }
  
  private void fillBumpBuffer() {
    Graphics g = this.image.getGraphics();
    g.setColor(this.backColor);
    g.fillRect(0, 0, 64, 64);
    g.setColor(this.topColor);
    int x;
    for (x = 0; x < 64; x += 4) {
      for (int y = 0; y < 64; y += 4) {
        g.drawLine(x, y, x, y);
        g.drawLine(x + 2, y + 2, x + 2, y + 2);
      } 
    } 
    g.setColor(this.shadowColor);
    for (x = 0; x < 64; x += 4) {
      for (int y = 0; y < 64; y += 4) {
        g.drawLine(x + 1, y + 1, x + 1, y + 1);
        g.drawLine(x + 3, y + 3, x + 3, y + 3);
      } 
    } 
    g.dispose();
  }
  
  private void createImage() {
    if (this.gc != null) {
      this.image = this.gc.createCompatibleImage(64, 64);
    } else {
      int[] cmap = { this.backColor.getRGB(), this.topColor.getRGB(), this.shadowColor.getRGB() };
      IndexColorModel icm = new IndexColorModel(8, 3, cmap, 0, false, -1, 0);
      this.image = new BufferedImage(64, 64, 13, icm);
    } 
  }
}
