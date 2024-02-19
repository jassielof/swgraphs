package com.jgoodies.looks.plastic.theme;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

public class DesertYellow extends DesertBlue {
  public String getName() {
    return "Desert Yellow";
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.YELLOW_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.YELLOW_LOW_LIGHTEST;
  }
  
  public ColorUIResource getTitleTextColor() {
    return Colors.GRAY_DARKER;
  }
  
  public ColorUIResource getMenuItemSelectedBackground() {
    return Colors.YELLOW_LOW_MEDIUMDARK;
  }
  
  public void addCustomEntriesToTable(UIDefaults table) {
    super.addCustomEntriesToTable(table);
    Object[] uiDefaults = { "ScrollBar.is3DEnabled", Boolean.TRUE, "ScrollBar.maxBumpsWidth", new Integer(30) };
    table.putDefaults(uiDefaults);
  }
}
