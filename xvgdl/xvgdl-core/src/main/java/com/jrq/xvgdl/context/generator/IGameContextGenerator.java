package com.jrq.xvgdl.context.generator;

import com.jrq.xvgdl.context.GameContext;
import com.jrq.xvgdl.exception.XvgdlException;

public interface IGameContextGenerator {

    GameContext generateContext(String contextConfigFile) throws XvgdlException;

}
