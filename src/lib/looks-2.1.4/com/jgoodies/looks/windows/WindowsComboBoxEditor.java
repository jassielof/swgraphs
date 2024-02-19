package com.jgoodies.looks.windows;

import com.jgoodies.looks.common.ComboBoxEditorTextField;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

class WindowsComboBoxEditor extends BasicComboBoxEditor {
  WindowsComboBoxEditor(boolean isTableCellEditor) {
    this.editor = (JTextField)new ComboBoxEditorTextField(isTableCellEditor);
  }
  
  public void setItem(Object item) {
    super.setItem(item);
    this.editor.selectAll();
  }
  
  static final class UIResource extends WindowsComboBoxEditor implements javax.swing.plaf.UIResource {
    UIResource(boolean isTableCellEditor) {
      super(isTableCellEditor);
    }
  }
}
