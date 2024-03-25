package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Test1Renderer
{
    private static long window;
    static int shaderProgram;

    public static void renderInit(long r_window)
    {
        window = r_window;
        // Define the vertices of the triangle
        float[] vertices = {
                0.0f, 0.5f, 0.0f, // Red vertex
                0.5f, -0.5f, 0.0f, // Green vertex
                -0.5f, -0.5f, 0.0f, // Blue vertex
        };

        // Create a vertex buffer object
        int vbo = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);

        // Set the vertex attribute pointer
        GL30.glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0); // Updated to pass in the position of each vertex
        GL30.glEnableVertexAttribArray(0);

        // Create a shader program
        String vertexShaderSource =
                "#version 330 core\n" +
                        "layout (location = 0) in vec3 position;\n" +
                        "void main() {\n" +
                        "    gl_Position = vec4(position, 1.0);\n" +
                        "}\n";
        String fragmentShaderSource =
                "#version 330 core\n" +
                        "out vec4 fragColor;\n" +
                        "void main() {\n" +
                        "    fragColor = vec4(position.x, position.y, 0.0, 1.0);\n" + // Multicolored fragment shader
                        "}\n";
        int vertexShader = GL30.glCreateShader(GL30.GL_VERTEX_SHADER);
        GL30.glShaderSource(vertexShader, vertexShaderSource);
        GL30.glCompileShader(vertexShader);
        int fragmentShader = GL30.glCreateShader(GL30.GL_FRAGMENT_SHADER);
        GL30.glShaderSource(fragmentShader, fragmentShaderSource);
        GL30.glCompileShader(fragmentShader);
        shaderProgram = GL30.glCreateProgram();
        GL30.glAttachShader(shaderProgram, vertexShader);
        GL30.glAttachShader(shaderProgram, fragmentShader);
        GL30.glLinkProgram(shaderProgram);
    }

    public static void render()
    {
        // Use the shader program
        GL30.glUseProgram(shaderProgram);

        // Draw the triangle
        GL30.glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
