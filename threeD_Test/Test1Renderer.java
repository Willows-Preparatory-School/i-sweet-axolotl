package threeD_Test;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL43;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.ARBFragmentShader.GL_FRAGMENT_SHADER_ARB;
import static org.lwjgl.opengl.ARBShaderObjects.*;
import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Test1Renderer
{
    static int[] indices;
    static float[] vertices;
    static int width = 800, height = 600;

    static long lastTime;
    static int grid;
    static int gridProgram;
    static int gridProgramMatLocation;
    static Quaternionf orientation = new Quaternionf();
    static Vector3f position = new Vector3f(0, 2, 5).negate();
    static Matrix4f mat = new Matrix4f();
    static boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST + 1];

    public static void renderInit(long window)
    {
        GL43.glClearColor(0.7f, 0.8f, 0.9f, 1);
        GL43.glEnable(GL43.GL_BLEND);
        GL43.glBlendFunc(GL43.GL_SRC_ALPHA, GL43.GL_ONE_MINUS_SRC_ALPHA);
        createGridProgram();
        createGrid();
        GLFW.glfwShowWindow(window);
        lastTime = System.nanoTime();
    }

    public static void render(int gridProgram, int gridProgramMatLocation, int grid)
    {
        GL43.glViewport(0, 0, width, height);
        GL43.glClear(GL43.GL_DEPTH_BUFFER_BIT | GL43.GL_COLOR_BUFFER_BIT);
        glUseProgramObjectARB(gridProgram);
        long thisTime = System.nanoTime();
        float dt = (thisTime - lastTime) * 1E-9f;
        lastTime = thisTime;
        try (MemoryStack stack = stackPush())
        {
            glUniformMatrix4fvARB(gridProgramMatLocation, false, updateMatrices(dt).get(stack.mallocFloat(16)));
        }
        GL43.glCallList(grid);
    }

    private static Matrix4f updateMatrices(float dt) {
        float rotateZ = 0f;
        float speed = 2f;
        if (keyDown[GLFW.GLFW_KEY_LEFT_SHIFT])
            speed = 10f;
        if (keyDown[GLFW.GLFW_KEY_Q])
            rotateZ -= 1f;
        if (keyDown[GLFW.GLFW_KEY_E])
            rotateZ += 1f;
        if (keyDown[GLFW.GLFW_KEY_W])
            position.add(orientation.positiveZ(new Vector3f()).mul(dt * speed));
        orientation.rotateLocalZ(rotateZ * dt * speed);
        return mat.setPerspective((float) Math.toRadians(60), (float) width / height, 0.1f, 1000.0f)
                .rotate(orientation)
                .translate(position);
    }

    private static void createGridProgram() {
        gridProgram = glCreateProgramObjectARB();
        int vs = glCreateShaderObjectARB(GL_VERTEX_SHADER_ARB);
        glShaderSourceARB(vs,
                "#version 110\n" +
                        "uniform mat4 viewProjMatrix;\n" +
                        "varying vec4 wp;\n" +
                        "void main(void) {\n" +
                        "  wp = gl_Vertex;\n" +
                        "  gl_Position = viewProjMatrix * gl_Vertex;\n" +
                        "}");
        glCompileShaderARB(vs);
        glAttachObjectARB(gridProgram, vs);
        int fs = glCreateShaderObjectARB(GL_FRAGMENT_SHADER_ARB);
        glShaderSourceARB(fs,
                "#version 110\n" +
                        "varying vec4 wp;\n" +
                        "void main(void) {\n" +
                        "  vec2 p = wp.xz / wp.w;\n" +
                        "  vec2 g = 0.5 * abs(fract(p) - 0.5) / fwidth(p);\n" +
                        "  float a = min(min(g.x, g.y), 1.0);\n" +
                        "  gl_FragColor = vec4(vec3(a), 1.0 - a);\n" +
                        "}");
        glCompileShaderARB(fs);
        glAttachObjectARB(gridProgram, fs);
        glLinkProgramARB(gridProgram);
        gridProgramMatLocation = glGetUniformLocationARB(gridProgram, "viewProjMatrix");
    }

    private static void createGrid() {
        grid = GL43.glGenLists(1);
        GL43.glNewList(grid, GL43.GL_COMPILE);
        GL43.glBegin(GL43.GL_TRIANGLES);
        GL43.glVertex4f(-1, 0, -1, 0);
        GL43.glVertex4f(-1, 0,  1, 0);
        GL43.glVertex4f( 0, 0,  0, 1);
        GL43.glVertex4f(-1, 0,  1, 0);
        GL43.glVertex4f( 1, 0,  1, 0);
        GL43.glVertex4f( 0, 0,  0, 1);
        GL43.glVertex4f( 1, 0,  1, 0);
        GL43.glVertex4f( 1, 0, -1, 0);
        GL43.glVertex4f( 0, 0,  0, 1);
        GL43.glVertex4f( 1, 0, -1, 0);
        GL43.glVertex4f(-1, 0, -1, 0);
        GL43.glVertex4f( 0, 0,  0, 1);
        GL43.glEnd();
        GL43.glEndList();
    }

    // This is a very bad way of doing this, and yet i dont care.
    /*
    private final Quaternionf orientation = new Quaternionf();
    private final Vector3f position = new Vector3f(0, 2, 5).negate();
    private boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST + 1];
    private static int grid;
    private static int gridProgram;
    private static int gridProgramMatLocation;
    private final Matrix4f mat = new Matrix4f();
    */

    public static void updateVariables(Quaternionf p_orientation, Vector3f p_position, boolean[] p_keyDown, int p_grid, int p_gridProgram, int p_gridProgramMatLocation, Matrix4f p_mat)
    {
        orientation = p_orientation;
        position = p_position;
        keyDown = p_keyDown;
        grid = p_grid;
        gridProgram = p_gridProgram;
        gridProgramMatLocation = p_gridProgramMatLocation;
        mat = p_mat;
    }
}
