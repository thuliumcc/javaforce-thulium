package javaforce.gl;

import java.util.*;
import java.io.*;

import javaforce.*;

/** <code>GLModel</code> is a set of <code>GLObject</code>'s that all share the same base orientation (rotation, translation, scale)
 * Each object can also have its own orientation in addition to this
 * Usually a 3DS file is loaded into one GLModel.
 * Each Object in the 3DS file will be stored into GLObject's.
 */

public class GLModel implements Cloneable {
  public ArrayList<GLObject> ol;  //obj list
  public GLMatrix m;  //translation, rotation, scale matrix for all sub-objects
  public boolean visible = true;
  public int refcnt;

  public GLModel() {
    m = new GLMatrix();
    m.setIdentity();
    ol = new ArrayList<GLObject>();
  }
  public GLModel(GLMatrix m) {  //for clone()
    this.m = m;
    ol = new ArrayList<GLObject>();
  }
  /**
  * Clones deep enough so that the cloned object will include seperate GLObjects, but share vertex, vertex point,
  * and animation data (except for the frame position).
  */
  public Object clone() {
    GLModel c = new GLModel((GLMatrix)m.clone());
    int objs = ol.size();
    for(int a=0;a<objs;a++) c.ol.add((GLObject)ol.get(a).clone());
    return c;
  }
  public void setVisible(boolean state) {visible = state;}
  public void addObject(GLObject obj) {
    ol.add(obj);
  }
  public void setIdentity() {
    m.setIdentity();
  }
  //these are additive
  public void rotate(float angle, float x, float y, float z) {
    m.addRotate(angle, x, y, z);
  }
  public void translate(float x, float y, float z) {
    m.addTranslate(x, y, z);
  }
  public void scale(float x, float y, float z) {
    m.addScale(x, y, z);
  }
  public void nextFrame() {
    GLObject obj;
    int size = ol.size();
    for(int i=0;i<size;i++) {
      obj = ol.get(i);
      obj.nextFrame();
    }
  }
  public void setFrame(int idx) {
    GLObject obj;
    int size = ol.size();
    for(int i=0;i<size;i++) {
      obj = ol.get(i);
      obj.setFrame(idx);
    }
  }
};
