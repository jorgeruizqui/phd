package es.jor.phd.xvgdl.model.object;

import java.util.Random;

import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.model.object.ai.IGameObjectAI;
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
    /** x position. */
    private int x;
    /** y position. */
    private int y;
    /** z position. */
    private int z;
    /** x intended position. */
    private int intendedX;
    /** y intended position. */
    private int intendedY;
    /** z intended position. */
    private int intendedZ;
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
	        setIntendedX(x);
	        setIntendedY(y);
	        setIntendedZ(z);
    	}
    }

    @Override
    public void resetMove() {
        setIntendedX(getX());
        setIntendedY(getY());
        setIntendedZ(getZ());
    }

    @Override
    public void applyAI(GameContext gameContext) {
        if (objectAI != null) {
            objectAI.applyAIonObject(gameContext, this);
        }
    }

    @Override
    public void update() {
    	setX(getIntendedX());
    	setY(getIntendedY());
    	setZ(getIntendedZ());
    }

    @Override
    public GameObject copy() {
        GameObject cloned = new GameObject();
        try {
            if (this.objectAI != null) {
                cloned.setObjectAI(this.objectAI.getClass().newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
        }
        cloned.setDynamic(isDynamic());
        cloned.setInstance((new Random()).nextInt());
        cloned.setIntendedX(getIntendedX());
        cloned.setFrozen(false);
        cloned.setIntendedY(getIntendedY());
        cloned.setIntendedZ(getIntendedZ());
        cloned.setX(getX());
        cloned.setY(getY());
        cloned.setZ(getZ());
        cloned.setName(getName());
        cloned.setSizeX(getSizeX());
        cloned.setSizeY(getSizeY());
        cloned.setSizeZ(getSizeZ());
        cloned.setObjectType(getObjectType());
        cloned.setVolatile(isVolatile());
        return cloned;
    }


}
