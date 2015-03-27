/**
 *
 * @author pquiring
 *
 * Created : Sept 7, 2013
 */

import java.awt.*;
import java.util.*;

public class CameraPanel extends javax.swing.JPanel {

  /**
   * Creates new form CameraPanel
   */
  public CameraPanel(ProjectPanel project) {
    initComponents();
    this.project = project;
    list.add(new CameraKey());
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
   * content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        formMousePressed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 605, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 68, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents

  private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
    project.selectTrack(this);
    selectedOffset = project.offset + evt.getX() / 16 * project.scale;
    repaint();
  }//GEN-LAST:event_formMousePressed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables

  private ProjectPanel project;
  public ArrayList<CameraKey> list = new ArrayList<CameraKey>();
  public boolean selected;
  private int selectedOffset;

  public void paint(Graphics g) {
    g.setColor(Color.GRAY);
    g.fillRect(0,0,getWidth(),getHeight());
    g.setColor(Color.RED);
    int offset = project.offset;
    int width = getWidth();
    int px = 0;
    CameraKey keys[] = list.toArray(new CameraKey[0]);
    while (px < width) {
      if (selectedOffset >= offset && selectedOffset <= (offset + project.scale-1)) {
        g.drawRect(px,0,15,15);
      }
      g.drawLine(px, 8, px+16, 8);
      for(int a=0;a<keys.length;a++) {
        if (keys[a].offset >= offset && keys[a].offset <= (offset + project.scale-1)) {
          //draw a square (key point)
          g.fillRect(px+4,4,8,8);
        }
      }
      px += 16;
      offset += project.scale;
    }
  }

  public Dimension getPreferredSize() {
    return new Dimension(project.getTracksWidth(), 16);
  }

  public void addKey() {
    CameraKey keys[] = list.toArray(new CameraKey[0]);
    int bestpos = -1;
    for(int a=0;a<keys.length;a++) {
      if (keys[a].offset == selectedOffset) return;  //already created
      if (keys[a].offset < selectedOffset) {
        bestpos = a+1;
      }
    }
    if (bestpos == -1) bestpos = 1;
    CameraKey key = new CameraKey();
    key.offset = selectedOffset;
    list.add(bestpos, key);
    repaint();
  }

  public void editKey() {
    CameraKey keys[] = list.toArray(new CameraKey[0]);
    for(int a=0;a<keys.length;a++) {
      if (keys[a].offset == selectedOffset) {
        Element e = new Element();
        e.type = Element.TYPE_CAMERA;
        e.use3d = true;
        e.tx = keys[a].tx;
        e.ty = keys[a].ty;
        e.tz = keys[a].tz;
        e.rx = keys[a].rx;
        e.ry = keys[a].ry;
        e.rz = keys[a].rz;
        EditElementProperties dialog = new EditElementProperties(null, true, e, keys[a].fov);
        dialog.setVisible(true);
        if (dialog.saved) {
          keys[a].tx = e.tx;
          keys[a].ty = e.ty;
          keys[a].tz = e.tz;
          keys[a].rx = e.rx;
          keys[a].ry = e.ry;
          keys[a].rz = e.rz;
          keys[a].fov = dialog.fov;
        }
        return;
      }
    }
  }

  public void deleteKey() {
    if (selectedOffset == 0) return;  //do not delete root key
    CameraKey keys[] = list.toArray(new CameraKey[0]);
    for(int a=0;a<keys.length;a++) {
      if (keys[a].offset == selectedOffset) {
        list.remove(a);
        repaint();
        return;
      }
    }
  }
}
