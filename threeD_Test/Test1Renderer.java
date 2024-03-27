package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
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
    static int VBO;
    static int VAO;

    public static void renderInit(long r_window)
    {
        window = r_window;
        vertices = new float[]{
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.0f, 0.5f, 0.0f
        };
        VBO = GL43.glGenBuffers();
        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, VBO);
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER, vertices, GL43.GL_STATIC_DRAW);

        System.out.println("Compiling vertex shader...");
        String vertexShaderSource =
                "#version 330 core\n" +
                        "layout (location = 0) in vec3 aPos;\n" +
                        "void main()\n" +
                        "{\n" +
                            "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
                        "}\0";
        int vertexShader = GL43.glCreateShader(GL43.GL_VERTEX_SHADER);
        GL43.glShaderSource(vertexShader, vertexShaderSource);
        GL43.glCompileShader(vertexShader);
        System.out.println("Compiled vertex shader. ^w^");

        System.out.println("Compiling fragment shader...");
        String fragmentShaderSource = "#version 330 core\n" +
                "out vec4 FragColor;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
                "} ";
        int fragmentShader = GL43.glCreateShader(GL43.GL_FRAGMENT_SHADER);
        GL43.glShaderSource(fragmentShader, fragmentShaderSource);
        GL43.glCompileShader(fragmentShader);
        System.out.println("Compiled fragment shader! :3");

        shaderProgram = GL43.glCreateProgram();
        GL43.glAttachShader(shaderProgram, vertexShader);
        GL43.glAttachShader(shaderProgram, fragmentShader);
        GL43.glLinkProgram(shaderProgram);
        System.out.println("Shader program linked.");

        // ..:: Initialization code (done once (unless your object frequently changes)) :: ..
        // 1. bind Vertex Array Object
        GL43.glBindVertexArray(VAO);
        // 2. copy our vertices array in a buffer for OpenGL to use
        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, VBO);
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER, vertices, GL43.GL_STATIC_DRAW);
        // 3. then set our vertex attributes pointers
        GL43.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L);
        GL43.glEnableVertexAttribArray(0);
    }

    public static void render()
    {
        /*
        // 0. copy our vertices array in a buffer for OpenGL to use
        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, VBO);
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER, vertices, GL43.GL_STATIC_DRAW);
        // 1. then set the vertex attributes pointers
        GL43.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L);
        GL43.glEnableVertexAttribArray(0);
        // 2. use our shader program when we want to render an object
        GL43.glUseProgram(shaderProgram);
        // 3. now draw the object
        //someOpenGLFunctionThatDrawsOurTriangle();
         */

        GL43.glUseProgram(shaderProgram);
        GL43.glBindVertexArray(VAO);
        GL43.glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
