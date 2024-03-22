package threeD_Test;

import org.lwjgl.opengl.GL43;

public class Test1Renderer
{
    static int[] indices;
    static float[] vertices;
    static int width = 800, height = 600;

    public static void renderInit()
    {
        // Initialize some state
        GL43.glClearColor(0.3f, 0.45f, 0.72f, 1.0f);
        GL43.glEnable(GL43.GL_CULL_FACE);
        GL43.glPolygonMode(GL43.GL_FRONT_AND_BACK, GL43.GL_LINE);
        GL43.glLoadIdentity();

        int columns = 100, rows = 100;
        GL43.glOrtho(0, columns - 1, rows - 1, 0, -1, 1);

        // Build vertices
        vertices = new float[columns * rows * 2];
        int i = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                vertices[i++] = x;
                vertices[i++] = y;
            }
        }

        // Build indices
        i = 0;
        indices = new int[(rows - 1) * (columns + 1) * 2];
        for (int y = 0; y < rows - 1; y++) {
            for (int x = 0; x < columns; x++) {
                indices[i++] = y * columns + x;
                indices[i++] = (y + 1) * columns + x;
            }
            if (y < height - 1) {
                indices[i++] = (y + 2) * columns - 1;
                indices[i++] = (y + 1) * columns;
            }
        }

        // Upload to buffer objects
        int vbo = GL43.glGenBuffers(), ibo = GL43.glGenBuffers();
        GL43.glBindBuffer(GL43.GL_ARRAY_BUFFER, vbo);
        GL43.glBufferData(GL43.GL_ARRAY_BUFFER, vertices, GL43.GL_STATIC_DRAW);
        GL43.glEnableClientState(GL43.GL_VERTEX_ARRAY);
        GL43.glBindBuffer(GL43.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL43.glBufferData(GL43.GL_ELEMENT_ARRAY_BUFFER, indices, GL43.GL_STATIC_DRAW);
        GL43.glVertexPointer(2, GL43.GL_FLOAT, 0, 0L);
    }

    public static void render()
    {
        GL43.glDrawElements(GL43.GL_TRIANGLE_STRIP, indices.length, GL43.GL_UNSIGNED_INT, 0L);
    }
}
