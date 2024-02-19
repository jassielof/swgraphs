package com.jgoodies.looks.plastic.theme;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class SkyYellow extends AbstractSkyTheme {
  public String getName() {
    return "Sky Yellow";
  }
  
  protected ColorUIResource getPrimary1() {
    return Colors.GRAY_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.YELLOW_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.YELLOW_LOW_LIGHTEST;
  }
  
  public ColorUIResource getFocusColor() {
    return Colors.ORANGE_FOCUS;
  }
  
  public ColorUIResource getPrimaryControlShadow() {
    return getPrimary3();
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return Colors.YELLOW_LOW_MEDIUMDARK;
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.maxBumpsWidth", new Integer(30) };
    table.putDefaults(uiDefaults);
  }
}
