//Pixel Shader

//given
uniform sampler2D u_sampler2D;

//set
varying vec4 v_color;
varying vec2 v_texCoord0;

//Code
void main() {
    vec4 color = texture2D(u_sampler2D, v_texCoord0) * v_color;
    color.rgb = vec3(1.) - color.rgb;
    gl_FragColor = color;
}