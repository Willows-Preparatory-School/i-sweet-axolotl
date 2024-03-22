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
    private static final Object[][] cubeVertices = {
            { -1.0f, 1.0f, 1.0f, 0xff000000 },
            { 1.0f, 1.0f, 1.0f, 0xff0000ff },
            { -1.0f, -1.0f, 1.0f, 0xff00ff00 },
            { 1.0f, -1.0f, 1.0f, 0xff00ffff },
            { -1.0f, 1.0f, -1.0f, 0xffff0000 },
            { 1.0f, 1.0f, -1.0f, 0xffff00ff },
            { -1.0f, -1.0f, -1.0f, 0xffffff00 },
            { 1.0f, -1.0f, -1.0f, 0xffffffff }
    };

    private static final int[] cubeIndices = {
            0, 1, 2, // 0
            1, 3, 2,
            4, 6, 5, // 2
            5, 6, 7,
            0, 2, 4, // 4
            4, 2, 6,
            1, 5, 3, // 6
            5, 7, 3,
            0, 4, 1, // 8
            4, 5, 1,
            2, 3, 6, // 10
            6, 3, 7
    };

    private BGFXVertexLayout layout;
    private static ByteBuffer vertices;
    private short vbh;
    private static ByteBuffer indices;
    private short ibh;
    private short program;

    //private Matrix4x3f view = new Matrix4x3f();
    private FloatBuffer viewBuf;
    //private Matrix4f proj = new Matrix4f();
    private FloatBuffer projBuf;
    //private Matrix4x3f model = new Matrix4x3f();
    private FloatBuffer modelBuf;

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
        // depth test
        glEnable(GL_DEPTH_TEST);

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

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // TODO: use the other videomode, please.
        GLFWVidMode videomode_render = glfwGetVideoMode(glfwGetPrimaryMonitor());
        assert videomode_render != null; // is videomode not null?

        // Debug output
        System.out.println("Refresh rate: " + videomode_render.refreshRate() + "hz");

        // Random cube stuff that i forgot how it works, send help
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            // Render everything in under this line
            // Rotate the cube
            glLoadIdentity();
            glTranslated(x, y, z);
            glRotatef(pitch, 0.1f, 0.0f, 0.0f);

            glBegin(GL_QUADS);
            glColor3f(0,1,1);
            glVertex3f(-wid/2f, hei/2f, len/2f);
            glVertex3f(-wid/2f, hei/2f,-len/2f);
            glVertex3f( wid/2f, hei/2f,-len/2f);
            glVertex3f( wid/2f, hei/2f, len/2f);
            glColor3f(1,0,0);
            glVertex3f(-wid/2f,-hei/2f,-len/2f);
            glVertex3f( wid/2f,-hei/2f,-len/2f);
            glVertex3f( wid/2f, hei/2f,-len/2f);
            glVertex3f(-wid/2f, hei/2f,-len/2f);
            glColor3f(0,1,0);
            glVertex3f(-wid/2f,-hei/2f,-len/2f);
            glVertex3f(-wid/2f,-hei/2f, len/2f);
            glVertex3f(-wid/2f, hei/2f, len/2f);
            glVertex3f(-wid/2f, hei/2f,-len/2f);
            glColor3f(0,0,1);
            glVertex3f( wid/2f,-hei/2f, len/2f);
            glVertex3f( wid/2f,-hei/2f,-len/2f);
            glVertex3f( wid/2f, hei/2f,-len/2f);
            glVertex3f( wid/2f, hei/2f, len/2f);
            glColor3f(1,1,0);
            glVertex3f(-wid/2f,-hei/2f,-len/2f);
            glVertex3f( wid/2f,-hei/2f,-len/2f);
            glVertex3f( wid/2f,-hei/2f, len/2f);
            glVertex3f(-wid/2f,-hei/2f, len/2f);
            glColor3f(1,0,1);
            glVertex3f(-wid/2f,-hei/2f, len/2f);
            glVertex3f( wid/2f,-hei/2f, len/2f);
            glVertex3f( wid/2f, hei/2f, len/2f);
            glVertex3f(-wid/2f, hei/2f, len/2f);
            glEnd();
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
