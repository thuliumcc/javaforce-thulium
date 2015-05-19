package javaforce.gl;

/**
 * UV Map - texture coords (see GLObject)
 *
 * @author pquiring
 */

import javaforce.JFArrayFloat;

public class GLUVMap {
  public JFArrayFloat tcl;  //texture coords list (UV)
  public String name;
  public int textureIndex = -1;
  public boolean texloaded = false;
  public int tcb = -1;  //GL buffer
  public int idx;  //map index

  public GLUVMap(int idx) {
    tcl = new JFArrayFloat();
    this.idx = idx;
    if (idx > 1) {
      System.out.println("GLUVMap:Warning:More than 2 UVMaps not supported");
    }
  }

  public void addText(float uv[]) {
    tcl.append(uv);
  }

  public void copyBuffers(GL gl) {
    int ids[] = new int[1];
    if (tcb == -1) {
      gl.glGenBuffers(1, ids);
      tcb = ids[0];
    }
    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tcb);
    gl.glBufferData(GL.GL_ARRAY_BUFFER, tcl.size() * 4, tcl.toArray(), GL.GL_STATIC_DRAW);
  }

  public void bindBuffers(GLScene scene, GL gl) {
    gl.glBindBuffer(GL.GL_ARRAY_BUFFER, tcb);
    gl.glVertexAttribPointer(scene.tca[idx], 2, GL.GL_FLOAT, GL.GL_FALSE, 0, 0);
  }
}
