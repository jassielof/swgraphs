package com.jgoodies.looks.plastic;

import com.jgoodies.looks.LookUtils;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.plaf.metal.MetalLookAndFeel;

public final class PlasticXPUtils {
  static void drawPlainButtonBorder(Graphics g, int x, int y, int w, int h) {
    drawButtonBorder(g, x, y, w, h, PlasticLookAndFeel.getControl(), PlasticLookAndFeel.getControlDarkShadow(), LookUtils.getSlightlyBrighter(PlasticLookAndFeel.getControlDarkShadow(), 1.25F));
  }
  
  static void drawPressedButtonBorder(Graphics g, int x, int y, int w, int h) {
    drawPlainButtonBorder(g, x, y, w, h);
    Color darkColor = translucentColor(PlasticLookAndFeel.getControlDarkShadow(), 128);
    Color lightColor = translucentColor(PlasticLookAndFeel.getControlHighlight(), 80);
    g.translate(x, y);
    g.setColor(darkColor);
    g.fillRect(2, 1, w - 4, 1);
    g.setColor(lightColor);
    g.fillRect(2, h - 2, w - 4, 1);
    g.translate(-x, -y);
  }
  
  static void drawDefaultButtonBorder(Graphics g, int x, int y, int w, int h) {
    drawPlainButtonBorder(g, x, y, w, h);
    drawInnerButtonDecoration(g, x, y, w, h, PlasticLookAndFeel.getPrimaryControlDarkShadow());
  }
  
  static void drawFocusedButtonBorder(Graphics g, int x, int y, int w, int h) {
    drawPlainButtonBorder(g, x, y, w, h);
    drawInnerButtonDecoration(g, x, y, w, h, PlasticLookAndFeel.getFocusColor());
  }
  
  static void drawDisabledButtonBorder(Graphics g, int x, int y, int w, int h) {
    drawButtonBorder(g, x, y, w, h, PlasticLookAndFeel.getControl(), MetalLookAndFeel.getControlShadow(), LookUtils.getSlightlyBrighter(MetalLookAndFeel.getControlShadow()));
  }
  
  public static void drawButtonBorder(Graphics g, int x, int y, int w, int h, Color backgroundColor, Color edgeColor, Color cornerColor) {
    g.translate(x, y);
    g.setColor(edgeColor);
    drawRect(g, 0, 0, w - 1, h - 1);
    g.setColor(cornerColor);
    g.fillRect(0, 0, 2, 2);
    g.fillRect(0, h - 2, 2, 2);
    g.fillRect(w - 2, 0, 2, 2);
    g.fillRect(w - 2, h - 2, 2, 2);
    g.setColor(backgroundColor);
    g.fillRect(0, 0, 1, 1);
    g.fillRect(0, h - 1, 1, 1);
    g.fillRect(w - 1, 0, 1, 1);
    g.fillRect(w - 1, h - 1, 1, 1);
    g.translate(-x, -y);
  }
  
  private static void drawInnerButtonDecoration(Graphics g, int x, int y, int w, int h, Color baseColor) {
    Color lightColor = translucentColor(baseColor, 90);
    Color mediumColor = translucentColor(baseColor, 120);
    Color darkColor = translucentColor(baseColor, 200);
    g.translate(x, y);
    g.setColor(lightColor);
    g.fillRect(2, 1, w - 4, 1);
    g.setColor(mediumColor);
    g.fillRect(1, 2, 1, h - 4);
    g.fillRect(w - 2, 2, 1, h - 4);
    drawRect(g, 2, 2, w - 5, h - 5);
    g.setColor(darkColor);
    g.fillRect(2, h - 2, w - 4, 1);
    g.translate(-x, -y);
  }
  
  static void drawRect(Graphics g, int x, int y, int w, int h) {
    g.fillRect(x, y, w + 1, 1);
    g.fillRect(x, y + 1, 1, h);
    g.fillRect(x + 1, y + h, w, 1);
    g.fillRect(x + w, y + 1, 1, h);
  }
  
  private static Color translucentColor(Color baseColor, int alpha) {
    return new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), alpha);
  }
}
