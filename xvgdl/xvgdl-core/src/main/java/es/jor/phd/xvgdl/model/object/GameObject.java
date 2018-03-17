package es.jor.phd.xvgdl.model.object;

import java.util.Random;

import es.indra.eplatform.util.log.ELogger;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.location.Position;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;
import es.jor.phd.xvgdl.util.GameConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Game Object
 *
 * @author jrquinones
 *
 */
@Getter
@Setter
@ToString
public class GameObject implements IGameObject {

    /** Name of the game object. */
    private String name;
    /** Instance. */
    private int instance;
    /** Position. */
    @Setter(lombok.AccessLevel.NONE)
    private Position position = new Position(-1, -1, -1);
    /** Intended position. */
    @Setter(lombok.AccessLevel.NONE)
    private Position intendedPosition = new Position(-1, -1, -1);
    /** Size x . */
    private int sizeX;
    /** Size y. */
    private int sizeY;
    /** Size z. */
    private int sizeZ;
    /** Game Object Type. */
    private GameObjectType objectType;
    /**
     * Is dynamic flag. Indicates if object can move during game or is fixed
     * forever.
     */
    private boolean isDynamic;
    /**
     * Is volatile flag. Indicates if object can appear/disappear during game or
     * is present forever.
     */
    private boolean isVolatile;

    /** Flag indicating if the object is frozen. */
    private boolean isFrozen;

    /**
     * Artificial Intelligence associated with this object.
     */
    private IGameObjectAI objectAI;

    @Override
    public String getId() {
        return getName() + "-" + getInstance();
    }

    @Override
    public void moveTo(int x, int y, int z) {
        if (!isFrozen()) {
            intendedPosition.setX(x);
            intendedPosition.setY(y);
            intendedPosition.setZ(z);
        }
    }

    @Override
    public void resetMove() {
        intendedPosition.setX(getPosition().getX());
        intendedPosition.setY(getPosition().getY());
        intendedPosition.setZ(getPosition().getZ());
    }

    @Override
    public void applyAI(GameContext gameContext) {
        if (objectAI != null) {
            objectAI.applyAIonObject(gameContext, this);
        }
    }

    public void setPosition(int x, int y, int z) {
        position.setX(x);
        position.setY(y);
        position.setZ(z);
    }

    @Override
    public int getX() {
        return position.getX();
    }

    @Override
    public int getY() {
        return position.getY();
    }

    @Override
    public int getZ() {
        return position.getZ();
    }

    public void setIntendedPosition(int x, int y, int z) {
        intendedPosition.setX(x);
        intendedPosition.setY(y);
        intendedPosition.setZ(z);
    }

    @Override
    public void update() {
        setX(getIntendedPosition().getX());
        setY(getIntendedPosition().getY());
        setZ(getIntendedPosition().getZ());
    }

    @Override
    public GameObject copy() {
        GameObject cloned = new GameObject();
        try {
            if (this.objectAI != null) {
                cloned.setObjectAI(this.objectAI.getClass().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            ELogger.error(this, GameConstants.GAME_ENGINE_LOGGER_CATEGORY, "Exception setting object class AI: ", e);
        }
        cloned.setDynamic(isDynamic());
        cloned.setInstance((new Random()).nextInt());
        cloned.setIntendedPosition(getIntendedPosition().getX(), getIntendedPosition().getY(),
                getIntendedPosition().getZ());
        cloned.setFrozen(false);
        cloned.setX(getPosition().getX());
        cloned.setY(getPosition().getY());
        cloned.setZ(getPosition().getZ());
        cloned.setName(getName());
        cloned.setSizeX(getSizeX());
        cloned.setSizeY(getSizeY());
        cloned.setSizeZ(getSizeZ());
        cloned.setObjectType(getObjectType());
        cloned.setVolatile(isVolatile());
        return cloned;
    }

    private void setX(int x) {
        position.setX(x);
    }

    private void setY(int y) {
        position.setY(y);
    }

    private void setZ(int z) {
        position.setZ(z);
    }

    @Override
    public boolean isLocatedAnyWhereInMap() {
        return (getX() >= 0 && getY() >= 0 && getZ() >= 0) || (getIntendedPosition().getX() >= 0
                && getIntendedPosition().getY() >= 0 && getIntendedPosition().getZ() >= 0);
    }
}
