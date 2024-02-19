package com.jgoodies.looks.common;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;
import javax.swing.text.Position;

public final class ExtPasswordView extends PasswordView {
  public ExtPasswordView(Element element) {
    super(element);
  }
  
  public float getPreferredSpan(int axis) {
    overrideEchoChar();
    return super.getPreferredSpan(axis);
  }
  
  public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
    overrideEchoChar();
    return super.modelToView(pos, a, b);
  }
  
  public int viewToModel(float fx, float fy, Shape a, Position.Bias[] bias) {
    overrideEchoChar();
    return super.viewToModel(fx, fy, a, bias);
  }
  
  protected int drawEchoCharacter(Graphics g, int x, int y, char c) {
    Container container = getContainer();
    if (!(container instanceof JPasswordField))
      return super.drawEchoCharacter(g, x, y, c); 
    JPasswordField field = (JPasswordField)container;
    if (canOverrideEchoChar(field))
      c = getEchoChar(); 
    Graphics2D g2 = (Graphics2D)g;
    Object newAAHint = RenderingHints.VALUE_ANTIALIAS_ON;
    Object oldAAHint = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    if (newAAHint != oldAAHint) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, newAAHint);
    } else {
      oldAAHint = null;
    } 
    int newX = super.drawEchoCharacter(g, x, y, c);
    if (oldAAHint != null)
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, oldAAHint); 
    return newX;
  }
  
  private void overrideEchoChar() {
    Container container = getContainer();
    if (!(container instanceof JPasswordField))
      return; 
    JPasswordField field = (JPasswordField)container;
    if (canOverrideEchoChar(field))
      setFieldEchoChar(field, getEchoChar()); 
  }
  
  private boolean canOverrideEchoChar(JPasswordField field) {
    return (field.echoCharIsSet() && field.getEchoChar() == '*');
  }
  
  private void setFieldEchoChar(JPasswordField field, char newEchoChar) {
    char oldEchoChar = field.getEchoChar();
    if (oldEchoChar == newEchoChar)
      return; 
    field.setEchoChar(newEchoChar);
  }
  
  private static char getEchoChar() {
    return ((Character)UIManager.get("PasswordField.echoChar")).charValue();
  }
}
