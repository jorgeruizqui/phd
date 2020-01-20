package com.jrq.xvgdl.context.generator;

import com.jrq.xvgdl.context.GameContext;

public interface IGameContextGenerator {

    GameContext generateContext(String contextConfigFile);

}
