#version 150

uniform sampler2D Sampler0;
uniform float Intensity;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord);

    // Boost only bright pixels (the additive rim already pushed them above
    // 1.0-adjacent luminance) so the rest of the frame stays untouched -
    // a soft, cheap stand-in for a real bloom convolution.
    float luminance = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    float boost = smoothstep(0.55, 1.0, luminance) * Intensity;

    fragColor = vec4(color.rgb + color.rgb * boost, color.a);
}
