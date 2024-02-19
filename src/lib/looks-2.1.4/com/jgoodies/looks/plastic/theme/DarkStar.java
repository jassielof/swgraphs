package com.jgoodies.looks.plastic.theme;

import javax.swing.plaf.ColorUIResource;

public class DarkStar extends InvertedColorTheme {
  private final ColorUIResource softWhite = new ColorUIResource(154, 154, 154);
  
  private final ColorUIResource primary1 = new ColorUIResource(83, 83, 61);
  
  private final ColorUIResource primary2 = new ColorUIResource(115, 107, 82);
  
  private final ColorUIResource primary3 = new ColorUIResource(156, 156, 123);
  
  private final ColorUIResource secondary1 = new ColorUIResource(32, 32, 32);
  
  private final ColorUIResource secondary2 = new ColorUIResource(96, 96, 96);
  
  private final ColorUIResource secondary3 = new ColorUIResource(84, 84, 84);
  
  public String getName() {
    return "Dark Star";
  }
  
  protected ColorUIResource getPrimary1() {
    return this.primary1;
  }
  
  protected ColorUIResource getPrimary2() {
    return this.primary2;
  }
  
  protected ColorUIResource getPrimary3() {
    return this.primary3;
  }
  
  protected ColorUIResource getSecondary1() {
    return this.secondary1;
  }
  
  protected ColorUIResource getSecondary2() {
    return this.secondary2;
  }
  
  protected ColorUIResource getSecondary3() {
    return this.secondary3;
  }
  
  protected ColorUIResource getSoftWhite() {
    return this.softWhite;
  }
}
