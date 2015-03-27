/*
 * FTPApp.java
 *
 * Created on Jan 2, 2011, 9:28:14 PM
 *
 * @author pquiring
 */

import javax.swing.*;
import java.awt.*;

import javaforce.*;

public class FTPApp extends javax.swing.JFrame {

  public static String version = "0.2.2";

  /**
   * Creates new form JFftp
   */
  public FTPApp() {
    initComponents();
    JF.centerWindow(this);
    JFLog.init(JF.getUserPath() + "/.jfftp.log", true);
    Site.loadIcons(this.getClass());
    JFImage icon = new JFImage();
    icon.loadPNG(this.getClass().getClassLoader().getResourceAsStream("jftp.png"));
    setIconImage(icon.getImage());
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    tabs = new javax.swing.JTabbedPane();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jSeparator1 = new javax.swing.JPopupMenu.Separator();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenuItem3 = new javax.swing.JMenuItem();
    jSeparator3 = new javax.swing.JPopupMenu.Separator();
    jMenuItem6 = new javax.swing.JMenuItem();
    jSeparator2 = new javax.swing.JPopupMenu.Separator();
    jMenuItem5 = new javax.swing.JMenuItem();
    jMenu2 = new javax.swing.JMenu();
    jMenuItem4 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("jfftp");

    jMenu1.setMnemonic('F');
    jMenu1.setText("File");

    jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
    jMenuItem1.setMnemonic('S');
    jMenuItem1.setText("Site Manager");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem1);
    jMenu1.add(jSeparator1);

    jMenuItem2.setMnemonic('I');
    jMenuItem2.setText("Import Sites...");
    jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem2ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem2);

    jMenuItem3.setMnemonic('E');
    jMenuItem3.setText("Export Sites...");
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem3ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem3);
    jMenu1.add(jSeparator3);

    jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
    jMenuItem6.setMnemonic('C');
    jMenuItem6.setText("Close Site");
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem6ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem6);
    jMenu1.add(jSeparator2);

    jMenuItem5.setMnemonic('X');
    jMenuItem5.setText("Exit");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem5ActionPerformed(evt);
      }
    });
    jMenu1.add(jMenuItem5);

    jMenuBar1.add(jMenu1);

    jMenu2.setMnemonic('H');
    jMenu2.setText("Help");

    jMenuItem4.setMnemonic('A');
    jMenuItem4.setText("About");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItem4ActionPerformed(evt);
      }
    });
    jMenu2.add(jMenuItem4);

    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
      SiteDetails sdArray[] = SiteMgr.showSiteMgr(this);
      if (sdArray == null) {
        return;
      }
      for (SiteDetails sd : sdArray) {
        connect(sd);
      }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
      Settings.importSettings();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
      Settings.exportSettings();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
      JF.showMessage("About", "jfftp/" + version + "\nFTP/SMB Client\nWebsite : http://jfftp.sourceforge.net\nBy : Peter Quiring(pquiring@gmail.com)");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
      exit();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
      closeSite();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new FTPApp().setVisible(true);
      }
    });
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItem4;
  private javax.swing.JMenuItem jMenuItem5;
  private javax.swing.JMenuItem jMenuItem6;
  private javax.swing.JPopupMenu.Separator jSeparator1;
  private javax.swing.JPopupMenu.Separator jSeparator2;
  private javax.swing.JPopupMenu.Separator jSeparator3;
  private javax.swing.JTabbedPane tabs;
  // End of variables declaration//GEN-END:variables

  public void connect(SiteDetails sd) {
    Site site = null;
    if (sd.protocol.equals("ftp")) {
      site = new SiteFTP();
    }
    if (sd.protocol.equals("ftps")) {
      site = new SiteFTPS();
    }
    if (sd.protocol.equals("sftp")) {
      site = new SiteSFTP();
    }
    if (sd.protocol.equals("smb")) {
      site = new SiteSMB();
    }
    if (site == null) {
      return;
    }
    site.init(sd.localDir);
    JScrollPane pane = new JScrollPane(site);
    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    JPanel panel = new JPanel(new GridLayout());
    panel.putClientProperty("site", site);
    site.putClientProperty("panel", panel);
    site.putClientProperty("pane", pane);
    site.putClientProperty("tabs", tabs);
    panel.add(pane);
    tabs.addTab(sd.name, panel);
    tabs.setSelectedComponent(panel);
    new Thread() {
      private Site site;
      private SiteDetails sd;

      public void run() {
        site.connect(sd);
      }

      public Thread init(Site site, SiteDetails sd) {
        this.site = site;
        this.sd = sd;
        return this;
      }
    }.init(site, sd).start();
    site.requestFocus();
  }

  public void exit() {
    Settings.saveSettings();
    System.exit(0);
  }

  public void closeSite() {
    try {
      JPanel panel = (JPanel) tabs.getSelectedComponent();
      if (panel == null) {
        return;
      }
      Site site = (Site) panel.getClientProperty("site");
      if (site == null) {
        return;
      }
      site.disconnect();
      tabs.remove(panel);
    } catch (Exception e) {
      JFLog.log(e);
    }
  }
}
