package javaforce.gl;

/**
 *
 * @author pquiring
 */

import com.sun.jna.*;
import com.sun.jna.ptr.*;
import com.sun.jna.win32.*;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

import javaforce.*;
import javaforce.jna.objc.*;
import static javaforce.jna.objc.NSOpenGLPixelFormat.*;

public class GLJNA extends GL {
  public GLJNA(GLInterface iface) {
    this.iface = iface;
  }

  public void glActiveTexture(int i1) {
    a1[0] = i1;
    api.glActiveTexture.invoke(a1);
  }
  public void glAttachShader(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glAttachShader.invoke(a2);
  }
  public void glBindBuffer(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindBuffer.invoke(a2);
  }
  public void glBindFramebuffer(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindFramebuffer.invoke(a2);
  }
  public void glBindRenderbuffer(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindRenderbuffer.invoke(a2);
  }
  public void glBindTexture(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBindTexture.invoke(a2);
  }
  public void glBlendFunc(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glBlendFunc.invoke(a2);
  }
  //NOTE : GLsizeiptr is 32 or 64 bits - use a Pointer
  public void glBufferData(int i1, int i2, float i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glBufferData(int i1, int i2, short i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glBufferData(int i1, int i2, int i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glBufferData(int i1, int i2, byte i3[], int i4) {
    a4[0] = i1;
    a4[1] = new Pointer(i2);
    a4[2] = i3;
    a4[3] = i4;
    api.glBufferData.invoke(a4);
  }
  public void glClear(int flags) {
    a1[0] = flags;
    api.glClear.invoke(a1);
  }
  public void glClearColor(float r, float g, float b, float a) {
    a4[0] = r;
    a4[1] = g;
    a4[2] = b;
    a4[3] = a;
    api.glClearColor.invoke(a4);
  }
  public void glColorMask(boolean r, boolean g, boolean b, boolean a) {
    a4[0] = r;
    a4[1] = g;
    a4[2] = b;
    a4[3] = a;
    api.glColorMask.invoke(a4);
  }
  public void glCompileShader(int id) {
    a1[0] = id;
    api.glCompileShader.invoke(a1);
  }
  public int glCreateProgram() {
    return api.glCreateProgram.invokeInt(null);
  }
  public int glCreateShader(int type) {
    a1[0] = type;
    return api.glCreateShader.invokeInt(a1);
  }
  public void glCullFace(int id) {
    a1[0] = id;
    api.glCullFace.invoke(a1);
  }
  public void glDeleteBuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glDeleteBuffers.invoke(a2);
  }
  public void glDeleteFramebuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glDeleteFramebuffers.invoke(a2);
  }
  public void glDeleteRenderbuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glDeleteRenderbuffers.invoke(a2);
  }
  public void glDeleteTextures(int i1, int i2[], int i3) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = i3;
    api.glDeleteTextures.invoke(a3);
  }
  public void glDrawElements(int i1, int i2, int i3, int i4) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = new Pointer(i4);
    api.glDrawElements.invoke(a4);
  }
  public void glDepthFunc(int i1) {
    a1[0] = i1;
    api.glDepthFunc.invoke(a1);
  }
  public void glDisable(int id) {
    a1[0] = id;
    api.glDisable.invoke(a1);
  }
  public void glDepthMask(boolean state) {
    a1[0] = state;
    api.glDepthMask.invoke(a1);
  }
  public void glEnable(int id) {
    a1[0] = id;
    api.glEnable.invoke(a1);
  }
  public void glEnableVertexAttribArray(int id) {
    a1[0] = id;
    api.glEnableVertexAttribArray.invoke(a1);
  }
  public void glFlush() {
    api.glFlush.invoke(null);
  }
  public void glFramebufferTexture2D(int i1, int i2, int i3, int i4, int i5) {
    a5[0] = i1;
    a5[1] = i2;
    a5[2] = i3;
    a5[3] = i4;
    a5[4] = i5;
    api.glFramebufferTexture2D.invoke(a5);
  }
  public void glFramebufferRenderbuffer(int i1, int i2, int i3, int i4) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = i4;
    api.glFramebufferRenderbuffer.invoke(a4);
  }
  public void glFrontFace(int id) {
    a1[0] = id;
    api.glFrontFace.invoke(a1);
  }
  public int glGetAttribLocation(int i1, String str) {
    a2[0] = i1;
    a2[1] = str;
    return api.glGetAttribLocation.invokeInt(a2);
  }
  public int glGetError() {
    return api.glGetError.invokeInt(null);
  }
  public String glGetProgramInfoLog(int id) {
    IntByReference len = new IntByReference();
    len.setValue(1024);
    Pointer str = malloc(1024);
    a4[0] = id;
    a4[1] = 1024;
    a4[2] = len;
    a4[3] = str;
    api.glGetProgramInfoLog.invoke(a4);
    String ret = str.getString(0);
    free(str);
    return ret;
  }
  public String glGetShaderInfoLog(int id) {
    IntByReference len = new IntByReference();
    len.setValue(1024);
    Pointer str = malloc(1024);
    a4[0] = id;
    a4[1] = 1024;
    a4[2] = len;
    a4[3] = str;
    api.glGetShaderInfoLog.invoke(a4);
    String ret = str.getString(0);
    free(str);
    return ret;
  }
  public String glGetString(int type) {
    a1[0] = type;
    return api.glGetString.invokeString(a1, false);
  }
  public void glGetIntegerv(int type, int i[]) {
    a2[0] = type;
    a2[1] = i;
    api.glGetIntegerv.invoke(a2);
  }
  public void glGenBuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenBuffers.invoke(a2);
  }
  public void glGenFramebuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenFramebuffers.invoke(a2);
  }
  public void glGenRenderbuffers(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenRenderbuffers.invoke(a2);
  }
  public void glGenTextures(int i1, int i2[]) {
    a2[0] = i1;
    a2[1] = i2;
    api.glGenTextures.invoke(a2);
  }
  public int glGetUniformLocation(int i1, String str) {
    a2[0] = i1;
    a2[1] = str;
    return api.glGetUniformLocation.invokeInt(a2);
  }
  public void glLinkProgram(int id) {
    a1[0] = id;
    api.glLinkProgram.invoke(a1);
  }
  public void glPixelStorei(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glPixelStorei.invoke(a2);
  }
  public void glReadPixels(int i1, int i2, int i3, int i4, int i5, int i6, int px[]) {
    a7[0] = i1;
    a7[1] = i2;
    a7[2] = i3;
    a7[3] = i4;
    a7[4] = i5;
    a7[5] = i6;
    a7[6] = px;
    api.glReadPixels.invoke(a7);
  }
  public void glRenderbufferStorage(int i1, int i2, int i3, int i4) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = i4;
    api.glRenderbufferStorage.invoke(a4);
  }
  public int glShaderSource(int type, int count, String src[], int src_lengths[]) {
    a4[0] = type;
    a4[1] = count;
    a4[2] = src;
    a4[3] = src_lengths;
    return api.glShaderSource.invokeInt(a4);
  }
  public int glStencilFunc(int func, int ref, int mask) {
    a3[0] = func;
    a3[1] = ref;
    a3[2] = mask;
    return api.glStencilFunc.invokeInt(a3);
  }
  public int glStencilMask(int mask) {
    a1[0] = mask;
    return api.glStencilMask.invokeInt(a1);
  }
  public int glStencilOp(int sfail, int dpfail, int dppass) {
    a3[0] = sfail;
    a3[1] = dpfail;
    a3[2] = dppass;
    return api.glStencilOp.invokeInt(a3);
  }
  public void glTexImage2D(int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int px[]) {
    a9[0] = i1;
    a9[1] = i2;
    a9[2] = i3;
    a9[3] = i4;
    a9[4] = i5;
    a9[5] = i6;
    a9[6] = i7;
    a9[7] = i8;
    a9[8] = px;
    api.glTexImage2D.invoke(a9);
  }
  public void glTexParameteri(int i1, int i2, int i3) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = i3;
    api.glTexParameteri.invoke(a3);
  }
  public void glUseProgram(int id) {
    a1[0] = id;
    api.glUseProgram.invoke(a1);
  }
  public void glUniformMatrix4fv(int i1, int i2, int i3, float m[]) {
    a4[0] = i1;
    a4[1] = i2;
    a4[2] = i3;
    a4[3] = m;
    api.glUniformMatrix4fv.invoke(a4);
  }
  public void glUniform3fv(int i1, int i2, float f[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = f;
    api.glUniform3fv.invoke(a3);
  }
  public void glUniform2fv(int i1, int i2, float f[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = f;
    api.glUniform2fv.invoke(a3);
  }
  public void glUniform1f(int i1, float f) {
    a2[0] = i1;
    a2[1] = f;
    api.glUniform1f.invoke(a2);
  }
  public void glUniform3iv(int i1, int i2, int v[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = v;
    api.glUniform3iv.invoke(a3);
  }
  public void glUniform2iv(int i1, int i2, int v[]) {
    a3[0] = i1;
    a3[1] = i2;
    a3[2] = v;
    api.glUniform2iv.invoke(a3);
  }
  public void glUniform1i(int i1, int i2) {
    a2[0] = i1;
    a2[1] = i2;
    api.glUniform1i.invoke(a2);
  }
  public void glVertexAttribPointer(int i1, int i2, int i3, int i4, int i5, int i6) {
    a6[0] = i1;
    a6[1] = i2;
    a6[2] = i3;
    a6[3] = i4;
    a6[4] = i5;
    a6[5] = new Pointer(i6);
    api.glVertexAttribPointer.invoke(a6);
  }
  public void glViewport(int x,int y,int w,int h) {
    a4[0] = x;
    a4[1] = y;
    a4[2] = w;
    a4[3] = h;
    api.glViewport.invoke(a4);
  }

}
