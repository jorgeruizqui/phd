package es.jor.phd.xvgdl.context.xml;

import es.indra.eplatform.properties.Properties;
import es.indra.eplatform.util.xml.BasicXMLHandler;
import es.indra.eplatform.util.xml.IgnorableXMLElementParser;
import es.indra.eplatform.util.xml.XMLException;
import es.jor.phd.xvgdl.context.GameContext;
import es.jor.phd.xvgdl.renderer.BasicAsciiRenderer;
import es.jor.phd.xvgdl.renderer.IGameRenderer;

/**
 * XML Handler for Context configuration
 *
 * @author jrquinones
 *
 */
public class GameRendererXMLHandler extends BasicXMLHandler {

    /** Renderer tag. */
    private static final String XMLTAG_RENDERER = "renderer";

    /** Instance of game context. */
    private GameContext gameContext;

    /**
     * Constructor.
     *
     * @param gameContext Game Context instance
     */
    public GameRendererXMLHandler(GameContext gameContext) {
        super();
        this.gameContext = gameContext;

        // Ignore grouping tags.
        this.register(new IgnorableXMLElementParser(XMLTAG_RENDERER));

        // Parsing tags
        this.register(new Properties.PropertyXMLElement());
        IGameRenderer gameRenderer = new BasicAsciiRenderer();
        gameRenderer.initializeRenderer(gameContext);
        this.gameContext.setRenderer(gameRenderer);
    }

    @Override
    public void parseResource(String resource) {

        try {
            super.parseResource(resource);
        } catch (XMLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onElementFinished(String xmlTag, Object obj) {
    }
}
