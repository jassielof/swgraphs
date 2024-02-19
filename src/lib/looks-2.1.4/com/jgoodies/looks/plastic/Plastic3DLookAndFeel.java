package com.jgoodies.looks.plastic;

import javax.swing.UIDefaults;

public class Plastic3DLookAndFeel extends PlasticLookAndFeel {
  public String getID() {
    return "JGoodies Plastic 3D";
  }
  
  public String getName() {
    return "JGoodies Plastic 3D";
  }
  
  public String getDescription() {
    return "The JGoodies Plastic 3D Look and Feel - © 2001-2007 JGoodies Karsten Lentzsch";
  }
  
  protected boolean is3DEnabled() {
    return true;
  }
  
  protected void initComponentDefaults(UIDefaults table) {
    super.initComponentDefaults(table);
    Object menuBarBorder = PlasticBorders.getThinRaisedBorder();
    Object toolBarBorder = PlasticBorders.getThinRaisedBorder();
    Object[] defaults = { "MenuBar.border", menuBarBorder, "ToolBar.border", toolBarBorder };
    table.putDefaults(defaults);
  }
  
  protected static void installDefaultThemes() {}
}
