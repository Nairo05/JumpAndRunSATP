//Pixel Shader
precision lowp float;

//given
uniform sampler2D u_sampler2D;

//set
varying vec4 v_color;
varying vec2 v_texCoord0;

//Code
void main() {
    gl_FragColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
}