package com.jgoodies.looks.windows;

import com.jgoodies.looks.FontPolicies;
import com.jgoodies.looks.FontPolicy;
import com.jgoodies.looks.FontSet;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.MicroLayout;
import com.jgoodies.looks.MicroLayoutPolicies;
import com.jgoodies.looks.MicroLayoutPolicy;
import com.jgoodies.looks.Options;
import com.jgoodies.looks.common.MinimumSizedIcon;
import com.jgoodies.looks.common.RGBGrayFilter;
import com.jgoodies.looks.common.ShadowPopupFactory;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.lang.reflect.Method;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;

public final class WindowsLookAndFeel extends WindowsLookAndFeel {
  public static final String BORDER_STYLE_KEY = "jgoodies.windows.borderStyle";
  
  public String getID() {
    return "JGoodies Windows";
  }
  
  public String getName() {
    return "JGoodies Windows";
  }
  
  public String getDescription() {
    return "The JGoodies Windows Look and Feel - © 2001-2007 JGoodies Karsten Lentzsch";
  }
  
  public static FontPolicy getFontPolicy() {
    FontPolicy policy = (FontPolicy)UIManager.get("Windows.fontPolicy");
    if (policy != null)
      return policy; 
    FontPolicy defaultPolicy = FontPolicies.getDefaultWindowsPolicy();
    return FontPolicies.customSettingsPolicy(defaultPolicy);
  }
  
  public static void setFontPolicy(FontPolicy fontPolicy) {
    UIManager.put("Windows.fontPolicy", fontPolicy);
  }
  
  public static MicroLayoutPolicy getMicroLayoutPolicy() {
    MicroLayoutPolicy policy = (MicroLayoutPolicy)UIManager.get("Windows.MicroLayoutPolicy");
    return (policy != null) ? policy : MicroLayoutPolicies.getDefaultWindowsPolicy();
  }
  
  public static void setMicroLayoutPolicy(MicroLayout microLayoutPolicy) {
    UIManager.put("Windows.MicroLayoutPolicy", microLayoutPolicy);
  }
  
  public void initialize() {
    super.initialize();
    ShadowPopupFactory.install();
  }
  
  public void uninitialize() {
    super.uninitialize();
    ShadowPopupFactory.uninstall();
  }
  
  public Icon getDisabledIcon(JComponent component, Icon icon) {
    Icon disabledIcon = RGBGrayFilter.getDisabledIcon(component, icon);
    return (disabledIcon != null) ? new IconUIResource(disabledIcon) : null;
  }
  
