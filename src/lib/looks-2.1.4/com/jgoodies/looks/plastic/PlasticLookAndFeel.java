package com.jgoodies.looks.plastic;

import com.jgoodies.looks.FontPolicies;
import com.jgoodies.looks.FontPolicy;
import com.jgoodies.looks.LookUtils;
import com.jgoodies.looks.MicroLayout;
import com.jgoodies.looks.MicroLayoutPolicies;
import com.jgoodies.looks.MicroLayoutPolicy;
import com.jgoodies.looks.common.MinimumSizedIcon;
import com.jgoodies.looks.common.RGBGrayFilter;
import com.jgoodies.looks.common.ShadowPopupFactory;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.IconUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

public class PlasticLookAndFeel extends MetalLookAndFeel {
  public static final String BORDER_STYLE_KEY = "Plastic.borderStyle";
  
  public static final String IS_3D_KEY = "Plastic.is3D";
  
  public static final String DEFAULT_THEME_KEY = "Plastic.defaultTheme";
  
  public static final String HIGH_CONTRAST_FOCUS_ENABLED_KEY = "Plastic.highContrastFocus";
  
  protected static final String TAB_STYLE_KEY = "Plastic.tabStyle";
  
  public static final String TAB_STYLE_DEFAULT_VALUE = "default";
  
  public static final String TAB_STYLE_METAL_VALUE = "metal";
  
  private static final Object THEME_KEY = new StringBuffer("Plastic.theme");
  
  private static boolean useMetalTabs = LookUtils.getSystemProperty("Plastic.tabStyle", "").equalsIgnoreCase("metal");
  
  private static boolean useHighContrastFocusColors = (LookUtils.getSystemProperty("Plastic.highContrastFocus") != null);
  
  private static List installedThemes;
  
  private static boolean is3DEnabled = false;
  
  private static boolean selectTextOnKeyboardFocusGained = LookUtils.IS_OS_WINDOWS;
  
  private static Method getCurrentThemeMethod = null;
  
  private static final String THEME_CLASSNAME_PREFIX = "com.jgoodies.looks.plastic.theme.";
  
  static {
    if (LookUtils.IS_JAVA_5_OR_LATER)
      getCurrentThemeMethod = getMethodGetCurrentTheme(); 
  }
  
  public PlasticLookAndFeel() {
    getPlasticTheme();
  }
  
  public String getID() {
    return "JGoodies Plastic";
  }
  
  public String getName() {
    return "JGoodies Plastic";
  }
  
  public String getDescription() {
    return "The JGoodies Plastic Look and Feel - © 2001-2007 JGoodies Karsten Lentzsch";
  }
  
  public static FontPolicy getFontPolicy() {
    FontPolicy policy = (FontPolicy)UIManager.get("Plastic.fontPolicy");
    if (policy != null)
      return policy; 
    FontPolicy defaultPolicy = FontPolicies.getDefaultPlasticPolicy();
    return FontPolicies.customSettingsPolicy(defaultPolicy);
  }
  
  public static void setFontPolicy(FontPolicy fontPolicy) {
    UIManager.put("Plastic.fontPolicy", fontPolicy);
  }
  
  public static MicroLayoutPolicy getMicroLayoutPolicy() {
    MicroLayoutPolicy policy = (MicroLayoutPolicy)UIManager.get("Plastic.MicroLayoutPolicy");
    return (policy != null) ? policy : MicroLayoutPolicies.getDefaultPlasticPolicy();
  }
  
  public static void setMicroLayoutPolicy(MicroLayout microLayoutPolicy) {
    UIManager.put("Plastic.MicroLayoutPolicy", microLayoutPolicy);
  }
  
  protected boolean is3DEnabled() {
    return is3DEnabled;
  }
  
  public static void set3DEnabled(boolean b) {
    is3DEnabled = b;
  }
  
  public static String getTabStyle() {
    return useMetalTabs ? "metal" : "default";
  }
  
  public static void setTabStyle(String tabStyle) {
    useMetalTabs = tabStyle.equalsIgnoreCase("metal");
  }
  
  public static boolean getHighContrastFocusColorsEnabled() {
    return useHighContrastFocusColors;
  }
  
