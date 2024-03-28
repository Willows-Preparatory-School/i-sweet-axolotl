package threeD_Test.GameThingy.Engine.scene;

import threeD_Test.GameThingy.Engine.IGuiInstance;
import threeD_Test.GameThingy.Engine.graph.Mesh;
import threeD_Test.GameThingy.Engine.graph.Model;
import threeD_Test.GameThingy.Engine.graph.TextureCache;
import threeD_Test.GameThingy.Engine.scene.lights.SceneLights;

import java.util.*;

public class Scene {

    private Map<String, Model> modelMap;
    private TextureCache textureCache;
    private Camera camera;
    private SkyBox skyBox;
    private IGuiInstance guiInstance;
    private Projection projection;
    private SceneLights sceneLights;

    public Scene(int width, int height) {
        modelMap = new HashMap<>();
        projection = new Projection(width, height);
        textureCache = new TextureCache();
        camera = new Camera();
    }

    public Camera getCamera() {
        return camera;
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public SceneLights getSceneLights() {
        return sceneLights;
    }

    public IGuiInstance getGuiInstance() {
        return guiInstance;
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public void addEntity(Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntitiesList().add(entity);
    }

    public void addModel(Model model) {
        modelMap.put(model.getId(), model);
    }

    public void cleanup() {
        modelMap.values().forEach(Model::cleanup);
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public Projection getProjection() {
        return projection;
    }

    public void setGuiInstance(IGuiInstance guiInstance) {
        this.guiInstance = guiInstance;
    }

    public void setSceneLights(SceneLights sceneLights) {
        this.sceneLights = sceneLights;
    }

    public void setSkyBox(SkyBox skyBox) {
        this.skyBox = skyBox;
    }

    public void resize(int width, int height) {
        projection.updateProjMatrix(width, height);
    }
}
