package com.jrq.xvgdl.context.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jrq.xvgdl.exception.XvgdlException;
import com.jrq.xvgdl.util.XvgdlUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class XVGDLGenericMapper<T> {

    private final Class<T> type;

    public XVGDLGenericMapper(Class<T> parameterClass) {
        type = parameterClass;
    }

    public T parse(String fileName) throws XvgdlException {

        XmlMapper xmlMapper = getXmlMapper();
        try {
            return xmlMapper.readValue(
                    XvgdlUtils.getFileInputStream(fileName),
                    type);
        } catch (Exception e) {
            log.error("Error parsing XVGDL component file: " + e.getMessage(), e);
            throw new XvgdlException("Exception parsing XML file: " + fileName, e);
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
