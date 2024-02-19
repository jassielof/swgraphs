package com.jgoodies.looks.plastic.theme;

import javax.swing.plaf.ColorUIResource;

public class DesertGreen extends DesertBlue {
  public String getName() {
    return "Desert Green";
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.GREEN_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.GREEN_LOW_LIGHTEST;
  }
  
  public ColorUIResource getTitleTextColor() {
    return Colors.GRAY_DARKER;
  }
}
