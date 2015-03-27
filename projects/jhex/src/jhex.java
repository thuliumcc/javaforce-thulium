/*
 * jhex.java
 *
 * Created on July 30, 2007, 10:13 AM
 */

/**
 * Multi-tabbed hex/text editor.
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

public class jhex extends javax.swing.JFrame implements FindEvent, ReplaceEvent, DocumentListener {

  /** Creates new form jhex */
  public jhex() {
    initComponents();
    initApp();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(formListener);
        addComponentListener(formListener);
        addWindowStateListener(formListener);

        tabs.addKeyListener(formListener);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ComponentListener, java.awt.event.KeyListener, java.awt.event.WindowListener, java.awt.event.WindowStateListener {
        FormListener() {}
        public void componentHidden(java.awt.event.ComponentEvent evt) {
        }

        public void componentMoved(java.awt.event.ComponentEvent evt) {
            if (evt.getSource() == jhex.this) {
                jhex.this.formComponentMoved(evt);
            }
        }

        public void componentResized(java.awt.event.ComponentEvent evt) {
            if (evt.getSource() == jhex.this) {
                jhex.this.formComponentResized(evt);
            }
        }

        public void componentShown(java.awt.event.ComponentEvent evt) {
        }

        public void keyPressed(java.awt.event.KeyEvent evt) {
            if (evt.getSource() == tabs) {
                jhex.this.tabsKeyPressed(evt);
            }
        }

        public void keyReleased(java.awt.event.KeyEvent evt) {
        }

        public void keyTyped(java.awt.event.KeyEvent evt) {
        }

        public void windowActivated(java.awt.event.WindowEvent evt) {
        }

        public void windowClosed(java.awt.event.WindowEvent evt) {
        }

        public void windowClosing(java.awt.event.WindowEvent evt) {
            if (evt.getSource() == jhex.this) {
                jhex.this.formWindowClosing(evt);
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
            if (evt.getSource() == jhex.this) {
                jhex.this.formWindowStateChanged(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

  private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
//    System.out.println("moved");
    if (Settings.bWindowMax) return;
    Point loc = getLocation();
    Settings.WindowXPos = loc.x;
    Settings.WindowYPos = loc.y;
  }//GEN-LAST:event_formComponentMoved

  private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//    System.out.println("resized");
    if (Settings.bWindowMax) return;
    Dimension size = getSize();
    Settings.WindowXSize = size.width;
    Settings.WindowYSize = size.height;
  }//GEN-LAST:event_formComponentResized

  private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
//    System.out.println("stateChanged");
    Settings.bWindowMax = evt.getNewState() == MAXIMIZED_BOTH;
  }//GEN-LAST:event_formWindowStateChanged

  private void tabsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabsKeyPressed
    //Key Pressed
    int f1 = evt.getKeyCode();
    int f2 = evt.getModifiers();
    int idx;
    if ((f1 == KeyEvent.VK_F1) && (f2 == 0)) {
      JOptionPane.showMessageDialog(this,
        "jhex/" + JF.getVersion() + "\n\n" +
        "F1 = Help\n" +
        "F2 = Edit Settings\n" +
        "CTRL-O = Open\n" +
        "CTRL-W = Close\n" +
        "CTRL-S = Save\n" +
        "CTRL-Q = Save As\n" +
        "CTRL-F = Find (string)\n" +
        "CTRL-G/F3 = Find Again\n" +
        "CTRL-R = Replace (string)\n" +
        "CTRL-B = Find (binary)\n" +
        "CTRL-T = Replace (binary)\n" +
        "CTRL-L = Goto Byte Offset\n" +
        "CTRL-E = Execute Command\n" +
        "ALT-# = Switch to document\n\n"
        , "Help", JOptionPane.INFORMATION_MESSAGE);
      return;
    }
    if ((f1 == KeyEvent.VK_F2) && (f2 == 0)) {
      EditSettings.editSettings(this);
      Settings.fnt = JF.getMonospacedFont(0, Settings.fontSize);
      Hex.changeFont(Settings.fnt);
      int cnt = pages.size();
      for(int a=0;a<cnt;a++) {
        pages.get(a).hex.repaint();
      }
    }
    if ((f1 == KeyEvent.VK_N) && (f2 == KeyEvent.CTRL_MASK)) { addpage("untitled", ""); return; }
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
    if ((f2 == KeyEvent.ALT_MASK) && (f1 >= KeyEvent.VK_0) && (f1 <= KeyEvent.VK_9)) {
      idx = f1 - KeyEvent.VK_0;
      if (idx == 0) idx = 9; else idx--;
      if (idx >= pages.size()) return;
      tabs.setSelectedIndex(idx);
      return;
    }
    if ((f1 == KeyEvent.VK_B) && (f2 == KeyEvent.CTRL_MASK)) { find_bin(); return; }
    if ((f1 == KeyEvent.VK_T) && (f2 == KeyEvent.CTRL_MASK)) { replace_bin(); return; }
    if ((f2 == KeyEvent.ALT_MASK) && (f1 == KeyEvent.VK_MINUS)) tabs.setSelectedIndex(10);
    if ((f2 == KeyEvent.ALT_MASK) && (f1 == KeyEvent.VK_EQUALS)) tabs.setSelectedIndex(11);
  }//GEN-LAST:event_tabsKeyPressed

  private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    exit();
  }//GEN-LAST:event_formWindowClosing

  /**
   * @param args the command line arguments
   */
  public static String args[];
  public static void main(String args[]) {
    jhex.args = args;
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new jhex().setVisible(true);
      }
    });
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables
  private class page {
    JPanel panel;
    JScrollPane scroll;
    Hex hex;
    boolean dirty;
    File filename;
  };
  private Vector<page> pages;
  private boolean bLoading = false;

  private void initApp() {
    setTitle("jhex");
    pages = new Vector<page>();
    loadcfg();
    setSize(Settings.WindowXSize, Settings.WindowYSize);
    setLocation(Settings.WindowXPos, Settings.WindowYPos);
    if (Settings.bWindowMax) setExtendedState(MAXIMIZED_BOTH);
    if (args != null) {
      for(int a=0;a<args.length;a++) loadpages(args[a]);
    }
    if (pages.size() == 0) addpage("untitled", "");
    tabs.setSelectedIndex(0);
    pages.get(0).hex.grabFocus();
  }
  public void loadcfg() {
    XML xml = new XML();
    String filename = JF.getUserPath() + "/.jhex.xml";
    File file = new File(filename);
    if (!file.exists()) return;  //doesn't exist
    if (!xml.read(filename)) return;  //bad cfg
    if (!xml.root.name.equals("jhex")) return;  //bad cfg
    xml.writeClass(xml.root, new Settings());
    Settings.fnt = JF.getMonospacedFont(0, Settings.fontSize);
  }
  public void savecfg() {
    XML xml = new XML();
    XML.XMLTag tag;
    xml.root.name = "jhex";
    xml.readClass(xml.root, new Settings());
    String filename = JF.getUserPath() + "/.jhex.xml";
    xml.write(filename);
  }
