/**
 * Created : Mar 7, 2012
 *
 * @author pquiring
 */

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

import javaforce.*;
import javaforce.linux.*;

public class ServersPanel extends javax.swing.JPanel implements ActionListener {

  /**
   * Creates new form ServersPanel
   */
  public ServersPanel() {
    initComponents();
    initButtons();
    updateInstallButtons();
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
    jScrollPane1 = new javax.swing.JScrollPane();
    jPanel1 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    config_apache = new javax.swing.JButton();
    install_apache = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    config_tomcat = new javax.swing.JButton();
    install_tomcat = new javax.swing.JButton();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    install_mysql = new javax.swing.JButton();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    install_xrdp = new javax.swing.JButton();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    jLabel12 = new javax.swing.JLabel();
    jLabel13 = new javax.swing.JLabel();
    jLabel14 = new javax.swing.JLabel();
    jLabel15 = new javax.swing.JLabel();
    jLabel16 = new javax.swing.JLabel();
    jLabel17 = new javax.swing.JLabel();
    jLabel18 = new javax.swing.JLabel();
    config_proxy = new javax.swing.JButton();
    install_proxy = new javax.swing.JButton();
    config_dhcp = new javax.swing.JButton();
    install_dhcp = new javax.swing.JButton();
    config_dns = new javax.swing.JButton();
    install_dns = new javax.swing.JButton();
    config_firewall = new javax.swing.JButton();
    install_pop3 = new javax.swing.JButton();
    install_smtp = new javax.swing.JButton();
    install_imap = new javax.swing.JButton();
    config_samba = new javax.swing.JButton();
    jLabel21 = new javax.swing.JLabel();
    install_ssh = new javax.swing.JButton();
    jLabel22 = new javax.swing.JLabel();
    install_php = new javax.swing.JButton();
    jLabel19 = new javax.swing.JLabel();
    config_vpn = new javax.swing.JButton();
    install_vpn = new javax.swing.JButton();
    jPanel2 = new javax.swing.JPanel();
    jLabel20 = new javax.swing.JLabel();
    jLabel23 = new javax.swing.JLabel();
    jLabel24 = new javax.swing.JLabel();
    config_j_dhcp = new javax.swing.JButton();
    install_j_dhcp = new javax.swing.JButton();
    config_j_dns = new javax.swing.JButton();
    install_j_dns = new javax.swing.JButton();
    config_j_proxy = new javax.swing.JButton();
    install_j_proxy = new javax.swing.JButton();

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

    jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
    jLabel1.setText("Web Servers:");

    jLabel2.setText("Apache");

    config_apache.setText("Config");

    install_apache.setText("Install");

    jLabel3.setText("Tomcat");

    config_tomcat.setText("Config");

    install_tomcat.setText("Install");

    jLabel4.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
    jLabel4.setText("Database Servers:");

    jLabel5.setText("MySQL");

    install_mysql.setText("Install");

    jLabel6.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
    jLabel6.setText("Remote Access Servers:");

    jLabel7.setText("Remote Desktop");

    install_xrdp.setText("Install");

    jLabel8.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
    jLabel8.setText("IP Servers:");

    jLabel9.setText("DHCP");

    jLabel10.setText("DNS");

    jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
    jLabel11.setText("EMail Servers:");

    jLabel12.setText("POP3");

    jLabel13.setText("SMTP");

    jLabel14.setText("IMAP");

    jLabel15.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
    jLabel15.setText("File Sharing/Active Directory Servers:");

    jLabel16.setText("Samba");

    jLabel17.setText("Proxy");

    jLabel18.setText("Firewall");

    config_proxy.setText("Config");

    install_proxy.setText("Install");

    config_dhcp.setText("Config");

    install_dhcp.setText("Install");

    config_dns.setText("Config");

    install_dns.setText("Install");

    config_firewall.setText("Config");

    install_pop3.setText("Install");

    install_smtp.setText("Install");

    install_imap.setText("Install");

    config_samba.setText("Config");

    jLabel21.setText("SSH Server");

    install_ssh.setText("Install");

    jLabel22.setText("PHP");

    install_php.setText("Install");

    jLabel19.setText("VPN");

    config_vpn.setText("Config");

    install_vpn.setText("Install");

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Java Servers"));

    jLabel20.setText("DHCP");

    jLabel23.setText("DNS");

    jLabel24.setText("Proxy");

    config_j_dhcp.setText("Config");

    install_j_dhcp.setText("Install");

    config_j_dns.setText("Config");

    install_j_dns.setText("Install");

    config_j_proxy.setText("Config");

    install_j_proxy.setText("Install");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel20)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(config_j_dhcp)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_j_dhcp))
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel23)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(config_j_dns)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_j_dns))
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel24)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(config_j_proxy)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_j_proxy)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel20)
          .addComponent(config_j_dhcp)
          .addComponent(install_j_dhcp))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel23)
          .addComponent(config_j_dns)
          .addComponent(install_j_dns))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel24)
          .addComponent(config_j_proxy)
          .addComponent(install_j_proxy))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel1)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(config_apache))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(config_tomcat)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(install_tomcat)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(install_apache)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(install_php))))
          .addComponent(jLabel4)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel5)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_mysql))
          .addComponent(jLabel6)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel7)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_xrdp))
          .addComponent(jLabel8)
          .addComponent(jLabel11)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel12)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_pop3))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel13)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_smtp))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel14)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_imap))
          .addComponent(jLabel15)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel16)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(config_samba))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel17)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(config_proxy)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_proxy))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel21)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(install_ssh))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(config_dhcp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(install_dhcp))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(config_dns)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(install_dns))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(config_firewall))
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(config_vpn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(install_vpn)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabel1)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(config_apache)
          .addComponent(install_apache)
          .addComponent(jLabel22)
          .addComponent(install_php))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(config_tomcat)
          .addComponent(install_tomcat))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel17)
          .addComponent(config_proxy)
          .addComponent(install_proxy))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel4)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel5)
          .addComponent(install_mysql))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel6)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel7)
          .addComponent(install_xrdp))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel21)
          .addComponent(install_ssh))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel8)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(config_dhcp)
              .addComponent(install_dhcp)
              .addComponent(jLabel9))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(config_dns)
              .addComponent(install_dns)
              .addComponent(jLabel10))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel19)
              .addComponent(config_vpn)
              .addComponent(install_vpn))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel18)
              .addComponent(config_firewall)))
          .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel11)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(install_pop3)
          .addComponent(jLabel12))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(install_smtp)
          .addComponent(jLabel13))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(install_imap)
          .addComponent(jLabel14))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel15)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(config_samba)
          .addComponent(jLabel16))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jScrollPane1.setViewportView(jPanel1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(jScrollPane1)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane1))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
    ConfigApp.This.setPanel(new MainPanel());
  }//GEN-LAST:event_backActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton back;
  private javax.swing.JButton config_apache;
  private javax.swing.JButton config_dhcp;
  private javax.swing.JButton config_dns;
  private javax.swing.JButton config_firewall;
  private javax.swing.JButton config_j_dhcp;
  private javax.swing.JButton config_j_dns;
  private javax.swing.JButton config_j_proxy;
  private javax.swing.JButton config_proxy;
  private javax.swing.JButton config_samba;
  private javax.swing.JButton config_tomcat;
  private javax.swing.JButton config_vpn;
  private javax.swing.JButton install_apache;
  private javax.swing.JButton install_dhcp;
  private javax.swing.JButton install_dns;
  private javax.swing.JButton install_imap;
  private javax.swing.JButton install_j_dhcp;
  private javax.swing.JButton install_j_dns;
  private javax.swing.JButton install_j_proxy;
  private javax.swing.JButton install_mysql;
  private javax.swing.JButton install_php;
  private javax.swing.JButton install_pop3;
  private javax.swing.JButton install_proxy;
  private javax.swing.JButton install_smtp;
  private javax.swing.JButton install_ssh;
  private javax.swing.JButton install_tomcat;
  private javax.swing.JButton install_vpn;
  private javax.swing.JButton install_xrdp;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel14;
  private javax.swing.JLabel jLabel15;
  private javax.swing.JLabel jLabel16;
  private javax.swing.JLabel jLabel17;
  private javax.swing.JLabel jLabel18;
  private javax.swing.JLabel jLabel19;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel20;
  private javax.swing.JLabel jLabel21;
  private javax.swing.JLabel jLabel22;
  private javax.swing.JLabel jLabel23;
  private javax.swing.JLabel jLabel24;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JToolBar jToolBar1;
  // End of variables declaration//GEN-END:variables

  //configButton, installButton, configPanel, name/desc, deb_pkgName, [rpm_pkgName]
  private Object servers[][];
  private String dpkg[][];

  private void initButtons() {
    servers = new Object[][] {
      {config_apache, install_apache, "ApachePanel", "Apache Web Server", "apache2", "httpd"},
      {null, install_php, null, "PHP Scripting Plugin", "php5", "php"},
      {config_tomcat, install_tomcat, "TomcatPanel", "Java Web Server", "tomcat7", "tomcat"},
      {config_proxy, install_proxy, "ProxyPanel", "Proxy Server", "squid3", "squid"},
      {null, install_mysql, "MySQLPanel", "MySQL Database Server", "mysql-server", "mysql-server"},
      {null, install_xrdp, "RemoteDesktopPanel", "Remote Desktop Server", "xrdp", "xrdp"},
      {null, install_ssh, null, "SSH Server", "openssh-server", "openssh-server"},
      {config_dhcp, install_dhcp, "DHCPPanel", "DHCP Server", "isc-dhcp-server", "dhcp"},
      {config_dns, install_dns, "DNSPanel", "DNS Server", "bind9", "bind"},
      {config_vpn, install_vpn, "VPNServerPanel", "VPN Server", "pptpd", "pptpd"},
      {config_firewall, null, "FirewallPanel", null, null},
      {null, install_pop3, "Pop3Panel", "POP3 Server", "dovecot-pop3d", "dovecot"},
      {null, install_smtp, "SMTPPanel", "SMTP Server", "sendmail", "sendmail"},
      {null, install_imap, "IMAPPanel", "IMAP Server", "dovecot-imapd", "dovecot"},
      {config_samba, null, "SambaPanel", "Samba Server", null, null},
      {config_j_dhcp, install_j_dhcp, "JDHCPPanel", "DHCP Server", "jdhcp", "jdhcp"},
      {config_j_dns, install_j_dns, "JDNSPanel", "DNS Server", "jdns", "jdns"},
      {config_j_proxy, install_j_proxy, "JProxyPanel", "Proxy Server", "jproxy", "jproxy"},
//      {config_asterisk, install_asterisk, "AsteriskPanel", "Asterisk VoIP Server", "asterisk"}
    };
    for(int a=0;a<servers.length;a++) {
      if (servers[a][0] != null) ((JButton)servers[a][0]).addActionListener(this);
      if (servers[a][1] != null) ((JButton)servers[a][1]).addActionListener(this);
    }
  }

  private void updateInstallButtons() {
    Linux.updateInstalled();
    for(int a=0;a<servers.length;a++) {
      if (Linux.isInstalled((String)servers[a][4])) {
        if (servers[a][0] != null) ((JButton)servers[a][0]).setEnabled(true);
        if (servers[a][1] != null) ((JButton)servers[a][1]).setText("Remove");
      } else {
        if (servers[a][0] != null) ((JButton)servers[a][0]).setEnabled(false);
        if (servers[a][1] != null) ((JButton)servers[a][1]).setText("Install");
      }
    }
  }

  private void config(String panelName) {
    try {
      Class cls = Class.forName(panelName);
      JPanel panel = (JPanel)cls.newInstance();
      ConfigApp.This.setPanel(panel);
    } catch (Exception e) {
      JFLog.log(e);
    }
  }

  private void install(String pkg, String name) {
    ShellProcess.logPrompt = true;  //testing
    if (Linux.isInstalled(pkg)) {
      if (!JF.showConfirm("Warning", "Are you sure you want to remove '" + name + "'?")) return;
      Linux.removePackage(pkg, name);
    } else {
      if (!JF.showConfirm("Warning", "Are you sure you want to install '" + name + "'?")) return;
      Linux.installPackage(pkg, name);
    }
    ShellProcess.logPrompt = false;
    updateInstallButtons();
  }

  public void actionPerformed(ActionEvent e) {
    JButton button = (JButton)e.getSource();
    for(int a=0;a<servers.length;a++) {
      if (servers[a][0] == button) {
        config((String)servers[a][2]);
        return;
      }
      if (servers[a][1] == button) {
        install((String)servers[a][4], (String)servers[a][3]);
        return;
      }
    }
  }
}
