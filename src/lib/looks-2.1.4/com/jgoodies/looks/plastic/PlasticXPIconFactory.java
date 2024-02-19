package com.jgoodies.looks.plastic;

import com.jgoodies.looks.LookUtils;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.Serializable;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

public final class PlasticXPIconFactory {
  private static CheckBoxIcon checkBoxIcon;
  
  private static RadioButtonIcon radioButtonIcon;
  
  static Icon getCheckBoxIcon() {
    if (checkBoxIcon == null)
      checkBoxIcon = new CheckBoxIcon(); 
    return checkBoxIcon;
  }
  
  static Icon getRadioButtonIcon() {
    if (radioButtonIcon == null)
      radioButtonIcon = new RadioButtonIcon(); 
    return radioButtonIcon;
  }
  
  private static final class CheckBoxIcon implements Icon, UIResource, Serializable {
    private CheckBoxIcon() {}
    
    private static final int SIZE = LookUtils.IS_LOW_RESOLUTION ? 13 : 15;
    
    public int getIconWidth() {
      return SIZE;
    }
    
    public int getIconHeight() {
      return SIZE;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      JCheckBox cb = (JCheckBox)c;
      ButtonModel model = cb.getModel();
      Graphics2D g2 = (Graphics2D)g;
      boolean paintFocus = ((model.isArmed() && !model.isPressed()) || (cb.hasFocus() && PlasticXPIconFactory.isBlank(cb.getText())));
      RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
      Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
      Object oldAAHint = g2.getRenderingHint(key);
      if (newAAHint != oldAAHint) {
        g2.setRenderingHint(key, newAAHint);
      } else {
        oldAAHint = null;
      } 
      drawBorder(g2, model.isEnabled(), x, y, SIZE - 1, SIZE - 1);
      drawFill(g2, model.isPressed(), x + 1, y + 1, SIZE - 2, SIZE - 2);
      if (paintFocus)
        drawFocus(g2, x + 1, y + 1, SIZE - 3, SIZE - 3); 
      if (model.isSelected())
        drawCheck(g2, model.isEnabled(), x + 3, y + 3, SIZE - 7, SIZE - 7); 
      if (oldAAHint != null)
        g2.setRenderingHint(key, oldAAHint); 
    }
    
    private void drawBorder(Graphics2D g2, boolean enabled, int x, int y, int width, int height) {
      g2.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlDisabled());
      g2.drawRect(x, y, width, height);
    }
    
    private void drawCheck(Graphics2D g2, boolean enabled, int x, int y, int width, int height) {
      g2.setColor(enabled ? UIManager.getColor("CheckBox.check") : MetalLookAndFeel.getControlDisabled());
      int right = x + width;
      int bottom = y + height;
      int startY = y + height / 3;
      int turnX = x + width / 2 - 2;
      g2.drawLine(x, startY, turnX, bottom - 3);
      g2.drawLine(x, startY + 1, turnX, bottom - 2);
      g2.drawLine(x, startY + 2, turnX, bottom - 1);
      g2.drawLine(turnX + 1, bottom - 2, right, y);
      g2.drawLine(turnX + 1, bottom - 1, right, y + 1);
      g2.drawLine(turnX + 1, bottom, right, y + 2);
    }
    
    private void drawFill(Graphics2D g2, boolean pressed, int x, int y, int w, int h) {
      Color upperLeft, lowerRight;
      if (pressed) {
        upperLeft = MetalLookAndFeel.getControlShadow();
        lowerRight = PlasticLookAndFeel.getControlHighlight();
      } else {
        upperLeft = PlasticLookAndFeel.getControl();
        lowerRight = PlasticLookAndFeel.getControlHighlight().brighter();
      } 
      g2.setPaint(new GradientPaint(x, y, upperLeft, (x + w), (y + h), lowerRight));
      g2.fillRect(x, y, w, h);
    }
    
