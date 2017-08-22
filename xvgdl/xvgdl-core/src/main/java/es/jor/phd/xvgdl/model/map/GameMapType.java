package es.jor.phd.xvgdl.model.map;

/**
 * Map Type
 *
 * @author jrquinones
 *
 */
public enum GameMapType {
    /** 2D Map Type */
    MAP_2D,
    /** 3D Map Type */
    MAP_3D;

    /**
     *
     * @param mapTypeSt String defining map type
     * @return the Map type according to the string parameter
     */
    public static GameMapType fromString(String mapTypeSt) {
        GameMapType rto = null;
        if (mapTypeSt.trim().equalsIgnoreCase("2D") || mapTypeSt.trim().equalsIgnoreCase(MAP_2D.toString())) {
            rto = MAP_2D;
        } else if (mapTypeSt.trim().equalsIgnoreCase("3D") || mapTypeSt.trim().equalsIgnoreCase(MAP_3D.toString())) {
            rto = MAP_3D;
        }
        return rto;
    }
}
