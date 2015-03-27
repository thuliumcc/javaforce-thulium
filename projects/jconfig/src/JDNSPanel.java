/**
 *
 * @author pquiring
 *
 * Created : Nov 19, 2013
 */

import java.io.*;

import javaforce.*;
import javaforce.linux.Linux;

public class JDNSPanel extends javax.swing.JPanel {

  /**
   * Creates new form JDNSPanel
   */
  public JDNSPanel() {
    initComponents();
    loadConfig();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jToolBar1 = new javax.swing.JToolBar();
    back = new javax.swing.JButton();
    save = new javax.swing.JButton();
    restart = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    config = new javax.swing.JTextArea();

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);

    back.setText("<Back");
    back.setFocusable(false);
    back.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    back.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    back.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        backActionPerformed(evt);
      }
    });
    jToolBar1.add(back);

    save.setText("Save");
    save.setFocusable(false);
    save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    save.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveActionPerformed(evt);
      }
    });
    jToolBar1.add(save);

    restart.setText("Restart Server");
    restart.setFocusable(false);
    restart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    restart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    restart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        restartActionPerformed(evt);
      }
    });
    jToolBar1.add(restart);

    config.setColumns(20);
    config.setRows(5);
    jScrollPane1.setViewportView(config);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

  private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
    ConfigApp.This.setPanel(new ServersPanel());
  }//GEN-LAST:event_backActionPerformed

  private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
    saveConfig();
  }//GEN-LAST:event_saveActionPerformed

  private void restartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartActionPerformed
    restartServer();
  }//GEN-LAST:event_restartActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton back;
  private javax.swing.JTextArea config;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JToolBar jToolBar1;
  private javax.swing.JButton restart;
  private javax.swing.JButton save;
  // End of variables declaration//GEN-END:variables

  private void loadConfig() {
    try {
      FileInputStream fis = new FileInputStream("/etc/jdns.cfg");
      byte cfg[] = JF.readAll(fis);
      fis.close();
      config.setText(new String(cfg));
    } catch (Exception e) {
      JFLog.log(e);
    }
  }

  private void saveConfig() {
    try {
      FileOutputStream fos = new FileOutputStream("/etc/jdns.cfg");
      fos.write(config.getText().getBytes());
      fos.close();
    } catch (Exception e) {
      JFLog.log(e);
    }
  }

  private void restartServer() {
    if (Linux.restartService("jdns"))
      JF.showMessage("Notice", "DNS Service Restarted");
    else
      JF.showError("Error", "Failed to Restart DNS Service");
  }
}
