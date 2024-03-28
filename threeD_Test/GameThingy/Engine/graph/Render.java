package threeD_Test.GameThingy.Engine.graph;

import org.lwjgl.opengl.GL;
import threeD_Test.GameThingy.Engine.Window;
import threeD_Test.GameThingy.Engine.scene.Scene;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    public Render() {
        GL.createCapabilities();
    }

    public void cleanup() {
        // Nothing to be done here yet
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
