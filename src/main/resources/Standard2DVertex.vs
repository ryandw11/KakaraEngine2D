#version 330

layout (location=0) in vec2 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform mat4 model;
uniform mat4 ortho;

// The following is for the sprite sheet:
uniform vec2 textureOffset;
uniform vec2 columnsRows;
uniform int isSpriteSheet;

void main()
{
    gl_Position = ortho * model * vec4(position.xy, 0.0, 1.0);
    if(isSpriteSheet == 0){
        outTexCoord = texCoord;
    }
    else{
        float x = (texCoord.x / columnsRows.x + textureOffset.x);
        float y = (texCoord.y / columnsRows.y + textureOffset.y);

        outTexCoord = vec2(x, y);
    }
}