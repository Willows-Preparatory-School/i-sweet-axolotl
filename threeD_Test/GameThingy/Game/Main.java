package threeD_Test.GameThingy.Game;

import threeD_Test.GameThingy.Engine.*;
import threeD_Test.GameThingy.Engine.graph.Mesh;
import threeD_Test.GameThingy.Engine.graph.Render;
import threeD_Test.GameThingy.Engine.scene.Scene;

public class Main implements IAppLogic {

    public static void main(String[] args) {
        Main main = new Main();
        Window.WindowOptions windowOptions = new Window.WindowOptions();
        windowOptions.height = 600;
        windowOptions.width = 800;
        Engine gameEng = new Engine("game engine thingy >:3", windowOptions, main);
        gameEng.start();
    }

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    @Override
    public void init(Window window, Scene scene, Render render) {
        float[] positions = new float[]{
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        };
        Mesh mesh = new Mesh(positions, 3);
        scene.addMesh("triangle", mesh);
    }

    @Override
    public void input(Window window, Scene scene, long diffTimeMillis) {
        // Nothing to be done yet
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        // Nothing to be done yet
    }
}
