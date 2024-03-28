package threeD_Test.GameThingy.Engine;

import threeD_Test.GameThingy.Engine.graph.Render;
import threeD_Test.GameThingy.Engine.scene.Scene;

public interface IAppLogic {

    void cleanup();

    void init(Window window, Scene scene, Render render);

    //void input(Window window, Scene scene, long diffTimeMillis);

    void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed);

    void update(Window window, Scene scene, long diffTimeMillis);
}
