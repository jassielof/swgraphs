package com.jgoodies.looks.windows;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import javax.swing.BoundedRangeModel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.TextUI;
import javax.swing.plaf.UIResource;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.LayeredHighlighter;
import javax.swing.text.Position;
import javax.swing.text.View;

final class WindowsFieldCaret extends DefaultCaret implements UIResource {
  private static final LayeredHighlighter.LayerPainter WindowsPainter = new WindowsHighlightPainter(null);
  
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
              
              private final WindowsFieldCaret this$0;
              
              public void run() {
                WindowsFieldCaret.this.setDot(0);
                WindowsFieldCaret.this.moveDot(c.getDocument().getLength());
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
  
  protected void adjustVisibility(Rectangle r) {
    SwingUtilities.invokeLater(new SafeScroller(r));
  }
  
  protected Highlighter.HighlightPainter getSelectionPainter() {
    return WindowsPainter;
  }
  
  private final class SafeScroller implements Runnable {
    private Rectangle r;
    
    private final WindowsFieldCaret this$0;
    
    SafeScroller(Rectangle r) {
      this.r = r;
    }
    
    public void run() {
      JTextField field = (JTextField)WindowsFieldCaret.this.getComponent();
      if (field != null) {
        TextUI ui = field.getUI();
        int dot = WindowsFieldCaret.this.getDot();
        Position.Bias bias = Position.Bias.Forward;
        Rectangle startRect = null;
        try {
          startRect = ui.modelToView(field, dot, bias);
        } catch (BadLocationException ble) {}
        Insets i = field.getInsets();
        BoundedRangeModel vis = field.getHorizontalVisibility();
        int x = this.r.x + vis.getValue() - i.left;
        int quarterSpan = vis.getExtent() / 4;
        if (this.r.x < i.left) {
          vis.setValue(x - quarterSpan);
        } else if (this.r.x + this.r.width > i.left + vis.getExtent()) {
          vis.setValue(x - 3 * quarterSpan);
        } 
        if (startRect != null)
          try {
            Rectangle endRect = ui.modelToView(field, dot, bias);
            if (endRect != null && !endRect.equals(startRect))
              WindowsFieldCaret.this.damage(endRect); 
          } catch (BadLocationException ble) {} 
      } 
    }
  }
  
  private static final class WindowsHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
    WindowsHighlightPainter(Color c) {
      super(c);
    }
    
    public void paint(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c) {
      Rectangle alloc = bounds.getBounds();
      try {
        TextUI mapper = c.getUI();
        Rectangle p0 = mapper.modelToView(c, offs0);
        Rectangle p1 = mapper.modelToView(c, offs1);
        Color color = getColor();
        if (color == null) {
          g.setColor(c.getSelectionColor());
        } else {
          g.setColor(color);
        } 
        boolean firstIsDot = false;
        boolean secondIsDot = false;
        if (c.isEditable()) {
          int dot = c.getCaretPosition();
          firstIsDot = (offs0 == dot);
          secondIsDot = (offs1 == dot);
        } 
        if (p0.y == p1.y) {
          Rectangle r = p0.union(p1);
          if (r.width > 0)
            if (firstIsDot) {
              r.x++;
              r.width--;
            } else if (secondIsDot) {
              r.width--;
            }  
          g.fillRect(r.x, r.y, r.width, r.height);
        } else {
          int p0ToMarginWidth = alloc.x + alloc.width - p0.x;
          if (firstIsDot && p0ToMarginWidth > 0) {
            p0.x++;
            p0ToMarginWidth--;
          } 
          g.fillRect(p0.x, p0.y, p0ToMarginWidth, p0.height);
          if (p0.y + p0.height != p1.y)
            g.fillRect(alloc.x, p0.y + p0.height, alloc.width, p1.y - p0.y + p0.height); 
          if (secondIsDot && p1.x > alloc.x)
            p1.x--; 
          g.fillRect(alloc.x, p1.y, p1.x - alloc.x, p1.height);
        } 
      } catch (BadLocationException e) {}
    }
    
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds, JTextComponent c, View view) {
      Color color = getColor();
      if (color == null) {
        g.setColor(c.getSelectionColor());
      } else {
        g.setColor(color);
      } 
      boolean firstIsDot = false;
      boolean secondIsDot = false;
      if (c.isEditable()) {
        int dot = c.getCaretPosition();
        firstIsDot = (offs0 == dot);
        secondIsDot = (offs1 == dot);
      } 
      if (offs0 == view.getStartOffset() && offs1 == view.getEndOffset()) {
        Rectangle alloc;
        if (bounds instanceof Rectangle) {
          alloc = (Rectangle)bounds;
        } else {
          alloc = bounds.getBounds();
        } 
        if (firstIsDot && alloc.width > 0) {
          g.fillRect(alloc.x + 1, alloc.y, alloc.width - 1, alloc.height);
        } else if (secondIsDot && alloc.width > 0) {
          g.fillRect(alloc.x, alloc.y, alloc.width - 1, alloc.height);
        } else {
          g.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);
        } 
        return alloc;
      } 
      try {
        Shape shape = view.modelToView(offs0, Position.Bias.Forward, offs1, Position.Bias.Backward, bounds);
        Rectangle r = (shape instanceof Rectangle) ? (Rectangle)shape : shape.getBounds();
        if (firstIsDot && r.width > 0) {
          g.fillRect(r.x + 1, r.y, r.width - 1, r.height);
        } else if (secondIsDot && r.width > 0) {
          g.fillRect(r.x, r.y, r.width - 1, r.height);
        } else {
          g.fillRect(r.x, r.y, r.width, r.height);
        } 
        return r;
      } catch (BadLocationException e) {
        return null;
      } 
    }
  }
}
