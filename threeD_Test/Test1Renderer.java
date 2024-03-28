package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL43;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.system.MemoryStack.stackPush;
import org.lwjgl.stb.*;

import javax.imageio.ImageIO;

public class Test1Renderer
{
    private static long window;
    static int shaderProgram;
    static float[] vertices;
    static float texCoords[];
    static int VBO;
    static int VAO;

    static Test1Shader shader;
    static int texture;

    public static void renderInit(long r_window)
    {
        window = r_window;

        shader = new Test1Shader("shader.vs", "shader.fs");

        // set up vertex data (and buffer(s)) and configure vertex attributes
        // ------------------------------------------------------------------
        float vertices[] = {
                // positions         // colors
                0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,  // bottom right
                -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,  // bottom left
                0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f   // top

        };

        VAO = GL43.glGenVertexArrays();
        VBO = GL43.glGenBuffers();
        // bind the Vertex Array Object first, then bind and set vertex buffer(s), and then configure vertex attributes(s).
        GL43.glBindVertexArray(VAO);

        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, VBO);
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER, vertices, GL43.GL_STATIC_DRAW);

        // position attribute
        GL43.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0L);
        GL43.glEnableVertexAttribArray(0);
        // color attribute
        GL43.glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
        GL43.glEnableVertexAttribArray(1);

        shader.use();

        // You can unbind the VAO afterwards so other VAO calls won't accidentally modify this VAO, but this rarely happens. Modifying other
        // VAOs requires a call to glBindVertexArray anyways so we generally don't unbind VAOs (nor VBOs) when it's not directly necessary.
        // glBindVertexArray(0);

        texture = Test1Texture.loadTexture("res/container.jpg");
        System.out.println(texture);
    }

    public static void render()
    {
        // render the triangle
        GL43.glBindVertexArray(VAO);
        GL43.glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
