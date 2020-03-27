package com.jrq.xvgdl.exception;

public class XvgdlException extends Exception {

    public XvgdlException(String message) {
        super(message);
    }

    public XvgdlException(String message, Throwable e) {
        super(message, e);
    }
}
