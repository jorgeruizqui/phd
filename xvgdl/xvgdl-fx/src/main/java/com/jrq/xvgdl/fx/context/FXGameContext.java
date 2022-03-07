package com.jrq.xvgdl.fx.context;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.fx.model.object.FXGameObject;
import com.jrq.xvgdl.model.object.GameObjectType;
import com.jrq.xvgdl.model.object.IGameObject;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FXGameContext extends GameContext {

    @Getter
    private Collection<FXGameObject> fxGameObjects = new CopyOnWriteArrayList<>();
    @Getter
    @Setter
    private GraphicsContext graphicsContext;

    // TODO property on MAP or context
    @Getter
    private int spriteFactors = 30;

    public FXGameContext() {
        super();
    }

    @Override
    public void loadGameContext(GameContext gc, String configurationFile) throws XvgdlException {
        super.loadGameContext(gc, configurationFile);
        initializeFxGameObjects();
    }

    public FXGameObject getFxGamePlayer() {
        return fxGameObjects.stream().filter((fxGameObject -> fxGameObject.getObjectType().equals(GameObjectType.PLAYER))).findFirst().orElse(null);
    }

    @Override
    public void loadGameContext(String configurationFile) throws XvgdlException {
        loadGameContext(null, configurationFile);
    }

    private void initializeFxGameObjects() {
        getObjectsAsList().forEach((gameObject) -> {
            fxGameObjects.add(FXGameObject.fromGameObject(this, gameObject));
        });
    }


    @Override
    public void removeGameObject(IGameObject gameObject) {
        log.info("Removing game object :" + gameObject.getId());
        super.removeGameObject(gameObject);
        fxGameObjects.removeIf(fxGameObject -> fxGameObject.getId().equals(gameObject.getId()));
    }
}