    private void drawFocus(Graphics2D g2, int x, int y, int width, int height) {
      g2.setPaint(new GradientPaint(x, y, PlasticLookAndFeel.getFocusColor().brighter(), width, height, PlasticLookAndFeel.getFocusColor()));
      g2.drawRect(x, y, width, height);
      g2.drawRect(x + 1, y + 1, width - 2, height - 2);
    }
  }
  
  private static final class RadioButtonIcon implements Icon, UIResource, Serializable {
    private RadioButtonIcon() {}
    
    private static final int SIZE = LookUtils.IS_LOW_RESOLUTION ? 13 : 15;
    
    private static final Stroke FOCUS_STROKE = new BasicStroke(2.0F);
    
    public int getIconWidth() {
      return SIZE;
    }
    
    public int getIconHeight() {
      return SIZE;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
      Graphics2D g2 = (Graphics2D)g;
      AbstractButton rb = (AbstractButton)c;
      ButtonModel model = rb.getModel();
      boolean paintFocus = ((model.isArmed() && !model.isPressed()) || (rb.hasFocus() && PlasticXPIconFactory.isBlank(rb.getText())));
      RenderingHints.Key key = RenderingHints.KEY_ANTIALIASING;
      Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
      Object oldAAHint = g2.getRenderingHint(key);
      if (newAAHint != oldAAHint) {
        g2.setRenderingHint(key, newAAHint);
      } else {
        oldAAHint = null;
      } 
      drawFill(g2, model.isPressed(), x, y, SIZE - 1, SIZE - 1);
      if (paintFocus)
        drawFocus(g2, x + 1, y + 1, SIZE - 3, SIZE - 3); 
      if (model.isSelected())
        drawCheck(g2, c, model.isEnabled(), x + 4, y + 4, SIZE - 8, SIZE - 8); 
      drawBorder(g2, model.isEnabled(), x, y, SIZE - 1, SIZE - 1);
      if (oldAAHint != null)
        g2.setRenderingHint(key, oldAAHint); 
    }
    
    private void drawBorder(Graphics2D g2, boolean enabled, int x, int y, int w, int h) {
      g2.setColor(enabled ? PlasticLookAndFeel.getControlDarkShadow() : MetalLookAndFeel.getControlDisabled());
      g2.drawOval(x, y, w, h);
    }
    
    private void drawCheck(Graphics2D g2, Component c, boolean enabled, int x, int y, int w, int h) {
      g2.translate(x, y);
      if (enabled) {
        g2.setColor(UIManager.getColor("RadioButton.check"));
        g2.fillOval(0, 0, w, h);
        UIManager.getIcon("RadioButton.checkIcon").paintIcon(c, g2, 0, 0);
      } else {
        g2.setColor(MetalLookAndFeel.getControlDisabled());
        g2.fillOval(0, 0, w, h);
      } 
      g2.translate(-x, -y);
    }
    
    private void drawFill(Graphics2D g2, boolean pressed, int x, int y, int w, int h) {
      Color upperLeft, lowerRight;
      if (pressed) {
        upperLeft = MetalLookAndFeel.getControlShadow();
        lowerRight = PlasticLookAndFeel.getControlHighlight();
      } else {
        upperLeft = PlasticLookAndFeel.getControl();
        lowerRight = PlasticLookAndFeel.getControlHighlight().brighter();
      } 
      g2.setPaint(new GradientPaint(x, y, upperLeft, (x + w), (y + h), lowerRight));
      g2.fillOval(x, y, w, h);
    }
    
    private void drawFocus(Graphics2D g2, int x, int y, int w, int h) {
      g2.setPaint(new GradientPaint(x, y, PlasticLookAndFeel.getFocusColor().brighter(), w, h, PlasticLookAndFeel.getFocusColor()));
      Stroke stroke = g2.getStroke();
      g2.setStroke(FOCUS_STROKE);
      g2.drawOval(x, y, w, h);
      g2.setStroke(stroke);
    }
  }
  
  private static boolean isBlank(String str) {
    int length;
    if (str == null || (length = str.length()) == 0)
      return true; 
    for (int i = length - 1; i >= 0; i--) {
      if (!Character.isWhitespace(str.charAt(i)))
        return false; 
    } 
    return true;
  }
}
