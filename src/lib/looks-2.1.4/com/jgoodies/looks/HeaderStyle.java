package com.jgoodies.looks;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public final class HeaderStyle {
  public static final HeaderStyle SINGLE = new HeaderStyle("Single");
  
  public static final HeaderStyle BOTH = new HeaderStyle("Both");
  
  private final String name;
  
  private HeaderStyle(String name) {
    this.name = name;
  }
  
  public static HeaderStyle from(JMenuBar menuBar) {
    return from0(menuBar);
  }
  
  public static HeaderStyle from(JToolBar toolBar) {
    return from0(toolBar);
  }
  
  private static HeaderStyle from0(JComponent c) {
    Object value = c.getClientProperty("jgoodies.headerStyle");
    if (value instanceof HeaderStyle)
      return (HeaderStyle)value; 
    if (value instanceof String)
      return valueOf((String)value); 
    return null;
  }
  
  private static HeaderStyle valueOf(String name) {
    if (name.equalsIgnoreCase(SINGLE.name))
      return SINGLE; 
    if (name.equalsIgnoreCase(BOTH.name))
      return BOTH; 
    throw new IllegalArgumentException("Invalid HeaderStyle name " + name);
  }
  
  public String toString() {
    return this.name;
  }
}