  public static void setHighContrastFocusColorsEnabled(boolean b) {
    useHighContrastFocusColors = b;
  }
  
  public static boolean isSelectTextOnKeyboardFocusGained() {
    return selectTextOnKeyboardFocusGained;
  }
  
  public static void setSelectTextOnKeyboardFocusGained(boolean b) {
    selectTextOnKeyboardFocusGained = b;
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
    String plasticPrefix = "com.jgoodies.looks.plastic.Plastic";
    String commonPrefix = "com.jgoodies.looks.common.ExtBasic";
    Object[] uiDefaults = { 
        "ButtonUI", "com.jgoodies.looks.plastic.PlasticButtonUI", "ToggleButtonUI", "com.jgoodies.looks.plastic.PlasticToggleButtonUI", "ComboBoxUI", "com.jgoodies.looks.plastic.PlasticComboBoxUI", "ScrollBarUI", "com.jgoodies.looks.plastic.PlasticScrollBarUI", "SpinnerUI", "com.jgoodies.looks.plastic.PlasticSpinnerUI", 
        "MenuBarUI", "com.jgoodies.looks.plastic.PlasticMenuBarUI", "ToolBarUI", "com.jgoodies.looks.plastic.PlasticToolBarUI", "MenuUI", "com.jgoodies.looks.plastic.PlasticMenuUI", "MenuItemUI", "com.jgoodies.looks.common.ExtBasicMenuItemUI", "CheckBoxMenuItemUI", "com.jgoodies.looks.common.ExtBasicCheckBoxMenuItemUI", 
        "RadioButtonMenuItemUI", "com.jgoodies.looks.common.ExtBasicRadioButtonMenuItemUI", "PopupMenuUI", "com.jgoodies.looks.plastic.PlasticPopupMenuUI", "PopupMenuSeparatorUI", "com.jgoodies.looks.common.ExtBasicPopupMenuSeparatorUI", "OptionPaneUI", "com.jgoodies.looks.plastic.PlasticOptionPaneUI", "ScrollPaneUI", "com.jgoodies.looks.plastic.PlasticScrollPaneUI", 
        "SplitPaneUI", "com.jgoodies.looks.plastic.PlasticSplitPaneUI", "PasswordFieldUI", "com.jgoodies.looks.plastic.PlasticPasswordFieldUI", "TextAreaUI", "com.jgoodies.looks.plastic.PlasticTextAreaUI", "TreeUI", "com.jgoodies.looks.plastic.PlasticTreeUI", "InternalFrameUI", "com.jgoodies.looks.plastic.PlasticInternalFrameUI", 
        "SeparatorUI", "com.jgoodies.looks.plastic.PlasticSeparatorUI", "ToolBarSeparatorUI", "com.jgoodies.looks.plastic.PlasticToolBarSeparatorUI", "FileChooserUI", "com.jgoodies.looks.plastic.PlasticFileChooserUI" };
    if (!useMetalTabs)
      uiDefaults = append(uiDefaults, "TabbedPaneUI", "com.jgoodies.looks.plastic.PlasticTabbedPaneUI"); 
    if (isSelectTextOnKeyboardFocusGained()) {
      uiDefaults = append(uiDefaults, "TextFieldUI", "com.jgoodies.looks.plastic.PlasticTextFieldUI");
      uiDefaults = append(uiDefaults, "FormattedTextFieldUI", "com.jgoodies.looks.plastic.PlasticFormattedTextFieldUI");
    } 
    table.putDefaults(uiDefaults);
  }
  
