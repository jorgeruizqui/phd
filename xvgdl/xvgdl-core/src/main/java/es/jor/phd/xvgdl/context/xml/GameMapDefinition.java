package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.IIdentificableObject;

/**
 * Game Map XML element Definition
 * @author jrquinones
 *
 */
public class GameMapDefinition extends Properties implements IIdentificableObject {

    /** XML main tag. */
    public static final String XMLTAG = "map";

    /** XML Attribute. Type. */
    public static final String XMLATTR_TYPE = "type";

    /** XML Attribute. Size. */
    public static final String XMLATTR_SIZE = "size";

    /** XML Attribute. Toroidal. */
    public static final String XMLATTR_TOROIDAL = "toroidal";

    /** XML Attribute. Generator. */
    public static final String XMLATTR_GENERATOR = "generator";

    @Override
    public String getId() {
        return getProperty(XMLATTR_TYPE);
    }

    @Override
    public void setXMLAttr(String key, String value) {
        setProperty(key, value);
    }
}
