package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.util.xml.BasicXMLHandler;
import es.indra.eplatform.util.xml.IgnorableXMLElementParser;
import es.indra.eplatform.util.xml.XMLException;
import es.indra.eplatform.util.xml.XMLObjectParser;
import es.jor.phd.xvgdl.context.GameContext;

/**
 * XML Handler for Context configuration
 * @author jrquinones
 *
 */
public class GameContextXMLHandler extends BasicXMLHandler {

    /** MAP tag. */
    private static final String XMLTAG_GAME_DEFINITION = "gameDefinition";
    /** Objects tag. */
    private static final String XMLTAG_OBJECTS = "objects";
    /** Players tag. */
    private static final String XMLTAG_PLAYERS = "players";
    /** Avatars tag. */
    private static final String XMLTAG_AVATARS = "avatars";
    /** Physics tag. */
    private static final String XMLTAG_PHYSICS = "physics";
    /** Events tag. */
    private static final String XMLTAG_EVENTS = "events";
    /** Rules tag. */
    private static final String XMLTAG_RULES = "rules";

    /** Instance of game context. */
    private GameContext gameContext;

    /**
     * Constructor.
     * @param gameContext
     */
    public GameContextXMLHandler(GameContext gameContext) {
        super();
        this.gameContext = gameContext;

        // Ignore grouping tags.
        this.register(new IgnorableXMLElementParser(XMLTAG_GAME_DEFINITION));
        this.register(new IgnorableXMLElementParser(XMLTAG_OBJECTS));
        this.register(new IgnorableXMLElementParser(XMLTAG_PLAYERS));
        this.register(new IgnorableXMLElementParser(XMLTAG_AVATARS));
        this.register(new IgnorableXMLElementParser(XMLTAG_PHYSICS));
        this.register(new IgnorableXMLElementParser(XMLTAG_EVENTS));
        this.register(new IgnorableXMLElementParser(XMLTAG_RULES));

        this.register(new XMLObjectParser(GameMapDefinition.XMLTAG, GameMapDefinition.class));
//        this.register(new XMLObjectParser(LsupIntegrationJMXBeanDefinition.XMLTAG,
//                                          LsupIntegrationJMXBeanDefinition.class));
    }

    @Override
    public void parseResource(String resource) {

        try {
            super.parseResource(resource);
        } catch (XMLException e) {
            e.printStackTrace();
        }
    }
}