  protected void initComponentDefaults(UIDefaults table) {
    super.initComponentDefaults(table);
    MicroLayout microLayout = getMicroLayoutPolicy().getMicroLayout(getName(), table);
    Insets buttonBorderInsets = microLayout.getButtonBorderInsets();
    Object marginBorder = new BasicBorders.MarginBorder();
    Object buttonBorder = PlasticBorders.getButtonBorder(buttonBorderInsets);
    Object comboBoxButtonBorder = PlasticBorders.getComboBoxArrowButtonBorder();
    Border comboBoxEditorBorder = PlasticBorders.getComboBoxEditorBorder();
    Object menuItemBorder = PlasticBorders.getMenuItemBorder();
    Object textFieldBorder = PlasticBorders.getTextFieldBorder();
    Object toggleButtonBorder = PlasticBorders.getToggleButtonBorder(buttonBorderInsets);
    Object scrollPaneBorder = PlasticBorders.getScrollPaneBorder();
    Object tableHeaderBorder = new BorderUIResource((Border)table.get("TableHeader.cellBorder"));
    Object menuBarEmptyBorder = marginBorder;
    Object menuBarSeparatorBorder = PlasticBorders.getSeparatorBorder();
    Object menuBarEtchedBorder = PlasticBorders.getEtchedBorder();
    Object menuBarHeaderBorder = PlasticBorders.getMenuBarHeaderBorder();
    Object toolBarEmptyBorder = marginBorder;
    Object toolBarSeparatorBorder = PlasticBorders.getSeparatorBorder();
    Object toolBarEtchedBorder = PlasticBorders.getEtchedBorder();
    Object toolBarHeaderBorder = PlasticBorders.getToolBarHeaderBorder();
    Object internalFrameBorder = getInternalFrameBorder();
    Object paletteBorder = getPaletteBorder();
    Color controlColor = table.getColor("control");
    Object checkBoxIcon = PlasticIconFactory.getCheckBoxIcon();
    Object checkBoxMargin = microLayout.getCheckBoxMargin();
    Object buttonMargin = microLayout.getButtonMargin();
    Object textInsets = microLayout.getTextInsets();
    Object wrappedTextInsets = microLayout.getWrappedTextInsets();
    Insets comboEditorInsets = microLayout.getComboBoxEditorInsets();
    Insets comboEditorBorderInsets = comboBoxEditorBorder.getBorderInsets(null);
    int comboBorderSize = comboEditorBorderInsets.left;
    int comboPopupBorderSize = microLayout.getComboPopupBorderSize();
    int comboRendererGap = comboEditorInsets.left + comboBorderSize - comboPopupBorderSize;
    Object comboRendererBorder = new EmptyBorder(1, comboRendererGap, 1, comboRendererGap);
    Object comboTableEditorInsets = new Insets(0, 0, 0, 0);
    Object menuItemMargin = microLayout.getMenuItemMargin();
    Object menuMargin = microLayout.getMenuMargin();
    MinimumSizedIcon minimumSizedIcon = new MinimumSizedIcon();
    Icon checkBoxMenuItemIcon = PlasticIconFactory.getCheckBoxMenuItemIcon();
    Icon radioButtonMenuItemIcon = PlasticIconFactory.getRadioButtonMenuItemIcon();
    Color menuItemForeground = table.getColor("MenuItem.foreground");
    Color inactiveTextBackground = table.getColor("TextField.inactiveBackground");
    int treeFontSize = table.getFont("Tree.font").getSize();
    Integer rowHeight = new Integer(treeFontSize + 6);
    Object treeExpandedIcon = PlasticIconFactory.getExpandedTreeIcon();
    Object treeCollapsedIcon = PlasticIconFactory.getCollapsedTreeIcon();
    ColorUIResource gray = new ColorUIResource(Color.GRAY);
    Boolean is3D = Boolean.valueOf(is3DEnabled());
    Character passwordEchoChar = new Character(LookUtils.IS_OS_WINDOWS ? 9679 : 8226);
    String iconPrefix = "icons/" + (LookUtils.IS_LOW_RESOLUTION ? "32x32/" : "48x48/");
    Object errorIcon = makeIcon(getClass(), iconPrefix + "dialog-error.png");
    Object informationIcon = makeIcon(getClass(), iconPrefix + "dialog-information.png");
    Object questionIcon = makeIcon(getClass(), iconPrefix + "dialog-question.png");
    Object warningIcon = makeIcon(getClass(), iconPrefix + "dialog-warning.png");
    Object[] defaults = { 
        "Button.border", buttonBorder, "Button.margin", buttonMargin, "CheckBox.margin", checkBoxMargin, "CheckBox.icon", checkBoxIcon, "CheckBoxMenuItem.border", menuItemBorder, 
        "CheckBoxMenuItem.margin", menuItemMargin, "CheckBoxMenuItem.checkIcon", checkBoxMenuItemIcon, "CheckBoxMenuItem.background", getMenuItemBackground(), "CheckBoxMenuItem.selectionForeground", getMenuItemSelectedForeground(), "CheckBoxMenuItem.selectionBackground", getMenuItemSelectedBackground(), 
        "CheckBoxMenuItem.acceleratorForeground", menuItemForeground, "CheckBoxMenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "CheckBoxMenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "ComboBox.selectionForeground", getMenuSelectedForeground(), "ComboBox.selectionBackground", getMenuSelectedBackground(), 
        "ComboBox.arrowButtonBorder", comboBoxButtonBorder, "ComboBox.editorBorder", comboBoxEditorBorder, "ComboBox.editorColumns", new Integer(5), "ComboBox.editorBorderInsets", comboEditorBorderInsets, "ComboBox.editorInsets", textInsets, 
        "ComboBox.tableEditorInsets", comboTableEditorInsets, "ComboBox.rendererBorder", comboRendererBorder, "EditorPane.margin", wrappedTextInsets, "InternalFrame.border", internalFrameBorder, "InternalFrame.paletteBorder", paletteBorder, 
        "List.font", getControlTextFont(), "Menu.border", PlasticBorders.getMenuBorder(), "Menu.margin", menuMargin, "Menu.arrowIcon", PlasticIconFactory.getMenuArrowIcon(), "MenuBar.emptyBorder", menuBarEmptyBorder, 
        "MenuBar.separatorBorder", menuBarSeparatorBorder, "MenuBar.etchedBorder", menuBarEtchedBorder, "MenuBar.headerBorder", menuBarHeaderBorder, "MenuItem.border", menuItemBorder, "MenuItem.checkIcon", minimumSizedIcon, 
        "MenuItem.margin", menuItemMargin, "MenuItem.background", getMenuItemBackground(), "MenuItem.selectionForeground", getMenuItemSelectedForeground(), "MenuItem.selectionBackground", getMenuItemSelectedBackground(), "MenuItem.acceleratorForeground", menuItemForeground, 
        "MenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "MenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), "OptionPane.errorIcon", errorIcon, "OptionPane.informationIcon", informationIcon, "OptionPane.questionIcon", questionIcon, 
        "OptionPane.warningIcon", warningIcon, "FileView.computerIcon", makeIcon(getClass(), "icons/Computer.gif"), "FileView.directoryIcon", makeIcon(getClass(), "icons/TreeClosed.gif"), "FileView.fileIcon", makeIcon(getClass(), "icons/File.gif"), "FileView.floppyDriveIcon", makeIcon(getClass(), "icons/FloppyDrive.gif"), 
        "FileView.hardDriveIcon", makeIcon(getClass(), "icons/HardDrive.gif"), "FileChooser.homeFolderIcon", makeIcon(getClass(), "icons/HomeFolder.gif"), "FileChooser.newFolderIcon", makeIcon(getClass(), "icons/NewFolder.gif"), "FileChooser.upFolderIcon", makeIcon(getClass(), "icons/UpFolder.gif"), "Tree.closedIcon", makeIcon(getClass(), "icons/TreeClosed.gif"), 
        "Tree.openIcon", makeIcon(getClass(), "icons/TreeOpen.gif"), "Tree.leafIcon", makeIcon(getClass(), "icons/TreeLeaf.gif"), "FormattedTextField.border", textFieldBorder, "FormattedTextField.margin", textInsets, "PasswordField.border", textFieldBorder, 
        "PasswordField.margin", textInsets, "PasswordField.echoChar", passwordEchoChar, "PopupMenu.border", PlasticBorders.getPopupMenuBorder(), "PopupMenu.noMarginBorder", PlasticBorders.getNoMarginPopupMenuBorder(), "PopupMenuSeparator.margin", new InsetsUIResource(3, 4, 3, 4), 
        "RadioButton.margin", checkBoxMargin, "RadioButtonMenuItem.border", menuItemBorder, "RadioButtonMenuItem.checkIcon", radioButtonMenuItemIcon, "RadioButtonMenuItem.margin", menuItemMargin, "RadioButtonMenuItem.background", getMenuItemBackground(), 
        "RadioButtonMenuItem.selectionForeground", getMenuItemSelectedForeground(), "RadioButtonMenuItem.selectionBackground", getMenuItemSelectedBackground(), "RadioButtonMenuItem.acceleratorForeground", menuItemForeground, "RadioButtonMenuItem.acceleratorSelectionForeground", getMenuItemSelectedForeground(), "RadioButtonMenuItem.acceleratorSelectionBackground", getMenuItemSelectedBackground(), 
        "Separator.foreground", getControlDarkShadow(), "ScrollPane.border", scrollPaneBorder, "ScrollPane.etchedBorder", scrollPaneBorder, "SimpleInternalFrame.activeTitleForeground", getSimpleInternalFrameForeground(), "SimpleInternalFrame.activeTitleBackground", getSimpleInternalFrameBackground(), 
        "Spinner.border", PlasticBorders.getFlush3DBorder(), "Spinner.defaultEditorInsets", textInsets, "SplitPane.dividerSize", new Integer(7), "TabbedPane.focus", getFocusColor(), "TabbedPane.tabInsets", new InsetsUIResource(1, 9, 1, 8), 
        "Table.foreground", table.get("textText"), "Table.gridColor", controlColor, "Table.scrollPaneBorder", scrollPaneBorder, "TableHeader.cellBorder", tableHeaderBorder, "TextArea.inactiveBackground", inactiveTextBackground, 
        "TextArea.margin", wrappedTextInsets, "TextField.border", textFieldBorder, "TextField.margin", textInsets, "TitledBorder.font", getTitleTextFont(), "TitledBorder.titleColor", getTitleTextColor(), 
        "ToggleButton.border", toggleButtonBorder, "ToggleButton.margin", buttonMargin, "ToolBar.emptyBorder", toolBarEmptyBorder, "ToolBar.separatorBorder", toolBarSeparatorBorder, "ToolBar.etchedBorder", toolBarEtchedBorder, 
        "ToolBar.headerBorder", toolBarHeaderBorder, "ToolTip.hideAccelerator", Boolean.TRUE, "Tree.expandedIcon", treeExpandedIcon, "Tree.collapsedIcon", treeCollapsedIcon, "Tree.line", gray, 
        "Tree.hash", gray, "Tree.rowHeight", rowHeight, "Button.is3DEnabled", is3D, "ComboBox.is3DEnabled", is3D, "MenuBar.is3DEnabled", is3D, 
        "ToolBar.is3DEnabled", is3D, "ScrollBar.is3DEnabled", is3D, "ToggleButton.is3DEnabled", is3D, "CheckBox.border", marginBorder, "RadioButton.border", marginBorder, 
        "ProgressBar.selectionForeground", getSystemTextColor(), "ProgressBar.selectionBackground", getSystemTextColor() };
    table.putDefaults(defaults);
    String soundPathPrefix = "/javax/swing/plaf/metal/";
    Object[] auditoryCues = (Object[])table.get("AuditoryCues.allAuditoryCues");
    if (auditoryCues != null) {
      String[] arrayOfString = new String[auditoryCues.length * 2];
      for (int i = 0; i < auditoryCues.length; i++) {
        Object auditoryCue = auditoryCues[i];
        arrayOfString[2 * i] = (String)auditoryCue;
        arrayOfString[2 * i + 1] = soundPathPrefix + table.getString(auditoryCue);
      } 
      table.putDefaults((Object[])arrayOfString);
    } 
  }
  
  protected void initSystemColorDefaults(UIDefaults table) {
    super.initSystemColorDefaults(table);
    table.put("unifiedControlShadow", table.getColor("controlDkShadow"));
    table.put("primaryControlHighlight", getPrimaryControlHighlight());
  }
  
  public static PlasticTheme createMyDefaultTheme() {
    String defaultName;
    if (LookUtils.IS_LAF_WINDOWS_XP_ENABLED) {
      defaultName = getDefaultXPTheme();
    } else if (LookUtils.IS_OS_WINDOWS_MODERN) {
      defaultName = "DesertBluer";
    } else {
      defaultName = "SkyBlue";
    } 
    String userName = LookUtils.getSystemProperty("Plastic.defaultTheme", "");
    boolean overridden = (userName.length() > 0);
    String themeName = overridden ? userName : defaultName;
    PlasticTheme theme = createTheme(themeName);
    PlasticTheme result = (theme != null) ? theme : (PlasticTheme)new SkyBluer();
    if (overridden) {
      String className = result.getClass().getName().substring("com.jgoodies.looks.plastic.theme.".length());
      if (className.equals(userName)) {
        LookUtils.log("I have successfully installed the '" + result.getName() + "' theme.");
      } else {
        LookUtils.log("I could not install the Plastic theme '" + userName + "'.");
        LookUtils.log("I have installed the '" + result.getName() + "' theme, instead.");
      } 
    } 
    return result;
  }
  
  private static String getDefaultXPTheme() {
    String fallbackName = "ExperienceBlue";
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    String xpstyleDll = (String)toolkit.getDesktopProperty("win.xpstyle.dllName");
    if (xpstyleDll == null)
      return fallbackName; 
    boolean isStyleLuna = xpstyleDll.endsWith("luna.msstyles");
    boolean isStyleRoyale = xpstyleDll.endsWith("Royale.msstyles");
    boolean isStyleAero = xpstyleDll.endsWith("Aero.msstyles");
    if (isStyleRoyale)
      return "ExperienceRoyale"; 
    if (isStyleLuna) {
      String xpstyleColorName = (String)toolkit.getDesktopProperty("win.xpstyle.colorName");
      if (xpstyleColorName == null)
        return fallbackName; 
      if (xpstyleColorName.equalsIgnoreCase("HomeStead"))
        return "ExperienceGreen"; 
      if (xpstyleColorName.equalsIgnoreCase("Metallic"))
        return "Silver"; 
      return fallbackName;
    } 
    if (isStyleAero)
      return "LightGray"; 
    return fallbackName;
  }
  
  public static List getInstalledThemes() {
    if (null == installedThemes)
      installDefaultThemes(); 
    Collections.sort(installedThemes, new Comparator() {
          public int compare(Object o1, Object o2) {
            MetalTheme theme1 = (MetalTheme)o1;
            MetalTheme theme2 = (MetalTheme)o2;
            return theme1.getName().compareTo(theme2.getName());
          }
        });
    return installedThemes;
  }
  
  protected static void installDefaultThemes() {
    installedThemes = new ArrayList();
    String[] themeNames = { 
        "BrownSugar", "DarkStar", "DesertBlue", "DesertBluer", "DesertGreen", "DesertRed", "DesertYellow", "ExperienceBlue", "ExperienceGreen", "ExperienceRoyale", 
        "LightGray", "Silver", "SkyBlue", "SkyBluer", "SkyGreen", "SkyKrupp", "SkyPink", "SkyRed", "SkyYellow" };
    for (int i = themeNames.length - 1; i >= 0; i--)
      installTheme(createTheme(themeNames[i])); 
  }
  
  protected static PlasticTheme createTheme(String themeName) {
    String className = "com.jgoodies.looks.plastic.theme." + themeName;
    try {
      Class cl = Class.forName(className);
      return (PlasticTheme)cl.newInstance();
    } catch (ClassNotFoundException e) {
    
    } catch (IllegalAccessException e) {
    
    } catch (InstantiationException e) {}
    LookUtils.log("Can't create theme " + className);
    return null;
  }
  
  public static void installTheme(PlasticTheme theme) {
    if (null == installedThemes)
      installDefaultThemes(); 
    installedThemes.add(theme);
  }
  
  public static PlasticTheme getPlasticTheme() {
    if (LookUtils.IS_JAVA_5_OR_LATER) {
      MetalTheme theme = getCurrentTheme0();
      if (theme instanceof PlasticTheme)
        return (PlasticTheme)theme; 
    } 
    PlasticTheme uimanagerTheme = (PlasticTheme)UIManager.get(THEME_KEY);
    if (uimanagerTheme != null)
      return uimanagerTheme; 
    PlasticTheme initialTheme = createMyDefaultTheme();
    setPlasticTheme(initialTheme);
    return initialTheme;
  }
  
  public static void setPlasticTheme(PlasticTheme theme) {
    if (theme == null)
      throw new NullPointerException("The theme must not be null."); 
    UIManager.put(THEME_KEY, theme);
    setCurrentTheme(theme);
  }
  
  public static BorderUIResource getInternalFrameBorder() {
    return new BorderUIResource(PlasticBorders.getInternalFrameBorder());
  }
  
  public static BorderUIResource getPaletteBorder() {
    return new BorderUIResource(PlasticBorders.getPaletteBorder());
  }
  
  public static ColorUIResource getPrimaryControlDarkShadow() {
    return getPlasticTheme().getPrimaryControlDarkShadow();
  }
  
  public static ColorUIResource getPrimaryControlHighlight() {
    return getPlasticTheme().getPrimaryControlHighlight();
  }
  
  public static ColorUIResource getPrimaryControlInfo() {
    return getPlasticTheme().getPrimaryControlInfo();
  }
  
  public static ColorUIResource getPrimaryControlShadow() {
    return getPlasticTheme().getPrimaryControlShadow();
  }
  
  public static ColorUIResource getPrimaryControl() {
    return getPlasticTheme().getPrimaryControl();
  }
  
  public static ColorUIResource getControlHighlight() {
    return getPlasticTheme().getControlHighlight();
  }
  
  public static ColorUIResource getControlDarkShadow() {
    return getPlasticTheme().getControlDarkShadow();
  }
  
  public static ColorUIResource getControl() {
    return getPlasticTheme().getControl();
  }
  
  public static ColorUIResource getFocusColor() {
    return getPlasticTheme().getFocusColor();
  }
  
  public static ColorUIResource getMenuItemBackground() {
    return getPlasticTheme().getMenuItemBackground();
  }
  
  public static ColorUIResource getMenuItemSelectedBackground() {
    return getPlasticTheme().getMenuItemSelectedBackground();
  }
  
  public static ColorUIResource getMenuItemSelectedForeground() {
    return getPlasticTheme().getMenuItemSelectedForeground();
  }
  
  public static ColorUIResource getWindowTitleBackground() {
    return getPlasticTheme().getWindowTitleBackground();
  }
  
  public static ColorUIResource getWindowTitleForeground() {
    return getPlasticTheme().getWindowTitleForeground();
  }
  
  public static ColorUIResource getWindowTitleInactiveBackground() {
    return getPlasticTheme().getWindowTitleInactiveBackground();
  }
  
  public static ColorUIResource getWindowTitleInactiveForeground() {
    return getPlasticTheme().getWindowTitleInactiveForeground();
  }
  
  public static ColorUIResource getSimpleInternalFrameForeground() {
    return getPlasticTheme().getSimpleInternalFrameForeground();
  }
  
  public static ColorUIResource getSimpleInternalFrameBackground() {
    return getPlasticTheme().getSimpleInternalFrameBackground();
  }
  
  public static ColorUIResource getTitleTextColor() {
    return getPlasticTheme().getTitleTextColor();
  }
  
  public static FontUIResource getTitleTextFont() {
    return getPlasticTheme().getTitleTextFont();
  }
  
  private static MetalTheme getCurrentTheme0() {
    if (getCurrentThemeMethod != null)
      try {
        return (MetalTheme)getCurrentThemeMethod.invoke(null, null);
      } catch (IllegalArgumentException e) {
      
      } catch (IllegalAccessException e) {
      
      } catch (InvocationTargetException e) {} 
    return null;
  }
  
  private static Method getMethodGetCurrentTheme() {
    try {
      Class clazz = MetalLookAndFeel.class;
      return clazz.getMethod("getCurrentTheme", new Class[0]);
    } catch (SecurityException e) {
    
    } catch (NoSuchMethodException e) {}
    return null;
  }
  
  private static Object[] append(Object[] source, String key, Object value) {
    int length = source.length;
    Object[] destination = new Object[length + 2];
    System.arraycopy(source, 0, destination, 0, length);
    destination[length] = key;
    destination[length + 1] = value;
    return destination;
  }
}
