package com.jgoodies.looks.windows;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.common.ExtBasicArrowButtonHandler;
import com.jgoodies.looks.common.ExtBasicSpinnerLayout;
import com.sun.java.swing.plaf.windows.WindowsSpinnerUI;
import java.awt.Component;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;

public final class WindowsSpinnerUI extends WindowsSpinnerUI {
  public static ComponentUI createUI(JComponent b) {
    return new WindowsSpinnerUI();
  }
  
  private static final ExtBasicArrowButtonHandler NEXT_BUTTON_HANDLER = new ExtBasicArrowButtonHandler("increment", true);
  
  private static final ExtBasicArrowButtonHandler PREVIOUS_BUTTON_HANDLER = new ExtBasicArrowButtonHandler("decrement", false);
  
  protected Component createPreviousButton() {
    if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED)
      return super.createPreviousButton(); 
    JButton b = new WindowsArrowButton(5);
    b.addActionListener((ActionListener)PREVIOUS_BUTTON_HANDLER);
    b.addMouseListener((MouseListener)PREVIOUS_BUTTON_HANDLER);
    return b;
  }
  
  protected Component createNextButton() {
    if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED)
      return super.createNextButton(); 
    JButton b = new WindowsArrowButton(1);
    b.addActionListener((ActionListener)NEXT_BUTTON_HANDLER);
    b.addMouseListener((MouseListener)NEXT_BUTTON_HANDLER);
    return b;
  }
  
  protected JComponent createEditor() {
    JComponent editor = this.spinner.getEditor();
    configureEditorBorder(editor);
    return editor;
  }
  
  protected LayoutManager createLayout() {
    return (LayoutManager)new ExtBasicSpinnerLayout();
  }
  
  protected void replaceEditor(JComponent oldEditor, JComponent newEditor) {
    this.spinner.remove(oldEditor);
    configureEditorBorder(newEditor);
    this.spinner.add(newEditor, "Editor");
  }
  
  private void configureEditorBorder(JComponent editor) {
    if (editor instanceof JSpinner.DefaultEditor) {
      JSpinner.DefaultEditor defaultEditor = (JSpinner.DefaultEditor)editor;
      JTextField editorField = defaultEditor.getTextField();
      Insets insets = UIManager.getInsets("Spinner.defaultEditorInsets");
      editorField.setBorder(new EmptyBorder(insets));
    } else if (editor instanceof javax.swing.JPanel && editor.getBorder() == null && editor.getComponentCount() > 0) {
      JComponent editorField = (JComponent)editor.getComponent(0);
      Insets insets = UIManager.getInsets("Spinner.defaultEditorInsets");
      editorField.setBorder(new EmptyBorder(insets));
    } 
  }
}
