package com.jgoodies.looks;

import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public final class BorderStyle {
  public static final BorderStyle EMPTY = new BorderStyle("Empty");
  
  public static final BorderStyle SEPARATOR = new BorderStyle("Separator");
  
  public static final BorderStyle ETCHED = new BorderStyle("Etched");
  
  private final String name;
  
  private BorderStyle(String name) {
    this.name = name;
  }
  
  public static BorderStyle from(JToolBar toolBar, String clientPropertyKey) {
    return from0(toolBar, clientPropertyKey);
  }
  
  public static BorderStyle from(JMenuBar menuBar, String clientPropertyKey) {
    return from0(menuBar, clientPropertyKey);
  }
  
  private static BorderStyle from0(JComponent c, String clientPropertyKey) {
    Object value = c.getClientProperty(clientPropertyKey);
    if (value instanceof BorderStyle)
      return (BorderStyle)value; 
    if (value instanceof String)
      return valueOf((String)value); 
    return null;
  }
  
  private static BorderStyle valueOf(String name) {
    if (name.equalsIgnoreCase(EMPTY.name))
      return EMPTY; 
    if (name.equalsIgnoreCase(SEPARATOR.name))
      return SEPARATOR; 
    if (name.equalsIgnoreCase(ETCHED.name))
      return ETCHED; 
    throw new IllegalArgumentException("Invalid BorderStyle name " + name);
  }
  
  public String toString() {
    return this.name;
  }
}
