package threeD_Test.GameThingy.Game;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiCond;
import org.joml.Math;
import threeD_Test.GameThingy.Engine.*;
import threeD_Test.GameThingy.Engine.Window;
import threeD_Test.GameThingy.Engine.graph.*;
import threeD_Test.GameThingy.Engine.scene.Camera;
import threeD_Test.GameThingy.Engine.scene.Entity;
import threeD_Test.GameThingy.Engine.scene.ModelLoader;
import threeD_Test.GameThingy.Engine.scene.Scene;
import threeD_Test.GameThingy.Engine.scene.lights.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.joml.*;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements IAppLogic {
    private static final float MOUSE_SENSITIVITY = 0.1f;
    private static final float MOVEMENT_SPEED = 0.005f;
    private Entity cubeEntity;
    private Vector4f displInc = new Vector4f();
    private float rotation;
    private LightControls lightControls;
    public static void main(String[] args) {
        Main main = new Main();
        Window.WindowOptions windowOptions = new Window.WindowOptions();
        windowOptions.height = 600;
        windowOptions.width = 800;
        Engine gameEng = new Engine("game engine thingy >:3", windowOptions, main);
        gameEng.start();
    }
/*
    @Override
    public void drawGui() {
        ImGui.newFrame();
        ImGui.setNextWindowPos(0, 0, ImGuiCond.Always);
        ImGui.showDemoWindow();
        ImGui.endFrame();
        ImGui.render();
    }
 */

    /*
    @Override
    public boolean handleGuiInput(Scene scene, Window window) {
        ImGuiIO imGuiIO = ImGui.getIO();
        MouseInput mouseInput = window.getMouseInput();
        Vector2f mousePos = mouseInput.getCurrentPos();
        imGuiIO.setMousePos(mousePos.x, mousePos.y);
        imGuiIO.setMouseDown(0, mouseInput.isLeftButtonPressed());
        imGuiIO.setMouseDown(1, mouseInput.isRightButtonPressed());

        return imGuiIO.getWantCaptureMouse() || imGuiIO.getWantCaptureKeyboard();
    }
     */

    @Override
    public void cleanup() {
        // Nothing to be done yet
    }

    public void init(Window window, Scene scene, Render render) {
        Model cubeModel = ModelLoader.loadModel("cube-model", "resources/models/computer/computer.obj",
                scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity("cube-entity", cubeModel.getId());
        cubeEntity.setPosition(0, 0, -2);
        scene.addEntity(cubeEntity);

        SceneLights sceneLights = new SceneLights();
        sceneLights.getAmbientLight().setIntensity(0.3f);
        scene.setSceneLights(sceneLights);
        sceneLights.getPointLights().add(new PointLight(new Vector3f(1, 1, 1),
                new Vector3f(0, 0, -1.4f), 1.0f));

        Vector3f coneDir = new Vector3f(0, 0, -1);
        sceneLights.getSpotLights().add(new SpotLight(new PointLight(new Vector3f(1, 1, 1),
                new Vector3f(0, 0, -1.4f), 0.0f), coneDir, 140.0f));

        lightControls = new LightControls(scene);
        scene.setGuiInstance(lightControls);

        // move model for easy viewing.
        cubeEntity.setRotation(0, 1, 0, 80);
        cubeEntity.setPosition(0, -0.4f, -1.1f);
    }

    public void input(Window window, Scene scene, long diffTimeMillis, boolean inputConsumed) {
        if (inputConsumed) {
            return;
        }
        float move = diffTimeMillis * MOVEMENT_SPEED;
        Camera camera = scene.getCamera();
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            camera.moveUp(move);
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(move);
        }

        MouseInput mouseInput = window.getMouseInput();
        if (mouseInput.isRightButtonPressed()) {
            Vector2f displVec = mouseInput.getDisplVec();
            camera.addRotation((float) Math.toRadians(-displVec.x * MOUSE_SENSITIVITY),
                    (float) Math.toRadians(-displVec.y * MOUSE_SENSITIVITY));
        }
    }

    @Override
    public void update(Window window, Scene scene, long diffTimeMillis) {
        /*
        rotation += 1.5;
        if (rotation > 360) {
            rotation = 0;
        }
        cubeEntity.setRotation(1, 1, 1, (float) Math.toRadians(rotation));
         */
        cubeEntity.updateModelMatrix();
        SceneLights sceneLights = scene.getSceneLights();
        AmbientLight ambientLight = sceneLights.getAmbientLight();
        // Get the current time in milliseconds
        long time = System.currentTimeMillis();

        // Calculate the hue based on the time
        float hue = (float) (time % 60000) / 60000.0f;

        // Create a new Color object with the calculated hue
        Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
        ambientLight.setColor(color.getRed(), color.getGreen(), color.getBlue());
    }
}
