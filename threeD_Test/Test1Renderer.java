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
    static int VBO;
    static int VAO;

    static Test1Shader shader;

    public static void renderInit(long r_window)
    {
        window = r_window;
        vertices = new float[]{
                // positions         // colors
                0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // bottom right
                -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // bottom left
                0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // top
        };
        VBO = GL43.glGenBuffers();
        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, VBO);
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER, vertices, GL43.GL_STATIC_DRAW);

        System.out.println("Compiling vertex shader...");
        String vertexShaderSource =
                "#version 330 core\n" +
                "layout (location = 0) in vec3 aPos;   // the position variable has attribute position 0\n" +
                "layout (location = 1) in vec3 aColor; // the color variable has attribute position 1\n" +
                "  \n" +
                "out vec3 ourColor; // output a color to the fragment shader\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    gl_Position = vec4(aPos, 1.0);\n" +
                "    ourColor = aColor; // set ourColor to the input color we got from the vertex data\n" +
                "}  ";
        int vertexShader = GL43.glCreateShader(GL43.GL_VERTEX_SHADER);
        GL43.glShaderSource(vertexShader, vertexShaderSource);
        GL43.glCompileShader(vertexShader);
        System.out.println("Compiled vertex shader. ^w^");

        System.out.println("Compiling fragment shader...");
        String fragmentShaderSource = "#version 330 core\n" +
                "out vec4 FragColor;  \n" +
                "in vec3 ourColor;\n" +
                "  \n" +
                "void main()\n" +
                "{\n" +
                "    FragColor = vec4(ourColor, 1.0);\n" +
                "}";
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
        // position attribute
        GL43.glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0L);
        GL43.glEnableVertexAttribArray(0);
        // color attribute
        GL43.glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3*4L);
        GL43.glEnableVertexAttribArray(1);

        shader = new Test1Shader("shader.vs", "shader.fs");
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
