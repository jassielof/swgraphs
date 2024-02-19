package com.jgoodies.looks.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;

final class WindowsArrowButton extends BasicArrowButton {
  public WindowsArrowButton(int direction) {
    super(direction);
  }
  
  public Dimension getPreferredSize() {
    int width = Math.max(5, UIManager.getInt("ScrollBar.width"));
    return new Dimension(width, width);
  }
  
  public void paintTriangle(Graphics g, int x, int y, int size, int triangleDirection, boolean isEnabled) {
    int i;
    Color oldColor = g.getColor();
    int j = 0;
    size = Math.max(size, 2);
    int mid = (size - 1) / 2;
    g.translate(x, y);
    if (isEnabled) {
      g.setColor(Color.black);
    } else {
      g.setColor(UIManager.getColor("controlShadow"));
    } 
    switch (triangleDirection) {
      case 1:
        for (i = 0; i < size; i++)
          g.drawLine(mid - i, i, mid + i, i); 
        if (!isEnabled) {
          g.setColor(UIManager.getColor("controlLtHighlight"));
          g.drawLine(mid - i + 2, i, mid + i, i);
        } 
        break;
      case 5:
        if (!isEnabled) {
          g.translate(1, 1);
          g.setColor(UIManager.getColor("controlLtHighlight"));
          for (i = size - 1; i >= 0; i--) {
            g.drawLine(mid - i, j, mid + i, j);
            j++;
          } 
          g.translate(-1, -1);
          g.setColor(UIManager.getColor("controlShadow"));
        } 
        j = 0;
        for (i = size - 1; i >= 0; i--) {
          g.drawLine(mid - i, j, mid + i, j);
          j++;
        } 
        break;
      case 7:
        for (i = 0; i < size; i++)
          g.drawLine(i, mid - i, i, mid + i); 
        if (!isEnabled) {
          g.setColor(UIManager.getColor("controlLtHighlight"));
          g.drawLine(i, mid - i + 2, i, mid + i);
        } 
        break;
      case 3:
        if (!isEnabled) {
          g.translate(1, 1);
          g.setColor(UIManager.getColor("controlLtHighlight"));
          for (i = size - 1; i >= 0; i--) {
            g.drawLine(j, mid - i, j, mid + i);
            j++;
          } 
          g.translate(-1, -1);
          g.setColor(UIManager.getColor("controlShadow"));
        } 
        j = 0;
        for (i = size - 1; i >= 0; i--) {
          g.drawLine(j, mid - i, j, mid + i);
          j++;
        } 
        break;
    } 
    g.translate(-x, -y);
    g.setColor(oldColor);
  }
}
