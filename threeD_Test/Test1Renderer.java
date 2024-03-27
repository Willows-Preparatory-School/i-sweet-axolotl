package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.util.Arrays;

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
    static float[] vertices;
    static float texCoords[];
    static int VBO;
    static int VAO;

    static Test1Shader shader;

    public static void renderInit(long r_window)
    {
        window = r_window;

        shader = new Test1Shader("shader.vs", "shader.fs");

        // set up vertex data (and buffer(s)) and configure vertex attributes
        // ------------------------------------------------------------------
        float vertices[] = {
                // positions          // colors           // texture coords
                0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f, // top right
                0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 0.0f, // bottom right
                -0.5f, -0.5f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 0.0f, // bottom left
                -0.5f,  0.5f, 0.0f,   1.0f, 1.0f, 0.0f,   0.0f, 1.0f  // top left
        };
        int indices[] = {
            0, 1, 3, // first triangle
            1, 2, 3  // second triangle
        };

        int VAO = GL43.glGenVertexArrays();
        int VBO = GL43.glGenBuffers();
        int EBO = GL43.glGenBuffers();

        GL43.glBindVertexArray(VAO);

        GL43.glBindBuffer(GL_ARRAY_BUFFER, VBO);
        GL43.glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

        GL43.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
        GL43.glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);
    }

    public static void render()
    {
        //GL43.glUseProgram(shaderProgram);
        shader.use();
        //shader.setFloat("ourColor", 1.0f);
        // change color :3
        // update the uniform color

        double timeValue = GLFW.glfwGetTime();
        float greenValue = (float) (Math.sin(timeValue) / 2.0f + 0.5f);
        //int vertexColorLocation = GL43.glGetUniformLocation(shaderProgram, "ourColor");
        //GL43.glUniform4f(vertexColorLocation, 0.0f, greenValue, 0.0f, 1.0f);
        shader.setVec4f("ourColor", new Vector4f(0.0f, greenValue, 0.0f, 1.0f));

        GL43.glBindVertexArray(VAO);
        GL43.glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
