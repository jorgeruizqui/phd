package com.jrq.xvgdl.util;

import com.jrq.xvgdl.exception.XvgdlException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utils
 *
 * @author jrquinones
 */
@Slf4j
public class XvgdlUtils {

    public static InputStream getFileInputStream(String configurationFile) throws XvgdlException {
        try {
            log.debug("Trying opening file " + configurationFile + " as FileInputStream");
            return new FileInputStream(configurationFile);
        } catch (Exception e) {
            log.debug("Previous failed. Trying opening file " + configurationFile + " as Resource Stream");
            try {
                return XvgdlUtils.class.getResourceAsStream(configurationFile);
            } catch (Exception ex) {
                throw new XvgdlException("Can't find file for opening: " + configurationFile);
            }
        }
    }
}
