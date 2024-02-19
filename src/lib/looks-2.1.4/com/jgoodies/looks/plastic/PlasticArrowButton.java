package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ButtonModel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalScrollButton;

class PlasticArrowButton extends MetalScrollButton {
  private Color shadowColor;
  
  private Color highlightColor;
  
  protected boolean isFreeStanding;
  
  public PlasticArrowButton(int direction, int width, boolean freeStanding) {
    super(direction, width, freeStanding);
    this.shadowColor = UIManager.getColor("ScrollBar.darkShadow");
    this.highlightColor = UIManager.getColor("ScrollBar.highlight");
    this.isFreeStanding = freeStanding;
  }
  
  public void setFreeStanding(boolean freeStanding) {
    super.setFreeStanding(freeStanding);
    this.isFreeStanding = freeStanding;
  }
  
  public void paint(Graphics g) {
    boolean leftToRight = PlasticUtils.isLeftToRight(this);
    boolean isEnabled = getParent().isEnabled();
    boolean isPressed = getModel().isPressed();
    Color arrowColor = isEnabled ? PlasticLookAndFeel.getControlInfo() : PlasticLookAndFeel.getControlDisabled();
    int width = getWidth();
    int height = getHeight();
    int w = width;
    int h = height;
    int arrowHeight = calculateArrowHeight(height, width);
    int arrowOffset = calculateArrowOffset();
    boolean paintNorthBottom = isPaintingNorthBottom();
    g.setColor(isPressed ? PlasticLookAndFeel.getControlShadow() : getBackground());
    g.fillRect(0, 0, width, height);
    if (getDirection() == 1) {
      paintNorth(g, leftToRight, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight, arrowOffset, paintNorthBottom);
    } else if (getDirection() == 5) {
      paintSouth(g, leftToRight, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight, arrowOffset);
    } else if (getDirection() == 3) {
      paintEast(g, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight);
    } else if (getDirection() == 7) {
      paintWest(g, isEnabled, arrowColor, isPressed, width, height, w, h, arrowHeight);
    } 
    if (PlasticUtils.is3D("ScrollBar."))
      paint3D(g); 
  }
  
  protected int calculateArrowHeight(int height, int width) {
    return (height + 1) / 4;
  }
  
  protected int calculateArrowOffset() {
    return 0;
  }
  
  protected boolean isPaintingNorthBottom() {
    return false;
  }
  
  private void paintWest(Graphics g, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight) {
    if (!this.isFreeStanding) {
      height += 2;
      width++;
      g.translate(-1, 0);
    } 
    g.setColor(arrowColor);
    int startX = (w + 1 - arrowHeight) / 2;
    int startY = h / 2;
    for (int line = 0; line < arrowHeight; line++)
      g.drawLine(startX + line, startY - line, startX + line, startY + line + 1); 
    if (isEnabled) {
      g.setColor(this.highlightColor);
      if (!isPressed) {
        g.drawLine(1, 1, width - 1, 1);
        g.drawLine(1, 1, 1, height - 3);
      } 
      g.drawLine(1, height - 1, width - 1, height - 1);
      g.setColor(this.shadowColor);
      g.drawLine(0, 0, width - 1, 0);
      g.drawLine(0, 0, 0, height - 2);
      g.drawLine(1, height - 2, width - 1, height - 2);
    } else {
      PlasticUtils.drawDisabledBorder(g, 0, 0, width + 1, height);
    } 
    if (!this.isFreeStanding) {
      height -= 2;
      width--;
      g.translate(1, 0);
    } 
  }
  
  private void paintEast(Graphics g, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight) {
    if (!this.isFreeStanding) {
      height += 2;
      width++;
    } 
    g.setColor(arrowColor);
    int startX = (w + 1 - arrowHeight) / 2 + arrowHeight - 1;
    int startY = h / 2;
    for (int line = 0; line < arrowHeight; line++)
      g.drawLine(startX - line, startY - line, startX - line, startY + line + 1); 
    if (isEnabled) {
      g.setColor(this.highlightColor);
      if (!isPressed) {
        g.drawLine(0, 1, width - 3, 1);
        g.drawLine(0, 1, 0, height - 3);
      } 
      g.drawLine(width - 1, 1, width - 1, height - 1);
      g.drawLine(0, height - 1, width - 1, height - 1);
      g.setColor(this.shadowColor);
      g.drawLine(0, 0, width - 2, 0);
      g.drawLine(width - 2, 1, width - 2, height - 2);
      g.drawLine(0, height - 2, width - 2, height - 2);
    } else {
      PlasticUtils.drawDisabledBorder(g, -1, 0, width + 1, height);
    } 
    if (!this.isFreeStanding) {
      height -= 2;
      width--;
    } 
  }
  
