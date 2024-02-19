package com.jgoodies.looks.plastic.theme;

import javax.swing.plaf.ColorUIResource;

public class DesertRed extends DesertBlue {
  public String getName() {
    return "Desert Red";
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.RED_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.RED_LOW_LIGHTER;
  }
  
  public ColorUIResource getFocusColor() {
    return Colors.PINK_LOW_DARK;
  }
  
  public ColorUIResource getTitleTextColor() {
    return Colors.GRAY_DARKER;
  }
}
