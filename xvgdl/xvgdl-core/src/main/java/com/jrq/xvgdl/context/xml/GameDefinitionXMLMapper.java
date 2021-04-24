package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.util.XvgdlUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class GameDefinitionXMLMapper {

    public GameDefinition parse(String fileName) throws XvgdlException {

        XmlMapper xmlMapper = getXmlMapper();
        try {
            return xmlMapper.readValue(
                    XvgdlUtils.getFileInputStream(fileName),
                    GameDefinition.class);
        } catch (Exception e) {
            log.error("Error parsing GamneContext file: " + e.getMessage(), e);
            throw new XvgdlException("Exception parsing XML file: " + fileName, e);
        }
    }

    public void storeXml(String path, GameDefinition gameDefinition) {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            xmlMapper.writeValue(new File(path), gameDefinition);
        } catch (IOException e) {
            log.error("Trying to store GameRuleDefinition in file {}", path, e);
        }
    }

    private XmlMapper getXmlMapper() {
        JacksonXmlModule jacksonXmlModule = new JacksonXmlModule();
        jacksonXmlModule.setDefaultUseWrapper(false);

        XmlMapper xmlMapper = new XmlMapper(jacksonXmlModule);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        xmlMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        xmlMapper.configOverride(List.class).setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);

        return xmlMapper;
    }
}
