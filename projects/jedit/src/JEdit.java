/*
 * JEdit.java
 *
 * Created on July 30, 2007, 10:13 AM
 */

/**
 * Multi-tabbed text editor.
 *
 * @author  pquiring
 */

import javaforce.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class JEdit extends javax.swing.JFrame implements FindEvent, ReplaceEvent, DocumentListener {

  private String getVersion() { return "0.7"; }

  /** Creates new form jedit */
  public JEdit() {
    initComponents();
    initApp();
    JFImage icon = new JFImage();
    icon.loadPNG(this.getClass().getClassLoader().getResourceAsStream("jedit.png"));
    setIconImage(icon.getImage());
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    FormListener formListener = new FormListener();

    setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    addComponentListener(formListener);
    addWindowStateListener(formListener);
    addWindowListener(formListener);
    getContentPane().setLayout(new java.awt.GridLayout(1, 0));

    pack();
  }

  // Code for dispatching events from components to event handlers.

  private class FormListener implements java.awt.event.ComponentListener, java.awt.event.WindowListener, java.awt.event.WindowStateListener {
    FormListener() {}
    public void componentHidden(java.awt.event.ComponentEvent evt) {
    }

    public void componentMoved(java.awt.event.ComponentEvent evt) {
      if (evt.getSource() == JEdit.this) {
        JEdit.this.formComponentMoved(evt);
      }
    }

    public void componentResized(java.awt.event.ComponentEvent evt) {
      if (evt.getSource() == JEdit.this) {
        JEdit.this.formComponentResized(evt);
      }
    }

    public void componentShown(java.awt.event.ComponentEvent evt) {
    }

    public void windowActivated(java.awt.event.WindowEvent evt) {
    }

    public void windowClosed(java.awt.event.WindowEvent evt) {
    }

    public void windowClosing(java.awt.event.WindowEvent evt) {
      if (evt.getSource() == JEdit.this) {
        JEdit.this.formWindowClosing(evt);
      }
    }

    public void windowDeactivated(java.awt.event.WindowEvent evt) {
    }

    public void windowDeiconified(java.awt.event.WindowEvent evt) {
    }

    public void windowIconified(java.awt.event.WindowEvent evt) {
    }

    public void windowOpened(java.awt.event.WindowEvent evt) {
    }

    public void windowStateChanged(java.awt.event.WindowEvent evt) {
      if (evt.getSource() == JEdit.this) {
        JEdit.this.formWindowStateChanged(evt);
      }
    }
  }// </editor-fold>//GEN-END:initComponents

  private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
    if (settings.bWindowMax) return;
    Point loc = getLocation();
    settings.WindowXPos = loc.x;
    settings.WindowYPos = loc.y;
  }//GEN-LAST:event_formComponentMoved

  private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    if (settings.bWindowMax) return;
    Dimension size = getSize();
    settings.WindowXSize = size.width;
    settings.WindowYSize = size.height;
  }//GEN-LAST:event_formComponentResized

  private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    settings.bWindowMax = evt.getNewState() == MAXIMIZED_BOTH;
  }//GEN-LAST:event_formWindowStateChanged

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    exit();
  }//GEN-LAST:event_formWindowClosing

  /**
   * @param args the command line arguments
   */
  public static String args[];
  public static void main(String args[]) {
    JEdit.args = args;
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new JEdit().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  private static Settings settings = new Settings();

  private class page {
    JPanel panel;
    JScrollPane scroll;
    JFTextArea txt;
    boolean dirty;
    File filename;
  };
  private Vector<page> pages;
  private boolean bLoading = false;
  private IndentBreakAction indentBreakAction = new IndentBreakAction();
  private JTabbedPane tabs;

  private void initApp() {
    tabs = new JTabbedPane();
    setContentPane(tabs);
    tabs.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        tabsKeyPressed(evt);
      }
    });
    setTitle("jedit");
    pages = new Vector<page>();
    loadcfg();
    setSize(settings.WindowXSize, settings.WindowYSize);
    setLocation(settings.WindowXPos, settings.WindowYPos);
    if (settings.bWindowMax) setExtendedState(MAXIMIZED_BOTH);
    if (args != null) {
      for(int a=0;a<args.length;a++) loadpages(args[a]);
    }
    if (pages.size() == 0) addpage("untitled");
    tabs.setSelectedIndex(0);
    pages.get(0).txt.grabFocus();
  }
  public void loadcfg() {
    XML xml = new XML();
    String filename = JF.getUserPath() + "/.jedit.xml";
    File file = new File(filename);
    if (!file.exists()) return;  //doesn't exist
    if (!xml.read(filename)) return;  //bad cfg
    if (!xml.root.name.equals("jedit")) return;  //bad cfg
    xml.writeClass(xml.root, settings);
    settings.fnt = JF.getMonospacedFont(0, settings.fontSize);
  }
  public void savecfg() {
    XML xml = new XML();
    XML.XMLTag tag;
    xml.root.name = "jedit";
    xml.readClass(xml.root, settings);
    String filename = JF.getUserPath() + "/.jedit.xml";
    xml.write(filename);
  }
