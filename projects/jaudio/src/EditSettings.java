/** Edit Settings
 *
 * @author pquiring
 *
 * Created : Apr 25, 2014
 */

import javaforce.*;
import javaforce.media.*;

public class EditSettings extends javax.swing.JDialog {

  /**
   * Creates new form EditSettings
   */
  public EditSettings(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    rateField.setSelectedItem("" + Settings.current.freq);
    useNative.setSelected(Settings.current.useNative);
    channels.setSelectedIndex(Settings.current.channels - 1);
    listInputDevices();
    listOutputDevices();
    JF.centerWindow(this);
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    ok = new javax.swing.JButton();
    cancel = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    rateField = new javax.swing.JComboBox();
    jLabel2 = new javax.swing.JLabel();
    inputDevice = new javax.swing.JComboBox();
    jLabel3 = new javax.swing.JLabel();
    outputDevice = new javax.swing.JComboBox();
    useNative = new javax.swing.JCheckBox();
    jLabel4 = new javax.swing.JLabel();
    channels = new javax.swing.JComboBox();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Settings");

    ok.setText("OK");
    ok.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        okActionPerformed(evt);
      }
    });

    cancel.setText("Cancel");
    cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelActionPerformed(evt);
      }
    });

    jLabel1.setText("Recording Rate:");

    rateField.setEditable(true);
    rateField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8000", "11025", "16000", "22050", "32000", "44100", "64000", "96000", " " }));

    jLabel2.setText("Recording Device:");

    jLabel3.setText("Playback Device:");

    useNative.setText("Use Native Sound (Improved Latency)");
    useNative.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        useNativeItemStateChanged(evt);
      }
    });

    jLabel4.setText("Recording Channels:");

    channels.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "mono", "stereo" }));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(cancel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(ok))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(outputDevice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(inputDevice, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
              .addComponent(jLabel4)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(channels, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(rateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(useNative))
              .addGap(0, 0, Short.MAX_VALUE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(rateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(useNative)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel4)
          .addComponent(channels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(inputDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(outputDevice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(ok)
          .addComponent(cancel))
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
    int rate = JF.atoi((String)rateField.getSelectedItem());
    if (rate < 8000) rate = 8000;
    if (rate > 96000) rate = 96000;
    Settings.current.freq = rate;
    Settings.current.useNative = useNative.isSelected();
    Settings.current.channels = channels.getSelectedIndex() + 1;
    Settings.current.input = (String)inputDevice.getSelectedItem();
//    JFLog.log("input=" + Settings.current.input);
    Settings.current.output = (String)outputDevice.getSelectedItem();
//    JFLog.log("output=" + Settings.current.output);
    Settings.saveSettings();
    dispose();
  }//GEN-LAST:event_okActionPerformed

  private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
    dispose();
  }//GEN-LAST:event_cancelActionPerformed

  private void useNativeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_useNativeItemStateChanged
    listInputDevices();
    listOutputDevices();
  }//GEN-LAST:event_useNativeItemStateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton cancel;
  private javax.swing.JComboBox channels;
  private javax.swing.JComboBox inputDevice;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JButton ok;
  private javax.swing.JComboBox outputDevice;
  private javax.swing.JComboBox rateField;
  private javax.swing.JCheckBox useNative;
  // End of variables declaration//GEN-END:variables

  private void listOutputDevices() {
    Sound.Output output = Sound.getOutput(useNative.isSelected());
    String devices[] = output.listDevices();
    int idx = -1;
    outputDevice.removeAllItems();
    boolean hasDefault = false;
    for(int a=0;a<devices.length;a++) {
      outputDevice.addItem(devices[a]);
      if (devices[a].equals(Settings.current.output)) {
        idx = a;
      }
    }
    if (idx != -1) {
      outputDevice.setSelectedIndex(idx);
    } else {
      outputDevice.setSelectedIndex(0);
    }
  }

  private void listInputDevices() {
    Sound.Input input = Sound.getInput(useNative.isSelected());
    String devices[] = input.listDevices();
    int idx = -1;
    inputDevice.removeAllItems();
    for(int a=0;a<devices.length;a++) {
      inputDevice.addItem(devices[a]);
      if (devices[a].equals(Settings.current.input)) {
        idx = a;
      }
    }
    if (idx != -1) {
      inputDevice.setSelectedIndex(idx);
    } else {
      inputDevice.setSelectedIndex(0);
    }
  }
}
