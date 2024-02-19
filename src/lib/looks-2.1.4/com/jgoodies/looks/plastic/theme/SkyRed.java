package com.jgoodies.looks.plastic.theme;

import javax.swing.plaf.ColorUIResource;

public class SkyRed extends AbstractSkyTheme {
  public String getName() {
    return "Sky Red";
  }
  
  protected ColorUIResource getPrimary1() {
    return Colors.RED_LOW_DARK;
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.RED_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.RED_LOW_LIGHTER;
  }
}
