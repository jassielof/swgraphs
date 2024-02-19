package com.jgoodies.looks.plastic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.Icon;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalInternalFrameTitlePane;

public final class PlasticInternalFrameTitlePane extends MetalInternalFrameTitlePane {
  private PlasticBumps paletteBumps;
  
  private final PlasticBumps activeBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlDarkShadow(), PlasticLookAndFeel.getPrimaryControl());
  
  private final PlasticBumps inactiveBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getControlHighlight(), PlasticLookAndFeel.getControlDarkShadow(), PlasticLookAndFeel.getControl());
  
  public PlasticInternalFrameTitlePane(JInternalFrame frame) {
    super(frame);
  }
  
  public void paintPalette(Graphics g) {
    boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
    int width = getWidth();
    int height = getHeight();
    if (this.paletteBumps == null)
      this.paletteBumps = new PlasticBumps(0, 0, PlasticLookAndFeel.getPrimaryControlHighlight(), PlasticLookAndFeel.getPrimaryControlInfo(), PlasticLookAndFeel.getPrimaryControlShadow()); 
    Color background = PlasticLookAndFeel.getPrimaryControlShadow();
    Color darkShadow = PlasticLookAndFeel.getControlDarkShadow();
    g.setColor(background);
    g.fillRect(0, 0, width, height);
    g.setColor(darkShadow);
    g.drawLine(0, height - 1, width, height - 1);
    int buttonsWidth = getButtonsWidth();
    int xOffset = leftToRight ? 4 : (buttonsWidth + 4);
    int bumpLength = width - buttonsWidth - 8;
    int bumpHeight = getHeight() - 4;
    this.paletteBumps.setBumpArea(bumpLength, bumpHeight);
    this.paletteBumps.paintIcon(this, g, xOffset, 2);
  }
  
  public void paintComponent(Graphics g) {
    PlasticBumps bumps;
    int bumpXOffset, bumpLength;
    if (this.isPalette) {
      paintPalette(g);
      return;
    } 
    boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
    boolean isSelected = this.frame.isSelected();
    int width = getWidth();
    int height = getHeight();
    Color background = null;
    Color foreground = null;
    Color shadow = null;
    if (isSelected) {
      background = PlasticLookAndFeel.getWindowTitleBackground();
      foreground = PlasticLookAndFeel.getWindowTitleForeground();
      bumps = this.activeBumps;
    } else {
      background = PlasticLookAndFeel.getWindowTitleInactiveBackground();
      foreground = PlasticLookAndFeel.getWindowTitleInactiveForeground();
      bumps = this.inactiveBumps;
    } 
    shadow = PlasticLookAndFeel.getControlDarkShadow();
    g.setColor(background);
    g.fillRect(0, 0, width, height);
    g.setColor(shadow);
    g.drawLine(0, height - 1, width, height - 1);
    g.drawLine(0, 0, 0, 0);
    g.drawLine(width - 1, 0, width - 1, 0);
    int titleLength = 0;
    int xOffset = leftToRight ? 5 : (width - 5);
    String frameTitle = this.frame.getTitle();
    Icon icon = this.frame.getFrameIcon();
    if (icon != null) {
      if (!leftToRight)
        xOffset -= icon.getIconWidth(); 
      int iconY = height / 2 - icon.getIconHeight() / 2;
      icon.paintIcon(this.frame, g, xOffset, iconY);
      xOffset += leftToRight ? (icon.getIconWidth() + 5) : -5;
    } 
    if (frameTitle != null) {
      Font f = getFont();
      g.setFont(f);
      FontMetrics fm = g.getFontMetrics();
      g.setColor(foreground);
      int yOffset = (height - fm.getHeight()) / 2 + fm.getAscent();
      Rectangle rect = new Rectangle(0, 0, 0, 0);
      if (this.frame.isIconifiable()) {
        rect = this.iconButton.getBounds();
      } else if (this.frame.isMaximizable()) {
        rect = this.maxButton.getBounds();
      } else if (this.frame.isClosable()) {
        rect = this.closeButton.getBounds();
      } 
      if (leftToRight) {
        if (rect.x == 0)
          rect.x = this.frame.getWidth() - (this.frame.getInsets()).right - 2; 
        int titleW = rect.x - xOffset - 4;
        frameTitle = getTitle(frameTitle, fm, titleW);
      } else {
        int titleW = xOffset - rect.x - rect.width - 4;
        frameTitle = getTitle(frameTitle, fm, titleW);
        xOffset -= SwingUtilities.computeStringWidth(fm, frameTitle);
      } 
      titleLength = SwingUtilities.computeStringWidth(fm, frameTitle);
      g.drawString(frameTitle, xOffset, yOffset);
      xOffset += leftToRight ? (titleLength + 5) : -5;
    } 
    int buttonsWidth = getButtonsWidth();
    if (leftToRight) {
      bumpLength = width - buttonsWidth - xOffset - 5;
      bumpXOffset = xOffset;
    } else {
      bumpLength = xOffset - buttonsWidth - 5;
      bumpXOffset = buttonsWidth + 5;
    } 
    int bumpYOffset = 3;
    int bumpHeight = getHeight() - 2 * bumpYOffset;
    bumps.setBumpArea(bumpLength, bumpHeight);
    bumps.paintIcon(this, g, bumpXOffset, bumpYOffset);
  }
  
  protected String getTitle(String text, FontMetrics fm, int availTextWidth) {
    if (text == null || text.equals(""))
      return ""; 
    int textWidth = SwingUtilities.computeStringWidth(fm, text);
    String clipString = "...";
    if (textWidth > availTextWidth) {
      int totalWidth = SwingUtilities.computeStringWidth(fm, clipString);
      int nChars;
      for (nChars = 0; nChars < text.length(); nChars++) {
        totalWidth += fm.charWidth(text.charAt(nChars));
        if (totalWidth > availTextWidth)
          break; 
      } 
      text = text.substring(0, nChars) + clipString;
    } 
    return text;
  }
  
  private int getButtonsWidth() {
    boolean leftToRight = PlasticUtils.isLeftToRight(this.frame);
    int w = getWidth();
    int x = leftToRight ? w : 0;
    int buttonWidth = this.closeButton.getIcon().getIconWidth();
    if (this.frame.isClosable())
      if (this.isPalette) {
        int spacing = 3;
        x += leftToRight ? (-spacing - buttonWidth + 2) : spacing;
        if (!leftToRight)
          x += buttonWidth + 2; 
      } else {
        int spacing = 4;
        x += leftToRight ? (-spacing - buttonWidth) : spacing;
        if (!leftToRight)
          x += buttonWidth; 
      }  
    if (this.frame.isMaximizable() && !this.isPalette) {
      int spacing = this.frame.isClosable() ? 10 : 4;
      x += leftToRight ? (-spacing - buttonWidth) : spacing;
      if (!leftToRight)
        x += buttonWidth; 
    } 
    if (this.frame.isIconifiable() && !this.isPalette) {
      int spacing = this.frame.isMaximizable() ? 2 : (this.frame.isClosable() ? 10 : 4);
      x += leftToRight ? (-spacing - buttonWidth) : spacing;
      if (!leftToRight)
        x += buttonWidth; 
    } 
    return leftToRight ? (w - x) : x;
  }
}
