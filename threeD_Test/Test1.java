package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.*;
//import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.*;

import java.nio.IntBuffer;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class Test1 {
    private static long window;
    private int width = 1200;
    private int height = 800;

    private static void run()
    {
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

    private static void init()
    {
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
        window = glfwCreateWindow(800, 600, "video games >:3", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            if(key == GLFW_KEY_F && action == GLFW_RELEASE)
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
            if(key == GLFW_KEY_G && action == GLFW_RELEASE)
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
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

        // Initialize OpenGL
        GLCapabilities caps = GL.createCapabilities();
        // Check if OpenGL 4.3 is supported
        if (!GL.getCapabilities().OpenGL43) {
            // Throw an error
            System.out.println("ERROR: OpenGL 4.3 appears to not be supported.\n" +
                    "You may crash!!!");
            System.out.println("OpenGL version found: " + GL43.glGetString(GL43.GL_VERSION));
            //throw new RuntimeException("OpenGL 4.3 is not supported");
        }
        else
        {
            System.out.println("OpenGL version found: " + GL43.glGetString(GL43.GL_VERSION));
        }

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // TODO: use the other videomode, please.
        GLFWVidMode videomode_render = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert videomode_render != null; // is videomode not null?

        // Debug output
        System.out.println("Refresh rate: " + videomode_render.refreshRate() + "hz");
    }

    private static void loop()
    {
        Test1Renderer.renderInit(window);

        while ( !glfwWindowShouldClose(window) )
        {
            // GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT
            glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

            // Render everything in under this line
            Test1Renderer.render();
            //End of render code.
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args)
    {
        run();
    }
}

