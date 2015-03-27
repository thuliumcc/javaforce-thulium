package jfile;

/**
 * Created : July 13, 2012
 *
 * @author pquiring
 */

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.*;
import java.util.Calendar;
import javax.swing.*;

import javaforce.*;
import javaforce.linux.*;

public class JFileProperties extends javax.swing.JFrame {
  //Don't use a real Dialog here cause that would stall the EDT

  /**
   * Creates new form EditIconProperties
   */
  public JFileProperties(FileEntry entry, boolean deleteOnCancel) {
    initComponents();
    setPosition();
    this.button = entry.button;
    this.entry = entry;
    File file = new File(entry.file);
    if (!file.exists()) {
      dispose();
      return;
    }
    desktopFile = entry.file.endsWith(".desktop") && !file.isDirectory();
    this.deleteOnCancel = deleteOnCancel;
    filename.setText(entry.file);
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(file.lastModified());
    date.setText(String.format("%d-%d-%d %02d:%02d"
      , c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)
      , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE)));
    size.setText("" + file.length());
    if (!desktopFile) {
      tabs.remove(shortcutPanel);
      if (file.isDirectory()) {
        //TODO : load folder stats (du)
      }
    } else {
      tabs.setSelectedComponent(shortcutPanel);
      String name = null, icon = null, exec = null;
      try {
        FileInputStream fis = new FileInputStream(file);
        byte data[] = JF.readAll(fis);
        fis.close();
        String str = new String(data);
        lns = str.split("\n");
        boolean desktopEntry = false;
        for(int a=0;a<lns.length;a++) {
          if (lns[a].startsWith("[Desktop Entry]")) {
            desktopEntry = true;
            continue;
          }
          if (lns[a].startsWith("[")) desktopEntry = false;
          if (!desktopEntry) continue;
          if (lns[a].startsWith("Name=")) {
            name = lns[a].substring(5);
          }
          if (lns[a].startsWith("Icon=")) {
            icon = lns[a].substring(5);
          }
          if (lns[a].startsWith("Exec=")) {
            exec = lns[a].substring(5);
          }
        }
        if ((name == null) || (icon == null) || (exec == null)) {
          dispose();
          return;
        }
        int i1 = entry.file.lastIndexOf("/");
        name = entry.file.substring(i1+1, entry.file.length() - 8);  //.desktop
        iconName.setText(name);
        iconCmd.setText(exec);
        iconIcon.setText(icon);
      } catch (Exception e) {
        JFLog.log(e);
        dispose();
        return;
      }
    }
    value = getPerms(entry.file);
    octal.setText(Integer.toString(value,8));
    owner_read.setSelected((value & 0x100) != 0);
    owner_write.setSelected((value & 0x80) != 0);
    owner_exec.setSelected((value & 0x40) != 0);
    group_read.setSelected((value & 0x20) != 0);
    group_write.setSelected((value & 0x10) != 0);
    group_exec.setSelected((value & 0x08) != 0);
    public_read.setSelected((value & 0x04) != 0);
    public_write.setSelected((value & 0x02) != 0);
    public_exec.setSelected((value & 0x01) != 0);
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
        generalTab = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        filename = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        size = new javax.swing.JTextField();
        shortcutPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        iconName = new javax.swing.JTextField();
        iconCmd = new javax.swing.JTextField();
        iconIcon = new javax.swing.JTextField();
        selectIcon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        permissionsPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        group_read = new javax.swing.JCheckBox();
        group_write = new javax.swing.JCheckBox();
        group_exec = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        public_read = new javax.swing.JCheckBox();
        public_write = new javax.swing.JCheckBox();
        public_exec = new javax.swing.JCheckBox();
        octal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        owner_read = new javax.swing.JCheckBox();
        owner_write = new javax.swing.JCheckBox();
        owner_exec = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Edit Properties");
        setResizable(false);

        jLabel5.setText("Filename");

        filename.setEditable(false);

        jLabel6.setText("Date");

        date.setEditable(false);

        jLabel7.setText("Size");

        size.setEditable(false);

        javax.swing.GroupLayout generalTabLayout = new javax.swing.GroupLayout(generalTab);
        generalTab.setLayout(generalTabLayout);
        generalTabLayout.setHorizontalGroup(
            generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filename)
                    .addComponent(date)
                    .addComponent(size, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))
                .addContainerGap())
        );
        generalTabLayout.setVerticalGroup(
            generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(filename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(240, Short.MAX_VALUE))
        );

        tabs.addTab("General", generalTab);

        jLabel2.setText("Command");

        jLabel3.setText("Icon");

        selectIcon.setText("Select...");
        selectIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectIconActionPerformed(evt);
            }
        });

        jLabel1.setText("Name");

        javax.swing.GroupLayout shortcutPanelLayout = new javax.swing.GroupLayout(shortcutPanel);
        shortcutPanel.setLayout(shortcutPanelLayout);
        shortcutPanelLayout.setHorizontalGroup(
            shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shortcutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconName)
                    .addComponent(iconCmd)
                    .addGroup(shortcutPanelLayout.createSequentialGroup()
                        .addComponent(iconIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectIcon)))
                .addContainerGap())
        );
        shortcutPanelLayout.setVerticalGroup(
            shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shortcutPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(iconName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(iconCmd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(shortcutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(iconIcon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectIcon))
                .addGap(192, 192, 192))
        );

        tabs.addTab("Shortcut", shortcutPanel);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Group"));

        group_read.setText("Read");
        group_read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                group_readActionPerformed(evt);
            }
        });

        group_write.setText("Write");
        group_write.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                group_writeActionPerformed(evt);
            }
        });

        group_exec.setText("Execute");
        group_exec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                group_execActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(group_read)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(group_write)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(group_exec))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(group_read, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(group_write)
                    .addComponent(group_exec))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Public"));

        public_read.setText("Read");
        public_read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                public_readActionPerformed(evt);
            }
        });

        public_write.setText("Write");
        public_write.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                public_writeActionPerformed(evt);
            }
        });

        public_exec.setText("Execute");
        public_exec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                public_execActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(public_read)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(public_write)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(public_exec))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(public_read, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(public_write)
                    .addComponent(public_exec))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        octal.setText("000");
        octal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                octalKeyTyped(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Owner"));

        owner_read.setText("Read");
        owner_read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                owner_readActionPerformed(evt);
            }
        });

        owner_write.setText("Write");
        owner_write.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                owner_writeActionPerformed(evt);
            }
        });

        owner_exec.setText("Execute");
        owner_exec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                owner_execActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(owner_read)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(owner_write)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(owner_exec))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(owner_read)
                    .addComponent(owner_exec)
                    .addComponent(owner_write))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel4.setText("Octal:");

        javax.swing.GroupLayout permissionsPanelLayout = new javax.swing.GroupLayout(permissionsPanel);
        permissionsPanel.setLayout(permissionsPanelLayout);
        permissionsPanelLayout.setHorizontalGroup(
            permissionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(permissionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(permissionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, permissionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(octal, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        permissionsPanelLayout.setVerticalGroup(
            permissionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(permissionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(permissionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(octal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        tabs.addTab("Permissions", permissionsPanel);

        ok.setText("Ok");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ok)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void selectIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectIconActionPerformed
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(false);
    chooser.setCurrentDirectory(new File("/usr/share/icons/hicolor/48x48/apps"));
    if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
    String icon = chooser.getSelectedFile().getName();
    int i = icon.lastIndexOf(".");
    iconIcon.setText(icon.substring(0, i));
  }//GEN-LAST:event_selectIconActionPerformed

  private void group_readActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_group_readActionPerformed
    updateOctal();
  }//GEN-LAST:event_group_readActionPerformed

  private void group_writeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_group_writeActionPerformed
    updateOctal();
  }//GEN-LAST:event_group_writeActionPerformed

  private void group_execActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_group_execActionPerformed
    updateOctal();
  }//GEN-LAST:event_group_execActionPerformed

  private void public_readActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_public_readActionPerformed
    updateOctal();
  }//GEN-LAST:event_public_readActionPerformed

  private void public_writeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_public_writeActionPerformed
    updateOctal();
  }//GEN-LAST:event_public_writeActionPerformed

  private void public_execActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_public_execActionPerformed
    updateOctal();
  }//GEN-LAST:event_public_execActionPerformed

  private void octalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_octalKeyTyped
    // TODO : consume invalid keys
  }//GEN-LAST:event_octalKeyTyped

  private void owner_readActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_owner_readActionPerformed
    updateOctal();
  }//GEN-LAST:event_owner_readActionPerformed

  private void owner_writeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_owner_writeActionPerformed
    updateOctal();
  }//GEN-LAST:event_owner_writeActionPerformed

  private void owner_execActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_owner_execActionPerformed
    updateOctal();
  }//GEN-LAST:event_owner_execActionPerformed

  private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
    value = -1;
    dispose();
    if (deleteOnCancel) {
      new File(entry.file).delete();
    }
  }//GEN-LAST:event_cancelActionPerformed

  private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
    if (desktopFile) {
      if (!saveShortcut()) return;
    }
    value = Integer.valueOf(octal.getText(), 8);
    setPerms(value, entry.file);
    dispose();
  }//GEN-LAST:event_okActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JTextField date;
    private javax.swing.JTextField filename;
    private javax.swing.JPanel generalTab;
    private javax.swing.JCheckBox group_exec;
    private javax.swing.JCheckBox group_read;
    private javax.swing.JCheckBox group_write;
    private javax.swing.JTextField iconCmd;
    private javax.swing.JTextField iconIcon;
    private javax.swing.JTextField iconName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField octal;
    private javax.swing.JButton ok;
    private javax.swing.JCheckBox owner_exec;
    private javax.swing.JCheckBox owner_read;
    private javax.swing.JCheckBox owner_write;
    private javax.swing.JPanel permissionsPanel;
    private javax.swing.JCheckBox public_exec;
    private javax.swing.JCheckBox public_read;
    private javax.swing.JCheckBox public_write;
    private javax.swing.JButton selectIcon;
    private javax.swing.JPanel shortcutPanel;
    private javax.swing.JTextField size;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables

  private JFileIcon button;
  private FileEntry entry;
  private String lns[];
  private boolean deleteOnCancel, desktopFile;

  public int value;

  private void updateOctal() {
    int newValue = 0;
    if (owner_read.isSelected()) newValue += 0x100;
    if (owner_write.isSelected()) newValue += 0x80;
    if (owner_exec.isSelected()) newValue += 0x40;
    if (group_read.isSelected()) newValue += 0x20;
    if (group_write.isSelected()) newValue += 0x10;
    if (group_exec.isSelected()) newValue += 0x08;
    if (public_read.isSelected()) newValue += 0x04;
    if (public_write.isSelected()) newValue += 0x02;
    if (public_exec.isSelected()) newValue += 0x01;
    octal.setText(Integer.toString(newValue,8));
  }

  private void badChar() {
    JF.showError("Error", "Name can not contain special characters:\n\t\t/\\:*?<>|");
  }

  private boolean saveShortcut() {
    String newName = iconName.getText().trim();
    if (newName.length() == 0) {JF.showError("Error", "Name must not be empty"); return false;}
    if (newName.indexOf("/") != -1) {badChar(); return false;}
    if (newName.indexOf("\\") != -1) {badChar(); return false;}
    if (newName.indexOf(":") != -1) {badChar(); return false;}
    if (newName.indexOf("*") != -1) {badChar(); return false;}
    if (newName.indexOf("?") != -1) {badChar(); return false;}
    if (newName.indexOf("<") != -1) {badChar(); return false;}
    if (newName.indexOf(">") != -1) {badChar(); return false;}
    if (newName.indexOf("|") != -1) {badChar(); return false;}
    //TODO : validate cmd
    try {
      int idx = entry.file.lastIndexOf("/");
      String newfn = entry.file.substring(0, idx+1) + newName + ".desktop";
      if (!entry.file.equals(newfn)) {
        if (new File(newfn).exists()) {
          JF.showError("Error", "A shortcut with the name already exists");
          return false;
        }
        new File(entry.file).delete();
        entry.file = newfn;
//        Dock.dock.saveConfig();  //FIXME
      }
      FileOutputStream fos = new FileOutputStream(newfn);
      boolean desktopEntry = false;
      for(int a=0;a<lns.length;a++) {
        if (lns[a].startsWith("[Desktop Entry]")) {
          desktopEntry = true;
        } else if (lns[a].startsWith("[")) {
          desktopEntry = false;
        }
        if (desktopEntry) {
          if (lns[a].startsWith("Name=")) {
            lns[a] = "Name=" + newName;
          }
          if (lns[a].startsWith("Icon=")) {
            lns[a] = "Icon=" + iconIcon.getText();
          }
          if (lns[a].startsWith("Exec=")) {
            lns[a] = "Exec=" + iconCmd.getText();
          }
        }
        fos.write((lns[a] + "\n").getBytes());
      }
      fos.close();
      //update icon
      if (button != null) {
        JFImage buttonImage = IconCache.loadIcon(iconIcon.getText());
        buttonImage = IconCache.scaleIcon(buttonImage, JFileBrowser.ix, JFileBrowser.iy);
        button.setIcon(buttonImage);
        button.setText(newName);
        button.repaint();
      }
    } catch (Exception e) {
      JFLog.log(e);
    }
    return true;
  }
  private int getPerms(String file) {
    //get file mode
    ShellProcess sp = new ShellProcess();
    String output = sp.run(new String[] {"stat", file}, false);
    String lns[] = output.split("\n");
    for(int a=0;a<lns.length;a++) {
      if (lns[a].startsWith("Access:")) {
        //Access: (0664/-rw-rw-r--) ...
        int i1 = lns[a].indexOf('(');
        int i2 = lns[a].indexOf('/');
        if ((i1 == -1) || (i2 == -1)) return 0;
        return Integer.valueOf(lns[a].substring(i1+1, i2), 8);
      }
    }
    return 0;
  }
  private void setPerms(int value, String file) {
    try {
      ShellProcess sp = new ShellProcess();
      String output = sp.run(new String[] {"chmod", Integer.toString(value, 8), file}, false);
    } catch (Exception e) {
      JFLog.log(e);
    }
  }
  public static void main(String args[]) {
    if (args.length != 1) {
      System.out.println("Usage : jfileprops file");
      return;
    }
    FileEntry entry = new FileEntry();
    File file = new File(args[0]);
    entry.file = file.getAbsolutePath();
    JFileProperties dialog = new JFileProperties(entry, false);
    dialog.setVisible(true);
  }
  private void setPosition() {
    Dimension d = getSize();
    Rectangle s = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    setLocation(s.width/2 - d.width/2, s.height/2 - d.height/2);
  }
}