//find data
  private String findstr = "";
  private String repstr = "";
  private boolean findww;
  private boolean findcw;
  private boolean findbin = false;
//interface FindEvent
  public void findEvent(FindDialog dialog) {
    if (findbin) {
      findstr = binary2String(dialog.getText());
    } else {
      findstr = dialog.getText();
    }
    findww = dialog.getWhole();
    findcw = dialog.getCase();
    findagain(false);
  }
//interface ReplaceEvent
  public boolean findEvent(ReplaceDialog dialog) {
    if (findbin) {
      findstr = binary2String(dialog.getFindText());
    } else {
      findstr = dialog.getFindText();
    }
    findww = dialog.getWhole();
    findcw = dialog.getCase();
    boolean ret = replace_find();
    if (!ret) notfound();
    return ret;
  }
  public void replaceEvent(ReplaceDialog dialog) {
    if (findbin) {
      findstr = binary2String(dialog.getFindText());
      repstr = binary2String(dialog.getReplaceText());
    } else {
      findstr = dialog.getFindText();
      repstr = dialog.getReplaceText();
    }
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
  private page addpage(String title, String txt) {
    page pg = new page();
    pg.panel = JF.createJPanel(new GridLayout(), null);
    pg.hex = new Hex(this);
    pg.hex.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        tabsKeyPressed(evt);
      }
    });
    pg.hex.setText(txt);
    pg.hex.setCaretPosition(0);
    pg.scroll = new JScrollPane(pg.hex);
    pg.scroll.setViewportView(pg.hex);
    pg.panel.add(pg.scroll);
    pg.filename = new File(title);
    pg.dirty = false;
    pg.panel.setVisible(true);
    tabs.addTab(title, pg.panel);
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
      tmp = pages.get(idx).hex.getText();
      FileOutputStream fos = new FileOutputStream(pages.get(idx).filename);
      byte data[] = new byte[tmp.length()];
      tmp.getBytes(0, tmp.length(), data, 0);  //this method will not encode the bytes (deprecated though)
      fos.write(data);
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
    if (pages.size() == 0) addpage("untitled", "");
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
        addpage(chooser.getSelectedFile().getAbsolutePath(), "");
        idx = tabs.getTabCount() - 1;
      }
      loadpage(idx);
      pages.get(idx).hex.grabFocus();
    }
  }
  private void loadpages(String filespec) {
    File f = new File(filespec);
    if (f.isDirectory()) {
/*
      String files[] = f.list();
      if (files == null || files.length == 0) return;
      for(int a=0;a<files.length;a++) {
        addpage(files[a], "");
        loadpage(tabs.getTabCount() - 1);
      }
*/
    } else {
      addpage(filespec, "");
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
      setText(idx, new String(txt, 0));  //new String(byte[], int) will not encode the bytes (new String(byte[]) does)
      pages.get(idx).hex.setCaretPosition(0);
      bLoading = false;
      tabs.setTitleAt(idx, pages.get(idx).filename.getName());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Failed to open '" + pages.get(idx).filename.toString() + "'", "Warning", JOptionPane.ERROR_MESSAGE);
      closepage(idx);
    }
  }
  private void notfound() {
    JOptionPane.showMessageDialog(this, "Unable to find match", "Notice", JOptionPane.ERROR_MESSAGE);
  }
  private void find() {
    findbin = false;
    FindDialog.showFindDialog(this, false, findstr, findww, findcw, this);
  }
  private void find_bin() {
    findbin = true;
    FindDialog.showFindDialog(this, false, string2Binary(findstr), findww, findcw, this);
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
    Hex hex = pages.get(getidx()).hex;
    if (findstr == null) return false;
    int findstrlen = findstr.length();
    char cafind[] = findstr.toCharArray();
    String txt = hex.getText();
    char ca[] = txt.toCharArray();
    boolean ok;
    int pos = hex.getCaretPosition();
    if (hex.isSelect()) pos++;
    for(;pos<ca.length - findstrlen;pos++) {
      ok = true;
      for(int a=0;a<findstrlen;a++) {
        if (!findcw) {
          if (Character.toUpperCase(ca[pos + a]) != Character.toUpperCase(cafind[a])) {ok = false; break;}
        } else {
          if (ca[pos + a] != cafind[a]) {ok = false; break;}
        }
      }
      if (!ok) continue;
      //found a match
      if (findww) {
        if ((pos > 0) && (isChar(ca[pos-1]))) continue;  //not a whole word
        if ((pos + findstrlen < ca.length) && (isChar(ca[pos + findstrlen]))) continue;  //not a whole word
      }
      hex.select(pos, pos+findstrlen-1);
      return true;
    }
    if (!quiet) notfound();
    return false;
  }
  private void replace() {
    findbin = false;
    ReplaceDialog.showReplaceDialog(this, true, findstr, repstr, findww, findcw, this);
  }
  private void replace_bin() {
    findbin = true;
    ReplaceDialog.showReplaceDialog(this, true, string2Binary(findstr), string2Binary(repstr), findww, findcw, this);
  }
  private boolean replace_find() {
    return findagain(true);
  }
  private void replace_replace() {
    Hex hex = pages.get(getidx()).hex;
    hex.delete();
    hex.paste(repstr.toCharArray());
  }
  private void replace_all() {
    int cnt = 0;
    pages.get(getidx()).hex.setCaretPosition(0);
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
      str = JOptionPane.showInputDialog(this, "Enter hex offset?",
        "Goto", JOptionPane.QUESTION_MESSAGE);
      if (str == null) return;
      line = JF.atox(str);
      pages.get(idx).hex.setCaretPosition(line);
    } catch (Exception e1) {}
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
      pb.redirectErrorStream();
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
      page pg = addpage("output-" + strs[0], baos.toString());
      pg.hex.grabFocus();
      pg.hex.setCaretPosition(0);
    } catch (Exception e) {
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
  public void setText(int idx, String txt) {
    pages.get(idx).hex.setText(txt);
  }
  public String binary2String(String in) {
    //in = "XX..."
    StringBuffer out = new StringBuffer();
    char ch;
    int len = in.length();
    for(int a=0;a<len/2;a++) {
      ch = (char)JF.atox(in.substring(a*2,a*2+2));
      out.append(ch);
    }
    if (len % 2 == 1) {
      ch = (char)JF.atox(in.substring(len-1));
      out.append(ch);
    }
    return out.toString();
  }
  public String string2Binary(String in) {
    //in = "abc..."
    //out = "414243..."
    StringBuffer out = new StringBuffer();
    char ch;
    int len = in.length();
    for(int a=0;a<len;a++) {
      ch = in.charAt(a);
      if (ch < 10) out.append("0");
      out.append(Integer.toString(in.charAt(a), 16));
    }
    return out.toString();
  }
}