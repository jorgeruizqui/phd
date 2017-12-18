package es.jor.phd.xvgdl.context.generator;

import es.jor.phd.xvgdl.context.GameContext;

public interface IGameContextGenerator {
    
    GameContext generateContext(String contextConfigFile);

}