  protected void initClassDefaults(UIDefaults table) {
    super.initClassDefaults(table);
    String windowsPrefix = "com.jgoodies.looks.windows.Windows";
    String commonPrefix = "com.jgoodies.looks.common.ExtBasic";
    Object[] uiDefaults = { 
        "ComboBoxUI", "com.jgoodies.looks.windows.WindowsComboBoxUI", "ButtonUI", "com.jgoodies.looks.windows.WindowsButtonUI", "ScrollPaneUI", "com.jgoodies.looks.windows.WindowsScrollPaneUI", "MenuBarUI", "com.jgoodies.looks.windows.WindowsMenuBarUI", "PopupMenuUI", "com.jgoodies.looks.windows.WindowsPopupMenuUI", 
        "OptionPaneUI", "com.jgoodies.looks.windows.WindowsOptionPaneUI", "SplitPaneUI", "com.jgoodies.looks.windows.WindowsSplitPaneUI", "TabbedPaneUI", "com.jgoodies.looks.windows.WindowsTabbedPaneUI", "TextFieldUI", "com.jgoodies.looks.windows.WindowsTextFieldUI", "FormattedTextFieldUI", "com.jgoodies.looks.windows.WindowsFormattedTextFieldUI", 
        "PasswordFieldUI", "com.jgoodies.looks.windows.WindowsPasswordFieldUI", "TextAreaUI", "com.jgoodies.looks.windows.WindowsTextAreaUI", "TreeUI", "com.jgoodies.looks.windows.WindowsTreeUI", "SeparatorUI", "com.jgoodies.looks.windows.WindowsSeparatorUI" };
    if (LookUtils.IS_JAVA_1_4_2_OR_LATER)
      uiDefaults = append(uiDefaults, "SpinnerUI", "com.jgoodies.looks.windows.WindowsSpinnerUI"); 
    if (!LookUtils.IS_JAVA_6_OR_LATER || !LookUtils.IS_OS_WINDOWS_VISTA || !LookUtils.IS_LAF_WINDOWS_XP_ENABLED) {
      uiDefaults = append(uiDefaults, "MenuItemUI", "com.jgoodies.looks.windows.WindowsMenuItemUI");
      uiDefaults = append(uiDefaults, "CheckBoxMenuItemUI", "com.jgoodies.looks.common.ExtBasicCheckBoxMenuItemUI");
      uiDefaults = append(uiDefaults, "RadioButtonMenuItemUI", "com.jgoodies.looks.common.ExtBasicRadioButtonMenuItemUI");
      uiDefaults = append(uiDefaults, "PopupMenuSeparatorUI", "com.jgoodies.looks.common.ExtBasicPopupMenuSeparatorUI");
    } 
    if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED) {
      if (!LookUtils.IS_JAVA_6_OR_LATER || !LookUtils.IS_OS_WINDOWS_VISTA)
        uiDefaults = append(uiDefaults, "MenuUI", "com.jgoodies.looks.windows.WindowsXPMenuUI"); 
      uiDefaults = append(uiDefaults, "ToolBarUI", "com.jgoodies.looks.windows.WindowsXPToolBarUI");
      uiDefaults = append(uiDefaults, "TableHeaderUI", "com.jgoodies.looks.windows.WindowsXPTableHeaderUI");
    } else {
      uiDefaults = append(uiDefaults, "MenuUI", "com.jgoodies.looks.common.ExtBasicMenuUI");
      uiDefaults = append(uiDefaults, "ToolBarUI", "com.jgoodies.looks.windows.WindowsToolBarUI");
      uiDefaults = append(uiDefaults, "ScrollBarUI", "com.jgoodies.looks.windows.WindowsScrollBarUI");
      if (!LookUtils.IS_JAVA_1_4_2_OR_LATER)
        uiDefaults = append(uiDefaults, "ToolBarSeparatorUI", "com.jgoodies.looks.windows.WindowsToolBarSeparatorUI"); 
    } 
    table.putDefaults(uiDefaults);
  }
  
  protected void initComponentDefaults(UIDefaults table) {
    super.initComponentDefaults(table);
    boolean isXP = LookUtils.IS_LAF_WINDOWS_XP_ENABLED;
    boolean isClassic = !isXP;
    boolean isVista = LookUtils.IS_OS_WINDOWS_VISTA;
    initFontDefaults(table);
    if (isClassic)
      initComponentDefaultsClassic(table); 
    if (isXP && LookUtils.IS_JAVA_1_4)
      initComponentDefaultsXP14(table); 
    MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout("Windows", table);
    if (!isVista || !LookUtils.IS_JAVA_6_OR_LATER || !LookUtils.IS_LAF_WINDOWS_XP_ENABLED)
      initMenuItemDefaults(table, microLayout); 
    Object marginBorder = new BasicBorders.MarginBorder();
    Object checkBoxMargin = microLayout.getCheckBoxMargin();
    Object etchedBorder = new UIDefaults.ProxyLazyValue("javax.swing.plaf.BorderUIResource", "getEtchedBorderUIResource");
    Object buttonBorder = new SimpleProxyLazyValue("com.jgoodies.looks.windows.WindowsLookAndFeel", "getButtonBorder");
    Object menuBorder = isXP ? WindowsBorders.getXPMenuBorder() : WindowsBorders.getMenuBorder();
    Object menuBarEmptyBorder = marginBorder;
    Object menuBarSeparatorBorder = WindowsBorders.getSeparatorBorder();
    Object menuBarEtchedBorder = WindowsBorders.getEtchedBorder();
    Object menuBarHeaderBorder = WindowsBorders.getMenuBarHeaderBorder();
    Object toolBarEmptyBorder = marginBorder;
    Object toolBarSeparatorBorder = WindowsBorders.getSeparatorBorder();
    Object toolBarEtchedBorder = WindowsBorders.getEtchedBorder();
    Object toolBarHeaderBorder = WindowsBorders.getToolBarHeaderBorder();
    Object buttonMargin = microLayout.getButtonMargin();
    Object toolBarSeparatorSize = LookUtils.IS_JAVA_1_4_2_OR_LATER ? null : new DimensionUIResource(6, (Options.getDefaultIconSize()).height);
    Object textInsets = microLayout.getTextInsets();
    Object wrappedTextInsets = microLayout.getWrappedTextInsets();
    Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
    int comboBorderSize = microLayout.getComboBorderSize();
    int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
    int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
    Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
    Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
    Object popupMenuSeparatorMargin = microLayout.getPopupMenuSeparatorMargin();
    int treeFontSize = table.getFont("Tree.font").getSize();
    Integer rowHeight = new Integer(treeFontSize + 6);
    Class superclass = getClass().getSuperclass();
    Color controlColor = table.getColor("control");
    Object disabledTextBackground = table.getColor("TextField.disabledBackground");
    Object inactiveTextBackground = table.getColor("TextField.inactiveBackground");
    Object comboBoxDisabledBackground = (isVista && isXP) ? table.getColor("ComboBox.background") : disabledTextBackground;
    Object menuBarBackground = isXP ? table.get("control") : table.get("menu");
    Object menuSelectionBackground = isXP ? table.get("MenuItem.selectionBackground") : table.get("Menu.background");
    Object menuSelectionForeground = isXP ? table.get("MenuItem.selectionForeground") : table.get("Menu.foreground");
    Character passwordEchoChar = new Character(isXP ? 9679 : 42);
    Object[] defaults = { 
        "Button.border", buttonBorder, "Button.margin", buttonMargin, "CheckBox.border", marginBorder, "CheckBox.margin", checkBoxMargin, "ComboBox.disabledBackground", comboBoxDisabledBackground, 
        "ComboBox.editorBorder", marginBorder, "ComboBox.editorColumns", new Integer(5), "ComboBox.editorInsets", comboEditorInsets, "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, 
        "EditorPane.margin", wrappedTextInsets, "Menu.border", menuBorder, "Menu.borderPainted", Boolean.TRUE, "Menu.background", menuBarBackground, "Menu.selectionForeground", menuSelectionForeground, 
        "Menu.selectionBackground", menuSelectionBackground, "MenuBar.background", menuBarBackground, "MenuBar.border", menuBarSeparatorBorder, "MenuBar.emptyBorder", menuBarEmptyBorder, "MenuBar.separatorBorder", menuBarSeparatorBorder, 
        "MenuBar.etchedBorder", menuBarEtchedBorder, "MenuBar.headerBorder", menuBarHeaderBorder, "FormattedTextField.disabledBackground", disabledTextBackground, "FormattedTextField.inactiveBackground", inactiveTextBackground, "FormattedTextField.margin", textInsets, 
        "PasswordField.margin", textInsets, "PasswordField.echoChar", passwordEchoChar, "PopupMenu.border", WindowsBorders.getPopupMenuBorder(), "PopupMenu.noMarginBorder", WindowsBorders.getNoMarginPopupMenuBorder(), "PopupMenuSeparator.margin", popupMenuSeparatorMargin, 
        "ScrollPane.etchedBorder", etchedBorder, "Spinner.defaultEditorInsets", textInsets, "RadioButton.border", marginBorder, "RadioButton.margin", checkBoxMargin, "Table.gridColor", controlColor, 
        "TextArea.margin", wrappedTextInsets, "TextArea.disabledBackground", disabledTextBackground, "TextArea.inactiveBackground", inactiveTextBackground, "TextField.margin", textInsets, "ToggleButton.margin", buttonMargin, 
        "ToolBar.emptyBorder", toolBarEmptyBorder, "ToolBar.separatorBorder", toolBarSeparatorBorder, "ToolBar.etchedBorder", toolBarEtchedBorder, "ToolBar.headerBorder", toolBarHeaderBorder, "ToolBar.separatorSize", toolBarSeparatorSize, 
        "ToolBar.margin", new InsetsUIResource(0, 10, 0, 0), "Tree.selectionBorderColor", controlColor, "Tree.rowHeight", rowHeight };
    if (LookUtils.IS_JAVA_1_4)
      defaults = append(defaults, new Object[] { "InternalFrame.icon", makeIcon(superclass, "icons/JavaCup.gif"), "OptionPane.errorIcon", isXP ? makeIcon(getClass(), "icons/xp/Error.png") : makeIcon(superclass, "icons/Error.gif"), "OptionPane.informationIcon", isXP ? makeIcon(getClass(), "icons/xp/Inform.png") : makeIcon(superclass, "icons/Inform.gif"), "OptionPane.warningIcon", isXP ? makeIcon(getClass(), "icons/xp/Warn.png") : makeIcon(superclass, "icons/Warn.gif"), "OptionPane.questionIcon", isXP ? makeIcon(getClass(), "icons/xp/Inform.png") : makeIcon(superclass, "icons/Question.gif") }); 
    if (LookUtils.IS_JAVA_1_4 || LookUtils.IS_JAVA_5)
      defaults = append(defaults, new Object[] { "Tree.openIcon", isXP ? makeIcon(getClass(), "icons/xp/TreeOpen.png") : makeIcon(getClass(), "icons/TreeOpen.gif"), "Tree.closedIcon", isXP ? makeIcon(getClass(), "icons/xp/TreeClosed.png") : makeIcon(getClass(), "icons/TreeClosed.gif") }); 
    if (LookUtils.IS_JAVA_6_OR_LATER)
      defaults = append(defaults, new Object[] { "Spinner.border", table.get("TextField.border") }); 
    table.putDefaults(defaults);
  }
  
  private void initComponentDefaultsClassic(UIDefaults table) {
    Object checkBoxIcon = new SimpleProxyLazyValue("com.jgoodies.looks.windows.WindowsLookAndFeel", "getCheckBoxIcon");
    Object radioButtonIcon = new SimpleProxyLazyValue("com.jgoodies.looks.windows.WindowsLookAndFeel", "getRadioButtonIcon");
    Border winInsetBorder = new BasicBorders.FieldBorder(table.getColor("controlShadow"), table.getColor("controlDkShadow"), table.getColor("controlHighlight"), table.getColor("controlLtHighlight"));
    Object[] defaults = { "CheckBox.checkColor", table.get("controlText"), "CheckBox.icon", checkBoxIcon, "RadioButton.checkColor", table.get("controlText"), "RadioButton.icon", radioButtonIcon, "Table.scrollPaneBorder", winInsetBorder };
    table.putDefaults(defaults);
  }
  
  private void initComponentDefaultsXP14(UIDefaults table) {
    Object[] defaults = { "TitledBorder.titleColor", table.getColor("activeCaption") };
    table.putDefaults(defaults);
  }
  
  private void initFontDefaults(UIDefaults table) {
    FontPolicy fontChoicePolicy = getFontPolicy();
    FontSet fontSet = fontChoicePolicy.getFontSet("Windows", table);
    initFontDefaults(table, fontSet);
  }
  
  private void initMenuItemDefaults(UIDefaults table, MicroLayout microLayout) {
    Object menuMargin = microLayout.getMenuMargin();
    Object menuItemMargin = microLayout.getMenuItemMargin();
    MinimumSizedIcon minimumSizedIcon = new MinimumSizedIcon();
    Object[] defaults = { 
        "Menu.margin", menuMargin, "MenuItem.borderPainted", Boolean.TRUE, "MenuItem.checkIcon", minimumSizedIcon, "MenuItem.margin", menuItemMargin, "CheckBoxMenuItem.margin", menuItemMargin, 
        "RadioButtonMenuItem.margin", menuItemMargin };
    table.putDefaults(defaults);
  }
  
  private static void initFontDefaults(UIDefaults table, FontSet fontSet) {
    Font controlFont = fontSet.getControlFont();
    Font menuFont = fontSet.getMenuFont();
    Font messageFont = fontSet.getMessageFont();
    Font toolTipFont = fontSet.getSmallFont();
    Font titleFont = fontSet.getTitleFont();
    Font windowFont = fontSet.getWindowTitleFont();
    Object[] defaults = { 
        "Button.font", controlFont, "CheckBox.font", controlFont, "ColorChooser.font", controlFont, "ComboBox.font", controlFont, "EditorPane.font", controlFont, 
        "FormattedTextField.font", controlFont, "Label.font", controlFont, "List.font", controlFont, "Panel.font", controlFont, "PasswordField.font", controlFont, 
        "ProgressBar.font", controlFont, "RadioButton.font", controlFont, "ScrollPane.font", controlFont, "Spinner.font", controlFont, "TabbedPane.font", controlFont, 
        "Table.font", controlFont, "TableHeader.font", controlFont, "TextArea.font", controlFont, "TextField.font", controlFont, "TextPane.font", controlFont, 
        "ToolBar.font", controlFont, "ToggleButton.font", controlFont, "Tree.font", controlFont, "Viewport.font", controlFont, "InternalFrame.titleFont", windowFont, 
        "OptionPane.font", messageFont, "OptionPane.messageFont", messageFont, "OptionPane.buttonFont", messageFont, "TitledBorder.font", titleFont, "ToolTip.font", toolTipFont, 
        "CheckBoxMenuItem.font", menuFont, "CheckBoxMenuItem.acceleratorFont", menuFont, "Menu.font", menuFont, "Menu.acceleratorFont", menuFont, "MenuBar.font", menuFont, 
        "MenuItem.font", menuFont, "MenuItem.acceleratorFont", menuFont, "PopupMenu.font", menuFont, "RadioButtonMenuItem.font", menuFont, "RadioButtonMenuItem.acceleratorFont", menuFont };
    table.putDefaults(defaults);
  }
  
  public static Border getButtonBorder() {
    return WindowsBorders.getButtonBorder();
  }
  
  public static Icon getCheckBoxIcon() {
    return WindowsIconFactory.getCheckBoxIcon();
  }
  
  public static Icon getRadioButtonIcon() {
    return WindowsIconFactory.getRadioButtonIcon();
  }
  
  private static Object[] append(Object[] source, String key, Object value) {
    int length = source.length;
    Object[] destination = new Object[length + 2];
    System.arraycopy(source, 0, destination, 0, length);
    destination[length] = key;
    destination[length + 1] = value;
    return destination;
  }
  
  private static Object[] append(Object[] source, Object[] keysAndValues) {
    int length = source.length;
    Object[] destination = new Object[length + keysAndValues.length];
    System.arraycopy(source, 0, destination, 0, length);
    for (int i = 0; i < keysAndValues.length; i++)
      destination[length + i] = keysAndValues[i]; 
    return destination;
  }
  
  private static class SimpleProxyLazyValue implements UIDefaults.LazyValue {
    private final String className;
    
    private final String methodName;
    
    public SimpleProxyLazyValue(String c, String m) {
      this.className = c;
      this.methodName = m;
    }
    
    public Object createValue(UIDefaults table) {
      Object instance = null;
      try {
        ClassLoader classLoader = (table != null) ? (ClassLoader)table.get("ClassLoader") : Thread.currentThread().getContextClassLoader();
        if (classLoader == null)
          classLoader = getClass().getClassLoader(); 
        Class c = Class.forName(this.className, true, classLoader);
        Method m = c.getMethod(this.methodName, null);
        instance = m.invoke(c, null);
      } catch (Throwable t) {
        LookUtils.log("Problem creating " + this.className + " with method " + this.methodName + t);
      } 
      return instance;
    }
  }
}
