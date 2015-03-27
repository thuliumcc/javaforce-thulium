/**
 * Created : Mar 23, 2012
 *
 * @author pquiring
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;

import javaforce.*;

public class BurnDialog extends javax.swing.JDialog {

  /**
   * Creates new form BurnDialog
   */
  public BurnDialog(java.awt.Frame parent, boolean modal, String tracks[]) {
    super(parent, modal);
    initComponents();
    this.tracks = tracks;
    listDevices();
    if (devs.size() == 0) {
      JF.showError("Error", "No Compatible Burner found!");
      burn.setEnabled(false);
    }
    setPosition();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        target = new javax.swing.JComboBox();
        burn = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        test = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Burn Disc");

        jLabel1.setText("Target Device:");

        burn.setText("Burn");
        burn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                burnActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        test.setText("Test Only");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(target, 0, 287, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(test)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(burn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(target, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(burn)
                    .addComponent(cancel)
                    .addComponent(test))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
    dispose();
  }//GEN-LAST:event_cancelActionPerformed

  private void burnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_burnActionPerformed
    int idx = target.getSelectedIndex();
    if (idx == -1) return;
    String dev = devs.get(idx);
    //must unmount dev
    try {
      Runtime.getRuntime().exec(new String[] {"umount", dev});
    } catch (Exception e) {}
    JF.sleep(1000);  //wait 1 sec
    String cmd[] = {"wodim", "dev=" + dev, "-v"};
    if (test.isSelected()) {
      cmd = Arrays.copyOf(cmd, cmd.length+1);
      cmd[cmd.length-1] = "-dummy";
    }
    int cmdidx = cmd.length;
    cmd = Arrays.copyOf(cmd, cmd.length + tracks.length);
    System.arraycopy(tracks, 0, cmd, cmdidx, tracks.length);
    JDialog dialog = new BurnProgressDialog(BurnApp.This, true, cmd);
    this.setVisible(false);
    dialog.setVisible(true);
    dispose();
  }//GEN-LAST:event_burnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton burn;
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox target;
    private javax.swing.JCheckBox test;
    // End of variables declaration//GEN-END:variables

  private String tracks[];
  private ArrayList<String> devs = new ArrayList<String>();
/* - sample output
  wodim: Overview of accessible drives (1 found) :
-------------------------------------------------------------------------
 0  dev='/dev/sg1'	rwrw-- : 'HL-DT-ST' 'DVDRAM GSA-4120B'
-------------------------------------------------------------------------

 */
  private void listDevices() {
    //exec : wodim --devices
    ShellProcess sp = new ShellProcess();
    String cmd[] = {"wodim", "--devices"};
    String output = sp.run(cmd, true);
    target.removeAllItems();
    devs.clear();
    if (output == null) return;
    String lns[] = output.split("\n");
    boolean start = false;
    for(int a=0;a<lns.length;a++) {
      if (start) {
        if (lns[a].startsWith("---")) return;
        int i1 = lns[a].indexOf("dev");
        int i2 = lns[a].indexOf("\t", i1+5);
        int i3 = lns[a].indexOf(":");
        String dev = lns[a].substring(i1+5, i2-1);
        String name = lns[a].substring(i3+1);
        devs.add(dev);
        target.addItem(name);
      } else {
        if (lns[a].startsWith("---")) start = true;
      }
    }
  }

  private void setPosition() {
    Rectangle s = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    Dimension d = getPreferredSize();
    setLocation(s.width/2 - d.width/2, s.height/2 - (d.height/2));
  }

}
