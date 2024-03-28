package threeD_Test.GameThingy.Engine;

import threeD_Test.GameThingy.Engine.scene.Scene;

public interface IGuiInstance {
    void drawGui();

    boolean handleGuiInput(Scene scene, Window window);
}
