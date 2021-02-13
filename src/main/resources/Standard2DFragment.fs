#version 330

in vec2 outTexCoord;
in vec3 mvPos;
out vec4 fragColor;

struct Material2D {
    sampler2D texture;
    vec4 color;
};

uniform Material2D material;

void main()
{
    fragColor = texture(material.texture, outTexCoord) * material.color;
}