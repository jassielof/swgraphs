package com.jgoodies.looks.plastic;

import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.plaf.basic.BasicButtonListener;

final class ActiveBasicButtonListener extends BasicButtonListener {
  private boolean mouseOver;
  
  ActiveBasicButtonListener(AbstractButton b) {
    super(b);
    this.mouseOver = false;
  }
  
  public void mouseEntered(MouseEvent e) {
    super.mouseEntered(e);
    AbstractButton button = (AbstractButton)e.getSource();
    button.getModel().setArmed(this.mouseOver = true);
  }
  
  public void mouseExited(MouseEvent e) {
    super.mouseExited(e);
    AbstractButton button = (AbstractButton)e.getSource();
    button.getModel().setArmed(this.mouseOver = false);
  }
  
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    AbstractButton button = (AbstractButton)e.getSource();
    button.getModel().setArmed(this.mouseOver);
  }
}