  protected void paintSouth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset) {
    if (!this.isFreeStanding) {
      height++;
      if (!leftToRight) {
        width++;
        g.translate(-1, 0);
      } else {
        width += 2;
      } 
    } 
    g.setColor(arrowColor);
    int startY = (h + 0 - arrowHeight) / 2 + arrowHeight - 1;
    int startX = w / 2;
    for (int line = 0; line < arrowHeight; line++)
      g.fillRect(startX - line - arrowOffset, startY - line, 2 * (line + 1), 1); 
    if (isEnabled) {
      g.setColor(this.highlightColor);
      if (!isPressed) {
        g.drawLine(1, 0, width - 3, 0);
        g.drawLine(1, 0, 1, height - 3);
      } 
      g.drawLine(0, height - 1, width - 1, height - 1);
      g.drawLine(width - 1, 0, width - 1, height - 1);
      g.setColor(this.shadowColor);
      g.drawLine(0, 0, 0, height - 2);
      g.drawLine(width - 2, 0, width - 2, height - 2);
      g.drawLine(1, height - 2, width - 2, height - 2);
    } else {
      PlasticUtils.drawDisabledBorder(g, 0, -1, width, height + 1);
    } 
    if (!this.isFreeStanding) {
      height--;
      if (!leftToRight) {
        width--;
        g.translate(1, 0);
      } else {
        width -= 2;
      } 
    } 
  }
  
  protected void paintNorth(Graphics g, boolean leftToRight, boolean isEnabled, Color arrowColor, boolean isPressed, int width, int height, int w, int h, int arrowHeight, int arrowOffset, boolean paintBottom) {
    if (!this.isFreeStanding) {
      height++;
      g.translate(0, -1);
      if (!leftToRight) {
        width++;
        g.translate(-1, 0);
      } else {
        width += 2;
      } 
    } 
    g.setColor(arrowColor);
    int startY = (h + 1 - arrowHeight) / 2;
    int startX = w / 2;
    for (int line = 0; line < arrowHeight; line++)
      g.fillRect(startX - line - arrowOffset, startY + line, 2 * (line + 1), 1); 
    if (isEnabled) {
      g.setColor(this.highlightColor);
      if (!isPressed) {
        g.drawLine(1, 1, width - 3, 1);
        g.drawLine(1, 1, 1, height - 1);
      } 
      g.drawLine(width - 1, 1, width - 1, height - 1);
      g.setColor(this.shadowColor);
      g.drawLine(0, 0, width - 2, 0);
      g.drawLine(0, 0, 0, height - 1);
      g.drawLine(width - 2, 1, width - 2, height - 1);
      if (paintBottom)
        g.fillRect(0, height - 1, width - 1, 1); 
    } else {
      PlasticUtils.drawDisabledBorder(g, 0, 0, width, height + 1);
      if (paintBottom) {
        g.setColor(PlasticLookAndFeel.getControlShadow());
        g.fillRect(0, height - 1, width - 1, 1);
      } 
    } 
    if (!this.isFreeStanding) {
      height--;
      g.translate(0, 1);
      if (!leftToRight) {
        width--;
        g.translate(1, 0);
      } else {
        width -= 2;
      } 
    } 
  }
  
  private void paint3D(Graphics g) {
    ButtonModel buttonModel = getModel();
    if ((buttonModel.isArmed() && buttonModel.isPressed()) || buttonModel.isSelected())
      return; 
    int width = getWidth();
    int height = getHeight();
    if (getDirection() == 3) {
      width -= 2;
    } else if (getDirection() == 5) {
      height -= 2;
    } 
    Rectangle r = new Rectangle(1, 1, width, height);
    boolean isHorizontal = (getDirection() == 3 || getDirection() == 7);
    PlasticUtils.addLight3DEffekt(g, r, isHorizontal);
  }
}
