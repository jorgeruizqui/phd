package es.jor.phd.xvgdl.model.object;

/**
 * Different kind of objects valid for a game
 * @author jrquinones
 *
 */
public enum GameObjectType {
    /** Wall */
    WALL,
    /** Enemy */
    ENEMY,
    /** Player */
    PLAYER,
    /** Item */
    ITEM;

    /**
     *
     * @param typeSt Type as string
     * @return The GameObjectType according to the typeSt
     */
    public static GameObjectType fromString(String typeSt) {
        GameObjectType type = null;

        if (typeSt.trim().equalsIgnoreCase(WALL.toString())) {
            type = WALL;
        } else if (typeSt.trim().equalsIgnoreCase(ENEMY.toString())) {
            type = ENEMY;
        } else if (typeSt.trim().equalsIgnoreCase(PLAYER.toString())) {
            type = PLAYER;
        } else if (typeSt.trim().equalsIgnoreCase(ITEM.toString())) {
            type = ITEM;
        }

        return type;
    }
}
