package com.jgoodies.looks.plastic;

import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.UIResource;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

final class PlasticFieldCaret extends DefaultCaret implements UIResource {
  private boolean isKeyboardFocusEvent = true;
  
  public void focusGained(FocusEvent e) {
    if (getComponent().isEnabled()) {
      setVisible(true);
      setSelectionVisible(true);
    } 
    final JTextComponent c = getComponent();
    if (c.isEnabled() && this.isKeyboardFocusEvent)
      if (c instanceof javax.swing.JFormattedTextField) {
        EventQueue.invokeLater(new Runnable() {
              private final JTextComponent val$c;
              
              private final PlasticFieldCaret this$0;
              
              public void run() {
                PlasticFieldCaret.this.setDot(0);
                PlasticFieldCaret.this.moveDot(c.getDocument().getLength());
              }
            });
      } else {
        setDot(0);
        moveDot(c.getDocument().getLength());
      }  
  }
  
  public void focusLost(FocusEvent e) {
    super.focusLost(e);
    if (!e.isTemporary())
      this.isKeyboardFocusEvent = true; 
  }
  
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e) || e.isPopupTrigger())
      this.isKeyboardFocusEvent = false; 
    super.mousePressed(e);
  }
  
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    if (e.isPopupTrigger()) {
      this.isKeyboardFocusEvent = false;
      if (getComponent() != null && getComponent().isEnabled() && getComponent().isRequestFocusEnabled())
        getComponent().requestFocus(); 
    } 
  }
}
