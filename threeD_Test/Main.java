package threeD_Test;

import org.lwjgl.*;
import org.lwjgl.bgfx.BGFXVertexLayout;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.opengl.*;

public class Main
{

    // The window handle
    private static long window;

    public static void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private static void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(800, 600, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode videomode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            assert videomode != null;
            glfwSetWindowPos(
                    window,
                    (videomode.width() - pWidth.get(0)) / 2,
                    (videomode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private static void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // depth test
        //glEnable(GL_DEPTH_TEST);

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // TODO: use the other videomode, please.
        GLFWVidMode videomode_render = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert videomode_render != null; // is videomode not null?

        // Debug output
        System.out.println("Refresh rate: " + videomode_render.refreshRate() + "hz");

        // other things
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
        GL30.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
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
        int shaderProgram = GL30.glCreateProgram();
        GL30.glAttachShader(shaderProgram, vertexShader);
        GL30.glAttachShader(shaderProgram, fragmentShader);
        GL30.glLinkProgram(shaderProgram);

        // Random cube stuff that i forgot how it works, send help
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            // GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            // Render everything in under this line
            // Use the shader program
            GL30.glUseProgram(shaderProgram);

            // Draw the triangle
            GL30.glDrawArrays(GL_TRIANGLES, 0, 3);
            //End of render code.

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main (String[] args)
    {
        System.out.println("Hello");
        run();
    }
}
