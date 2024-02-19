package com.jgoodies.looks.plastic.theme;

import javax.swing.plaf.ColorUIResource;

public class SkyGreen extends AbstractSkyTheme {
  public String getName() {
    return "Sky Green";
  }
  
  protected ColorUIResource getPrimary2() {
    return Colors.GREEN_LOW_MEDIUM;
  }
  
  protected ColorUIResource getPrimary3() {
    return Colors.GREEN_LOW_LIGHTEST;
  }
}
