package es.jor.phd.xvgdl.context.xml;

import es.jor.phd.xvgdl.context.GameContext;

/**
 * XML Handler for Context configuration
 *
 * @author jrquinones
 *
 */
public class GameRendererXMLHandler extends Object {

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
/*
        // Ignore grouping tags.
        this.register(new IgnorableXMLElementParser(XMLTAG_RENDERER));

        // Parsing tags
        this.register(new Properties.PropertyXMLElement());
        IGameRenderer gameRenderer = new BasicAsciiRenderer();
        gameRenderer.initializeRenderer(gameContext);
        this.gameContext.setGameRenderer(gameRenderer);

 */
    }

    public void parseResource(String resource) {
/*
        try {
            if (resource != null) {
                super.parseResource(resource);
            }
        } catch (XMLException e) {
            ELogger.error(this, GameConstants.GAME_CONTEXT_LOGGER_CATEGORY,
                    "XML Exception parsing renderer resource file.", e);
        }
        */
    }


    public void onElementFinished(String xmlTag, Object obj) {
    }
}
