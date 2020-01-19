package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class GameDefinitionXMLMapper {

    public GameDefinition parse(String fileName) {

        XmlMapper xmlMapper = getXmlMapper();
        try {
            return xmlMapper.readValue(
                    getClass().getResourceAsStream(fileName),
                    GameDefinition.class);
        } catch (IOException e) {
            log.error("Error parsing GamneContext file: " + e.getMessage(), e);
        }
        return null;
    }

    private XmlMapper getXmlMapper() {
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);

        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        return xmlMapper;
    }

}