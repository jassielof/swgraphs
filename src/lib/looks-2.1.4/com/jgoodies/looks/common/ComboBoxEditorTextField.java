package com.jgoodies.looks.common;

import javax.swing.JTextField;
import javax.swing.UIManager;

public final class ComboBoxEditorTextField extends JTextField {
  public ComboBoxEditorTextField(boolean isTableCellEditor) {
    super("", UIManager.getInt("ComboBox.editorColumns"));
    if (isTableCellEditor)
      setMargin(UIManager.getInsets("ComboBox.tableEditorInsets")); 
    setBorder(UIManager.getBorder("ComboBox.editorBorder"));
  }
  
  public void setText(String s) {
    if (getText().equals(s))
      return; 
    super.setText(s);
  }
}
