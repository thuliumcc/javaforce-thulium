/**
 *
 * @author pquiring
 *
 * Created : Sept 18, 2013
 */

import java.awt.*;
import javax.swing.*;

import javaforce.gl.*;

//Frame or JFrame causes flickering

public class TestGLCanvas extends Frame {

  GLCode common = new GLCode(true);

  /**
   * Creates new form TestGLCanvas
   */
  public TestGLCanvas() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
   * content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabel1 = new javax.swing.JLabel();
    canvas = new GLCanvas();

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel1.setText("Test OpenGL");
    add(jLabel1, java.awt.BorderLayout.NORTH);
    add(canvas, java.awt.BorderLayout.CENTER);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private java.awt.Canvas canvas;
  private javax.swing.JLabel jLabel1;
  // End of variables declaration//GEN-END:variables

  public void init() {
    setSize(640, 480);
    doLayout();
    setPosition();
    ((GLCanvas)canvas).init(common);
  }

  private void setPosition() {
    Dimension d = getSize();
    Rectangle s = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    if ((d.width > s.width) || (d.height > s.height)) {
      if (d.width > s.width) d.width = s.width;
      if (d.height > s.height) d.height = s.height;
      setSize(d);
    }
    setLocation(s.width/2 - d.width/2, s.height/2 - d.height/2);
  }
}
