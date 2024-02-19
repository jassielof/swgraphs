package com.jgoodies.looks.plastic;

import com.jgoodies.looks.common.ExtBasicArrowButtonHandler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

public final class PlasticXPSpinnerUI extends PlasticSpinnerUI {
  public static ComponentUI createUI(JComponent b) {
    return new PlasticXPSpinnerUI();
  }
  
  private static final ExtBasicArrowButtonHandler NEXT_BUTTON_HANDLER = new ExtBasicArrowButtonHandler("increment", true);
  
  private static final ExtBasicArrowButtonHandler PREVIOUS_BUTTON_HANDLER = new ExtBasicArrowButtonHandler("decrement", false);
  
  protected Component createPreviousButton() {
    return new SpinnerXPArrowButton(5, PREVIOUS_BUTTON_HANDLER);
  }
  
  protected Component createNextButton() {
    return new SpinnerXPArrowButton(1, NEXT_BUTTON_HANDLER);
  }
  
  private static final class SpinnerXPArrowButton extends PlasticArrowButton {
    SpinnerXPArrowButton(int direction, ExtBasicArrowButtonHandler handler) {
      super(direction, UIManager.getInt("ScrollBar.width") - 1, false);
      addActionListener((ActionListener)handler);
      addMouseListener((MouseListener)handler);
    }
    
    protected int calculateArrowHeight(int height, int width) {
      int arrowHeight = Math.min((height - 4) / 3, (width - 4) / 3);
      return Math.max(arrowHeight, 3);
    }
    
    protected boolean isPaintingNorthBottom() {
      return true;
    }
    
    protected int calculateArrowOffset() {
      return 1;
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
      int startY = 1 + (h + 1 - arrowHeight) / 2;
      int startX = w / 2;
      for (int line = 0; line < arrowHeight; line++)
        g.fillRect(startX - line - arrowOffset, startY + line, 2 * (line + 1), 1); 
      paintNorthBorder(g, isEnabled, width, height, paintBottom);
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
    
    private void paintNorthBorder(Graphics g, boolean isEnabled, int w, int h, boolean paintBottom) {
      if (isEnabled) {
        boolean isPressed = (this.model.isPressed() && this.model.isArmed());
        if (isPressed) {
          PlasticXPUtils.drawPressedButtonBorder(g, 0, 1, w - 2, h);
        } else {
          PlasticXPUtils.drawPlainButtonBorder(g, 0, 1, w - 2, h);
        } 
      } else {
        PlasticXPUtils.drawDisabledButtonBorder(g, 0, 1, w - 2, h + 1);
      } 
      g.setColor(isEnabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
      g.fillRect(0, 1, 1, 1);
      if (paintBottom)
        g.fillRect(0, h - 1, w - 1, 1); 
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
      int startY = (h + 0 - arrowHeight) / 2 + arrowHeight - 2;
      int startX = w / 2;
      for (int line = 0; line < arrowHeight; line++)
        g.fillRect(startX - line - arrowOffset, startY - line, 2 * (line + 1), 1); 
      paintSouthBorder(g, isEnabled, width, height);
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
    
    private void paintSouthBorder(Graphics g, boolean isEnabled, int w, int h) {
      if (isEnabled) {
        boolean isPressed = (this.model.isPressed() && this.model.isArmed());
        if (isPressed) {
          PlasticXPUtils.drawPressedButtonBorder(g, 0, -2, w - 2, h + 1);
        } else {
          PlasticXPUtils.drawPlainButtonBorder(g, 0, -2, w - 2, h + 1);
        } 
      } else {
        PlasticXPUtils.drawDisabledButtonBorder(g, 0, -2, w - 2, h + 1);
      } 
      g.setColor(isEnabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlShadow());
      g.fillRect(0, h - 2, 1, 1);
    }
  }
}
