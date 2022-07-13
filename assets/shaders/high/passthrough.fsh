//Pixel Shader
#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif
//given
uniform sampler2D u_texture;
//set
varying LOWP vec4 v_color;
varying vec2 v_texCoords;
//Code
void main() {
    gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}