//find data
  private String findstr = "";
  private String repstr = "";
  private boolean findww;
  private boolean findcw;
//interface FindEvent
  public void findEvent(FindDialog dialog) {
    findstr = dialog.getText();
    findww = dialog.getWhole();
    findcw = dialog.getCase();
    findagain(false);
  }
//interface ReplaceEvent
  public boolean findEvent(ReplaceDialog dialog) {
    findstr = dialog.getFindText();
    findww = dialog.getWhole();
    findcw = dialog.getCase();
    boolean ret = replace_find();
    if (!ret) notfound();
    return ret;
  }
  public void replaceEvent(ReplaceDialog dialog) {
    findstr = dialog.getFindText();
    repstr = dialog.getReplaceText();
    findww = dialog.getWhole();
    findcw = dialog.getCase();
    replace_replace();
  }
  public void replaceAllEvent(ReplaceDialog dialog) {
    findstr = dialog.getFindText();
    repstr = dialog.getReplaceText();
    findww = dialog.getWhole();
    findcw = dialog.getCase();
    replace_all();
  }
  private JFTextArea createJFTextArea() {
    JFTextArea txt = new JFTextArea();
    txt.setFont(settings.fnt);
    txt.setTabSize(settings.tabSize);
    txt.setLineWrap(settings.bLineWrap);
    txt.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        tabsKeyPressed(evt);
      }
    });
    txt.getDocument().addDocumentListener(this);
    txt.getActionMap().put(DefaultEditorKit.insertBreakAction, indentBreakAction);
    return txt;
  }
  private page addpage(String title) {
    page pg = new page();
    pg.panel = JF.createJPanel(new GridLayout(), null);
    pg.txt = createJFTextArea();
    pg.scroll = new JScrollPane(pg.txt);
    pg.panel.add(pg.scroll);
    pg.filename = new File(title);
    pg.dirty = false;
    pg.panel.setVisible(true);
    tabs.addTab(title, pg.panel);
    this.doLayout();
    tabs.setSelectedComponent(pg.panel);
    pages.add(pg);
    return pg;
  }
  private int getidx() { return tabs.getSelectedIndex(); }
  private boolean savepage() {
    int idx = getidx();
    if (pages.get(idx).dirty == false) return true;
    if (pages.get(idx).filename.toString().equals("untitled")) {return savepageas();}
    String tmp;
    try {
      tmp = pages.get(idx).txt.getText();
      if (settings.bClean) {
        boolean cleaned = false;
        while (tmp.indexOf(" \n") != -1) {tmp = tmp.replaceAll(" \n" ,"\n");cleaned = true;}
        if (cleaned) {
          int pos = pages.get(idx).txt.getCaretPosition();
          int line = pages.get(idx).txt.getLineOfOffset(pos);
          setText(pages.get(idx).txt, tmp);
          pages.get(idx).txt.setCaretPosition(pages.get(idx).txt.getLineStartOffset(line));
        }
      }
      if (!settings.bUnix) {
        tmp = tmp.replaceAll("\n", "\r\n");
      }
      FileOutputStream fos = new FileOutputStream(pages.get(idx).filename);
      fos.write(tmp.getBytes("UTF-8"));
      fos.close();
      tabs.setTitleAt(idx, pages.get(idx).filename.getName());
      pages.get(idx).dirty = false;
      return true;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Failed to save '" + pages.get(idx).filename.toString() + "'", "Warning", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }
  private boolean savepageas() {
    int idx = getidx();
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    chooser.setCurrentDirectory(new File(JF.getCurrentPath()));
    if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      pages.get(idx).dirty = true;
      pages.get(idx).filename = chooser.getSelectedFile();
      tabs.setTitleAt(idx, pages.get(idx).filename.getName());
      return savepage();
    }
    return false;
  }
  private boolean closepage(int idx) {
    tabs.setSelectedIndex(idx);
    return closepage();
  }
  private boolean closepage() {
    int idx = getidx();
    if (pages.get(idx).dirty) {
      //confirm to Save : Yes/No/Cancel
      switch (JOptionPane.showConfirmDialog(this, "Do you wish to save '" + pages.get(idx).filename.toString() + "' ?", "Confirm",
        JOptionPane.YES_NO_CANCEL_OPTION)) {
        case JOptionPane.YES_OPTION:
          if (!savepage()) return false;
          break;
        case JOptionPane.NO_OPTION:
          break;
        default:
        case JOptionPane.CANCEL_OPTION:
          return false;
      }
    }
    pages.remove(idx);
    tabs.remove(idx);
    if (pages.size() == 0) addpage("untitled");
    return true;
  }
  private void openpage() {
    int idx = getidx();
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    chooser.setCurrentDirectory(new File(JF.getCurrentPath()));
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      //check if current page is "untitled" and !dirty
      if (pages.get(idx).filename.toString().equals("untitled") && pages.get(idx).dirty == false) {
        //load on current page
        pages.get(idx).filename = chooser.getSelectedFile();
      } else {
        addpage(chooser.getSelectedFile().getAbsolutePath());
        idx = tabs.getTabCount() - 1;
      }
      loadpage(idx);
      pages.get(idx).txt.grabFocus();
    }
  }
  private void loadpages(String filespec) {
    File f = new File(filespec);
    if (f.isDirectory()) {
/*
      String files[] = f.list();
      if (files == null || files.length == 0) return;
      for(int a=0;a<files.length;a++) {
        addpage(files[a]);
        loadpage(tabs.getTabCount() - 1);
      }
*/
    } else {
      addpage(filespec);
      loadpage(tabs.getTabCount() - 1);
    }
  }
  private void loadpage(int idx) {
    byte txt[];
    if ((pages.get(idx).filename.toString().indexOf("*") != -1) || (pages.get(idx).filename.toString().indexOf("?") != -1)) {
      closepage(idx);
      return;
    }
    try {
      File file = pages.get(idx).filename;
      if (!file.exists()) return;
      FileInputStream fis = new FileInputStream(file);
      txt = new byte[fis.available()];
      fis.read(txt);
      fis.close();
      bLoading = true;
      setText(pages.get(idx).txt, new String(txt, "UTF-8"));
      pages.get(idx).txt.setCaretPosition(0);
      bLoading = false;
      tabs.setTitleAt(idx, pages.get(idx).filename.getName());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Failed to open '" + pages.get(idx).filename.toString() + "'", "Warning", JOptionPane.ERROR_MESSAGE);
      closepage(idx);
    }
  }
  private void notfound() {
    JOptionPane.showMessageDialog(this, "Unable to find match", "Notice", JOptionPane.ERROR_MESSAGE);
//    tabs.repaint();  //FIX BUG ON MY COMPUTER (JAVA DOESN'T LIKE MY VIDEO CARD)
  }
  private void find() {
    FindDialog.showFindDialog(this, false, findstr, findww, findcw, this);
//    tabs.repaint();  //FIX BUG ON MY COMPUTER (JAVA DOESN'T LIKE MY VIDEO CARD)
  }
  private boolean isChar(char ch) {
    //return true if ch is a char that would be a part of a whole word
    if ((ch >= 'a') && (ch <= 'z')) return true;
    if ((ch >= 'A') && (ch <= 'Z')) return true;
    if ((ch >= '0') && (ch <= '9')) return true;
    if (ch == '_') return true;
    return false;
  }
  private boolean findagain(boolean quiet) {
    int idx = getidx();
    if (findstr == null) return false;
    String txt = pages.get(idx).txt.getText();
    int txtlen = txt.length();
    int findstrlen = findstr.length();
    int pos = -1;
    for(int a=pages.get(idx).txt.getCaretPosition();a<txt.length();a++) {
      if (txt.regionMatches(!findcw, a, findstr, 0, findstrlen)) {
        if (findww) {
          if ((a > 0) && (isChar(txt.charAt(a-1)))) continue;
          if ((a < txtlen - findstrlen) && (isChar(txt.charAt(a+findstrlen)))) continue;
        }
        pos = a;
        break;
      }
    }
    if (pos == -1) {if (!quiet) notfound(); return false;}
    pages.get(idx).txt.setCaretPosition(pos);
    pages.get(idx).txt.select(pos, pos + findstr.length());
    return true;
  }
  private void replace() {
    int idx = getidx();
    ReplaceDialog.showReplaceDialog(this, true, findstr, repstr, findww, findcw, this);
//    tabs.repaint();  //FIX BUG ON MY COMPUTER (JAVA DOESN'T LIKE MY VIDEO CARD)
  }
  private boolean replace_find() {
    int idx = getidx();
    return findagain(true);
  }
  private void replace_replace() {
    int idx = getidx();
    pages.get(idx).txt.replaceSelection(repstr);
  }
  private void replace_all() {
    int idx = getidx();
    int cnt = 0;
    JFTextArea txt = pages.get(idx).txt;
    txt.setCaretPosition(0);
    while (true) {
      if (!replace_find()) break;
      replace_replace();
      cnt++;
    }
    JOptionPane.showMessageDialog(this,
      "Replaced " + cnt + " occurances"
      , "Info", JOptionPane.INFORMATION_MESSAGE);
  }
  private void gotopos() {
    String str;
    int line;
    int idx = getidx();
    try {
      str = JOptionPane.showInputDialog(this, "Which line?",
        "Goto", JOptionPane.QUESTION_MESSAGE);
      if (str == null) return;
      line = JF.atoi(str) - 1;
      if (line < 0) return;
      pages.get(idx).txt.setCaretPosition(pages.get(idx).txt.getLineStartOffset(line));
    } catch (Exception e2) {}
  }
  private boolean isActive(Process proc) {
    try {
      int exitValue = proc.exitValue();
      return false;
    } catch (Exception e) {
      return true;
    }
  }
  private void execute() {
    try {
      String str = JOptionPane.showInputDialog(this, "Enter OS command",
        "Execute", JOptionPane.QUESTION_MESSAGE);
      if (str == null) return;
      String strs[] = str.split(" ");
      ArrayList<String> list = new ArrayList<String>();
      for(int a=0;a<strs.length;a++) list.add(strs[a]);
      ProcessBuilder pb = new ProcessBuilder(list);
      pb.redirectErrorStream(true);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      Process proc = pb.start();
//      proc.waitFor();  //dead locks often
      InputStream is = proc.getInputStream();
      while (isActive(proc)) {
        int fs = is.available();
        if (fs > 0) {
          byte data[] = new byte[fs];
          int read = is.read(data);
          baos.write(data, 0, read);
        }
      }
      int fs = is.available();
      if (fs > 0) {
        byte data[] = new byte[fs];
        int read = is.read(data);
        baos.write(data, 0, read);
      }
      if (baos.size() == 0) {
        JOptionPane.showMessageDialog(this, "No output", "Execute",
          JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      page pg = addpage("output-" + strs[0]);
      setText(pg.txt, baos.toString());
      pg.txt.grabFocus();
      pg.txt.setCaretPosition(0);
    } catch (Exception e) {
    }
  }
  private void print() {
    int idx = getidx();
    try {
      pages.get(idx).txt.print();  //Java 1.6
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Failed to print '" + pages.get(idx).filename.toString() + "'", "Warning", JOptionPane.ERROR_MESSAGE);
    }
  }
  private void exit() {
    //close all windows
    int cnt = pages.size();
    for(int a=0;a<cnt;a++) {
      if (!closepage()) return;
    }
    savecfg();
    System.exit(0);
  }
  public void changedUpdate(DocumentEvent e) {
    changed();
  }
  public void insertUpdate(DocumentEvent e) {
    changed();
  }
  public void removeUpdate(DocumentEvent e) {
    changed();
  }
  public void changed() {
    if (bLoading) return;
    int idx = getidx();
    if (idx < 0 || idx >= pages.size()) return;
    if (pages.get(idx).dirty == false) {
      pages.get(idx).dirty = true;
      tabs.setTitleAt(idx, pages.get(idx).filename.getName() + " *");
    }
  }
  public void setText(JFTextArea ta, String txt) {
    txt = txt.replaceAll("\r\n","\n");  //PC file
    txt = txt.replaceAll("\r","\n");  //MAC file
    ta.setText(txt);
    ta.clearHistory();
  }
  public static class IndentBreakAction extends TextAction {
    /**
     * Creates this object with the appropriate identifier.
     */
    public IndentBreakAction() {
      super(DefaultEditorKit.insertBreakAction);
    }

    /**
     * The operation to perform when this action is triggered.
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
      JTextComponent target = getTextComponent(e);

      if (target == null) return;

      if ((! target.isEditable()) || (! target.isEnabled())) {
        UIManager.getLookAndFeel().provideErrorFeedback(target);
        return;
      }

      try {
        //  Determine which line we are on

        Document doc = target.getDocument();
        Element rootElement = doc.getDefaultRootElement();
        int selectionStart = target.getSelectionStart();
        int line = rootElement.getElementIndex( selectionStart );

        //  Get the text for this line

        int start = rootElement.getElement(line).getStartOffset();
        int end = rootElement.getElement(line).getEndOffset();
        int length = end - start;
        String text = doc.getText(start, length);
        int offset = 0;

        //  Get the number of white spaces characters at the start of the line

        for (offset = 0; offset < length; offset++) {
          char c = text.charAt(offset);

          if (c != ' ' && c != '\t')
            break;
        }

        //  When splitting the text include white space at start of line
        //  else do default processing

        if ((selectionStart - start >= offset) && (settings.bAutoIndent))
          target.replaceSelection("\n" + text.substring(0, offset) );
        else
          target.replaceSelection("\n");
      } catch(BadLocationException ble) {}
    }
  }
  private void shift_left(char ch) {
    if (pages.size() == 0) return;
    int idx = getidx();
    JFTextArea txt = pages.get(idx).txt;
    int ss = txt.getSelectionStart();
    int se = txt.getSelectionEnd();
    if (ss == se) return;
    String lns[] = txt.getSelectedText().split("\n", -1);
    StringBuffer newString = new StringBuffer();
    for(int a=0;a<lns.length;a++) {
      if (a > 0) newString.append("\n");
      if ((lns[a].length() > 0) && (lns[a].charAt(0) == ch)) { lns[a] = lns[a].substring(1); se--; }
      newString.append(lns[a]);
    }
    txt.replaceSelection(newString.toString());
    txt.setSelectionStart(ss);
    txt.setSelectionEnd(se);
  }
  private void shift_right(char ch) {
    if (pages.size() == 0) return;
    int idx = getidx();
    JFTextArea txt = pages.get(idx).txt;
    int ss = txt.getSelectionStart();
    int se = txt.getSelectionEnd();
    if (ss == se) return;
    String lns[] = txt.getSelectedText().split("\n", -1);
    StringBuffer newString = new StringBuffer();
    String chstr = "" + ch;
    for(int a=0;a<lns.length;a++) {
      if (a > 0) newString.append("\n");
      newString.append(chstr);
      newString.append(lns[a]);
      se++;
    }
    txt.replaceSelection(newString.toString());
    txt.setSelectionStart(ss);
    txt.setSelectionEnd(se);
  }

  private void tabsKeyPressed(java.awt.event.KeyEvent evt) {
    //Key Pressed
    int f1 = evt.getKeyCode();
    int f2 = evt.getModifiers();
    int idx;
    JFTextArea txt;
    if ((f1 == KeyEvent.VK_F1) && (f2 == 0)) {
      JOptionPane.showMessageDialog(this,
        "jedit/" + getVersion() + "\n\n" +
        "F1 = Help\n" +
        "F2 = Edit Settings\n" +
        "F4 = Document Info\n" +
        "F5 = Shift Selection left 1 space\n" +
        "F6 = Shift Selection right 1 space\n" +
        "F7 = Shift Selection left 1 tab\n" +
        "F8 = Shift Selection right 1 tab\n" +
        "CTRL-O = Open\n" +
        "CTRL-W = Close\n" +
        "CTRL-S = Save\n" +
        "CTRL-Q = Save As\n" +
        "CTRL-F = Find\n" +
        "CTRL-G/F3 = Find Again\n" +
        "CTRL-R = Replace\n" +
        "CTRL-L = Goto Line #\n" +
        "CTRL-E = Execute Command\n" +
        "CTRL-P = Print\n" +
        "ALT-# = Switch to document\n\n"
        , "Help", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if ((f1 == KeyEvent.VK_F2) && (f2 == 0)) {
      EditSettings.editSettings(this, settings);
      settings.fnt = JF.getMonospacedFont(0, settings.fontSize);
      int cnt = pages.size();
      for(int a=0;a<cnt;a++) {
        txt = pages.get(a).txt;
        txt.setFont(settings.fnt);
        txt.setTabSize(settings.tabSize);
        txt.setLineWrap(settings.bLineWrap);
      }
    }
    if ((f1 == KeyEvent.VK_F4) && (f2 == 0)) {
      idx = getidx();
      txt = pages.get(idx).txt;
      int cp, px, py;
      try {
        cp = txt.getCaretPosition();
        py = txt.getLineOfOffset(cp);
        px = cp - txt.getLineStartOffset(py);
      } catch (Exception e) {
        py = -1;
        px = -1;
      }
      px++;
      py++;
      JOptionPane.showMessageDialog(this,
        "Lines : " + txt.getLineCount() + "\n" + "Current Position : " + px + "," + py + "\nJVM=" + System.getProperty("java.version")
        , "Info", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if ((f1 == KeyEvent.VK_F5) && (f2 == 0)) { shift_left(' '); }
    if ((f1 == KeyEvent.VK_F6) && (f2 == 0)) { shift_right(' '); }
    if ((f1 == KeyEvent.VK_F7) && (f2 == 0)) { shift_left('\t'); }
    if ((f1 == KeyEvent.VK_F8) && (f2 == 0)) { shift_right('\t'); }
    if ((f1 == KeyEvent.VK_N) && (f2 == KeyEvent.CTRL_MASK)) { addpage("untitled"); return; }
    if ((f1 == KeyEvent.VK_S) && (f2 == KeyEvent.CTRL_MASK)) { savepage(); return; }
    if ((f1 == KeyEvent.VK_Q) && (f2 == KeyEvent.CTRL_MASK)) { savepageas(); return; }
    if ((f1 == KeyEvent.VK_W) && (f2 == KeyEvent.CTRL_MASK)) { closepage(); return; }
    if ((f1 == KeyEvent.VK_O) && (f2 == KeyEvent.CTRL_MASK)) { openpage(); return; }
    if ((f1 == KeyEvent.VK_F) && (f2 == KeyEvent.CTRL_MASK)) { find(); return; }
    if ((f1 == KeyEvent.VK_G) && (f2 == KeyEvent.CTRL_MASK)) { findagain(false); return; }
    if ((f1 == KeyEvent.VK_F3) && (f2 == 0)) { findagain(false); return; }
    if ((f1 == KeyEvent.VK_R) && (f2 == KeyEvent.CTRL_MASK)) { replace(); return; }
    if ((f1 == KeyEvent.VK_L) && (f2 == KeyEvent.CTRL_MASK)) { gotopos(); return; }
    if ((f1 == KeyEvent.VK_E) && (f2 == KeyEvent.CTRL_MASK)) { execute(); return; }
    if ((f1 == KeyEvent.VK_P) && (f2 == KeyEvent.CTRL_MASK)) { print(); return; }
    if ((f2 == KeyEvent.ALT_MASK) && (f1 >= KeyEvent.VK_0) && (f1 <= KeyEvent.VK_9)) {
      idx = f1 - KeyEvent.VK_0;
      if (idx == 0) idx = 9; else idx--;
      if (idx >= pages.size()) return;
      tabs.setSelectedIndex(idx);
      return;
    }
    if ((f2 == KeyEvent.ALT_MASK) && (f1 == KeyEvent.VK_MINUS)) tabs.setSelectedIndex(10);
    if ((f2 == KeyEvent.ALT_MASK) && (f1 == KeyEvent.VK_EQUALS)) tabs.setSelectedIndex(11);
  }
}
