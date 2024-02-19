package com.jgoodies.looks.common;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.InternationalFormatter;

public final class ExtBasicArrowButtonHandler extends AbstractAction implements MouseListener, FocusListener {
  private final Timer autoRepeatTimer;
  
  private final boolean isNext;
  
  private JSpinner spinner;
  
  private JButton arrowButton;
  
  public ExtBasicArrowButtonHandler(String name, boolean isNext) {
    super(name);
    this.isNext = isNext;
    this.autoRepeatTimer = new Timer(60, this);
    this.autoRepeatTimer.setInitialDelay(300);
  }
  
  private JSpinner eventToSpinner(AWTEvent e) {
    Object src = e.getSource();
    while (src instanceof Component && !(src instanceof JSpinner))
      src = ((Component)src).getParent(); 
    return (src instanceof JSpinner) ? (JSpinner)src : null;
  }
  
  public void actionPerformed(ActionEvent e) {
    JSpinner spinner = this.spinner;
    if (!(e.getSource() instanceof Timer)) {
      spinner = eventToSpinner(e);
      if (e.getSource() instanceof JButton)
        this.arrowButton = (JButton)e.getSource(); 
    } else if (this.arrowButton != null && !this.arrowButton.getModel().isPressed() && this.autoRepeatTimer.isRunning()) {
      this.autoRepeatTimer.stop();
      spinner = null;
      this.arrowButton = null;
    } 
    if (spinner != null)
      try {
        int calendarField = getCalendarField(spinner);
        spinner.commitEdit();
        if (calendarField != -1)
          ((SpinnerDateModel)spinner.getModel()).setCalendarField(calendarField); 
        Object value = this.isNext ? spinner.getNextValue() : spinner.getPreviousValue();
        if (value != null) {
          spinner.setValue(value);
          select(spinner);
        } 
      } catch (IllegalArgumentException iae) {
        UIManager.getLookAndFeel().provideErrorFeedback(spinner);
      } catch (ParseException pe) {
        UIManager.getLookAndFeel().provideErrorFeedback(spinner);
      }  
  }
  
  private void select(JSpinner aSpinner) {
    JComponent editor = aSpinner.getEditor();
    if (editor instanceof JSpinner.DateEditor) {
      JSpinner.DateEditor dateEditor = (JSpinner.DateEditor)editor;
      JFormattedTextField ftf = dateEditor.getTextField();
      Format format = dateEditor.getFormat();
      Object value;
      if (format != null && (value = aSpinner.getValue()) != null) {
        SpinnerDateModel model = dateEditor.getModel();
        DateFormat.Field field = DateFormat.Field.ofCalendarField(model.getCalendarField());
        if (field != null)
          try {
            AttributedCharacterIterator iterator = format.formatToCharacterIterator(value);
            if (!select(ftf, iterator, field) && field == DateFormat.Field.HOUR0)
              select(ftf, iterator, DateFormat.Field.HOUR1); 
          } catch (IllegalArgumentException iae) {} 
      } 
    } 
  }
  
  private boolean select(JFormattedTextField ftf, AttributedCharacterIterator iterator, DateFormat.Field field) {
    int max = ftf.getDocument().getLength();
    iterator.first();
    while (true) {
      Map attrs = iterator.getAttributes();
      if (attrs != null && attrs.containsKey(field)) {
        int start = iterator.getRunStart(field);
        int end = iterator.getRunLimit(field);
        if (start != -1 && end != -1 && start <= max && end <= max)
          ftf.select(start, end); 
        return true;
      } 
      if (iterator.next() == Character.MAX_VALUE)
        return false; 
    } 
  }
  
  private int getCalendarField(JSpinner aSpinner) {
    JComponent editor = aSpinner.getEditor();
    if (editor instanceof JSpinner.DateEditor) {
      JSpinner.DateEditor dateEditor = (JSpinner.DateEditor)editor;
      JFormattedTextField ftf = dateEditor.getTextField();
      int start = ftf.getSelectionStart();
      JFormattedTextField.AbstractFormatter formatter = ftf.getFormatter();
      if (formatter instanceof InternationalFormatter) {
        Format.Field[] fields = ((InternationalFormatter)formatter).getFields(start);
        for (int counter = 0; counter < fields.length; counter++) {
          if (fields[counter] instanceof DateFormat.Field) {
            int calendarField;
            if (fields[counter] == DateFormat.Field.HOUR1) {
              calendarField = 10;
            } else {
              calendarField = ((DateFormat.Field)fields[counter]).getCalendarField();
            } 
            if (calendarField != -1)
              return calendarField; 
          } 
        } 
      } 
    } 
    return -1;
  }
  
  public void mousePressed(MouseEvent e) {
    if (SwingUtilities.isLeftMouseButton(e) && e.getComponent().isEnabled()) {
      this.spinner = eventToSpinner(e);
      this.autoRepeatTimer.start();
      focusSpinnerIfNecessary();
    } 
  }
  
  public void mouseReleased(MouseEvent e) {
    this.autoRepeatTimer.stop();
    this.spinner = null;
    this.arrowButton = null;
  }
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mouseEntered(MouseEvent e) {
    if (this.spinner != null && !this.autoRepeatTimer.isRunning())
      this.autoRepeatTimer.start(); 
  }
  
  public void mouseExited(MouseEvent e) {
    if (this.autoRepeatTimer.isRunning())
      this.autoRepeatTimer.stop(); 
  }
  
  private void focusSpinnerIfNecessary() {
    Component fo = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    if (this.spinner.isRequestFocusEnabled() && (fo == null || !SwingUtilities.isDescendingFrom(fo, this.spinner))) {
      Container root = this.spinner;
      if (!root.isFocusCycleRoot())
        root = root.getFocusCycleRootAncestor(); 
      if (root != null) {
        FocusTraversalPolicy ftp = root.getFocusTraversalPolicy();
        Component child = ftp.getComponentAfter(root, this.spinner);
        if (child != null && SwingUtilities.isDescendingFrom(child, this.spinner))
          child.requestFocus(); 
      } 
    } 
  }
  
  public void focusGained(FocusEvent e) {}
  
  public void focusLost(FocusEvent e) {
    if (this.autoRepeatTimer.isRunning())
      this.autoRepeatTimer.stop(); 
    this.spinner = null;
    if (this.arrowButton != null) {
      ButtonModel model = this.arrowButton.getModel();
      model.setPressed(false);
      model.setArmed(false);
      this.arrowButton = null;
    } 
  }
}
