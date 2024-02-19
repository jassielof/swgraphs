package com.jgoodies.looks.windows;

import com.sun.java.swing.plaf.windows.WindowsTextAreaUI;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.JTextComponent;

public final class WindowsTextAreaUI extends WindowsTextAreaUI {
  public static ComponentUI createUI(JComponent c) {
    return new WindowsTextAreaUI();
  }
  
  public void installUI(JComponent c) {
    super.installUI(c);
    updateBackground((JTextComponent)c);
  }
  
  protected void propertyChange(PropertyChangeEvent evt) {
    super.propertyChange(evt);
    String propertyName = evt.getPropertyName();
    if ("editable".equals(propertyName) || "enabled".equals(propertyName))
      updateBackground((JTextComponent)evt.getSource()); 
  }
  
  private void updateBackground(JTextComponent c) {
    Color background = c.getBackground();
    if (!(background instanceof javax.swing.plaf.UIResource))
      return; 
    Color newColor = null;
    if (!c.isEnabled())
      newColor = UIManager.getColor("TextArea.disabledBackground"); 
    if (newColor == null && !c.isEditable())
      newColor = UIManager.getColor("TextArea.inactiveBackground"); 
    if (newColor == null)
      newColor = UIManager.getColor("TextArea.background"); 
    if (newColor != null && newColor != background)
      c.setBackground(newColor); 
  }
}
