package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicInteger;

import static org.lwjgl.glfw.GLFW.nglfwGetFramebufferSize;
import static org.lwjgl.system.MemoryUtil.memAddress;

public class Test1 {
    private static long window;
    private int width = 1200;
    private int height = 800;

    private static void run() {
        System.out.println("Hello!");
        // Initialize GLFW
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        AtomicInteger width = new AtomicInteger(800);
        AtomicInteger height = new AtomicInteger(600);
        // Create a window
        window = GLFW.glfwCreateWindow(width.get(), height.get(), "Hello World", 0, 0);
        if (window == 0) {
            System.out.println("Failed to create window");
            GLFW.glfwTerminate();
            return;
        }

        // Make the window's context current
        GLFW.glfwMakeContextCurrent(window);

        // HiDPI fix:
        try (MemoryStack frame = MemoryStack.stackPush()) {
            IntBuffer framebufferSize = frame.mallocInt(2);
            nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
            width.set(framebufferSize.get(0));
            height.set(framebufferSize.get(1));
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1);

        // Initialize OpenGL
        GLCapabilities caps = GL.createCapabilities();

        // Check if OpenGL 4.3 is supported
        if (!GL.getCapabilities().OpenGL43) {
            // Throw an error
            System.out.println("ERROR: OpenGL 4.3 appears to not be supported.\n" +
                    "You may crash!!!");
            System.out.println("OpenGL version found: " + GL11.glGetString(GL11.GL_VERSION));
            //throw new RuntimeException("OpenGL 4.3 is not supported");
        }
        else
        {
            System.out.println("OpenGL version found: " + GL11.glGetString(GL11.GL_VERSION));
        }

        // Set the clear color
        GL43.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);

        Test1Renderer.renderInit(window);

        System.out.println("Loop start.");
        // The game loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Clear the screen
            GL43.glClear(GL43.GL_COLOR_BUFFER_BIT);

            // -----------RENDER HERE--------
            //Test1Renderer.render();
            // ------END OF RENDER CODE------

            // Swap buffers
            GLFW.glfwSwapBuffers(window);

            // Poll for events
            GLFW.glfwPollEvents();
        }

        // Destroy the window
        GLFW.glfwDestroyWindow(window);

        // Terminate GLFW
        GLFW.glfwTerminate();
    }

    public static void main(String[] args)
    {
        run();
    }
}

