package server;

/**
 *
 * @author pquiring
 */

import java.io.*;
import java.net.*;

import javaforce.*;
import javaforce.service.*;
import javaforce.jna.*;

public class RDP implements WebHandler, WinRDPServer.RDPListener {
  private Web web;
  private String base64_password;
  private String rdpPass;

  private WinRDPServer rdp;

  private ServerSocket ss;
  private boolean active;
  private int rdpPort = -1;
  private String rdpHost;

  public void start(String webPass, String rdpPass) {
    if (webPass == null || rdpPass.length() == 0) {
      return;
    }
    this.rdpPass = rdpPass;
    String u_p = ":" + webPass;
    base64_password = new String(Base64.encode(u_p.getBytes()));
    web = new Web();
    if (!web.start(this, 33001, true)) {
      JF.showError("Error", "Failed to start Web Server");
      return;
    }
    //start redir service
    try {
      ss = new ServerSocket(33002);
      active = true;
      new Thread() {
        public void run() {
          while (active) {
            try {
              Socket s1 = ss.accept();
              if (rdpPort == -1) {
                s1.close();
                continue;
              }
              System.out.println("Connecting redir service to:" + rdpPort);
              Socket s2 = new Socket(rdpHost, rdpPort);
              InputStream s1i = s1.getInputStream(), s2i = s2.getInputStream();
              OutputStream s1o = s1.getOutputStream(), s2o = s2.getOutputStream();
              System.out.println("Starting port redir threads");
              new Redir(s1i, s2o).start();
              new Redir(s2i, s1o).start();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close() {
    rdpPort = -1;
    if (rdp != null) {
      rdp.stop();
      rdp = null;
    }
    if (web != null) {
      web.stop();
      web = null;
    }
    if (ss != null) {
      active = false;
      try {ss.close();} catch (Exception e) {}
    }
  }

  private void send401(WebResponse res) {
    res.addCookie("WWW-Authenticate", "Basic realm=\"jfRDP\"");
    res.setStatus(401, "401 Auth required.");
  }

  private void noCache(WebResponse res) {
    res.addHeader("Cache-Control: no-cache, no-store, must-revalidate");
    res.addHeader("Pragma: no-cache");
    res.addHeader("Expires: 0");
  }

  public void doGet(WebRequest req, WebResponse res) {
    String url = req.getURL();
    if (!url.equals("/invite")) {
      res.setStatus(404, "404 Object not found.");
      return;
    }
    String auth = req.getHeader("Authorization");
    if (auth == null) {
      send401(res);
      return;
    }
    //f = Basic "base64(username:password)"
    String f[] = auth.split(" ");
    if (f.length != 2 || !f[0].equals("Basic") || !f[1].equals(base64_password)) {
      send401(res);
      return;
    }
    if (rdpPass == null || rdpPass.length() == 0) {
      try {res.write("Server setup incomplete".getBytes());} catch (Exception e) {e.printStackTrace();}
      return;
    }
    if (rdp != null) {
      rdp.stop();
      rdp = null;
    }
    rdp = new WinRDPServer();
    rdp.start("Auth", "Group", rdpPass, 1, 0);  //0=port (ignored for now)
    String txt = rdp.getConnectionString();
    txt = fixIP(txt, req.getHost());
    System.out.println("Modified ConnectionString=" + txt);
    noCache(res);
    res.setContentType("text/plain");
    try {res.write(txt.getBytes());} catch (Exception e) {e.printStackTrace();}
    System.out.println("Connection string delivered.");
  }

  public void doPost(WebRequest req, WebResponse res) {
    res.setStatus(500, "500 POST not supported");
  }

  private String fixIP(String txt, String host) {
    XML xml = new XML();
    ByteArrayInputStream bais = new ByteArrayInputStream(txt.getBytes());
    xml.read(bais);
    //...
    XML.XMLTag E = xml.root;
    XML.XMLTag A = xml.getTag(new String[] {"E", "A"});
    XML.XMLTag C = xml.getTag(new String[] {"E", "C"});
    XML.XMLTag T = xml.getTag(new String[] {"E", "C", "T"});
    //enumerate T's L children
    int cnt = T.getChildCount();
    boolean gotIP4 = false;
    for(int i=0;i<cnt;) {
      XML.XMLTag l = T.getChildAt(i);
      String N = l.getArg("N");
      if (gotIP4 || N.indexOf(":") != -1) {
        //IP6 - ignore it
        T.remove(i);
        cnt--;
      } else {
        //IP4 - use it but change P and H
        rdpHost = l.getArg("N");
        l.setArg("N", host);
        String sp = l.getArg("P");
        rdpPort = Integer.valueOf(sp);
        l.setArg("P", "33002");
        gotIP4 = true;
        i++;
      }
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    xml.write(baos);
    return new String(baos.toByteArray());
  }

  public void onAttendeeConnect() {
  }

  public void onAttendeeDisconnect() {
    close();
  }

  public class Redir extends Thread {
    private InputStream is;
    private OutputStream os;
    public Redir(InputStream is, OutputStream os) {
      this.is = is;
      this.os = os;
    }
    public void run() {
      byte buf[] = new byte[1460];
      try {
        while (active) {
          int read = is.read(buf);
          if (read == -1) return;
          if (read > 0) {
            os.write(buf, 0, read);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
