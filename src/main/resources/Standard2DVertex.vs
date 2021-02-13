#version 330

layout (location=0) in vec2 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 model;
uniform mat4 ortho;

void main()
{
    gl_Position = ortho * model * vec4(position.xy, 0.0, 1.0);
    outTexCoord = texCoord;
}