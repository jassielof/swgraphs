package com.jgoodies.looks.plastic;

import com.jgoodies.looks.common.ExtBasicArrowButtonHandler;
import com.jgoodies.looks.common.ExtBasicSpinnerLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

public class PlasticSpinnerUI extends BasicSpinnerUI {
  public static ComponentUI createUI(JComponent b) {
    return new PlasticSpinnerUI();
  }
  
  private static final ExtBasicArrowButtonHandler nextButtonHandler = new ExtBasicArrowButtonHandler("increment", true);
  
  private static final ExtBasicArrowButtonHandler previousButtonHandler = new ExtBasicArrowButtonHandler("decrement", false);
  
  protected Component createPreviousButton() {
    return new SpinnerArrowButton(5, previousButtonHandler);
  }
  
  protected Component createNextButton() {
    return new SpinnerArrowButton(1, nextButtonHandler);
  }
  
  protected LayoutManager createLayout() {
    return (LayoutManager)new ExtBasicSpinnerLayout();
  }
  
  protected JComponent createEditor() {
    JComponent editor = this.spinner.getEditor();
    configureEditorBorder(editor);
    return editor;
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
  
  private static final class SpinnerArrowButton extends PlasticArrowButton {
    private SpinnerArrowButton(int direction, ExtBasicArrowButtonHandler handler) {
      super(direction, UIManager.getInt("ScrollBar.width"), true);
      addActionListener((ActionListener)handler);
      addMouseListener((MouseListener)handler);
    }
    
    protected int calculateArrowHeight(int height, int width) {
      int arrowHeight = Math.min((height - 4) / 3, (width - 4) / 3);
      return Math.max(arrowHeight, 3);
    }
    
    protected int calculateArrowOffset() {
      return 1;
    }
    
    protected boolean isPaintingNorthBottom() {
      return true;
    }
  }
}
