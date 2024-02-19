package com.jgoodies.looks.common;

import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.Options;
import java.awt.Component;
import javax.swing.Popup;
import javax.swing.PopupFactory;

public final class ShadowPopupFactory extends PopupFactory {
  static final String PROP_HORIZONTAL_BACKGROUND = "jgoodies.hShadowBg";
  
  static final String PROP_VERTICAL_BACKGROUND = "jgoodies.vShadowBg";
  
  private final PopupFactory storedFactory;
  
  private ShadowPopupFactory(PopupFactory storedFactory) {
    this.storedFactory = storedFactory;
  }
  
  public static void install() {
    if (LookUtils.IS_OS_MAC)
      return; 
    PopupFactory factory = PopupFactory.getSharedInstance();
    if (factory instanceof ShadowPopupFactory)
      return; 
    PopupFactory.setSharedInstance(new ShadowPopupFactory(factory));
  }
  
  public static void uninstall() {
    PopupFactory factory = PopupFactory.getSharedInstance();
    if (!(factory instanceof ShadowPopupFactory))
      return; 
    PopupFactory stored = ((ShadowPopupFactory)factory).storedFactory;
    PopupFactory.setSharedInstance(stored);
  }
  
  public Popup getPopup(Component owner, Component contents, int x, int y) throws IllegalArgumentException {
    Popup popup = super.getPopup(owner, contents, x, y);
    return Options.isPopupDropShadowActive() ? ShadowPopup.getInstance(owner, contents, x, y, popup) : popup;
  }
}
