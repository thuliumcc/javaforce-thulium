package javaforce.gl;

/**
 * Reference Fragment Shader
 *
 * @author pquiring
 *
 */

/*
 * uniform = set with glUniform...()
 * attribute = points to input array with glVertexAttribPointer()
 * varying = passed from vertex shader to fragment shader (shared memory)
 */

public class GLFragmentShader {
  public static String source =
"varying vec2 vTextureCoord1;\n" +
"varying vec2 vTextureCoord2;\n" +
"varying float vLength;\n" +
"varying flat int vUVMaps;\n" +  //flat = not interpolated
"\n" +
"uniform sampler2D uSampler1;\n" +
"uniform sampler2D uSampler2;\n" +
"\n" +
"void main() {\n" +
"  vec4 textureColor1 = texture2D(uSampler1, vTextureCoord1);\n" +
"  if (textureColor1.a == 0.0) discard;\n" +
"  if (vUVMaps > 1) {" +
"    vec4 textureColor2 = texture2D(uSampler2, vTextureCoord2);\n" +
"    if (textureColor2.a != 0.0) {\n" +
"      textureColor1 = textureColor2;\n" +  //or you could blend the colors with "+="
"    }\n" +
"  }" +
"  gl_FragColor = textureColor1;\n" +
"}\n";
}
