package es.jor.phd.xvgdl.context.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import es.jor.phd.xvgdl.model.rules.GameRule;
import es.jor.phd.xvgdl.model.rules.GameRuleType;
import es.jor.phd.xvgdl.model.rules.IGameRule;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Game Rule XML element Definition
 *
 * @author jrquinones
 *
 */
@Slf4j
@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "gameDefinition")
public class GameDefinition {

    @JacksonXmlProperty
    private GamePropertiesDefinition properties;

    @JacksonXmlProperty
    private GameMapDefinition map;

    @JacksonXmlElementWrapper
    private List<GamePlayerDefinition> players = new ArrayList<>();

    @JacksonXmlElementWrapper
    private List<GameObjectDefinition> objects = new ArrayList<>();

    @JacksonXmlElementWrapper
    private List<GameEventDefinition> events = new ArrayList<>();

    @JacksonXmlElementWrapper
    private List<GameRuleDefinition> rules = new ArrayList<>();

    @JacksonXmlElementWrapper
    private List<GameEndConditionDefinition> endConditions = new ArrayList<>();
}
