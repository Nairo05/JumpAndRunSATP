//Pixel Shader
precision lowp float;
//given
uniform sampler2D u_sampler2D;
uniform vec3 u_shift;
//set
varying vec4 v_color;
varying vec2 v_texCoord0;
//Code
void main() {
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    color.rgb = u_shift * color.rgb;
    gl_FragColor = color;
}