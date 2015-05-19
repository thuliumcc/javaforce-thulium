package javaforce.gl;

/**
 * Reference Vertex Shader
 *
 * @author pquiring
 *
 */

/*
 * uniform = set with glUniform...()
 * attribute = points to input array with glVertexAttribPointer()
 * varying = passed from vertex shader to fragment shader (shared memory)
 */

public class GLVertexShader {
  public static String source =
"attribute vec3 aVertexPosition;\n" +
"attribute vec2 aTextureCoord1;\n" +
"attribute vec2 aTextureCoord2;\n" +
"\n" +
"uniform mat4 uPMatrix;\n" +  //perspective matrix
"uniform mat4 uVMatrix;\n" +  //view (world) matrix
"uniform mat4 uMMatrix;\n" +  //model matrix
"uniform int uUVMaps;\n" +  //# of UVMaps (1 or 2 supported)
"\n" +
"varying flat int vUVMaps;\n" +
"varying vec2 vTextureCoord1;\n" +
"varying vec2 vTextureCoord2;\n" +
"varying float vLength;\n" +
"\n" +
"void main() {\n" +
"  gl_Position = uPMatrix * uVMatrix * uMMatrix * vec4(aVertexPosition, 1.0);\n" +  //NOTE:order of matrix multiply matters
"  vUVMaps = uUVMaps;\n" +
"  vTextureCoord1 = aTextureCoord1;\n" +
"  if (uUVMaps > 1) {\n" +
"    vTextureCoord2 = aTextureCoord2;\n" +
"  }\n" +
"  vLength = length(gl_Position);\n" +
"}\n";
}
