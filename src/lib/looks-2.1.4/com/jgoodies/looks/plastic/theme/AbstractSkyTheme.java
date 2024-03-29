package com.jgoodies.looks.plastic.theme;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public abstract class AbstractSkyTheme extends SkyBluer {
  private static final ColorUIResource SECONDARY2 = new ColorUIResource(164, 164, 164);
  
  private static final ColorUIResource SECONDARY3 = new ColorUIResource(225, 225, 225);
  
  protected ColorUIResource getPrimary1() {
    return Colors.GRAY_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.BLUE_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.BLUE_LOW_LIGHTEST;
  }
  
  protected ColorUIResource getSecondary1() {
    return Colors.GRAY_MEDIUM;
  }
  
  protected ColorUIResource getSecondary2() {
    return SECONDARY2;
  }
  
  protected ColorUIResource getSecondary3() {
    return SECONDARY3;
  }
  
  public ColorUIResource getPrimaryControlShadow() {
    return getPrimary3();
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return getPrimary1();
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", null, "ScrollBar.thumbHighlight", getPrimaryControlHighlight() };
    table.putDefaults(uiDefaults);
  }
}
