package es.jor.phd.xvgdl.model.object;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.location.DirectionVector;
import es.jor.phd.xvgdl.model.location.Position;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * Game Object
 *
 * @author jrquinones
 *
 */
@Getter
@Setter
@ToString
@Slf4j
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
    /** Speed factor. */
    private Double speedFactor = 1d;
    /** Direction Vector. If it's 0,0,0 means that the object is stopped. */
    private DirectionVector direction = new DirectionVector(0, 0, 0);
    
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
        
        // If direction vector is informed, object is moving in that direction according the speed factor
        if (direction.notZero()) {
            setX((int) (getX() + (speedFactor * direction.getX())));
            setY((int) (getY() + (speedFactor * direction.getY())));
            setZ((int) (getZ() + (speedFactor * direction.getZ())));
            setIntendedPosition(getX(), getY(), getZ());
        }
    }

    @Override
    public GameObject copy() {
        GameObject cloned = new GameObject();
        try {
            if (this.objectAI != null) {
                cloned.setObjectAI(this.objectAI.getClass().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Exception setting object class AI: ", e);
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
